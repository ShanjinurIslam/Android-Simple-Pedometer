package com.promitee.fitnesstracker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class hweight extends AppCompatActivity {
    private int client_id ;
    private EditText h ;
    private EditText w ;
    private EditText age ;
    private sqliteHelper database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hweight);
        h = findViewById(R.id.h) ;
        w = findViewById(R.id.w) ;
        age = findViewById(R.id.age) ;
        database = new sqliteHelper(this) ;

        Intent intent = getIntent() ;
        client_id = intent.getExtras().getInt("client_id") ;
        Log.d("In hweight",String.valueOf(client_id)) ;
    }

    private boolean validateFields() {
        int yourDesiredLength = 0;
        if (Float.parseFloat(h.getText().toString())< yourDesiredLength) {
            h.setError("Your Input is Invalid");
            return false;
        } else if (Float.parseFloat(w.getText().toString())< 8) {
            w.setError("Password should be minimum of length 8");
            return false;
        }else if (Integer.parseInt(age.getText().toString()) < 10) {
            age.setError("Your Input is Invalid");
            return false;
        } else {
            return true;
        }
    }

    public void next(View view) {
        if(validateFields()){
            database.insertToClientDetailsTable(client_id,Integer.parseInt(age.getText().toString()),Float.parseFloat(h.getText().toString()),Float.parseFloat(w.getText().toString())) ;

        }

        final MediaPlayer player = MediaPlayer.create(this,R.raw.button_click) ;
        player.start();
        Intent intent = new Intent(hweight.this,targetlist.class) ;
        intent.putExtra("c_id",client_id) ;
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
    }
}
