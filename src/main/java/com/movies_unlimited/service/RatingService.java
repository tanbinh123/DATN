package com.movies_unlimited.service;

import com.movies_unlimited.entity.FavoriteEntity;
import com.movies_unlimited.entity.RatingEntity;
import com.movies_unlimited.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {
    private final Map<Integer, Map<Integer, Integer>> ratings;
    private final Map<Integer, Double> averageRating;
    @Autowired
    private RatingRepository ratingRepository;

    public RatingService() {
        ratings = new HashMap<>();
        averageRating = new HashMap<>();
    }

    public void readFile() {
        List<RatingEntity> ratingEntityList = ratingRepository.findAll();
        for (RatingEntity ratingEntity : ratingEntityList) {
            int idUser = ratingEntity.getAccount().getId();
            int idMovie = ratingEntity.getProduct().getId();
            int rating = ratingEntity.getRating();
            if (ratings.containsKey(idUser)) {
                ratings.get(idUser).put(idMovie, rating);
                averageRating.put(idUser, averageRating.get(idUser) + rating);
            } else {
                Map<Integer, Integer> movieRating = new HashMap<>();
                movieRating.put(idMovie, rating);
                ratings.put(idUser, movieRating);
                averageRating.put(idUser, (double) rating);
            }
        }
        Iterator entries = averageRating.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            entry.setValue((double) entry.getValue() / (double) ratings.get(entry.getKey()).size());
        }
    }

    /**
     * sim(i,j) = numerator / (sqrt(userDenominator^2) * sqrt(otherUserDenominator^2))
     * numerator = sum((r(u,i) - r(u)) * (r(v,i) - r(v)))
     **/
    public Map<Integer, Double> getNeighbourhoods(Map<Integer, Integer> userRatings, int k) {
        Map<Integer, Double> neighbourhoods = new HashMap<>();
        ValueComparator valueComparator = new ValueComparator(neighbourhoods);
        Map<Integer, Double> sortedNeighbourhoods = new TreeMap<>(valueComparator);

        double userAverage = getAverage(userRatings);

        for (int user : ratings.keySet()) {
            ArrayList<Integer> matches = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : userRatings.entrySet()) {
                int movie = entry.getKey();
                if (ratings.get(user).containsKey(movie)) {
                    matches.add(movie);
                }
            }
            double matchRate;
            if (matches.size() > 1) {
                double numerator = 0, userDenominator = 0, otherUserDenominator = 0;
                for (int movie : matches) {
                    double u = userRatings.get(movie) - userAverage;
                    double v = ratings.get(user).get(movie) - averageRating.get(user);

                    numerator += u * v;
                    userDenominator += u * u;
                    otherUserDenominator += v * v;
                }
                if (userDenominator == 0 || otherUserDenominator == 0) {
                    matchRate = 0;
                } else {
                    matchRate = numerator / (Math.sqrt(userDenominator) * Math.sqrt(otherUserDenominator));
                }
            } else {
                matchRate = 0;
            }

            neighbourhoods.put(user, matchRate);
        }
        sortedNeighbourhoods.putAll(neighbourhoods);

        Map<Integer, Double> output = new TreeMap<>();

        Iterator entries = sortedNeighbourhoods.entrySet().iterator();
        int i = 0;
        while (entries.hasNext() && i < k) {
            Map.Entry entry = (Map.Entry) entries.next();
            if ((double) entry.getValue() > 0) {
                output.put((int) entry.getKey(), (double) entry.getValue());
                i++;
            }
        }

        return output;
    }

    /**
     * r(u,i) = r(u) + sum(sim(u,v) * (r(v,i) - r(v))) / sum(abs(sim(u,v)))
     */
    public Map<Integer, Double> getRecommendations(Map<Integer, Integer> userRatings,
                                                   Map<Integer, Double> neighbourhoods, Map<Integer, String> movies) {
        Map<Integer, Double> predictedRatings = new HashMap<>();

        double userAverage = getAverage(userRatings);

        for (int movie : movies.keySet()) {
            if (!userRatings.containsKey(movie)) {
                double numerator = 0, denominator = 0;
                for (int neighbourhood : neighbourhoods.keySet()) {
                    if (ratings.get(neighbourhood).containsKey(movie)) {
                        double matchRate = neighbourhoods.get(neighbourhood);
                        numerator +=
                                matchRate * (ratings.get(neighbourhood).get(movie) - averageRating.get(neighbourhood));
                        denominator += Math.abs(matchRate);
                    }
                }
                double predictedRating = 0;
                if (denominator > 0) {
                    predictedRating = userAverage + numerator / denominator;
                    if (predictedRating > 5) {
                        predictedRating = 5;
                    }
                }
                predictedRatings.put(movie, predictedRating);
            }
        }

        return predictedRatings;
    }

    private double getAverage(Map<Integer, Integer> userRatings) {
        double userAverage = 0;
        Iterator userEntries = userRatings.entrySet().iterator();
        while (userEntries.hasNext()) {
            Map.Entry entry = (Map.Entry) userEntries.next();
            userAverage += (int) entry.getValue();
        }
        return userAverage / userRatings.size();
    }

    public RatingEntity getRatingByAccountIDAndProductID(int accountid, int productid) {
        return ratingRepository.findByAccount_idAndProduct_id(accountid, productid);
    }

    public Set<RatingEntity> getRatingByProductID(int productid) {
        return ratingRepository.findByProduct_id(productid);
    }

    public RatingEntity save(RatingEntity rate) {
        return ratingRepository.save(rate);
    }

    public Set<RatingEntity> getRatingsByUserId(int id) {
        return ratingRepository.findByAccount_id(id);
    }
}
