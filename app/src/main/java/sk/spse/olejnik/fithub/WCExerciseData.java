package sk.spse.olejnik.fithub;

import java.util.List;

public class WCExerciseData {

    private Integer sets, reps;
    private Boolean repstosec;

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

    public Boolean getRepstosec() {
        return repstosec;
    }

    public void setRepstosec(Boolean repstosec) {
        this.repstosec = repstosec;
    }




    public WCExerciseData(Integer sets, Integer reps, Boolean repstosec) {
        this.sets = sets;
        this.reps = reps;
        this.repstosec = repstosec;
    }





}

