package com.movies_unlimited.api;

import com.movies_unlimited.Ultil.AccountUltil;
import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.FavoriteEntity;
import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.entity.RatingEntity;
import com.movies_unlimited.service.AccountService;
import com.movies_unlimited.service.FavoriteService;
import com.movies_unlimited.service.ProductService;
import com.movies_unlimited.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountAPI {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "/favorite/{productId}", method = RequestMethod.GET)
    public Object getFavoriteAPI(@PathVariable(value = "productId") int productId) {
        AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
        if (account != null) {
            FavoriteEntity fav = favoriteService.getFavotiteByAccountIDAndProductID(account.getId(), productId);
            if (fav == null) {
                fav = new FavoriteEntity();
                fav.setAccount(account);
                ProductEntity product = productService.getProductById(productId);
                fav.setProduct(product);
                FavoriteEntity favsaved = favoriteService.save(fav);
                if (favsaved != null && favsaved.getId() > 0) {
                    return "favorited";
                }
            } else {
                fav.setAccount(account);
                ProductEntity product = productService.getProductById(productId);
                fav.setProduct(product);
                favoriteService.delete(fav);
                return "unfavorited";
            }
        }
        return "login";
    }

    @RequestMapping(value = "/rating/{productId}/{rating}", method = RequestMethod.GET)
    public Object ratingProductAPI(@PathVariable(value = "productId") int productId,
                          @PathVariable(value = "rating") int rating) {
        AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
        if (rating<=0 && rating>5){
            return "error";
        }
        if (account != null) {
            RatingEntity rate = ratingService.getRatingByAccountIDAndProductID(account.getId(), productId);
            if (rate == null) {
                rate = new RatingEntity();
                rate.setAccount(account);
                ProductEntity product = productService.getProductById(productId);
                rate.setProduct(product);
                rate.setRating(rating);
                RatingEntity rateSaved = ratingService.save(rate);
                if (rateSaved != null && rateSaved.getId() > 0) {
                    return "rated";
                }
            } else {
                rate.setRating(rating);
                RatingEntity rateSaved = ratingService.save(rate);
                if (rateSaved != null && rateSaved.getId() > 0) {
                    return "rated";
                }
            }
        }
        return "login";
    }

    @RequestMapping(value = "/get-rating/{productId}", method = RequestMethod.GET)
    public Object getRatingAPI(@PathVariable(value = "productId") int productId) {
        AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
        if (account != null) {
            RatingEntity rate = ratingService.getRatingByAccountIDAndProductID(account.getId(), productId);
            if (rate == null) {
               return 0;
            } else {
                return rate.getRating();
            }
        }
        return "login";
    }
}
