package com.example.project3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

//The Vacation Names List Fragment
public class NamesFragment extends ListFragment {

    //Reference to the interface
    private locationSelection mLocation = null;

    //Variable of the current index selected
    private int currentIndex = -1;

    //Notify the Image Fragment witch image it should show
    public interface locationSelection{
        public void onLocationSelection(int index);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        //Create a new interface object
        try {
             mLocation = (locationSelection) activity;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "did not create interface method");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Inflate List View Fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {

        super.onActivityCreated(savedState);

        //Set List View to be clicked
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //Set list items. Each item will be the name of a vacation location
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_names, MainActivity.vacationNames));

        if(-1 != currentIndex) {
            getListView().setItemChecked(currentIndex, true);
            mLocation.onLocationSelection(currentIndex);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        //Get the index of the clicked item on the list
        if(currentIndex != pos) {
            currentIndex = pos;
            mLocation.onLocationSelection(pos);
        }

        l.setItemChecked(currentIndex, true);
    }

}
