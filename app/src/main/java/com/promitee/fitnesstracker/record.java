package com.promitee.fitnesstracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class record extends Fragment {

    private ListView listView;
    private recordAdapter rAdapter;
    private sqliteHelper database ;

    public record() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record, container, false);

        listView = (ListView) view.findViewById(R.id.workout_list);
        ArrayList<workout> arrayList = new ArrayList<>() ;
        database = new sqliteHelper(view.getContext()) ;
        arrayList = database.getHistory(getArguments().getInt("client_id")) ;

        Log.d("Size Array List",String.valueOf(arrayList.size())) ;

        rAdapter = new recordAdapter(getActivity().getApplicationContext(),getArguments().getInt("client_id"),arrayList);
        listView.setAdapter(rAdapter);

        return view;
    }

}
