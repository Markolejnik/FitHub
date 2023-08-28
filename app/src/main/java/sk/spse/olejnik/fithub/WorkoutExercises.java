package sk.spse.olejnik.fithub;

import java.util.List;

public class WorkoutExercises {

    private String title;
    private String type;
    private String link;
    private String image1;
    private String image2;
    private List<String> muscleGroupsUsed;
    private int sets;
    private int reps;
    private boolean isTimeFormat;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public List<String> getMuscleGroupsUsed() {
        return muscleGroupsUsed;
    }

    public void setMuscleGroupsUsed(List<String> muscleGroupsUsed) {
        this.muscleGroupsUsed = muscleGroupsUsed;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public boolean isTimeFormat() {
        return isTimeFormat;
    }

    public void setTimeFormat(boolean timeFormat) {
        isTimeFormat = timeFormat;
    }


}
