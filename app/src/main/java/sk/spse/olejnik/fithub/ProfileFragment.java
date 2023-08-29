package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    TableLayout tableLayout;

    List<WorkoutData> workouts;


    private WorkoutViewModel workoutViewModel;


    TextView nickname, joined;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    String titleValue, targetedMGValue, json;

    List<WorkoutExercises> exercises;

    Gson gson;
    Type type;

    private SharedPreferences preferences;


    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nickname = view.findViewById(R.id.nicknameText);
        joined = view.findViewById(R.id.joinedText);
        tableLayout = view.findViewById(R.id.tableWorkoutList);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

        exercises = new ArrayList<>();
        workouts = new ArrayList<>();
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();
        preferences = getActivity().getBaseContext().getSharedPreferences("workouts", Context.MODE_PRIVATE);

        gson = new Gson();
        json = preferences.getString("workoutsJson", "");
        type = new TypeToken<List<WorkoutData>>() {
        }.getType();
        workouts = gson.fromJson(json, type);

        if (!Objects.equals(json, "")) {
            for (WorkoutData workout: workouts) {
                createNewWorkout(workout.getTitle(), workout.getTargetedMuscleGroups(), workout.getExercises());
            }
        }

        workoutViewModel.getWorkoutLiveData().observe(getViewLifecycleOwner(), new Observer<WorkoutData>() {
            @Override
            public void onChanged(@Nullable WorkoutData workoutData) {
                if (workoutData != null) {
                    titleValue = workoutData.getTitle();
                    targetedMGValue = workoutData.getTargetedMuscleGroups();
                    exercises = workoutData.getExercises();
                    SharedPreferences.Editor editor = preferences.edit();
                    List<WorkoutData> list = new ArrayList<>();
                    list.add(new WorkoutData(titleValue, targetedMGValue, exercises));
                    gson = new Gson();
                    String workoutsJson = gson.toJson(list);
                    editor.putString("workoutsJson", workoutsJson);
                    editor.apply();
                    createNewWorkout(titleValue, targetedMGValue, exercises);
                }
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.workouts) {
                    Navigation.findNavController(view).navigate(R.id.action_Profile_to_Workouts);
                    return true;
                }
                else if (id == R.id.exercises) {
                    Navigation.findNavController(view).navigate(R.id.action_Profile_to_Exercise);
                    return true;
                }
                else if (id == R.id.profile) {
                    return true;
                }
                else if (id == R.id.settings) {
                    Navigation.findNavController(view).navigate(R.id.action_Profile_to_Settings);
                    return true;
                }
                else{return false;}
            }});

        checkSignedIn();

    }

    public void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            nickname.setText("Not logged in");
        } else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            final String current = user.getEmail();
            db.collection("users")
                    .whereEqualTo("email", current)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    nickname.setText((CharSequence) document.get("nickname"));
                                    joined.setText("Joined: " + (CharSequence) document.get("joined"));
                                }
                            }

            Log.i("here is the user", auth.getCurrentUser().getEmail());

        }
    });}
    }

    @SuppressLint("SetTextI18n")
    void createNewWorkout(String title, String targetedMG, List<WorkoutExercises> exercises) {
        TableRow tableRow = new TableRow(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layoutDraft = inflater.inflate(R.layout.layout_workout_draft, null);
        TextView workoutTitle = layoutDraft.findViewById(R.id.draftTitle);
        TextView exerciseCount = layoutDraft.findViewById(R.id.exerciseCount);
        TextView workoutMG = layoutDraft.findViewById(R.id.draftTargetedMG);
        workoutTitle.setText(title);
        workoutMG.setText(targetedMG);
        exerciseCount.setText(exercises.size() + " Exercises");
        tableRow.addView(layoutDraft);
        tableLayout.addView(tableRow);

    }

}