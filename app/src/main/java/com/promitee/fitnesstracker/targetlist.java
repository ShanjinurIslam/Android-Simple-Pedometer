package com.promitee.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class targetlist extends AppCompatActivity {
    private int client_id ;
    private CheckBox wl;
    private CheckBox rf;
    private sqliteHelper database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.targetlist);

        database = new sqliteHelper(this) ;
        wl = findViewById(R.id.wl) ;
        rf = findViewById(R.id.rf) ;

        Intent intent = getIntent() ;
        client_id = intent.getExtras().getInt("c_id") ;
        Log.d("In hweight",String.valueOf(client_id)) ;
    }


    public void finishReg(View view) {
        int a = wl.isChecked()?1:0 ;
        int b = rf.isChecked()?1:0 ;


        database.insertToClientOptions(client_id,a,b,5000) ;


        Intent intent = new Intent(targetlist.this,dashboard.class) ;
        intent.putExtra("client_id",client_id) ;
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
    }

}
