package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    ConstraintLayout root, workoutContainer, privateContainer;
    TextView nickname, joined;
    Button publicButton, privateButton;
    RecyclerView recyclerView, privateRecycler;
    SwipeRefreshLayout swipe, swipe2;
    ImageView settings, profilePicture;

    List<PublishedWorkoutData> pWorkouts, pUserWorkouts;
    String currentNickname, url;

    private WorkoutViewModel workoutViewModel;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    WorkoutsAdapter privateAdapter, publicAdapter;

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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkSignedIn();
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.constraintLayout);
        settings = view.findViewById(R.id.settings);
        nickname = view.findViewById(R.id.nicknameText);
        joined = view.findViewById(R.id.joinedText);
        profilePicture = view.findViewById(R.id.profilePicture);
        privateRecycler = view.findViewById(R.id.recyclerView2);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipe = view.findViewById(R.id.swipeRefreshLayout);
        swipe2 = view.findViewById(R.id.swipeRefreshLayout2);
        privateContainer = view.findViewById(R.id.privateContainer);
        workoutContainer = view.findViewById(R.id.publicContainer);
        privateButton = view.findViewById(R.id.privateButton);
        publicButton = view.findViewById(R.id.publicButton);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

        privateContainer.setVisibility(View.GONE);

        pWorkouts = new ArrayList<>();
        pUserWorkouts = new ArrayList<>();

        // Get View Model
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();


        // Handle bottom navigation clicks
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {

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
        });

        // Navigate to settings
        settings.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_Profile_to_Settings);
        });

        // Handle showing private workouts
        privateButton.setOnClickListener(v -> {
            if (!(privateButton.getBackgroundTintList() == ContextCompat.getColorStateList(getContext(), R.color.colorAccent))) {
                workoutContainer.setVisibility(View.GONE);
                privateContainer.setVisibility(View.VISIBLE);
                privateButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorAccent));
                publicButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.barbackground));
            }
        });

        // Handle showing public workouts
        publicButton.setOnClickListener(v -> {
            if (!(publicButton.getBackgroundTintList() == ContextCompat.getColorStateList(getContext(), R.color.colorAccent))) {
                privateContainer.setVisibility(View.GONE);
                workoutContainer.setVisibility(View.VISIBLE);
                publicButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorAccent));
                privateButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.barbackground));
            }
        });

        // Refresh public workouts
        swipe.setOnRefreshListener(() -> {

            retrievePublicData();
            publicAdapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
        });

        // Refresh private workouts
        swipe2.setOnRefreshListener(() -> {

            retrievePrivateData(user.getEmail());
            privateAdapter.notifyDataSetChanged();
            swipe2.setRefreshing(false);
        });

        // Public workouts layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Private workouts layout manager
        LinearLayoutManager privateLayoutManager = new LinearLayoutManager(getContext());
        privateLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        privateRecycler.setLayoutManager(privateLayoutManager);

    }

    // Check if user is signed in
    public void checkSignedIn() {

        // Check for network availability
        if (NetworkUtils.isNetworkAvailable(getContext())) {

            if (auth.getCurrentUser() == null) {
                nickname.setText("Not logged in");
                Log.i("workouts local", auth.getCurrentUser().getEmail());

            } else {
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String current = user.getEmail();

                // Get workout data
                retrievePublicData();
                retrievePrivateData(current);

                // Load users info
                db.collection("users")
                        .whereEqualTo("email", current)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    currentNickname = document.get("nickname").toString();
                                    nickname.setText(currentNickname);
                                    joined.setText("Joined: " + (CharSequence) document.get("joined"));
                                    try {
                                        url = document.get("pfpUrl").toString();
                                        Glide.with(getContext())
                                                .load(url)
                                                .into(profilePicture);
                                        profilePicture.setClipToOutline(true);
                                    } catch (Exception e) {
                                        Log.d("Profile Picture Exception", "No Profile Picture Found");
                                    }
                                }
                            }
                        });
            }
        } else Toast.makeText(getContext(), "No/Limited Network Connectivity, try again later please", Toast.LENGTH_LONG).show();

    }

    // Open workout detail
    private void createWorkoutDetail(String title, String targetedMG, List<WorkoutExercises> exercises) {
        WorkoutData data = new WorkoutData(title, targetedMG, exercises);
        workoutViewModel.updateWorkoutData(data);
        Navigation.findNavController(root).navigate(R.id.action_Profile_to_WorkoutDetail);
    }

    // Get public workouts data
    public void retrievePublicData() {
        if (pWorkouts != null) pWorkouts.clear();
        db.collection("workouts").whereEqualTo("author", user.getDisplayName()).orderBy("published", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        int size = document.getData().size();
                        Map<String, Object> data = document.getData();
                        for (int i = 0; i < size / 3; i++) {
                            String formattedDate = "";
                            WorkoutData w = null;
                            String author = (String) data.get("author");
                            HashMap<String, Object> workout = (HashMap<String, Object>) data.get("workout");
                            Timestamp time = (Timestamp) data.get("published");
                            HashMap<String, Boolean> rating = (HashMap<String, Boolean>) data.get("rating");

                            // Get workout data
                            if (workout != null) {
                                ArrayList<HashMap<String, Object>> exercises = (ArrayList<HashMap<String, Object>>) workout.get("exercises");
                                ArrayList<WorkoutExercises> workoutExercises = new ArrayList<>();
                                if (exercises != null) {
                                    for (HashMap<String, Object> exercise : exercises) {
                                        WorkoutExercises workoutExercise = new WorkoutExercises((String) exercise.get("name"), (String) exercise.get("type"), (String) exercise.get("link"), Long.valueOf((String) String.valueOf(exercise.get("image1"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("image2"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("imageMini"))).intValue(), (List<String>) exercise.get("muscleGroup"), Long.valueOf((String) String.valueOf(exercise.get("sets"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("reps"))).intValue(), (Boolean) exercise.get("repstosec"));
                                        workoutExercises.add(workoutExercise);
                                    }
                                }
                                String title = (String) workout.get("title");
                                String targetedMuscleGroups = (String) workout.get("targetMuscleGroups");
                                w = new WorkoutData(title, targetedMuscleGroups, workoutExercises);
                            }

                            // Get time data
                            if (time != null) {
                                Instant instant = time.toDate().toInstant();
                                LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm", Locale.getDefault());
                                formattedDate = formatter.format(localDateTime);
                            }

                            // Add workout to list
                            pWorkouts.add(new PublishedWorkoutData(formattedDate, author, document.getId(), rating, w));

                            // Initialize public workouts adapter
                            publicAdapter = new WorkoutsAdapter(pWorkouts, currentNickname, true, getContext());
                            publicAdapter.setOnEntryClickListener((view, position) -> createWorkoutDetail(pWorkouts.get(position).getWorkout().getTitle(), pWorkouts.get(position).getWorkout().getTargetedMuscleGroups(), pWorkouts.get(position).getWorkout().getExercises()));
                            recyclerView.setAdapter(publicAdapter);
                            publicAdapter.notifyItemInserted(i);
                        }
                    }
                }
            }
        });

    }

    // Get private workouts data
    public void retrievePrivateData(String current) {
        if (pUserWorkouts != null) pUserWorkouts.clear();
        db.collection("users")
                .whereEqualTo("email", current)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            ArrayList<HashMap<String, Object>> workouts = (ArrayList<HashMap<String, Object>>) data.get("workouts");
                            Integer size = workouts.size();
                            for (int i = 0; i < size; i++) {
                                HashMap<String, Object> workout = workouts.get(i);
                                String author = "";
                                WorkoutData w = null;

                                // Get author data
                                if (workout.get("author") != null)
                                    author = workout.get("author").toString();

                                // Get workout data
                                if (workout != null) {
                                    ArrayList<HashMap<String, Object>> exercises = (ArrayList<HashMap<String, Object>>) workout.get("exercises");
                                    ArrayList<WorkoutExercises> workoutExercises = new ArrayList<>();
                                    for (HashMap<String, Object> exercise : exercises) {
                                        WorkoutExercises workoutExercise = new WorkoutExercises((String) exercise.get("name"), (String) exercise.get("type"), (String) exercise.get("link"), Long.valueOf((String) String.valueOf(exercise.get("image1"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("image2"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("imageMini"))).intValue(), (List<String>) exercise.get("muscleGroup"), Long.valueOf((String) String.valueOf(exercise.get("sets"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("reps"))).intValue(), (Boolean) exercise.get("repstosec"));
                                        workoutExercises.add(workoutExercise);
                                    }
                                    String title = (String) workout.get("title");
                                    String targetedMuscleGroups = (String) workout.get("targetMuscleGroups");
                                    w = new WorkoutData(title, targetedMuscleGroups, workoutExercises);
                                }

                                // Add workout to list
                                pUserWorkouts.add(new PublishedWorkoutData("", author, document.getId(), null, w));

                                // Initialize private workouts adapter
                                privateAdapter = new WorkoutsAdapter(pUserWorkouts, currentNickname, true, getContext());
                                privateAdapter.setOnEntryClickListener((view, position) -> createWorkoutDetail(pUserWorkouts.get(position).getWorkout().getTitle(), pUserWorkouts.get(position).getWorkout().getTargetedMuscleGroups(), pUserWorkouts.get(position).getWorkout().getExercises()));
                                privateRecycler.setAdapter(privateAdapter);
                                privateAdapter.notifyItemInserted(i);
                            }
                        }
                    }
                });
    }

}