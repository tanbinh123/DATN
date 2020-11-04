package com.movies_unlimited.controller;

import com.movies_unlimited.Ultil.AccountUltil;
import com.movies_unlimited.entity.*;
import com.movies_unlimited.repository.AccountRepository;
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
public class FunctionController {
    private final ProductService productService;
    private final AccountService accountService;
    private final CommentService commentService;
    private final OrderService orderService;
    private final FavoriteService favoriteService;
    private final AccountRepository accountRepository;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model,
                         @RequestParam(value = "searchText") String searchText,
                         @RequestParam(value = "action") String action,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "sort", required = false) String sort) {
        if (page == null || page <= 0) {
            page = 1;
        }
        int id;
        try {
            id = Integer.valueOf(searchText);
        } catch (NumberFormatException e) {
            id = 0;
        }
        if (action.equals("searchProduct")) {
            Page<ProductEntity> productsPage;
            if (sort == null || sort.isEmpty()) {
                sort = "none";
            }

            if (searchText == null || searchText.isEmpty()) {
                productsPage = productService.getProductsActive(page, sort);
            } else {
                productsPage = productService.getProductByAnyActive(searchText, page, sort);
            }
            List<ProductEntity> products = new ArrayList<>(productsPage.getContent());
            for (int i = 0; i < products.size(); i++) {
                ProductEntity product = products.get(i);
                List<FavoriteEntity> favs = favoriteService.getFavoritesByProductId(product.getId());
                product.setFavorites(favs);
                products.set(i, product);
            }
            model.addAttribute("page", productsPage.getTotalPages());
            model.addAttribute("products", products);
            return "home";
        } else if (action.equals("searchAccount")) {
            Page<AccountEntity> accountsPage = accountService.searchAccounts(searchText, page);
            model.addAttribute("accounts", accountsPage.getContent());
            model.addAttribute("page", accountsPage.getTotalPages());
            return "admin/account-manager";
        } else if (action.equals("searchProductSeller")) {
            boolean searchId = false;
            if (id > 0) {
                List<ProductEntity> products = new ArrayList<>();
                ProductEntity product = productService.getProductById(id);
                if (product != null) {
                    products.add(product);
                    searchId = true;
                }
                model.addAttribute("products", products);
            }
            if (!searchId) {
                Page<ProductEntity> productsPage = productService.getProductByAny(searchText, page);
                List<ProductEntity> products = new ArrayList<>(productsPage.getContent());
                model.addAttribute("products", products);
                model.addAttribute("page", productsPage.getTotalPages());
            }

            return "seller/product-manager";
        } else if (action.equals("searchOrderSeller")) {
            boolean searchId = false;
            if (id > 0) {
                List<OrderEntity> orders = new ArrayList<>();
                OrderEntity order = orderService.getOrderById(id);
                if (order != null) {
                    AccountEntity account = accountService.findAccountByOrderId(order.getId());
                    order.setAccount(account);
                    orders.add(order);
                    searchId = true;
                }
                model.addAttribute("orders", orders);
            }
            if (!searchId) {
                Page<OrderEntity> ordersPage = orderService.getOrdersByAny(searchText, page);
                List<OrderEntity> orders = new ArrayList<>(ordersPage.getContent());
                for (int i = 0; i < orders.size(); i++) {
                    OrderEntity order = orders.get(i);
                    AccountEntity account = accountService.findAccountByOrderId(order.getId());
                    order.setAccount(account);
                    orders.set(i, order);
                }
                model.addAttribute("orders", orders);
                model.addAttribute("page", ordersPage.getTotalPages());
            }
            return "seller/order-manager";
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String comment(@ModelAttribute(value = "content") String content,
                          @ModelAttribute(value = "productId") int productId) {
        AccountEntity account = accountRepository.findAccountByEmail(AccountUltil.getAccount());
        if (account != null) {
            ProductEntity product = productService.getProductById(productId);
            CommentEntity comment = new CommentEntity();
            comment.setContent(content);
            comment.setCommentDate(new Date());
            comment.setAccount(account);
            comment.setProduct(product);
            commentService.save(comment);
        }
        return "redirect:/product?id=" + productId;
    }
}
