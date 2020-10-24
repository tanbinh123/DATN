package com.movies_unlimited.controller;

import com.movies_unlimited.entity.FavoriteEntity;
import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.service.FavoriteService;
import com.movies_unlimited.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;

    private final FavoriteService favoriteService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model,@RequestParam(value = "page", required = false) Integer page) {
        if (page == null || page <= 0) {
            page = 1;
        }
        Page<ProductEntity> productsPage = productService.getProductsActive(page);
        List<ProductEntity> productsList = productsPage.getContent();
        List<ProductEntity> products = new ArrayList<>();
        for (int i = 0; i < productsList.size(); i++) {
            ProductEntity product = productsList.get(i);
            List<FavoriteEntity> favs = favoriteService.getFavoritesByProductId(product.getId());
            product.setFavorites(favs);
            products.add(product);
        }
        model.addAttribute("page", productsPage.getTotalPages());
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping(value = { "/category" }, method = RequestMethod.GET)
    public String category(Model model) {
        return "category";
    }

    @RequestMapping(value = { "/checkout" }, method = RequestMethod.GET)
    public String checkout(Model model) {
        return "checkout";
    }

    @RequestMapping(value = {  "/product" }, method = RequestMethod.GET)
    public String product(Model model) {
        return "product";
    }

    @RequestMapping(value = {  "/cart" }, method = RequestMethod.GET)
    public String cart(Model model) {
        return "cart";
    }

}
