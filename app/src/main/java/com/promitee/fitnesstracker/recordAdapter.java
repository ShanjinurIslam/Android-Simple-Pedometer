package com.promitee.fitnesstracker;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class recordAdapter extends ArrayAdapter<workout> {

    private Context mContext;
    private List<workout> workoutList = new ArrayList<>();
    private sqliteHelper database ;
    private int client_id ;

    public recordAdapter(Context context, int client_id, ArrayList<workout> list) {
        super(context, 0 , list);
        mContext = context;
        workoutList = list;
        this.client_id = client_id ;
        database = new sqliteHelper(context) ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.history_index,parent,false);

        workout current = workoutList.get(position);
        int counts = current.getSteps() ;
        float cals = current.getCal() ;

        ArcView step = (ArcView) listItem.findViewById(R.id.steps);

        ArcView cal = (ArcView) listItem.findViewById(R.id.cal);

        ArcView miles = (ArcView) listItem.findViewById(R.id.miles);

        System.out.println("I am here: "+counts*(1.0/(float)database.getSteps(client_id)));
        step.setProgress((int)(counts*(1.0/(float)database.getSteps(client_id))*100),String.valueOf(counts)) ;
        Log.d("Count value: ",String.valueOf(counts)) ;
        step.invalidate();

        cal.setProgress((int)(cals),String.valueOf(new DecimalFormat(".##").format(cals))) ;
        cal.invalidate();

        float m = (float) (database.getSteps(client_id)*(1.0/2200.0));
        miles.setProgress((int)(counts*1.0/(2200*m)),String.valueOf(new DecimalFormat(".##").format(counts*(1.0/2200.0)))) ;
        miles.invalidate();

        TextView textView = listItem.findViewById(R.id.date) ;
        textView.setText(current.getDate());

        return listItem;
    }
}
