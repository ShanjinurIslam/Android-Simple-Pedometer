package com.promitee.fitnesstracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class goals extends Fragment {
    ArcView bmi,stepsArc,weightArc ;
    EditText w,s ;
    private sqliteHelper database ;
    private Button weight ;
    private Button target ;
    public goals() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goals, container, false);
        bmi = view.findViewById(R.id.bmi) ;
        stepsArc = view.findViewById(R.id.stepsArc) ;
        weightArc = view.findViewById(R.id.weightArc) ;

        w = view.findViewById(R.id.weight) ;
        s = view.findViewById(R.id.steps) ;


        w.setHint(String.valueOf(getArguments().getFloat("weight")));
        database = new sqliteHelper(view.getContext()) ;
        // Required empty public constructor

        float bmi_value = getArguments().getFloat("weight")/(float)(Math.pow(getArguments().getFloat("height"),2)) ;

        bmi.setAnimatable(true);
        bmi.setProgress((int)(bmi_value)*2,new DecimalFormat(".##").format(bmi_value));

        weightArc.setAnimatable(true);
        weightArc.setProgress((int)(getArguments().getFloat("weight")),new DecimalFormat(".##").format(getArguments().getFloat("weight")));

        stepsArc.setAnimatable(true);
        s.setHint(String.valueOf(database.getSteps(getArguments().getInt("client_id"))));
        stepsArc.setProgress((int)(database.getSteps(getArguments().getInt("client_id"))*(0.01)),String.valueOf(database.getSteps(getArguments().getInt("client_id"))));

        target = view.findViewById(R.id.updateSteps) ;
        weight = view.findViewById(R.id.updateWeight) ;

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!s.getText().toString().equals("")){
                    int steps = (int)(Float.parseFloat(s.getText().toString())) ;
                    database.updateSteps(getArguments().getInt("client_id"),steps) ;
                    stepsArc.setProgress((int)(database.getSteps(getArguments().getInt("client_id"))*(0.01)),String.valueOf(database.getSteps(getArguments().getInt("client_id"))));
                    stepsArc.invalidate();
                    s.setText("");
                    s.setHint(String.valueOf(database.getSteps(getArguments().getInt("client_id"))));

                }
                else s.setError("Enter Value");

            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!w.getText().toString().equals("")){
                    float weight = Float.parseFloat(w.getText().toString()) ;
                    database.updateWeight(getArguments().getInt("client_id"),weight) ;

                    float bmi_value = database.getWeight(getArguments().getInt("client_id"))/(float)(Math.pow(getArguments().getFloat("height"),2)) ;

                    bmi.setAnimatable(true);
                    bmi.setProgress((int)(bmi_value)*2,new DecimalFormat(".##").format(bmi_value));

                    weightArc.setAnimatable(true);
                    weightArc.setProgress((int)(database.getWeight(getArguments().getInt("client_id"))),new DecimalFormat(".##").format(database.getWeight(getArguments().getInt("client_id"))));

                    weightArc.invalidate();
                    bmi.invalidate();

                    w.setText("");
                    w.setHint(String.valueOf(database.getWeight(getArguments().getInt("client_id"))));


                }
                else w.setError("Enter Value");
            }
        });

        return view;
    }

}
