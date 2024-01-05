package sk.spse.olejnik.fithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  {


    FirebaseAuth auth;

    private ExerciseViewModel exerciseViewModel;
    private FilterViewModel filterViewModel;
    private WorkoutViewModel workoutViewModel;
    private ProgressViewModel progressViewModel;

    public static int height, width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        auth = FirebaseAuth.getInstance();
        checkSignedIn();

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        filterViewModel = new ViewModelProvider(this).get(FilterViewModel.class);
        workoutViewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        progressViewModel = new ViewModelProvider(this).get(ProgressViewModel.class);

        WorkoutsFragment workoutsFragment = new WorkoutsFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        CalendarFragment calendarFragment = new CalendarFragment();
        ExerciseFragment exerciseFragment = new ExerciseFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));
        }

    }
    public void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        } else {
            Log.i("here is the user", auth.getCurrentUser().getEmail());
        }
    }
    public ExerciseViewModel getExerciseViewModel() {
        return exerciseViewModel;
    }

    public FilterViewModel getFilterViewModel() {return filterViewModel;}

    public WorkoutViewModel getWorkoutViewModel() {return workoutViewModel;}

    public ProgressViewModel getProgressViewModel() {return progressViewModel;}


}