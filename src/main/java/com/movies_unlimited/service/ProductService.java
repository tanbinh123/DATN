package com.movies_unlimited.service;

import com.movies_unlimited.Ultil.AccountUltil;
import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.entity.RatingEntity;
import com.movies_unlimited.entity.enums.ActiveStatus;
import com.movies_unlimited.repository.AccountRepository;
import com.movies_unlimited.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    final int NUM_RATINGS = 20;
    final int NUM_NEIGHBOURHOODS = 100;
    final int NUM_RECOMMENDATIONS = 6;
    final int MIN_VALUE_RECOMMENDATION = 4;
    final boolean RANDOM_RATINGS = true;

    private final ProductRepository productRepository;
    private final RatingService rating;
    private final AccountRepository accountRepository;

    public Page<ProductEntity> getProductsActive(int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("date").descending());
        return productRepository.findAllActiveProductPageable(ActiveStatus.ACTIVE, pageable);
    }

    public List<ProductEntity> getProductsActive() {
        return productRepository.findAllActiveProduct(ActiveStatus.ACTIVE);
    }

    public Page<ProductEntity> getProductByCategoryId(int categoryId, int page, String sort) {
        Pageable pageable;
        if (sort.equals("Latest")) {
            pageable = PageRequest.of(page, 12, Sort.by("date").descending());
        } else if (sort.equals("Oldest")) {
            pageable = PageRequest.of(page, 12, Sort.by("date").ascending());
        } else if (sort.equals("HightoLow")) {
            pageable = PageRequest.of(page, 12, Sort.by("price").descending());
        } else if (sort.equals("LowtoHigh")) {
            pageable = PageRequest.of(page, 12, Sort.by("price").ascending());
        } else {
            pageable = PageRequest.of(page, 12, Sort.by("date").descending());
        }
        return productRepository.findProductByCategoryID(categoryId, ActiveStatus.ACTIVE, pageable);
    }

    public Page<ProductEntity> getProducts(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 9);
        return productRepository.findAll(pageable);
    }

    public List<ProductEntity> getProducts() {
        return (List<ProductEntity>) productRepository.findAll();
    }

    public ProductEntity getProductById(Integer id) {
        return productRepository.getById(id);
    }

    public List<ProductEntity> getProductsByPromotionId(int id) {
        return productRepository.findAllProductByPromotionId(id);
    }

    public Page<ProductEntity> getProductsActive(int page, String sort) {
        Pageable pageable;
        if (sort.equals("Latest")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.DESC, "date");
        } else if (sort.equals("Oldest")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.ASC, "date");
        } else if (sort.equals("HightoLow")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.DESC, "price");
        } else if (sort.equals("LowtoHigh")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.ASC, "price");
        } else {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.DESC, "date");
        }
        return productRepository.findAllActiveProductPageable(ActiveStatus.ACTIVE, pageable);
    }

    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public Page<ProductEntity> getProductByAnyActive(String searchText, int page, String sort) {
        Pageable pageable;
        if (sort.equals("Latest")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.DESC, "date");
        } else if (sort.equals("Oldest")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.ASC, "date");
        } else if (sort.equals("HightoLow")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.DESC, "price");
        } else if (sort.equals("LowtoHigh")) {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.ASC, "price");
        } else {
            pageable = PageRequest.of(page - 1, 9, Sort.Direction.DESC, "date");
        }
        return productRepository.findProductByAnyActive(searchText, ActiveStatus.ACTIVE, pageable);
    }

    public Page<ProductEntity> getProductByAny(String searchText, int page) {
        Pageable pageable = PageRequest.of(page - 1, 9);
        return productRepository.findProductByAny(searchText, pageable);
    }


    public Set<ProductEntity> recommendMovie() {
        AccountEntity account = accountRepository.findAccountByEmail(AccountUltil.getAccount());
        account.setId(1);
        Set<RatingEntity> ratingsFromDatabase = rating.getRatingsByUserId(account.getId());

        List<ProductEntity> products = productRepository.findAllActiveProduct(ActiveStatus.ACTIVE);
        rating.readFile();
        HashMap<Integer, Integer> ratings = new HashMap<>();

        for (RatingEntity  ratingEntity : ratingsFromDatabase) {
            ratings.put(ratingEntity.getProduct().getId(),ratingEntity.getRating());
        }

        Map<Integer, Double> neighbourhoods = rating.getNeighbourhoods(ratings, NUM_NEIGHBOURHOODS);
        Map<Integer, String> moviesIntegerStringMap = new HashMap<Integer, String>();
        for (ProductEntity product : products) {
            moviesIntegerStringMap.put(product.getId(), product.getName());
        }

        Map<Integer, Double> recommendations = rating.getRecommendations(ratings, neighbourhoods, moviesIntegerStringMap);

        ValueComparator valueComparator = new ValueComparator(recommendations);
        Map<Integer, Double> sortedRecommendations = new TreeMap<>(valueComparator);
        sortedRecommendations.putAll(recommendations);

        Iterator entries = sortedRecommendations.entrySet().iterator();
        int i = 0;
        Set<ProductEntity> recommendProducts = new HashSet<>();
        while (entries.hasNext() && i < NUM_RECOMMENDATIONS) {
            Map.Entry entry = (Map.Entry) entries.next();
            if ((double) entry.getValue() >= MIN_VALUE_RECOMMENDATION) {
                recommendProducts.add(productRepository.getById((int) entry.getKey()));
                i++;
            }
        }

        return recommendProducts;
    }
}


class ValueComparator implements Comparator<Integer> {
    private final Map<Integer, Double> base;

    public ValueComparator(Map<Integer, Double> base) {
        this.base = base;
    }

    public int compare(Integer a, Integer b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}