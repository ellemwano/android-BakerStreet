package com.mwano.lauren.baker_street;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {

    //
    public static String doubleToStringFormat(Double doubleValue) {
        NumberFormat nf = new DecimalFormat("#.##");
        return nf.format(doubleValue);
    }

}
