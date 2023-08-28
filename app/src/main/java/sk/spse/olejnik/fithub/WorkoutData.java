package sk.spse.olejnik.fithub;

import java.util.HashMap;
import java.util.List;

public class WorkoutData {

    private String title;
    private String targetedMuscleGroups;
    private List<WorkoutExercises> exercises;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetedMuscleGroups() {
        return targetedMuscleGroups;
    }

    public void setTargetedMuscleGroups(String targetedMuscleGroups) {
        this.targetedMuscleGroups = targetedMuscleGroups;
    }

    public List<WorkoutExercises> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExercises> exercises) {
        this.exercises = exercises;
    }

}
