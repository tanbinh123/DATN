package com.movies_unlimited.recommender_system;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Movies {

    private Map<Integer, String> movies;

    public Movies() {
        movies = new HashMap<>();
    }

    public int size() {
        return movies.size();
    }

    public String getName(int id) {
        return movies.get(id);
    }

    public Map<Integer, String> getMovies() {
        return  movies;
    }

    public void readFile(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while (line != null) {
                String[] splitLine = line.split("\\|");
                movies.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
