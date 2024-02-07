package sk.spse.olejnik.fithub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WorkoutViewModel extends AndroidViewModel {

    public WorkoutViewModel(@NonNull Application application) {super(application);}

    // Workout live data
    private MutableLiveData<WorkoutData> workoutLiveData = new MutableLiveData<>();

    // Getter
    public LiveData<WorkoutData> getWorkoutLiveData() {
        return workoutLiveData;
    }

    // Update all data in one go
    public void updateWorkoutData(WorkoutData newData) {
        workoutLiveData.setValue(newData);
    }
}
