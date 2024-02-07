package sk.spse.olejnik.fithub;

import java.util.HashMap;

public class PublishedWorkoutData {
    private String time;
    private String author;
    private String id;
    private HashMap<String, Boolean> ratings;
    private WorkoutData workout;

    // Published workout data constructor
    public PublishedWorkoutData(String time, String author, String id,HashMap<String, Boolean> ratings, WorkoutData workout) {
        this.time = time;
        this.author = author;
        this.id = id;
        this.ratings = ratings;
        this.workout = workout;
    }

    // Getters and setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public HashMap<String, Boolean> getRatings() {
        return ratings;
    }

    public void setRatings(HashMap<String, Boolean> ratings) {
        this.ratings = ratings;
    }

    public WorkoutData getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutData workout) {
        this.workout = workout;
    }

}
