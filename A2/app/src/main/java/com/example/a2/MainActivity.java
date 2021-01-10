package com.example.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Permissions and Intents
    private static final String PRJ3_PERMISSION = "edu.uic.cs478.f20.kaboom";
    private static final String PRJ3_INTENT = "edu.uic.cs478.BroadcastReceiver";

    //Broadcast Receiver
    private BroadcastReceiver mReceiver2 = null;
    private IntentFilter mFilter2 = null;

    //Used to display which vacation location the user picked
    public static String[] vacationPicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vacationPicked = getResources().getStringArray(R.array.vacationNamePicked);

    }

    //Check if app A2 is given permission
    public void checkPermission2(View v)
    {
        checkPermission();
    }

    //Check for permission already given
    private void checkPermission()
    {
        //If permission has already been given then register A2s receiver
        if(ContextCompat.checkSelfPermission(this, PRJ3_PERMISSION) == PackageManager.PERMISSION_GRANTED)
        {
            if(mFilter2 == null && mReceiver2 == null) {

                //Set Filter
                mFilter2 = new IntentFilter(PRJ3_INTENT);
                mFilter2.setPriority(80);

                //Set Receiver and Register Receiver
                mReceiver2 = new A2Receiver();
                registerReceiver(mReceiver2, mFilter2);

            }

            //If permission has already been given lunch app A3
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.project3");
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

        if(results.length > 0)
        {
            //If permission is granted then register A2s receiver
            if(results[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(mFilter2 == null && mReceiver2 == null) {

                    //Set Filter
                    mFilter2 = new IntentFilter(PRJ3_INTENT);
                    mFilter2.setPriority(80);

                    //Set Receiver and Register Receiver
                    mReceiver2 = new A2Receiver();
                    registerReceiver(mReceiver2, mFilter2);

                }

                //If user gives permission lunch app A3
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.project3");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(MainActivity.this, "There is no package available in android", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                //If user does not provide permission then display toast and close the application
                Toast.makeText(this, "No Permission Was Given To App A2", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //When app A2 closes unregister its receiver
        try {
            unregisterReceiver(mReceiver2);
        }
        catch(Exception e) {
            //Toast.makeText(MainActivity.this, "NULL Caught", Toast.LENGTH_LONG).show();
        }
    }

}