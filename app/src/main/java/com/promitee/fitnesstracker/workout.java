package com.promitee.fitnesstracker;

public class workout {

    private int steps ;
    private float cal ;
    private String date ;

    public workout(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public workout(int steps, float cal, String Date){
        this.steps = steps ;
        this.cal = cal ;
        this.date = Date ;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public float getCal() {
        return cal;
    }

    public void setCal(float cal) {
        this.cal = cal;
    }
}
