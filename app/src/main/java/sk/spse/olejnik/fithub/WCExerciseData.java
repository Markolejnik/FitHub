package sk.spse.olejnik.fithub;

public class WCExerciseData {

    private Integer sets, reps;
    private Boolean repstosec;


    // Workout Creation Exercise Data Constructor
    public WCExerciseData(Integer sets, Integer reps, Boolean repstosec) {
        this.sets = sets;
        this.reps = reps;
        this.repstosec = repstosec;
    }

    // Getters and setters
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

}

