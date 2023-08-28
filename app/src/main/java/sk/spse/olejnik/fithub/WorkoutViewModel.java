package sk.spse.olejnik.fithub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WorkoutViewModel extends AndroidViewModel {

    public WorkoutViewModel(@NonNull Application application) {super(application);}

    private MutableLiveData<WorkoutData> workoutLiveData = new MutableLiveData<>();

    // Constructor, getters, and setters

    public LiveData<WorkoutData> getWorkoutLiveData() {
        return workoutLiveData;
    }

    // Method to update all fields in one go
    public void updateWrokoutData(WorkoutData newData) {
        workoutLiveData.setValue(newData);
    }
}
