package com.movies_unlimited.controller;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.FavoriteEntity;
import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.service.AccountService;
import com.movies_unlimited.service.CategoryService;
import com.movies_unlimited.service.FavoriteService;
import com.movies_unlimited.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;

    private final FavoriteService favoriteService;

    private final CategoryService categoryService;

    private final AccountService accountService;

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

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String categoryPageLimit(Model model,
                                    @RequestParam("id") int id,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "sort", required = false) String sort) {
        if (page == null || page <= 0) {
            page = 1;
        }
        Page<ProductEntity> productsPage = null;
        if(sort==null || sort.isEmpty()){
            productsPage = productService.getProductByCategoryId(id,page,"none");
        }else{
            productsPage = productService.getProductByCategoryId(id,page,sort);
        }

        List<ProductEntity> productsList = productsPage.getContent();
        List<ProductEntity> products = new ArrayList<>();
        for (int i = 0; i < productsList.size(); i++) {
            ProductEntity product = productsList.get(i);
            List<FavoriteEntity> favs = favoriteService.getFavoritesByProductId(product.getId());
            product.setFavorites(favs);
            products.add(product);
        }
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("page", productsPage.getTotalPages());
        model.addAttribute("products", products);
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


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new AccountEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") AccountEntity userForm) {
        userForm.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        accountService.save(userForm);
        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

}
