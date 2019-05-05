package com.tvs_assessment_test.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
public class Common {

    public static final String BASE_URL = "http://tvsfit.mytvs.in/reporting/vrm/api/test_new/int/gettabledata.php";
    private static final String HOST_NAME = ".mytvs.in";

    public static void ShowToastMessage(Context context, String message) {

        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);

            toast.setGravity(Gravity.CENTER, 0, 0);

            toast.show();
        }

    }

    public static void VolleyExceptions(Activity parActivity, VolleyError volleyError) {

        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            //message = "The server could not be found. Please try again after some time!!";
            message = "Please check your credentials!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }

        Common.ShowToastMessage(parActivity, message);
    }

}
