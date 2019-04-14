package com.promitee.fitnesstracker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class register extends AppCompatActivity {
    private EditText name ;
    private EditText username ;
    private EditText pass ;
    private sqliteHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name = findViewById(R.id.setname) ;
        username = findViewById(R.id.setusername) ;
        pass = findViewById(R.id.setpass) ;
        database = new sqliteHelper(this) ;
    }

    private boolean validateFields() {
        int yourDesiredLength = 5;
        if (username.getText().length() < yourDesiredLength) {
            username.setError("Your Input is Invalid");
            return false;
        } else if (pass.getText().length() < 8) {
            pass.setError("Password should be minimum of length 8");
            return false;
        }else if (name.getText().length() < yourDesiredLength) {
            name.setError("Your Input is Invalid");
            return false;
        } else {
            return true;
        }
    }

    public void next(View view) {
        if(validateFields()){
            if(database.username_exists(username.getText().toString())){
                username.setError("A user registered using this name"); ;
            }
            else{
                long i = database.insertToClientTable(name.getText().toString(),username.getText().toString(),pass.getText().toString()) ;
                Log.d("In register",String.valueOf(i)) ;
                Intent intent = new Intent(register.this,hweight.class) ;
                intent.putExtra("client_id",(int)i) ;
                startActivity(intent);
            }
        }
        final MediaPlayer player = MediaPlayer.create(this,R.raw.button_click) ;
        player.start();

    }

    @Override
    public void onBackPressed(){
    }
}
