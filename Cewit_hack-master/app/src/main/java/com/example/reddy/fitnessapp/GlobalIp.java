package com.example.reddy.fitnessapp;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by HP on 26-04-2017.
 */

public class GlobalIp {
    static String ip = "172.30.2.165";
    //static ArrayList<HomeActivity.DataClass> al=new ArrayList<>();
    int flag=0;
    ConnectivityManager connec;
    public final int isInternetOn(ConnectivityManager conn) {

        // get Connectivity Manager object to check connection


        connec=conn;
        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            flag=1;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            flag=0;
        }
        return flag;
    }


}
