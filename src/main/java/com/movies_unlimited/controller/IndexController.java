package com.movies_unlimited.controller;

import com.movies_unlimited.service.AccountService;
import com.movies_unlimited.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {

    private final ProductService productService;

    @RequestMapping("/")
    public String index() {
        productService.recommendMovie();
        return "Greetings from Spring Boot!";
    }

}
