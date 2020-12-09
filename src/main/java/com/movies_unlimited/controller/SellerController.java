package com.movies_unlimited.controller;

import com.movies_unlimited.entity.*;
import com.movies_unlimited.entity.enums.ActiveStatus;
import com.movies_unlimited.entity.enums.OrderStatus;
import com.movies_unlimited.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellerController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderService orderService;
    private final AccountService accountService;
    private final PromotionService promotionService;

    @RequestMapping("/seller")
    public String viewSeller(Model model,
                             @RequestParam(value = "action", required = false) String action,
                             @RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "page", required = false) Integer page) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (action == null || action.isEmpty()) {
            return "redirect:/account";
        } else if (action.equals("order-manager")) {
            Page<OrderEntity> ordersPage = orderService.getOrders(page);
            List<OrderEntity> orders = new ArrayList<>(ordersPage.getContent());
            for (int i = 0; i < orders.size(); i++) {
                OrderEntity order = orders.get(i);
                AccountEntity account = accountService.findAccountByOrderId(order.getId());
                order.setAccount(account);
                orders.set(i, order);
            }
            model.addAttribute("orders", orders);
            model.addAttribute("page", ordersPage.getTotalPages());
            return "seller/order-manager";
        } else if (action.equals("update-order")) {
            OrderEntity order = orderService.getOrderById(id);
            AccountEntity account = accountService.findAccountByOrderId(id);
            order.setAccount(account);
            model.addAttribute("orderSaved", order);
            model.addAttribute("status", OrderStatus.values());
            return "seller/update-order";
        } else if (action.equals("product-manager")) {
            Page<ProductEntity> productsPage = productService.getProducts(page);
            model.addAttribute("products", productsPage.getContent());
            model.addAttribute("page", productsPage.getTotalPages());
            return "seller/product-manager";
        } else if (action.equals("promo-manager")) {
            Page<PromotionEntity> promosPage = promotionService.getPromotions(page);
            List<PromotionEntity> promos = new ArrayList<>(promosPage.getContent());
            model.addAttribute("promos", promos);
            model.addAttribute("page", promosPage.getTotalPages());
            return "seller/promo-manager";
        } else if (action.equals("add-product")) {
            ProductEntity product = new ProductEntity();
            model.addAttribute("product", product);
            model.addAttribute("categorys", categoryService.getCategorys());
            return "seller/add-product";
        } else if (action.equals("edit-product")) {
            if (id == null || id <= 0) {
                return "redirect:/seller?action=product-manager";
            }
            model.addAttribute("categories", categoryService.getCategorys());
            model.addAttribute("status", ActiveStatus.values());
            List<CategoryEntity> allCategory = categoryService.getCategorys();
            List<CategoryEntity> productCategory = categoryService.getCategoryByProductId(id);
            List<Boolean> categoryBoolean = new ArrayList<>();

            for (CategoryEntity size : allCategory) {
                boolean isSizeChecked = false;
                for (CategoryEntity s : productCategory) {
                    if (s.getId() == size.getId()) {
                        categoryBoolean.add(true);
                        isSizeChecked = true;
                        break;
                    }
                }
                if (!isSizeChecked) {
                    categoryBoolean.add(false);
                }
            }
            model.addAttribute("categoryBoolean", categoryBoolean);


            ProductEntity product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "seller/edit-product";
        } else if (action.equals("add-promo")) {
            model.addAttribute("products", productService.getProductsActive());
            return "seller/add-promo";
        } else if (action.equals("edit-promo")) {
            if (id == null || id <= 0) {
                return "redirect:/seller?action=promo-manager";
            }
            List<ProductEntity> productApplys = productService.getProductsByPromotionId(id);
            List<ProductEntity> products = productService.getProducts();
            List<Boolean> productsBoolean = new ArrayList<>();
            for (ProductEntity p : products) {
                boolean isApply = false;
                for (ProductEntity pa : productApplys) {
                    if (pa.getId() == p.getId()) {
                        productsBoolean.add(true);
                        isApply = true;
                        break;
                    }
                }
                if (!isApply) {
                    productsBoolean.add(false);
                }
            }
            model.addAttribute("productsBoolean", productsBoolean);
            model.addAttribute("products", products);
            model.addAttribute("promotion", promotionService.getPromotionById(id));
            model.addAttribute("status", ActiveStatus.values());
            return "seller/edit-promo";
        } else {
            return "redirect:/account";
        }
    }

    @RequestMapping(value = "/seller/update-order", method = RequestMethod.POST)
    public String updateOrder(Model model, @ModelAttribute(value = "status") String status, @ModelAttribute(value = "id") int id) {
        OrderStatus _status = OrderStatus.valueOf(status.toUpperCase());
        OrderEntity order = orderService.getOrderById(id);
        order.setStatus(_status);
        AccountEntity account = accountService.findAccountByOrderId(id);
        order.setAccount(account);
        OrderEntity orderUpdated = orderService.saveOrder(order);
        if (orderUpdated != null && orderUpdated.getId() > 0) {
            model.addAttribute("messageSuccess", "Successfully updated order");
        } else {
            model.addAttribute("messageError", "Update failed");
        }
        model.addAttribute("orderSaved", order);
        model.addAttribute("status", OrderStatus.values());
        return "seller/update-order";
    }

    @RequestMapping(value = "/seller/add-product", method = RequestMethod.POST)
    public String addProduct(Model model,
                             @ModelAttribute(value = "product") ProductEntity product) {
        ProductEntity productEntity = product;
        productEntity.setDate(new Date());
        productEntity.setStatus(ActiveStatus.ACTIVE);
        ProductEntity productEntitySave = productService.saveProduct(productEntity);
        model.addAttribute("messageSuccess", "Successfully added products");
        model.addAttribute("categorys", categoryService.getCategorys());
        return "seller/add-product";
    }

    @RequestMapping(value = "/seller/edit-product", method = RequestMethod.POST)
    public String editProduct(Model model,
                              @RequestParam(value = "category") List<Integer> category,
                              @ModelAttribute(value = "product") ProductEntity product,
                              @ModelAttribute(value = "statusradio") String status) {
        if (category.size() > 0) {

            List<CategoryEntity> categories = new ArrayList<>();
            for (int i = 0; i < category.size(); i++) {
                CategoryEntity sizeEntity = categoryService.getCategoryById(category.get(i));
                categories.add(sizeEntity);
            }

            ProductEntity productEntity = productService.getProductById(product.getId());
            productEntity.setName(product.getName());
            productEntity.setCategories(product.getCategories());
            productEntity.setDescription(product.getDescription());
            productEntity.setPrice(product.getPrice());
            productEntity.setCategories(categories);
            productEntity.setImage(product.getImage());
            productEntity.setStatus(ActiveStatus.valueOf(status.toUpperCase()));

            ProductEntity productEntitySave = productService.saveProduct(productEntity);
            if (productEntitySave != null && productEntitySave.getId() > 0) {
                model.addAttribute("messageSuccess", "Successfully updated product");
            } else {
                model.addAttribute("messageError", "Update product failed");
            }
        } else {
            model.addAttribute("messageError", "No category selected");
        }
        return "redirect:/seller?action=edit-product&id=" + product.getId();
    }

    @RequestMapping(value = "/seller/add-promo", method = RequestMethod.POST)
    public String addPromo(Model model,
                           @ModelAttribute(value = "promotion") PromotionEntity promo,
                           @RequestParam(value = "product") List<Integer> product) {
        if (promo.getEndDate().before(promo.getStartDate())) {
            model.addAttribute("messageError", "Please select End date >= Start Date");
            model.addAttribute("products", productService.getProductsActive());
            return "seller/add-promo";
        }
        if (promo.getDiscount() <= 0) {
            model.addAttribute("messageError", "Please set discount > 0%");
            model.addAttribute("products", productService.getProductsActive());
            return "seller/add-promo";
        }
        if (product.isEmpty()) {
            model.addAttribute("messageError", "Please select product");
            model.addAttribute("products", productService.getProductsActive());
            return "seller/add-promo";
        }
        PromotionEntity promoCheck = promotionService.getPromotionByName(promo.getName());
        if (promoCheck != null) {
            model.addAttribute("messageError", "This promotion name already exists");
            model.addAttribute("products", productService.getProductsActive());
            return "seller/add-promo";
        }
        List<ProductEntity> products = new ArrayList<>();
        for (Integer p : product) {
            products.add(productService.getProductById(p));
        }
        promo.setProducts(products);
        PromotionEntity promoSaved = promotionService.save(promo);
        if (promoSaved != null && promoSaved.getId() > 0) {
            model.addAttribute("messageSuccess", "Successfully added promotion");
        } else {
            model.addAttribute("messageError", "Add failed");
        }
        model.addAttribute("products", productService.getProducts());
        return "seller/add-promo";
    }

    @RequestMapping(value = "/seller/edit-promo", method = RequestMethod.POST)
    public String editPromo(Model model,
                            @ModelAttribute(value = "promotion") PromotionEntity promo,
                            @RequestParam(value = "product") List<Integer> productsId) {
        if (promo.getEndDate().before(promo.getStartDate())) {
            model.addAttribute("messageError", "Please select End date > Start Date");
        } else {
            if (promo.getDiscount() <= 0) {
                model.addAttribute("messageError", "Please set discount > 0%");
            } else {
                if (productsId.isEmpty()) {
                    model.addAttribute("messageError", "Please select product");
                } else {
                    PromotionEntity promoCheck = promotionService.getPromotionByName(promo.getName());
                    if (promoCheck == null) {
                        model.addAttribute("messageError", "Please don't change promotion name");
                    } else {
                        List<ProductEntity> products = new ArrayList<>();
                        for (int i : productsId) {
                            products.add(productService.getProductById(i));
                        }
                        promo.setProducts(products);
                        PromotionEntity promoSaved = promotionService.save(promo);
                        if (promoSaved != null && promoSaved.getId() > 0) {
                            model.addAttribute("messageSuccess", "Successfully updated promotion");
                        } else {
                            model.addAttribute("messageError", "Update failed");
                        }
                    }
                }
            }
        }
        return "redirect:/seller?action=edit-promo&id=" + promo.getId();
    }
}
