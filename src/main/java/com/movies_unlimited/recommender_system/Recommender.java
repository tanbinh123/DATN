package com.movies_unlimited.recommender_system;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Recommender {
    public static void main(String[] args) {

        final int NUM_NEIGHBOURHOODS = 400;

        Movies movies = new Movies();
        movies.readFile(new File("src/main/java/com/movies_unlimited/data/ml-data/u.item").getAbsolutePath());

        Users users = new Users();
        users.readFile(new File("src/main/java/com/movies_unlimited/data/ml-data/u.data").getAbsolutePath());

        Users usersBase = new Users();
        usersBase.readFile(new File("src/main/java/com/movies_unlimited/data/ml-data/u5.base").getAbsolutePath());

        Users usersTest = new Users();
        usersTest.readFile(new File("src/main/java/com/movies_unlimited/data/ml-data/u5.test").getAbsolutePath());

        double cc = 0;
        int d = 0;
        for (int userId = 1; userId <= users.getRatings().size(); userId++) {
            if (usersTest.getRatings().get(userId) == null) {
                d++;
                continue;
            }
            HashMap<Integer, Integer> ratings = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry : usersBase.getRatings().get(userId).entrySet()) {
                int idMovie = entry.getKey();
                int rating = entry.getValue();
                ratings.put(idMovie, rating);
            }

            Map<Integer, Double> neighbourhoods = users.getNeighbourhoods(ratings, NUM_NEIGHBOURHOODS);
            Map<Integer, Double> recommendations = users.getRecommendations(ratings, neighbourhoods, movies.getMovies());
            ValueComparator valueComparator = new ValueComparator(recommendations);
            Map<Integer, Double> sortedRecommendations = new TreeMap<>(valueComparator);
            sortedRecommendations.putAll(recommendations);

            Iterator entries = sortedRecommendations.entrySet().iterator();
            int i = 0;

            double bb = 0;
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                if (usersTest.getRatings().get(userId).get(entry.getKey()) != null) {
                    bb += ((double) entry.getValue() - usersTest.getRatings().get(userId).get(entry.getKey())) * ((double) entry.getValue() - usersTest.getRatings().get(userId).get(entry.getKey()));
                    i++;
                    System.out.println("Movie: " + movies.getName((int) entry.getKey()) + ", Rating: " + entry.getValue() + ", Rating: " + usersTest.getRatings().get(userId).get(entry.getKey()));
                }
            }
            System.out.println(userId);

            cc += Math.sqrt(bb / i);
        }
        System.out.println(cc / (users.getRatings().size() - d));
    }
}
