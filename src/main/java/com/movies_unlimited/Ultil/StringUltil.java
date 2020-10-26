package com.movies_unlimited.Ultil;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUltil {
    public static String fromDoubleToVN(double d){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(d);
    }
    public static String fromDoubleToUS(double d){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(d);
    }
    public static String fromDateToUS(Date d){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(d);
    }
    public static String fromDateToVN(Date d){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(d);
    }
    
    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        } // If there was an Exception 
        // while creating URL object 
        catch (Exception e) {
            return false;
        }
    }
}
