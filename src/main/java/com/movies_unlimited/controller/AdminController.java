package com.movies_unlimited.controller;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.AccountRoleEntity;
import com.movies_unlimited.entity.enums.ActiveStatus;
import com.movies_unlimited.entity.enums.Role;
import com.movies_unlimited.service.AccountRoleService;
import com.movies_unlimited.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String viewAdmin(Model model,
                            @RequestParam(value = "action", required = false) String action,
                            @RequestParam(value = "page", required = false) Integer page) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (action.equals("account-manager")) {
            Page<AccountEntity> accountsPage = accountService.getAccounts(page);
            model.addAttribute("accounts", accountsPage.getContent());
            model.addAttribute("page", accountsPage.getTotalPages());
            return "admin/account-manager";
        } else if (action.equals("add-account")) {
            model.addAttribute("roles", accountRoleService.getAccountRoles());
            return "admin/add-account";
        } else {
            return "redirect:/account";
        }
    }

    @RequestMapping(value = {"/admin/edit-account"}, method = RequestMethod.GET)
    public String viewPageAccount(Model model, @RequestParam(value = "id") int id) {
        AccountEntity account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        model.addAttribute("roles", accountRoleService.getAccountRoles());
        model.addAttribute("activeStatus", ActiveStatus.values());
        return "admin/viewaccount";
    }

    @RequestMapping(value = "/admin/add-account", method = RequestMethod.POST)
    public String addAccount(Model model,
                             @ModelAttribute(value = "account") AccountEntity account,
                             @RequestParam(value = "roles", required=false) List<Integer> roles) {
        AccountEntity checkEmail = accountService.getAccountByEmail(account.getEmail());
        if (roles==null){
            roles = new ArrayList<>();
            roles.add(1);
        }
        if (checkEmail == null) {
            Set<AccountRoleEntity> accountRoles = new HashSet<>();
            for (int roleId : roles) {
                accountRoles.add(accountRoleService.getAccountRolesById(roleId));
            }
            account.setAccountRoles(accountRoles);
            account.setStatus(ActiveStatus.ACTIVE);
            account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
            AccountEntity accountSaved = accountService.save(account);
            if (accountSaved != null && accountSaved.getId() > 0) {
                model.addAttribute("messageSuccess", "Successful add account");
            } else {
                model.addAttribute("messageError", "Error add account");
            }
        } else {
            model.addAttribute("messageError", "This email address already exists");
        }


        model.addAttribute("roles", accountRoleService.getAccountRoles());
        return "admin/add-account";
    }

    @RequestMapping(value = "/admin/update-account", method = RequestMethod.POST)
    public String viewUpdateAccount(Model model,
                                    @ModelAttribute(value = "account") AccountEntity acc,
                                    @RequestParam(value = "roles", required=false) List<Integer> roles,
                                    @ModelAttribute(value = "statusradio") String status) {
        AccountEntity account = accountService.getAccountById(acc.getId());
        if(roles!=null){
            account.setFullName(acc.getFullName());
            account.setAddress(acc.getAddress());
            account.setBirthday(acc.getBirthday());
            account.setEmail(acc.getEmail());
            account.setPhone(acc.getPhone());
            account.setStatus(ActiveStatus.valueOf(status));
            Set<AccountRoleEntity> accountRoles = new HashSet<>();
            for (int roleId : roles) {
                accountRoles.add(accountRoleService.getAccountRolesById(roleId));
            }
            account.setAccountRoles(accountRoles);
            AccountEntity accountupdated = accountService.save(account);

            if (accountupdated != null && accountupdated.getId() > 0) {
                model.addAttribute("account", accountupdated);
                model.addAttribute("messageSuccess", "Successful account update");
            } else {
                model.addAttribute("account", account);
                model.addAttribute("messageError", "Update failed");
            }
        }
        else {
            model.addAttribute("account", account);
            model.addAttribute("messageError", "Update failed");
        }
        model.addAttribute("roles", accountRoleService.getAccountRoles());
        model.addAttribute("activeStatus", ActiveStatus.values());
        return "admin/viewaccount";
    }
}
