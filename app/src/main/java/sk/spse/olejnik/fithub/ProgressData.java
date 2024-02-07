package sk.spse.olejnik.fithub;

import java.util.Calendar;

public class ProgressData {

    Exercises exercise;
    Integer reps, weight;
    Calendar date;
    Boolean bodyweight;
    Boolean lbtokg;
    String note;
    Boolean repstosec; // seconds is true

    // Progress data constructor
    public ProgressData(Exercises exercise, Integer reps, Integer weight, Calendar date, Boolean repstosec, Boolean bodyweight, Boolean lbtokg, String note) {
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
        this.repstosec = repstosec;
        this.bodyweight = bodyweight;
        this.lbtokg = lbtokg;
        this.note = note;
    }

    // Getters and setters
    public Exercises getExercise() {
        return exercise;
    }

    public void setExercise(Exercises exercise) {
        this.exercise = exercise;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Boolean getBodyweight() {
        return bodyweight;
    }

    public void setBodyweight(Boolean bodyweight) {
        this.bodyweight = bodyweight;
    }

    public Boolean getRepstosec() {
        return repstosec;
    }

    public void setRepstosec(Boolean repstosec) {
        this.repstosec = repstosec;
    }

    public Boolean getLbtokg() {return lbtokg;}

    public void setLbtokg(Boolean lbtokg) {this.lbtokg = lbtokg;}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
