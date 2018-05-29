package com.mwano.lauren.baker_street;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {

    /**
     * Format Double into String and remove the trailing .0
     * @param doubleValue
     * @return a String with the value of the Double without the .0 if no decimal
     */
    public static String doubleToStringFormat(Double doubleValue) {
        NumberFormat nf = new DecimalFormat("#.##");
        return nf.format(doubleValue);
    }

    /**
     * Check whether there is a Network connection
     * @param context
     * @return a boolean, true for connection and false otherwise
     * Code source: https://stackoverflow.com/a/44773973/8691157
     */
    public static boolean isNetworkConnected(Context context) {
        // get Connectivity Manager to get network status
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
