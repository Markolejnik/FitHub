package sk.spse.olejnik.fithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    MenuItem workouts, profile, settings;
    FragmentContainerView workoutsContainer, profileContainer, settingsContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WorkoutsFragment workoutsFragment = new WorkoutsFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        workoutsContainer = findViewById(R.id.workoutsContainerView);
        profileContainer = findViewById(R.id.profileContainerView);
        settingsContainer = findViewById(R.id.settingsContainerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        workoutsContainer.setVisibility(View.VISIBLE);
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
                        profileContainer.setVisibility(View.GONE);
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
                        settingsContainer.setVisibility(View.GONE);
                        return true;
                    }
                else if (id == R.id.settings && settingsContainer.getVisibility() == View.GONE) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.settingsContainerView, settingsFragment)
                                .commit();
                        settingsContainer.setVisibility(View.VISIBLE);
                        profileContainer.setVisibility(View.GONE);
                        workoutsContainer.setVisibility(View.GONE);
                        return true;
                    }
                else{return false;}
            }});


    }

}