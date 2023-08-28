package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class WorkoutsFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    ConstraintLayout content;
    TextView duration, muscleGroup, title;
    ImageView thumbnail;
    FloatingActionButton fab;



    public WorkoutsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thumbnail = view.findViewById(R.id.thumbnail);
        fab = view.findViewById(R.id.fab);


        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.workouts) {

                    return true;
                }
                else if (id == R.id.exercises) {
                    Navigation.findNavController(view).navigate(R.id.action_Workouts_to_Exercise);
                    return true;
                }
                else if (id == R.id.profile) {
                    Navigation.findNavController(view).navigate(R.id.action_Workouts_to_Profile);
                    return true;
                }
                else if (id == R.id.settings) {
                    Navigation.findNavController(view).navigate(R.id.action_Workouts_to_Settings);
                    return true;
                }
                else{return false;}
            }});

        fab.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            int backStackEntryCount = fragmentManager.getBackStackEntryCount();

            for (int i = 0; i < backStackEntryCount; i++) {
                fragmentManager.popBackStackImmediate();
            }
//            bottomNavigationView.setVisibility(View.GONE);
//            onPause();
            Navigation.findNavController(view).navigate(R.id.action_Workouts_to_WorkoutCreation);
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        bottomNavigationView.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.workouts);
    }
}