package sk.spse.olejnik.fithub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ExerciseViewModel extends AndroidViewModel {

    // Live data for exercise detail
    private MutableLiveData<ExerciseData> exerciseLiveData = new MutableLiveData<>();

    // Live data for workout creation
    private MutableLiveData<ExerciseData> creationLiveData = new MutableLiveData<>();

    // View Model constructor
    public ExerciseViewModel(@NonNull Application application) {
        super(application);
    }

    // Getters
    public LiveData<ExerciseData> getExerciseLiveData() {
        return exerciseLiveData;
    }

    public LiveData<ExerciseData> getCreationLiveData() {return creationLiveData;}

    // Methods to update all fields in one go
    public void updateExerciseData(ExerciseData newData) {
        exerciseLiveData.setValue(newData);
    }

    public void updateCreationData(ExerciseData newData) {
        creationLiveData.setValue(newData);
    }

}
