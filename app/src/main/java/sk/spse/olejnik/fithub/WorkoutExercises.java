package sk.spse.olejnik.fithub;

import java.util.List;

public class WorkoutExercises {

    private String title;
    private String type;
    private String link;
    private Integer image1;
    private Integer image2;
    private Integer imageMini;
    private List<String> muscleGroupsUsed;
    private Integer sets;
    private Integer reps;
    private boolean isTimeFormat;


    // Workout exercises constructor
    public WorkoutExercises(String title, String type, String link, Integer image1, Integer image2, Integer imageMini, List<String> muscleGroupsUsed, Integer sets, Integer reps, boolean isTimeFormat) {
        this.title = title;
        this.type = type;
        this.link = link;
        this.image1 = image1;
        this.image2 = image2;
        this.imageMini = imageMini;
        this.muscleGroupsUsed = muscleGroupsUsed;
        this.sets = sets;
        this.reps = reps;
        this.isTimeFormat = isTimeFormat;
    }

    // Getters and setters
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

    public Integer getImage1() {
        return image1;
    }

    public void setImage1(Integer image1) {
        this.image1 = image1;
    }

    public Integer getImage2() {
        return image2;
    }

    public void setImage2(Integer image2) {
        this.image2 = image2;
    }

    public Integer getImageMini() {
        return imageMini;
    }

    public void setImageMini(Integer imageMini) {
        this.imageMini = imageMini;
    }

    public List<String> getMuscleGroupsUsed() {
        return muscleGroupsUsed;
    }

    public void setMuscleGroupsUsed(List<String> muscleGroupsUsed) {
        this.muscleGroupsUsed = muscleGroupsUsed;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public boolean isTimeFormat() {
        return isTimeFormat;
    }

    public void setTimeFormat(boolean timeFormat) {
        isTimeFormat = timeFormat;
    }

}
