package com.movies_unlimited.controller;

import com.movies_unlimited.Ultil.AccountUltil;
import com.movies_unlimited.Ultil.StringUltil;
import com.movies_unlimited.entity.*;
import com.movies_unlimited.entity.enums.OrderStatus;
import com.movies_unlimited.repository.AccountRepository;
import com.movies_unlimited.service.OrderDetailService;
import com.movies_unlimited.service.OrderService;
import com.movies_unlimited.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final AccountRepository accountRepository;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cartPage(Model model) {
        return "cart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkoutPage(Model model, HttpSession session) {
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        if (order == null) {
            return "redirect:/cart";
        }
        AccountEntity account = accountRepository.findAccountByEmail(AccountUltil.getAccount());
        if (account != null) {
            model.addAttribute("account", account);
        }
        return "checkout";
    }

    @RequestMapping(value = "/checkout-process", method = RequestMethod.POST)
    public String checkoutProcess(Model model,
                                  HttpSession session,
                                  @ModelAttribute(value = "shipping") ShippingEntity shipping) {

        OrderEntity order = (OrderEntity) session.getAttribute("order");
        if (order == null) {
            return "redirect:/cart";
        }
        if (order.getOrderDetails().isEmpty()) {
            return "redirect:/cart";
        }
        order.setShipping(shipping);
        AccountEntity account = accountRepository.findAccountByEmail(AccountUltil.getAccount());
        if (account != null) {
            order.setAccount(account);
        }
        order.setOrderDate(new Date());
        order.setTotalPrice(order.getTotal());
        OrderEntity ordersaved = orderService.saveOrder(order);
        if (ordersaved != null && ordersaved.getId() > 0) {
            boolean saveOk = true;
            for (OrderDetailEntity orderDetai : order.getOrderDetails()) {
                orderDetai.setOrder(ordersaved);
                OrderDetailEntity orderDetailSaved = orderDetailService.save(orderDetai);
                if (orderDetailSaved == null || orderDetailSaved.getId() < 1) {
                    saveOk = false;
                    break;
                }
            }
            if (saveOk) {
                session.removeAttribute("order");
                String host = "http://localhost:8080/JV27_Spring_Project_Final";
                String content = "<html>\n"
                        + "   <head>\n"
                        + "      <style>\n"
                        + "         *{\n"
                        + "         box-sizing: border-box;\n"
                        + "         }\n"
                        + "		 body{\n"
                        + "			margin: 0 auto;\n"
                        + "		 }\n"
                        + "         .table-footer{\n"
                        + "         font-weight: bold;\n"
                        + "         }\n"
                        + "         .order-info{\n"
                        + "         text-align: center;\n"
                        + "		 margin-bottom: 2em;\n"
                        + "         }\n"
                        + "         .order-info p{\n"
                        + "         color: #000;\n"
                        + "         font-weight: bold;\n"
                        + "         margin-bottom: 1em;\n"
                        + "         }\n"
                        + "         .table{\n"
                        + "         width: 90%;\n"
                        + "         max-width: 90%;\n"
                        + "         margin-bottom: 1rem;\n"
                        + "         background-color: transparent;\n"
                        + "         border-collapse: collapse;\n"
                        + "         display: table;\n"
                        + "         border-spacing: 2px;\n"
                        + "         border-color: grey;\n"
                        + "		 text-align: left;\n"
                        + "		 margin-left: 2em;\n"
                        + "		 margin-right: 2em;\n"
                        + "         }\n"
                        + "         .table tr {\n"
                        + "         display: table-row;\n"
                        + "         vertical-align: inherit;\n"
                        + "         border-color: inherit;\n"
                        + "         }\n"
                        + "         .table thead th {\n"
                        + "         vertical-align: bottom;\n"
                        + "         border-bottom: 2px solid #dee2e6;\n"
                        + "         }\n"
                        + "         .table td, .table th {\n"
                        + "         padding: .75rem;\n"
                        + "         vertical-align: top;\n"
                        + "         border-top: 1px solid #dee2e6;\n"
                        + "         }\n"
                        + "         tbody {\n"
                        + "         display: table-row-group;\n"
                        + "         vertical-align: middle;\n"
                        + "         border-color: inherit;\n"
                        + "         }\n"
                        + "      </style>\n"
                        + "   </head>\n"
                        + "   <body>\n"
                        + "      <div class=\"order-info\">\n"
                        + "         <p>Order Id: #" + order.getId() + "</p>\n"
                        + "         <p>Order Date: " + StringUltil.fromDateToUS(order.getOrderDate()) + "</p>\n"
                        + "         <p>Order Status: " + order.getStatus() + "</p>\n"
                        + "		 <a href=\"" + host + "/order-detail?id=" + order.getId() + "&email=" + shipping.getEmail() + "\">Click here to view order details</a>\n"
                        + "      </div>\n"
                        + "      <table class=\"table\">\n"
                        + "         <thead>\n"
                        + "            <tr>\n"
                        + "               <th>Product</th>\n"
                        + "               <th>Size</th>\n"
                        + "               <th>Price</th>\n"
                        + "               <th>Quantity</th>\n"
                        + "               <th>Total</th>\n"
                        + "            </tr>\n"
                        + "         </thead>\n"
                        + "         <tbody>";
                for (OrderDetailEntity od : order.getOrderDetails()) {
                    content += "<tr>\n"
                            + "               <td><a href=\"" + host + "/product?id=" + od.getProduct().getId() + "\">" + od.getProduct().getName() + "</a></td>\n"
                            + "               <td>" + od.getProduct().getPrice() + "</td>\n"
                            + "               <td>" + od.getQuantity() + "</td>\n"
                            + "               <td>" + od.getTotal() + "</td>\n"
                            + "            </tr>";
                }
                content += "<tr class=\"table-footer\">\n"
                        + "               <td colspan=\"4\">Total</td>\n"
                        + "               <td>" + order.getTotal() + "</td>\n"
                        + "            </tr>\n"
                        + "         </tbody>\n"
                        + "      </table>\n"
                        + "	  \n"
                        + "   </body>\n"
                        + "</html>";
                model.addAttribute("id", order.getId());
                model.addAttribute("email", shipping.getEmail());
                model.addAttribute("messageSuccess", "Thank You For Your Purchase");
            } else {
                model.addAttribute("messageError", "Sorry, the order failed");
            }

        } else {
            model.addAttribute("messageError", "Sorry, the order failed");
        }

        return "checkoutStatus";
    }

    @RequestMapping(value = "/order/{productId}", method = RequestMethod.GET)
    public String orderProduct(HttpSession session, @PathVariable(value = "productId") int productId) {
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        if (order == null) {
            order = new OrderEntity();

            Set<OrderDetailEntity> orderDetails = new HashSet<>();

            ProductEntity product = productService.getProductById(productId);
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(1);

            orderDetails.add(orderDetail);
            order.setOrderDetails(orderDetails);
            session.setAttribute("order", order);
        } else {
            Set<OrderDetailEntity> orderDetails = order.getOrderDetails();
            boolean isUpdate = false;
            for (OrderDetailEntity orderDetail : orderDetails) {
                if (orderDetail.getProduct().getId() == productId) {
                    int quantity = orderDetail.getQuantity() + 1;
                    orderDetail.setQuantity(quantity);
                    isUpdate = true;
                    break;
                }
            }
            if (!isUpdate) {
                ProductEntity product = productService.getProductById(productId);
                OrderDetailEntity orderDetail = new OrderDetailEntity();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(1);
                orderDetails.add(orderDetail);
            }
            order.setOrderDetails(orderDetails);
            session.setAttribute("order", order);
        }
        return "redirect:/cart";
    }

    @RequestMapping("/order/delete")
    public String deleteOrder(HttpSession session,
                              @RequestParam(value = "productId") int productId) {
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        Set<OrderDetailEntity> orderDetails = order.getOrderDetails();
        for (OrderDetailEntity orderDetail : orderDetails) {
            if (orderDetail.getProduct().getId() == productId) {
                orderDetails.remove(orderDetail);
                break;
            }
        }
        order.setOrderDetails(orderDetails);
        session.setAttribute("order", order);
        return "redirect:/cart";
    }

    @RequestMapping("/order/cancel")
    public String guestCancelOrder(@RequestParam(value = "id") int orderId,
                                   @RequestParam(value = "email") String email,
                                   @RequestHeader(value = "Referer") String referer) {
        OrderEntity order = orderService.getOrderByIdAndEmail(orderId, email);
        if (order == null) {
            return "redirect:" + referer;
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderService.saveOrder(order);
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/order/update")
    public String updateOrder(HttpSession session,
                              @RequestParam(value = "productId") int productId,
                              @RequestParam(value = "quantity") int quantity) {
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        Set<OrderDetailEntity> orderDetails = order.getOrderDetails();
        for (OrderDetailEntity orderDetail : orderDetails) {
            if (orderDetail.getProduct().getId() == productId) {
                if (quantity <= 0) {
                    quantity = 1;
                }
                orderDetail.setQuantity(quantity);
                break;
            }
        }
        order.setOrderDetails(orderDetails);
        session.setAttribute("order", order);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/order/clear")
    public String clearOrder(HttpSession session) {
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        if (order != null) {
            session.removeAttribute("order");
        }
        return "redirect:/cart";
    }

    @RequestMapping(value = "/order/cancel/{id}")
    public String cancelOrder(Model model, @PathVariable(value = "id") int id) {
        AccountEntity account = accountRepository.findAccountByEmail(AccountUltil.getAccount());
        List<OrderEntity> orders = orderService.getOrdersByAccountId(account.getId());
        for (int i = 0; i < orders.size(); i++) {
            OrderEntity order = orders.get(i);
            if (order.getId() == id) {
                order.setStatus(OrderStatus.CANCELLED);
                orderService.saveOrder(order);
                break;
            }
        }
        return "redirect:/account?action=myorder";
    }

    @RequestMapping(value = "/apply-promotion", method = RequestMethod.POST)
    public String applyPromotion(Model model,
                                 HttpSession session,
                                 @ModelAttribute(value = "promotion_name") String promotion_name) {
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        if (order == null) {
            return "redirect:/cart";
        }
        boolean isPromotionApply = false;
        Set<OrderDetailEntity> orderDetails = order.getOrderDetails();
        for (OrderDetailEntity orderDetail :
                orderDetails) {
            orderDetails.add(orderDetail);
        }
        order.setTotalPrice(order.getTotal());
        order.setOrderDetails(orderDetails);
        session.setAttribute("order", order);
        if (isPromotionApply) {
            model.addAttribute("messageSuccess", "Successfully added promotion");
        } else {
            model.addAttribute("messageError", "Invalid promotional code");
        }
        return "cart";
    }

    @RequestMapping(value = "/remove-promotion")
    public String removePromotion(HttpSession session,
                                  @RequestParam(value = "id") int id) {
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        if (order != null) {
            Set<OrderDetailEntity> orderDetails = order.getOrderDetails();
            order.setOrderDetails(orderDetails);
            session.setAttribute("order", order);
        }
        return "redirect:/cart";
    }

    @RequestMapping(value = "/order-detail")
    public String viewOrderDetail(Model model,
                                  @RequestParam(value = "id") int id,
                                  @RequestParam(value = "email") String email) {
        OrderEntity order = orderService.getOrderById(id);
        if (!order.getShipping().getEmail().equals(email)) {
            return "redirect:/home";
        }
        model.addAttribute("vieworder", order);
        return "order-detail";
    }
}
