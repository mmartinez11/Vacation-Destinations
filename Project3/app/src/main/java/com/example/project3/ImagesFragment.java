package com.example.project3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

//The Vacation Images Fragment
public class ImagesFragment extends Fragment {

    private ImageView vacationV = null;
    private  int currentIndex = -1;

    //This method is used to get the current index being used
    public int getIndexInUse()
    {
        return currentIndex;
    }

    //This method will show the image of the corresponding vacation location based on the index
    public void showImageAtIndex(int newIndex)
    {
        //If no location is chosen the fragment will be empty
        if(newIndex < 0)
        {
            return;
        }
            currentIndex = newIndex;
            vacationV.setImageResource(MainActivity.vacationImages[newIndex]);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate Image Fragment
        return inflater.inflate(R.layout.location_images, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get Image View object from the fxml file
        vacationV = (ImageView) getActivity().findViewById(R.id.vacationView);
        showImageAtIndex(currentIndex);

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

}
