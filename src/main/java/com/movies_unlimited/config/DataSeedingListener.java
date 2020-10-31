package com.movies_unlimited.config;

import com.movies_unlimited.entity.*;
import com.movies_unlimited.entity.enums.ActiveStatus;
import com.movies_unlimited.entity.enums.CategoryEnum;
import com.movies_unlimited.entity.enums.Role;
import com.movies_unlimited.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Autowired
    private RatingRepository ratingRepository;

    private void addRoleIfMissing(Role role) {
        if (accountRoleRepository.findByName(role.toString()) == null) {
            AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
            accountRoleEntity.setName(role.toString());
            accountRoleRepository.save(accountRoleEntity);
        }
    }

    private void addUserIfMissing(String email, String password, Role... roles) {
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

    public void readFileMovie() {
        String filename = new File("src/main/java/com/movies_unlimited/data/ml-data/u.item").getAbsolutePath();
        List<ProductEntity> productEntities = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while (line != null) {
                ProductEntity productEntity = new ProductEntity();
                String[] splitLine = line.split("\\|");
                productEntity.setId(Integer.parseInt(splitLine[0]));
                productEntity.setName(splitLine[1]);
                DateFormat format = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
                Date date = format.parse(splitLine[2]);
                productEntity.setDate(date);
                Random r = new Random();
                productEntity.setPrice(Math.round(20 + (50 - 20) * r.nextDouble() * 100.0) / 100.0);
                List<CategoryEntity> categoryEntities = new ArrayList<>();
                for (int i = 5; i <= 23; i++) {
                    if (splitLine[i].equals("1")) {
                        categoryEntities.add(categoryRepository.findById(i - 4));
                    }
                }
                productEntity.setCategories(categoryEntities);
                productEntities.add(productEntity);
                line = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        productRepository.saveAll(productEntities);
    }

    public void readFileRating() {
        String filename = new File("src/main/java/com/movies_unlimited/data/ml-data/u.data").getAbsolutePath();
        List<RatingEntity> ratings = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while (line != null) {
                RatingEntity rating = new RatingEntity();
                String[] splitLine = line.split("\t");
                rating.setAccount(accountRepository.findById(Integer.parseInt(splitLine[0])));
                rating.setProduct(productRepository.getById(Integer.parseInt(splitLine[1])));
                rating.setRating(Integer.parseInt(splitLine[2]));
                ratings.add(rating);
                line = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ratingRepository.saveAll(ratings);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        addRoleIfMissing(Role.ROLE_ADMIN);
//        addRoleIfMissing(Role.ROLE_USER);
//        addRoleIfMissing(Role.ROLE_SELLER);
//
//        for (int i = 0; i < 943; ++i) {
//            String username = "test" + i + "@gmail.com";
//            addUserIfMissing(username, "123456789aaA", Role.ROLE_ADMIN, Role.ROLE_USER);
//        }
//
//        addUserIfMissing("congtrinh2404@gmail.com", "123456789aaA", Role.ROLE_USER);
//        addUserIfMissing("congtrinhadmin2404@gmail.com", "123456789aaA", Role.ROLE_USER, Role.ROLE_ADMIN);
//
//        for (CategoryEnum category : CategoryEnum.values()) {
//            addCategoryIfMissing(category);
//        }
//        readFileMovie();
//        readFileRating();
    }

}
