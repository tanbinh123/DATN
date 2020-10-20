package com.movies_unlimited.recommender_system;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Integer> {
    private Map<Integer, Double> base;

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

