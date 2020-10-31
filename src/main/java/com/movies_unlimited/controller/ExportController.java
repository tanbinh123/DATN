package com.movies_unlimited.controller;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.OrderDetailEntity;
import com.movies_unlimited.entity.OrderEntity;
import com.movies_unlimited.entity.PromotionEntity;
import com.movies_unlimited.service.AccountService;
import com.movies_unlimited.service.OrderService;
import com.movies_unlimited.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ExportController {
    private final OrderService orderService;
    private final AccountService accountService;

    @RequestMapping(value = "/downloadOrderExcel")
    public String downloadOrderExcel(Model model, HttpServletResponse response) throws IOException {
        List<OrderEntity> orders = orderService.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            OrderEntity order = orders.get(i);
            AccountEntity account = accountService.findAccountByOrderId(order.getId());
            order.setAccount(account);
            Set<OrderDetailEntity> orderDetails = order.getOrderDetails();
            for (OrderDetailEntity orderDetail:
                    orderDetails) {
                orderDetails.add(orderDetail);
            }
            order.setOrderDetails(orderDetails);
            orders.set(i, order);
        }
        model.addAttribute("orders", orders);
        return "excelView";
    }
}
