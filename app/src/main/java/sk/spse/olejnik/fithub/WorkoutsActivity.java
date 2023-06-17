package sk.spse.olejnik.fithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class WorkoutsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.workouts);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("here", "onOptionsItemSelected: ");
                int id = item.getItemId();
                switch (id) {
                    case R.id.workouts: {
                        return true;
                    }
                    case R.id.profile: {
                        Intent profileIntent = new Intent(WorkoutsActivity.this, SettingsActivity.class);
                        startActivity(profileIntent);
                        finish();
                        return true;
                    }
                    case R.id.settings: {
                        Intent settingsIntent = new Intent(WorkoutsActivity.this, ProfileActivity.class);
                        startActivity(settingsIntent);
                        finish();
                        return true;
                    }

                }
                return false;
            }
        });

    }
}