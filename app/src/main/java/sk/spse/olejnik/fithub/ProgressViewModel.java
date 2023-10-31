package sk.spse.olejnik.fithub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ProgressViewModel extends AndroidViewModel {

    private MutableLiveData<ProgressData> ProgressLiveData = new MutableLiveData<>();


    public ProgressViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ProgressData> getProgressLiveData() {
        return ProgressLiveData;
    }

    // Method to update all fields in one go
    public void updateProgressData(ProgressData newData) {
        ProgressLiveData.setValue(newData);
    }
}

