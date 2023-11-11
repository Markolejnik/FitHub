package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    TableLayout draftLayout;
    LinearLayout workoutLayout;
    ConstraintLayout root, workoutContainer, draftContainer;
    TextView nickname, joined;
    Button workoutButton, draftButton;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    ImageView settings;

    List<WorkoutData> workouts, userWorkouts, userDrafts;



    private WorkoutViewModel workoutViewModel;



    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    String titleValue, targetedMGValue, json, currentNickname;
    Boolean isWorkout;

    List<WorkoutExercises> exercises;
    List<String> times, usernames;

    Gson gson;
    Type type;

    private SharedPreferences preferences;

    WorkoutsAdapter workoutsAdapter;

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
        root = view.findViewById(R.id.constraintLayout);
        settings = view.findViewById(R.id.settings);
        nickname = view.findViewById(R.id.nicknameText);
        joined = view.findViewById(R.id.joinedText);
        draftLayout = view.findViewById(R.id.tableDraftList);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipe = view.findViewById(R.id.swipeRefreshLayout);
//        workoutLayout = view.findViewById(R.id.layoutWorkout);
        draftContainer = view.findViewById(R.id.draftContainer);
        workoutContainer = view.findViewById(R.id.workoutContainer);
        draftButton = view.findViewById(R.id.draftButton);
        workoutButton = view.findViewById(R.id.workoutButton);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

        draftContainer.setVisibility(View.GONE);

        isWorkout = true;
        exercises = new ArrayList<>();
        workouts = new ArrayList<>();
        times = new ArrayList<>();
        userWorkouts = new ArrayList<>();
        usernames = new ArrayList<>();
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();
        preferences = getActivity().getBaseContext().getSharedPreferences("workouts", Context.MODE_PRIVATE);

        gson = new Gson();
        json = preferences.getString("workoutsJson", "");
        type = new TypeToken<List<WorkoutData>>() {
        }.getType();
        workouts = gson.fromJson(json, type);

        if (!Objects.equals(json, "")) {
            for (WorkoutData workout: workouts) {
                createNewDraft(workout.getTitle(), workout.getTargetedMuscleGroups(), workout.getExercises());
            }
        }

        workoutViewModel.getWorkoutLiveData().observe(getViewLifecycleOwner(), new Observer<WorkoutData>() {
            @Override
            public void onChanged(@Nullable WorkoutData workoutData) {
                if (workoutData != null) {
                    titleValue = workoutData.getTitle();
                    targetedMGValue = workoutData.getTargetedMuscleGroups();
                    exercises = workoutData.getExercises();
                    List<WorkoutData> list = new ArrayList<>();
                    list.add(new WorkoutData(titleValue, targetedMGValue, exercises));
                    SharedPreferences.Editor editor = preferences.edit();
                    gson = new Gson();
                    String workoutsJson = gson.toJson(list);
                    editor.putString("workoutsJson", workoutsJson);
                    editor.apply();
                    createNewDraft(titleValue, targetedMGValue, exercises);
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
                else if (id == R.id.calendar) {
                    Navigation.findNavController(view).navigate(R.id.action_Profile_to_Calendar);
                    return true;
                }
                else{return false;}
            }
        });

        settings.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_Profile_to_Settings);
        });

        draftButton.setOnClickListener(v -> {
            if (!(draftButton.getBackgroundTintList() == ContextCompat.getColorStateList(getContext(), R.color.colorAccent))) {
                workoutContainer.setVisibility(View.GONE);
                draftContainer.setVisibility(View.VISIBLE);
                draftButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorAccent));
                workoutButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.barbackground));
            }
        });

        workoutButton.setOnClickListener(v -> {
            if (!(workoutButton.getBackgroundTintList() == ContextCompat.getColorStateList(getContext(), R.color.colorAccent))) {
                draftContainer.setVisibility(View.GONE);
                workoutContainer.setVisibility(View.VISIBLE);
                workoutButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorAccent));
                draftButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.barbackground));
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                workoutsAdapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });

        workoutsAdapter = new WorkoutsAdapter(userWorkouts, usernames, times, getContext());
        workoutsAdapter.setOnEntryClickListener(new WorkoutsAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position) {
                createWorkoutDetail(workouts.get(position).getTitle(), workouts.get(position).getTargetedMuscleGroups(), workouts.get(position).getExercises());
            }
        });
        checkSignedIn();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(workoutsAdapter);


    }

    public void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            nickname.setText("Not logged in");
            Log.i("workouts local", auth.getCurrentUser().getEmail());


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
                                    currentNickname = document.get("nickname").toString();
                                    nickname.setText(currentNickname);
                                    joined.setText("Joined: " + (CharSequence) document.get("joined"));
//                                    userWorkouts = new ArrayList<>();
//                                    List<String> temp = (List<String>) document.get("workouts");
//                                    gson = new Gson();
//                                    type = new TypeToken<WorkoutData>() {
//                                    }.getType();
//                                    for (String t: temp) {
//                                        WorkoutData w = gson.fromJson(t, type);
//                                        userWorkouts.add(w);
//                                    }
//                                    for (WorkoutData workout: userWorkouts) {
//                                        createNewWorkout(workout.getTitle(), workout.getTargetedMuscleGroups(), workout.getExercises());
//                                    }
                                }
                            }




            Log.i("here is the user", auth.getCurrentUser().getEmail());
            Log.i("workouts public", auth.getCurrentUser().getEmail());

        }
    });}
    db.collection("workouts")
            .orderBy("published", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        int size = document.getData().size();

                        Map<String, Object> data = document.getData();
//                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        for (int i = 0; i < size/3; i++) {
                                Log.i("author", (String) data.get("author"));
                                Log.i("author", currentNickname);
                            if (Objects.equals((String) data.get("author"), currentNickname)) {
                                Log.i("executing", currentNickname);
                                String author = (String) data.get("author");
                                String workout = (String) data.get("workout");
                                Timestamp time = (Timestamp) data.get("published");

                                if (author != null) {
                                    usernames.add(author);
                                }
                                if (workout != null) {
                                    Log.i("asgds", workout);
                                    gson = new Gson();
                                    type = new TypeToken<WorkoutData>() {
                                    }.getType();
                                    WorkoutData w = gson.fromJson(workout, type);
                                    userWorkouts.add(w);
                                    //                            workouts.add((WorkoutData) entry.getValue());
                                }
                                if (time != null) {
                                    Instant instant = time.toDate().toInstant();
                                    LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm", Locale.getDefault());
                                    String formattedDate = formatter.format(localDateTime);
                                    times.add(formattedDate);
                                }
                                workoutsAdapter.notifyItemInserted(i);
                                Log.i("workoutsUser", userWorkouts.size() + "");
                            }
                        }
                    }

                }
            });
    }

    @SuppressLint("SetTextI18n")
    void createNewDraft(String title, String targetedMG, List<WorkoutExercises> exercises) {
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
        draftLayout.addView(tableRow);

    }
//    @SuppressLint("SetTextI18n")
//    void createNewWorkout(String title, String targetedMG, List<WorkoutExercises> exercises) {
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View layoutWorkout = inflater.inflate(R.layout.layout_workout, null);
//        TextView user = layoutWorkout.findViewById(R.id.username);
//        TextView published = layoutWorkout.findViewById(R.id.published);
//        TextView workoutTitle = layoutWorkout.findViewById(R.id.workoutTitle);
//        TextView exerciseCount = layoutWorkout.findViewById(R.id.exerciseCount);
//        TextView workoutMG = layoutWorkout.findViewById(R.id.targetedMG);
//        ImageView profilePicture = layoutWorkout.findViewById(R.id.profilePicture);
//
//
//        layoutWorkout.setOnClickListener(v -> {
//            createWorkoutDetail(title, targetedMG, exercises);
//        });
//        published.setVisibility(View.GONE);
//        profilePicture.setVisibility(View.VISIBLE);
//        user.setText(nickname.getText());
//        workoutTitle.setText(title);
//        workoutMG.setText(targetedMG);
//        workoutLayout.setPadding(20, 20, 20, 20);
//        exerciseCount.setText(exercises.size() + " Exercises");
//        workoutLayout.addView(layoutWorkout);
//
//    }

    void createWorkoutDetail(String title, String targetedMG, List<WorkoutExercises> exercises) {
        WorkoutData data = new WorkoutData(title, targetedMG, exercises);
        workoutViewModel.updateWorkoutData(data);
        Navigation.findNavController(root).navigate(R.id.action_Profile_to_WorkoutDetail);
    }

}