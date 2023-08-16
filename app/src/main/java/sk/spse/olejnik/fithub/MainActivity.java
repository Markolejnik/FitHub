package sk.spse.olejnik.fithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    MenuItem workouts, profile, settings;
    FragmentContainerView workoutsContainer, exerciseContainer, profileContainer, settingsContainer;

    FirebaseAuth auth;

    private ExerciseViewModel exerciseViewModel;

    public static int height, width, image1, image2, bottomNavHeight;

    static String title, link, type;
    static List<String> muscleGroups;

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

        WorkoutsFragment workoutsFragment = new WorkoutsFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        SettingsFragment settingsFragment = new SettingsFragment();
        ExerciseFragment exerciseFragment = new ExerciseFragment();

        workoutsContainer = findViewById(R.id.workoutsContainerView);
        exerciseContainer = findViewById(R.id.exerciseContainerView);
        profileContainer = findViewById(R.id.profileContainerView);
        settingsContainer = findViewById(R.id.settingsContainerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavHeight = bottomNavigationView.getHeight();

        workoutsContainer.setVisibility(View.VISIBLE);
        exerciseContainer.setVisibility(View.GONE);
        profileContainer.setVisibility(View.GONE);
        settingsContainer.setVisibility(View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.workoutsContainerView, workoutsFragment)
                .commit();
        bottomNavigationView.setSelectedItemId(R.id.workouts);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.workouts && workoutsContainer.getVisibility() == View.GONE) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.workoutsContainerView, workoutsFragment)
                                .commit();
                        workoutsContainer.setVisibility(View.VISIBLE);
                        exerciseContainer.setVisibility(View.GONE);
                        profileContainer.setVisibility(View.GONE);
                        settingsContainer.setVisibility(View.GONE);
                        return true;
                    }
                else if (id == R.id.exercises && exerciseContainer.getVisibility() == View.GONE) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.exerciseContainerView, exerciseFragment)
                            .commit();
                    exerciseContainer.setVisibility(View.VISIBLE);
                    profileContainer.setVisibility(View.GONE);
                    workoutsContainer.setVisibility(View.GONE);
                    settingsContainer.setVisibility(View.GONE);
                    return true;
                }
                else if (id == R.id.profile && profileContainer.getVisibility() == View.GONE) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.profileContainerView, profileFragment)
                                .commit();
                    profileContainer.setVisibility(View.VISIBLE);
                    workoutsContainer.setVisibility(View.GONE);
                    exerciseContainer.setVisibility(View.GONE);
                    settingsContainer.setVisibility(View.GONE);
                    return true;
                    }
                else if (id == R.id.settings && settingsContainer.getVisibility() == View.GONE) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.settingsContainerView, settingsFragment)
                                .commit();
                        settingsContainer.setVisibility(View.VISIBLE);
                        exerciseContainer.setVisibility(View.GONE);
                        profileContainer.setVisibility(View.GONE);
                        workoutsContainer.setVisibility(View.GONE);
                        return true;
                    }
                else{return false;}
            }});

    }
    public void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        } else {
            Log.i("here is the user", auth.getCurrentUser().getEmail());
        }
    }
//    public void saveData() {
//        Bundle args = getIntent().getExtras();
//        if (args != null) {
//            title = args.getString("title");
//            muscleGroups = args.getStringArrayList("groups");
//
//            image1 = args.getInt("img1");
//            image2 = args.getInt("img2");
//            link = args.getString("link");
//            type = args.getString("type");

//            // Do something with the data, like passing it to another Fragment
//        }
//    }
    public ExerciseViewModel getExerciseViewModel() {
        return exerciseViewModel;
    }


}