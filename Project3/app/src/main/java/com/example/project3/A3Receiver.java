package com.example.project3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

//This Receiver Was Used For Testing Purposes
public class A3Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1)
    {
        int indexA = arg1.getIntExtra(MainActivity.INDEX_VACATION, 0);
        Toast.makeText(arg0, "Receiver A3 in action! The index is: " + indexA, Toast.LENGTH_LONG).show();
    }

}
