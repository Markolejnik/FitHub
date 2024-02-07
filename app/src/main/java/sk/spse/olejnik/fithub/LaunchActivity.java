package sk.spse.olejnik.fithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show loading screen
        setContentView(R.layout.activity_launch);

        // Navigate to main activity
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}