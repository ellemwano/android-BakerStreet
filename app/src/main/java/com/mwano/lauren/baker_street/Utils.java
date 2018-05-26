package com.mwano.lauren.baker_street;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {

    //
    static String doubleToStringFormat(double doubleValue) {
        NumberFormat nf = new DecimalFormat("#.##");
        return nf.format(doubleValue);
    }

}
