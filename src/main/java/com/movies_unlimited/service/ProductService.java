package com.movies_unlimited.service;

import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.recommender_system.Users;
import com.movies_unlimited.recommender_system.ValueComparator;
import com.movies_unlimited.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
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

    public void recommendMovie() {

        List<ProductEntity> products = productRepository.findAll();

        //Users users = new Users();
        //users.readFile(new File("src/main/java/com/movies_unlimited/data/ml-data/u.data").getAbsolutePath());

        users.readFile();

        HashMap<Integer, Integer> ratings = new HashMap<>();

        Random random = new Random();
        Scanner in = new Scanner(System.in);

        System.out.println("**********************************************");
        System.out.println("Obtaining user data: ");
        System.out.println("**********************************************");
        for (int i = 0; i < NUM_RATINGS; i++) {
            int idMovie = random.nextInt(products.size());
            while (ratings.containsKey(idMovie)) {
                idMovie = random.nextInt(products.size());
            }
            int rating;
            do {
                System.out.println("Movie: " + productRepository.findById(idMovie).getName());
                System.out.println("Enter your rating (1-5):");
                if (RANDOM_RATINGS) {
                    rating = random.nextInt(5) + 1;
                    System.out.println(rating);
                } else {
                    rating = Integer.parseInt(in.nextLine());
                }
            } while (rating < 0 || rating > 5);
            ratings.put(idMovie, rating);
        }

        Map<Integer, Double> neighbourhoods = users.getNeighbourhoods(ratings, NUM_NEIGHBOURHOODS);
        Map<Integer, String> moviesIntegerStringMap = new HashMap<Integer, String>();
        for (ProductEntity product:products) {
            moviesIntegerStringMap.put(product.getId(),product.getName());
        }

        Map<Integer, Double> recommendations = users.getRecommendations(ratings, neighbourhoods, moviesIntegerStringMap);

        ValueComparator valueComparator = new ValueComparator(recommendations);
        Map<Integer, Double> sortedRecommendations = new TreeMap<>(valueComparator);
        sortedRecommendations.putAll(recommendations);

        System.out.println("**********************************************");
        System.out.println("Recommendations: ");
        System.out.println("**********************************************");
        Iterator entries = sortedRecommendations.entrySet().iterator();
        int i = 0;
        while (entries.hasNext() && i < NUM_RECOMMENDATIONS) {
            Map.Entry entry = (Map.Entry) entries.next();


            if ((double) entry.getValue() >= MIN_VALUE_RECOMMENDATION) {
                System.out.println("Movie: " + productRepository.findById((int) entry.getKey()).getName() + ", Rating: " + entry.getValue());
                i++;
            }
        }
    }
}
