package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project3.NamesFragment.locationSelection;

public class MainActivity extends AppCompatActivity implements locationSelection{

    //Orientation Tags
    static final String IMAGE_FRAG = "savedImageFragment";
    static final String NAME_FRAG = "savedNameFragment";


    //----Permission/Receiver Utilities------------------------------------
    private static final String PRJ3_PERMISSION = "edu.uic.cs478.f20.kaboom";
    private static final String PRJ3_INTENT = "edu.uic.cs478.BroadcastReceiver";

    public static final String INDEX_VACATION = "IDX";

    //Names of vacation location list/Images of vacation destinations
    public static String[] vacationNames;
    public static int[] vacationImages;

    //Fragment References
    private ImagesFragment mImageFragment = new ImagesFragment();
    private NamesFragment mNameFragment = new NamesFragment();

    FragmentManager mFragmentManager;

    //Frame Layouts/Utilities
    private FrameLayout mNameLayout;
    private FrameLayout mImagesLayout;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Initializing arrays
        vacationImages = new int[]{ R.drawable.busan, R.drawable.cancun, R.drawable.losangeles, R.drawable.paris, R.drawable.vancouver};
        vacationNames = getResources().getStringArray(R.array.vacationName);

        setContentView(R.layout.activity_main);

        //-----------------Dynamic Fragments---//
        mNameLayout = (FrameLayout) findViewById(R.id.names_fragment);
        mImagesLayout = (FrameLayout) findViewById(R.id.images_fragment);
        mFragmentManager = getSupportFragmentManager();

        //If the bundle is not null that means we have to restore fragments
        if(savedInstanceState != null)
        {
            //Get Names Fragment from the bundle
            mNameFragment = (NamesFragment) mFragmentManager.getFragment(savedInstanceState, NAME_FRAG);

            //Get Images Fragment from the bundle
            if(mFragmentManager.getFragment(savedInstanceState, IMAGE_FRAG) != null) {
                mImageFragment = (ImagesFragment) mFragmentManager.getFragment(savedInstanceState, IMAGE_FRAG);
                setLayout();
            }
        }
        else {
            //If there is no orientation change then this will set the default
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.names_fragment, mNameFragment);
            fragmentTransaction.commit();
        }

        //Fragment Manager Listener
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setLayout();
            }
        });

        //------------Action Bar Settings--------//
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  Application A3");

        actionBar.setIcon(R.drawable.ic_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    //-------Dynamic Fragments-------------//
    private void setLayout()
    {
        int currentOrientation = getResources().getConfiguration().orientation;

        //Before a location is picked the Names fragment will populate the entire screen
        if(!mImageFragment.isAdded())
        {
            mNameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            mImagesLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
        }
        else
        {
            //When a location is chosen in Landscape mode then the image fragment will take 2/3 of the screen
            if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE){

                mNameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                mImagesLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));

            }
            //If the location is chosen in the Portrait mode then the image fragment will populate the entire screen
            else {
                mNameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
                mImagesLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    //Implement the interface method to communicate between fragments
    @Override
    public void onLocationSelection(int index) {

        //Add Image fragment when a location is picked from the list
        if(!mImageFragment.isAdded()){
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.images_fragment, mImageFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            mFragmentManager.executePendingTransactions();
        }

        //Set Image from location chosen in Image Fragment
        if(mImageFragment.getIndexInUse() != index)
        {
            mImageFragment.showImageAtIndex(index);
        }
    }


    //-------------------Action Bar Methods-----//
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate Action Bar
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //If option 1 is chosen then send an ordered broadcast
        if(item.getItemId() == R.id.option1)
        {
            //If location has been chosen then send broadcast
            if(mImageFragment.getIndexInUse() != -1) {

                try {
                    Intent intent = new Intent(PRJ3_INTENT);
                    intent.putExtra(INDEX_VACATION, mImageFragment.getIndexInUse());
                    sendOrderedBroadcast(intent, PRJ3_PERMISSION);
                }catch(Exception e)
                {
                    throw new ClassCastException("A1 and A2 do not have permissions");
                }
            }
            //If there is no location chosen then let the user know
            else
            {
                Toast.makeText(this, "Please Choose a Vacation Destination", Toast.LENGTH_SHORT).show();
            }
        }
        //If option 2 is chosen exit application
        else if(item.getItemId() == R.id.option2)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() { super.onPause(); }

    //In This method will save the fragments so they can be used again when the orientation changes
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        //Save Names Fragment in bundle
        mFragmentManager.putFragment(savedInstanceState, NAME_FRAG, mFragmentManager.findFragmentById(R.id.names_fragment));

        if (mImageFragment.isAdded())
        {
            //Save images fragment in bundle
            mFragmentManager.putFragment(savedInstanceState, IMAGE_FRAG, mFragmentManager.findFragmentById(R.id.images_fragment));
        }

        super.onSaveInstanceState(savedInstanceState);
    }

}