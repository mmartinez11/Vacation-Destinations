package com.example.a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class A2Receiver extends BroadcastReceiver {

    //ID to get index from intent
    public static final String INDEX_VACATION = "IDX";

    @Override
    public void onReceive(Context context, Intent intent) {

        //Get index from intent that was given by app A3
        int index = intent.getIntExtra(INDEX_VACATION, 0);

        //A2 will display a toast on what vacation location the user picked
        Toast.makeText(context, "Receiver A2 in action! The Vacation picked was: "+ MainActivity.vacationPicked[index], Toast.LENGTH_LONG).show();
    }
}
