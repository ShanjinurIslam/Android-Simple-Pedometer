package com.promitee.fitnesstracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private sqliteHelper sqliteHelper ;
    private EditText username ;
    private EditText password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteHelper = new sqliteHelper(this) ;
        username = findViewById(R.id.username) ;
        password = findViewById(R.id.password) ;
    }

    private boolean validateFields() {
        int yourDesiredLength = 5;
        if (username.getText().length() < yourDesiredLength) {
            username.setError("Username should be length of five");
            return false;
        } else if (password.getText().length() < 8) {
            password.setError("Password should be length of 8");
            return false;
        } else {
            return true;
        }
    }

    public void toRegister(View view) {
        final MediaPlayer player = MediaPlayer.create(this,R.raw.button_click) ;
        player.start();
        startActivity(new Intent(MainActivity.this,register.class));
    }

    public void signIn(View view) {
        if(validateFields()){
            int client_id = sqliteHelper.logincheck(username.getText().toString(),password.getText().toString()) ;
            if(client_id==-1){
                username.setError("No user registered using this name");
            }
            else{
                final MediaPlayer player = MediaPlayer.create(this,R.raw.button_click) ;
                player.start();
                //int client_id = sqliteHelper.getClientID(username.getText().toString()) ;
                Log.d("In sign in",String.valueOf(1)) ;
                Intent intent = new Intent(MainActivity.this,dashboard.class) ;
                intent.putExtra("client_id",client_id) ;
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed(){
    }
}
