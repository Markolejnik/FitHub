package sk.spse.olejnik.fithub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CalendarFragment extends Fragment {
    ConstraintLayout root;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    CalendarView calendarView;
//    Button signOut, delete;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    private SharedPreferences preferences, preferences2;

    public CalendarFragment() {}

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        signOut = view.findViewById(R.id.signOut);
//        delete = view.findViewById(R.id.deleteUser);
        root = view.findViewById(R.id.calendarRoot);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        calendarView = view.findViewById(R.id.calendarView);
        fab = view.findViewById(R.id.fab);


        bottomNavigationView.setSelectedItemId(R.id.calendar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.workouts) {
                    Navigation.findNavController(view).navigate(R.id.action_Calendar_to_Workouts);
                    return true;
                }
                else if (id == R.id.exercises) {
                    Navigation.findNavController(view).navigate(R.id.action_Calendar_to_Exercise);
                    return true;
                }
                else if (id == R.id.profile) {
                    Navigation.findNavController(view).navigate(R.id.action_Calendar_to_Profile);
                    return true;
                }
                else if (id == R.id.calendar) {

                    return true;
                }
                else{return false;}
            }
        });
        fab.setOnClickListener(v -> {
            Navigation.findNavController(root).navigate(R.id.action_Calendar_to_CalendarExercise);
        });
//        checkSignedIn();
//
//        delete.setOnClickListener(v1 -> deleteUser());
    }

//    public void checkSignedIn() {
//
//        if (auth.getCurrentUser() == null) {
//            signOut.setText("Sign In");
//            signOut.setOnClickListener(v1 -> {
//                getActivity().finish();
//                Intent intent = new Intent(getActivity(), RegisterActivity.class);
//                startActivity(intent);
//            });
//        } else {
//
//            user = FirebaseAuth.getInstance().getCurrentUser();
//            final String current = user.getEmail();
//            db.collection("users")
//                    .whereEqualTo("email", current)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                signOut.setText("Sign Out");
//                                signOut.setOnClickListener(v1 -> {
//                                    FirebaseAuth.getInstance().signOut();
//                                    getActivity().finish();
//                                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
//                                    startActivity(intent);
//                                });
//                                Log.i("here is the user", auth.getCurrentUser().getEmail());
//                            }
//
//                        }
//                    });}
//    }
//
//    public void deleteUser(){
//        try {
//
//            user.delete()
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                preferences = getActivity().getBaseContext().getSharedPreferences("database", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = preferences.edit();
//                                editor.clear();
//                                editor.apply();
//                                preferences2 = getActivity().getBaseContext().getSharedPreferences("workouts", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor2 = preferences2.edit();
//                                editor2.clear();
//                                editor2.apply();
//                                Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_LONG).show();
//                                checkSignedIn();
//                            }
//                        }
//                    });
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "Account deletion failed", Toast.LENGTH_SHORT).show();
//        }
//    }
}