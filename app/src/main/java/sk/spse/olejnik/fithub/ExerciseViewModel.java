package sk.spse.olejnik.fithub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private MutableLiveData<ExerciseData> exerciseLiveData = new MutableLiveData<>();
    private MutableLiveData<ExerciseData> creationLiveData = new MutableLiveData<>();

    // Constructor
    public ExerciseViewModel(@NonNull Application application) {
        super(application);
    }

    // Constructor, getters, and setters

    public LiveData<ExerciseData> getExerciseLiveData() {
        return exerciseLiveData;
    }
    public LiveData<ExerciseData> getCreationLiveData() {return creationLiveData;}

    // Method to update all fields in one go
    public void updateExerciseData(ExerciseData newData) {
        exerciseLiveData.setValue(newData);
    }
    public void updateCreationData(ExerciseData newData) {
        creationLiveData.setValue(newData);
    }

//    private MutableLiveData<String> title = new MutableLiveData<>();
//    private MutableLiveData<List<String>> muscleGroups = new MutableLiveData<>();
//    private MutableLiveData<Integer> image1 = new MutableLiveData<>();
//    private MutableLiveData<Integer> image2 = new MutableLiveData<>();
//    private MutableLiveData<String> link = new MutableLiveData<>();
//    private MutableLiveData<String> type = new MutableLiveData<>();
//
//
//
//    private String newTitle, newLink, newType;
//    private List<String> newMuscleGroups;
//    private int newImage1, newImage2;
//
//

//
//    // Getters and Setters for each field
//    public MutableLiveData<String> getTitle() {
//        return title;
//    }
//
//    public void setTitle(String newTitle) {
//        this.title.setValue(newTitle);
//    }
//
//    public void setMuscleGroups(List<String> newMuscleGroups) {
//        this.muscleGroups.setValue(newMuscleGroups);
//    }
//
//    public MutableLiveData<Integer> getImage1() {
//        return image1;
//    }
//
//    public void setImage1(int newImage1) {
//        this.image1.setValue(newImage1);
//    }
//
//    public MutableLiveData<Integer> getImage2() {
//        return image2;
//    }
//
//    public void setImage2(int newImage2) {
//        this.image2.setValue(newImage2);
//    }
//
//    public MutableLiveData<String> getLink() {
//        return link;
//    }
//
//    public void setLink(String newLink) {
//        this.link.setValue(newLink);
//    }
//
//    public MutableLiveData<String> getType() {
//        return type;
//    }
//
//    public void setType(String newType) {
//        this.type.setValue(newType);
//    }
//
//    // New Temporary variables
//
//    public String getNewTitle() {
//        return newTitle;
//    }
//
//    public void setNewTitle(String newTitle) {
//        this.newTitle = newTitle;
//    }
//
//    public String getNewLink() {
//        return newLink;
//    }
//
//    public void setNewLink(String newLink) {
//        this.newLink = newLink;
//    }
//
//    public String getNewType() {
//        return newType;
//    }
//
//    public void setNewType(String newType) {
//        this.newType = newType;
//    }
//
//    public List<String> getNewMuscleGroups() {
//        return newMuscleGroups;
//    }
//
//    public void setNewMuscleGroups(List<String> newMuscleGroups) {
//        this.newMuscleGroups = newMuscleGroups;
//    }
//
//    public int getNewImage1() {
//        return newImage1;
//    }
//
//    public void setNewImage1(int newImage1) {
//        this.newImage1 = newImage1;
//    }
//
//    public int getNewImage2() {
//        return newImage2;
//    }
//
//    public void setNewImage2(int newImage2) {
//        this.newImage2 = newImage2;
//    }
}
