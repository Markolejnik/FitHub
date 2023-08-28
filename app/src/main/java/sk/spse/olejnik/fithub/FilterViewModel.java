package sk.spse.olejnik.fithub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FilterViewModel extends AndroidViewModel {
    private MutableLiveData<FilterData> filterLiveData = new MutableLiveData<>();

    // Constructor
    public FilterViewModel(@NonNull Application application) {
        super(application);
    }

    // Constructor, getters, and setters

    public LiveData<FilterData> getFilterLiveData() {
        return filterLiveData;
    }

    // Method to update all fields in one go
    public void updateExerciseData(FilterData newData) {
        filterLiveData.setValue(newData);
    }
}
