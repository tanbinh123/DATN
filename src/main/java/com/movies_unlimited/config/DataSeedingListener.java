package com.movies_unlimited.config;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.AccountRoleEntity;
import com.movies_unlimited.entity.CategoryEntity;
import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.entity.enums.ActiveStatus;
import com.movies_unlimited.entity.enums.CategoryEnum;
import com.movies_unlimited.entity.enums.Role;
import com.movies_unlimited.repository.AccountRepository;
import com.movies_unlimited.repository.AccountRoleRepository;
import com.movies_unlimited.repository.CategoryRepository;
import com.movies_unlimited.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private void addRoleIfMissing(Role role) {
        if (accountRoleRepository.findByName(role.toString()) == null) {
            AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
            accountRoleEntity.setName(role.toString());
            accountRoleRepository.save(accountRoleEntity);
        }
    }

    private void addUserIfMissing(String email, String password, Role... roles) {
        System.out.println(accountRepository.findByEmail(email).isPresent());
        if (!accountRepository.findByEmail(email).isPresent()) {

            Set<AccountRoleEntity> roleIsExists = new HashSet<>();
            for (Role role : roles) {
                roleIsExists.add(accountRoleRepository.findByName(role.toString()));
            }
            accountRepository.save(AccountEntity
                    .builder()
                    .email(email)
                    .password(new BCryptPasswordEncoder().encode(password))
                    .address("Quang Nam")
                    .accountRoles(roleIsExists)
                    .fullName("Le Cong Trinh")
                    .phone("0774093482")
                    .birthday(new Date())
                    .status(ActiveStatus.ACTIVE)
                    .build());
        }
    }

    private void addCategoryIfMissing(CategoryEnum category) {
        if (categoryRepository.findByName(category.toString()) == null) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setName(category.toString());
            categoryRepository.save(categoryEntity);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {





//        addRoleIfMissing(Role.ROLE_ADMIN);
//        addRoleIfMissing(Role.ROLE_USER);
//        addUserIfMissing("congtrinh2404@gmail.com", "123456789aaA", Role.ROLE_USER);
//        addUserIfMissing("congtrinhadmin2404@gmail.com", "123456789aaA", Role.ROLE_USER, Role.ROLE_ADMIN);
//
//        for (int i = 0; i < 15; ++i) {
//            String username = "congtrinh" + i + "@gmail.com";
//            addUserIfMissing(username, "123456789aaA", Role.ROLE_ADMIN, Role.ROLE_USER);
//        }
//
//        for (CategoryEnum category : CategoryEnum.values()) {
//            addCategoryIfMissing(category);
//        }
        readFileMovie();
    }



    public void readFileMovie() {
        String filename = "C:\\Users\\USER\\Desktop\\IT\\DATN\\movies_unlimited\\src\\main\\java\\com\\movies_unlimited\\data\\ml-data\\u.item";
        List<ProductEntity> productEntities = new ArrayList<>();
        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while (line != null) {
                ProductEntity productEntity = new ProductEntity();
                String[] splitLine = line.split("\\|");
                productEntity.setId(Integer.parseInt(splitLine[0]));
                productEntity.setName(splitLine[1]);
                System.out.println(splitLine[2]);

                line = br.readLine();
                productEntities.add(productEntity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        productRepository.saveAll(productEntities);
    }

}
