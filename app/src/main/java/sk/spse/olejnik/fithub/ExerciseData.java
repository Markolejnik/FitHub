package sk.spse.olejnik.fithub;

import java.util.List;

public class ExerciseData {

    private String newTitle, newLink, newType;
    private List<String> newMuscleGroups;
    private Integer newImage1, newImage2;


    public ExerciseData() {}

    public ExerciseData(String newTitle, String newLink, String newType, List<String> newMuscleGroups, Integer newImage1, Integer newImage2) {
        this.newTitle = newTitle;
        this.newLink = newLink;
        this.newType = newType;
        this.newMuscleGroups = newMuscleGroups;
        this.newImage1 = newImage1;
        this.newImage2 = newImage2;
    }


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

    public Integer getNewImage1() {
        return newImage1;
    }

    public void setNewImage1(Integer newImage1) {
        this.newImage1 = newImage1;
    }

    public Integer getNewImage2() {
        return newImage2;
    }

    public void setNewImage2(Integer newImage2) {
        this.newImage2 = newImage2;
    }




}
