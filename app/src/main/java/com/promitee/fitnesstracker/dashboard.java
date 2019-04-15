package com.promitee.fitnesstracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView name,age ;
    final Fragment exercise = new exercise();
    final Fragment record = new record();
    final Fragment goals = new goals();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = exercise ;
    sqliteHelper database ;
    BottomNavigationView bottomNavigationView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new sqliteHelper(this) ;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        int client_id = getIntent().getExtras().getInt("client_id") ;

        Bundle client_details = new Bundle();
        client_details.putInt("client_id",client_id);
        client_details.putFloat("height", database.getHeight(client_id));
        client_details.putFloat("weight",database.getWeight(client_id));
        client_details.putInt("wl",database.weightloss(client_id));
        client_details.putInt("rf",database.regular(client_id));


        exercise.setArguments(client_details);
        record.setArguments(client_details);
        goals.setArguments(client_details);

        fm.beginTransaction().add(R.id.main_frame,exercise, "1").commit();
        fm.beginTransaction().add(R.id.main_frame, goals, "2").hide(goals).commit();
        fm.beginTransaction().add(R.id.main_frame, record, "3").hide(record).commit();

        bottomNavigationView = findViewById(R.id.bottomView) ;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.exercise) {
                    toolbar.setTitle("Exercise");
                    fm.beginTransaction().hide(active).show(exercise).commit();
                    active = exercise;
                    fm.beginTransaction().detach(active).attach(active).commit() ;
                    return true;
                } else if (id == R.id.goal) {

                    toolbar.setTitle("Goals");
                    fm.beginTransaction().hide(active).show(goals).commit();
                    active = goals;
                    fm.beginTransaction().detach(active).attach(active).commit() ;
                    return true;
                } else if (id == R.id.record) {
                    toolbar.setTitle("History");
                    fm.beginTransaction().hide(active).show(record).commit();
                    active = record;
                    fm.beginTransaction().detach(active).attach(active).commit() ;
                    return true;
                }
                return false ;
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(dashboard.this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(dashboard.this,MainActivity.class));
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            new AlertDialog.Builder(dashboard.this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(dashboard.this,MainActivity.class));
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.maps) {
            startActivity(new Intent(dashboard.this, mylocation.class));
        } else if (id == R.id.firebase) {
            //couldn't do it
        }else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Hey I am using fitness tracker.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Promotion");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }


}
