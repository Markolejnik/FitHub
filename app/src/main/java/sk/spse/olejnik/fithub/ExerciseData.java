package sk.spse.olejnik.fithub;

import java.util.List;

public class ExerciseData {

    private String newTitle, newLink, newType;
    private List<String> newMuscleGroups;
    private int newImage1, newImage2;


    public ExerciseData() {}


    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewLink() {
        return newLink;
    }

    public void setNewLink(String newLink) {
        this.newLink = newLink;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public List<String> getNewMuscleGroups() {
        return newMuscleGroups;
    }

    public void setNewMuscleGroups(List<String> newMuscleGroups) {
        this.newMuscleGroups = newMuscleGroups;
    }

    public int getNewImage1() {
        return newImage1;
    }

    public void setNewImage1(int newImage1) {
        this.newImage1 = newImage1;
    }

    public int getNewImage2() {
        return newImage2;
    }

    public void setNewImage2(int newImage2) {
        this.newImage2 = newImage2;
    }




}
