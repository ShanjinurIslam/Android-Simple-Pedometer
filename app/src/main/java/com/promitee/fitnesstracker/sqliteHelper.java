package com.promitee.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.EditText;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

public class sqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fitnesstracker_db";

    public sqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE if not exists client (client_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,username TEXT,password TEXT)";
        db.execSQL(table1);
        String table2 = "CREATE TABLE if not exists client_details (client_id INTEGER , age INTEGER,height REAL,weight REAL)";
        db.execSQL(table2);
        String table3 = "CREATE TABLE if not exists client_options (client_id INTEGER, weightloss INT,regular INT,steps INT)";
        db.execSQL(table3);
        String table4 = "CREATE TABLE if not exists client_history (client_id INTEGER, steps INT,calories REAL, time DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(table4);
        String table5 = "CREATE TABLE if not exists client_goal (client_id INTEGER, steps INT,calories REAL,miles REAL)";
        db.execSQL(table5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }

    public long insertToClientTable(String name,String username,String password){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("username",username);
        values.put("password",password);

        long id = db.insert("client", null, values);
        db.close();
        return id;
    }

    public long updateSteps(int client_id,int steps){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put("steps",steps);

        long id = db.update("client_options",values,"client_id ="+client_id,null) ;
        db.close();
        return id;
    }

    public void updateWeight(int client_id, float weight) {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put("weight",weight);

        long id = db.update("client_details",values,"client_id ="+client_id,null) ;
        db.close();
    }

    public long insertToClientDetailsTable(int client_id, int age,float height,float weight){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put("client_id",client_id);
        values.put("age",age);
        values.put("height",height);
        values.put("weight",weight);

        long id = db.insert("client_details", null, values);
        db.close();
        return id;
    }

    public long insertToClientOptions(int client_id, int wl, int rf, int i){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put("client_id",client_id);
        values.put("weightloss",wl);
        values.put("regular",rf);
        values.put("steps",i);

        long id = db.insert("client_options", null, values);
        db.close();
        return id;
    }

    public long insertToClientHistory(int client_id, int steps, float calories){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put("client_id",client_id);
        values.put("steps",steps);
        values.put("calories",calories);

        long id = db.insert("client_history", null, values);
        db.close();

        Log.d("History insertion",String.valueOf(id)) ;
        return id;
    }

    public int logincheck(String username,String password){
        String sql = "SELECT * from client where username='"+username+"' and password ='"+password+"'" ;
        int client_id = -1 ;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql,null) ;
        if (cursor.moveToFirst()){
            client_id = cursor.getInt(cursor.getColumnIndex("client_id")) ;
        }

        cursor.close();
        return client_id ;
    }

    public boolean username_exists(String username){
        String sql = "SELECT username from client where username='"+username+"'" ;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql,null) ;
        boolean b =  cursor.moveToFirst() ;
        cursor.close();
        return b ;
    }


    public float getHeight(int client_id) {
        String sql = "SELECT * from client_details where client_id=" + client_id;
        float val = -1;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            val = cursor.getFloat(cursor.getColumnIndex("height"));
        }
        cursor.close();
        return val;
    }

    public float getWeight(int client_id) {
        String sql = "SELECT * from client_details where client_id=" + client_id;
        float val = -1;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            val = cursor.getFloat(cursor.getColumnIndex("weight"));
        }
        cursor.close();
        return val;
    }

    public int weightloss(int client_id) {
        String sql = "SELECT * from client_options where client_id=" + client_id;
        int client_options = -1 ;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql,null) ;
        if (cursor.moveToFirst()){
            client_options = cursor.getInt(cursor.getColumnIndex("weightloss")) ;
        }
        cursor.close();
        return client_options ;
    }

    public int regular(int client_id) {
        String sql = "SELECT * from client_options where client_id=" + client_id;
        int client_options = -1 ;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql,null) ;
        if (cursor.moveToFirst()){
            client_options = cursor.getInt(cursor.getColumnIndex("regular")) ;
        }

        cursor.close();
        return client_options ;
    }

    public int getSteps(int client_id) {
        String sql = "SELECT * from client_options where client_id=" + client_id;
        int client_options = -1 ;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql,null) ;
        if (cursor.moveToFirst()){
            client_options = cursor.getInt(cursor.getColumnIndex("steps")) ;
        }
        cursor.close();
        return client_options ;
    }


    public String getName(int client_id) {
        String sql = "SELECT * from client where client_id=" + client_id;
        String client_options = "" ;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql,null) ;
        if (cursor.moveToFirst()){
            client_options = cursor.getString(cursor.getColumnIndex("name")) ;
        }
        cursor.close();
        return client_options ;
    }

    public String getAge(int client_id) {
        String sql = "SELECT * from client_details where client_id=" + client_id;
        int val = -1;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            val = cursor.getInt(cursor.getColumnIndex("age"));
        }
        cursor.close();
        return "Age: "+String.valueOf(val- Calendar.getInstance().get(Calendar.YEAR));
    }

    public ArrayList<workout> getHistory(int client_id) {
        String sql = "SELECT * from client_history where client_id=" + client_id;
        Cursor cursor = this.getWritableDatabase().rawQuery(sql, null);
        ArrayList<workout> mArrayList = new ArrayList<workout>();

        Log.d("Client_id",String.valueOf(client_id)) ;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            mArrayList.add(new workout( cursor.getInt(cursor.getColumnIndex("steps")), cursor.getFloat(cursor.getColumnIndex("calories")), cursor.getString(cursor.getColumnIndex("time"))) );
        }

        return mArrayList ;
    }
}
