package com.movies_unlimited.Ultil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUltil {
    public static double roundDoubleMoney(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();
 
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
}
