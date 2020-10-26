package com.movies_unlimited.controller;

import com.movies_unlimited.Ultil.AccountUltil;
import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.OrderEntity;
import com.movies_unlimited.service.AccountRoleService;
import com.movies_unlimited.service.AccountService;
import com.movies_unlimited.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AccountController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRoleService accountRoleService;

    @RequestMapping(value = "/update-account", method = RequestMethod.POST)
    public String updateAccount(Model model, @ModelAttribute(value = "account") AccountEntity acc) {
        AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
        account.setFullName(acc.getFullName());
        account.setAddress(acc.getAddress());
        account.setBirthday(acc.getBirthday());
        account.setPhone(acc.getPhone());
        AccountEntity checkEmail = accountService.getAccountByEmail(acc.getEmail());
        if (checkEmail == null) {
            account.setEmail(acc.getEmail());
        }
        accountService.save(account);
        model.addAttribute("messageSuccess", "Successfully updated account profile");
        model.addAttribute("account", account);
        return "user/profile";
    }

    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public String updatePassword(Model model,
                                 @ModelAttribute(value = "password") String password,
                                 @ModelAttribute(value = "newpassword1") String newpassword1,
                                 @ModelAttribute(value = "newpassword2") String newpassword2) {
        if (newpassword1.equals(newpassword2)) {
            AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
            PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(password,account.getPassword())) {
                account.setPassword(new BCryptPasswordEncoder().encode(newpassword1));
                accountService.save(account);
                model.addAttribute("messageSuccess", "Successfully updated password");
            } else {
                model.addAttribute("messageError", "Password incorrect");
            }
        } else {
            model.addAttribute("messageError", "The new password is not the same");
        }
        return "user/changepassword";
    }

    @RequestMapping(value = { "/account" }, method = RequestMethod.GET)
    public String viewAccount(Model model,
                              @RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "page", required = false) Integer page) {
        if (action == null || action.isEmpty()) {
            AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
            model.addAttribute("account", account);
            return "user/profile";
        } else if (action.equals("myorder")) {
            if (page == null || page <= 0) {
                page = 1;
            }
            AccountEntity account =  accountService.getAccountByEmail(AccountUltil.getAccount());
            Page<OrderEntity> ordersPage = orderService.getOrdersByAccountId(account.getId(), page);
            model.addAttribute("orders", ordersPage.getContent());
            model.addAttribute("page", ordersPage.getTotalPages());
            return "user/myorder";
        } else if (action.equals("changepassword")) {
            return "user/changepassword";
        } else {
            AccountEntity account = accountService.getAccountByEmail(AccountUltil.getAccount());
            model.addAttribute("account", account);
            return "user/profile";
        }
    }


}
