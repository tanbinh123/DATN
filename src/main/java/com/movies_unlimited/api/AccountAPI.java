/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movies_unlimited.api;

import com.movies_unlimited.Ultil.AccountUltil;
import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.FavoriteEntity;
import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.service.AccountService;
import com.movies_unlimited.service.FavoriteService;
import com.movies_unlimited.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author taing
 */
@RestController
@RequestMapping("/api")
public class AccountAPI {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ProductService productService;


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/favorite/{productId}", method = RequestMethod.GET)
    public Object getBook(@PathVariable(value = "productId") int productId) {
        AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
        if (account != null) {
            FavoriteEntity fav = favoriteService.getFavotiteByAccountIDAndProductID(account.getId(), productId);
            if (fav == null) {                             
                fav = new FavoriteEntity();
                fav.setAccount(account);
                ProductEntity product = productService.getProductById(productId);
                fav.setProduct(product);
                FavoriteEntity favsaved =  favoriteService.save(fav);
                if(favsaved!=null && favsaved.getId()>0){
                    return "favorited";
                }                
            }else{
                fav.setAccount(account);
                ProductEntity product = productService.getProductById(productId);
                fav.setProduct(product);
                favoriteService.delete(fav);
                return "unfavorited";
            }
        }
        return "login";
    }
}
