package com.movies_unlimited.service;

import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.entity.enums.ActiveStatus;
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
    final int NUM_NEIGHBOURHOODS = 10;
    final int NUM_RECOMMENDATIONS = 20;
    final int MIN_VALUE_RECOMMENDATION = 4;
    final boolean RANDOM_RATINGS = false;

    private final ProductRepository productRepository;

    private final RatingService users;

    public Page<ProductEntity> getProductsActive(int page){
        Pageable pageable = PageRequest.of(page, 12, Sort.by("date").descending());
        return productRepository.findAllActiveProductPageable(ActiveStatus.ACTIVE,pageable);
    }

    public Page<ProductEntity> getProductByCategoryId(int categoryId, int page, String sort) {
        Pageable pageable;
        if(sort.equals("Latest")){
            pageable = PageRequest.of(page, 12, Sort.by("date").descending());
        }else if(sort.equals("Oldest")){
            pageable = PageRequest.of(page, 12, Sort.by("date").ascending());
        }else if(sort.equals("HightoLow")){
            pageable = PageRequest.of(page, 12, Sort.by("price").descending());
        }else if(sort.equals("LowtoHigh")){
            pageable = PageRequest.of(page, 12, Sort.by("price").ascending());
        }else{
            pageable = PageRequest.of(page, 12, Sort.by("date").descending());
        }
        return productRepository.findProductByCategoryID(categoryId,ActiveStatus.ACTIVE,pageable);
    }

//    public void recommendMovie() {
//        List<ProductEntity> products = productRepository.findAll();
//        users.readFile();
//        HashMap<Integer, Integer> ratings = new HashMap<>();
//
//        Random random = new Random();
//        Scanner in = new Scanner(System.in);
//        System.out.println("**********************************************");
//        for (int i = 0; i < NUM_RATINGS; i++) {
//            int idMovie = random.nextInt(products.size());
//            while (ratings.containsKey(idMovie)) {
//                idMovie = random.nextInt(products.size());
//            }
//            int rating;
//            do {
//                System.out.println("Movie: " + productRepository.findById(idMovie).getName());
//                System.out.println("Enter your rating (1-5):");
//                if (RANDOM_RATINGS) {
//                    rating = random.nextInt(5) + 1;
//                    System.out.println(rating);
//                } else {
//                    rating = Integer.parseInt(in.nextLine());
//                }
//            } while (rating < 0 || rating > 5);
//            ratings.put(idMovie, rating);
//        }
//
//        Map<Integer, Double> neighbourhoods = users.getNeighbourhoods(ratings, NUM_NEIGHBOURHOODS);
//        Map<Integer, String> moviesIntegerStringMap = new HashMap<Integer, String>();
//        for (ProductEntity product : products) {
//            moviesIntegerStringMap.put(product.getId(), product.getName());
//        }
//
//        Map<Integer, Double> recommendations = users.getRecommendations(ratings, neighbourhoods, moviesIntegerStringMap);
//
//        ValueComparator valueComparator = new ValueComparator(recommendations);
//        Map<Integer, Double> sortedRecommendations = new TreeMap<>(valueComparator);
//        sortedRecommendations.putAll(recommendations);
//
//        System.out.println("**********************************************");
//        System.out.println("Recommendations: ");
//        System.out.println("**********************************************");
//        Iterator entries = sortedRecommendations.entrySet().iterator();
//        int i = 0;
//        while (entries.hasNext() && i < NUM_RECOMMENDATIONS) {
//            Map.Entry entry = (Map.Entry) entries.next();
//            if ((double) entry.getValue() >= MIN_VALUE_RECOMMENDATION) {
//                System.out.println("Movie: " + productRepository.findById((int) entry.getKey()).getName() + ", Rating: " + entry.getValue());
//                i++;
//            }
//        }
//    }
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