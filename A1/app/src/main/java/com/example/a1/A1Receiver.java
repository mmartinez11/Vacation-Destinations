package com.example.a1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class A1Receiver extends BroadcastReceiver {

    //IDs used to send and get intent extras
    public static final String INDEX_VACATION = "IDX";
    public static final String INDEX_WEB = "IDXW";

    @Override
    public void onReceive(Context arg0, Intent arg1)
    {
        //Get intent extra index that was passed by app A3
        int index = arg1.getIntExtra(INDEX_VACATION, 0);

        //Put the index into an intent extra to send to the web activity
        Intent intent = new Intent(arg0, webActivity.class);
        intent.putExtra(INDEX_WEB, index);

        //Start web activity
        arg0.startActivity(intent);

    }

}
