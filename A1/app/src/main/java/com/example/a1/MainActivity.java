package com.example.a1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    //Permissions
    private static final String PRJ3_PERMISSION = "edu.uic.cs478.f20.kaboom";
    private static final String PRJ3_INTENT = "edu.uic.cs478.BroadcastReceiver";

    //Broadcast Receiver
    private BroadcastReceiver mReceiver = null;
    private IntentFilter mFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Check for permission
    public void checkPermission(View v)
    {
        checkPermission();
    }

    //Check for permission already given
    private void checkPermission()
    {
        //Permission has already been given register receiver
        if(ContextCompat.checkSelfPermission(this, PRJ3_PERMISSION) == PackageManager.PERMISSION_GRANTED) {

            if(mFilter == null && mReceiver == null) {

                //Set Filter
                mFilter = new IntentFilter(PRJ3_INTENT);
                mFilter.setPriority(50);

                //Set Receiver and Register Receiver
                mReceiver = new A1Receiver();
                registerReceiver(mReceiver, mFilter);

            }

            //If Permission given Lunch app A2
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.a2");
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                Toast.makeText(MainActivity.this, "There is no package available in android", Toast.LENGTH_LONG).show();
            }

        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{PRJ3_PERMISSION}, 0);
        }

    }

    //Ask the user for permission
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {

        if(results.length > 0) {

            //User gives permission then register receiver
            if(results[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(mFilter == null && mReceiver == null) {

                    //Set Filter
                    mFilter = new IntentFilter(PRJ3_INTENT);
                    mFilter.setPriority(10);

                    //Set Receiver and Register Receiver
                    mReceiver = new A1Receiver();
                    registerReceiver(mReceiver, mFilter);

                }

                //If Permission given Lunch app A2
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.a2");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(MainActivity.this, "There is no package available in android", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "No Permission Given To App A1", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver when app closes
        try {
            unregisterReceiver(mReceiver);
        }
        catch(Exception e) {
            //Toast.makeText(MainActivity.this, "NULL Caught", Toast.LENGTH_LONG).show();
        }

    }
}