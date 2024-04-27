package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.SearchView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class WorkoutsFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    ConstraintLayout root;
    ImageView thumbnail, startSearch, logo;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    ProgressBar loadingSpinner;
    SearchView search;

    List<PublishedWorkoutData> pWorkouts;

    List<WorkoutData> workouts;
    List<String> usernames, times;

    Boolean isLoading = false;
    String currentNickname;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;
    DocumentSnapshot lastVisibleDocument = null;
    Query query;

    WorkoutsAdapter workoutsAdapter;

    private WorkoutViewModel workoutViewModel;

    public WorkoutsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkSignedIn();
        return inflater.inflate(R.layout.fragment_workouts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.workoutsLayout);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        search = view.findViewById(R.id.search);
        startSearch = view.findViewById(R.id.startSearch);
        logo = view.findViewById(R.id.logo);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipe = view.findViewById(R.id.swipeRefreshLayout);
        loadingSpinner = view.findViewById(R.id.loadingSpinner);
        thumbnail = view.findViewById(R.id.thumbnail);
        fab = view.findViewById(R.id.fab);

        loadingSpinner.setVisibility(View.GONE);
        loadingSpinner.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

        if (usernames == null) {
            pWorkouts = new ArrayList<>();
            workouts = new ArrayList<>();
            usernames = new ArrayList<>();
            times = new ArrayList<>();
        }

        // Get View Model
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();

        // Handle bottom navigation menu
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setOnItemSelectedListener(item -> {

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
            else if (id == R.id.calendar) {
                Navigation.findNavController(view).navigate(R.id.action_Workouts_to_Calendar);
                return true;
            }
            else{return false;}
        });

        // Initiate searching
        startSearch.setOnClickListener(v -> {
            startSearch.setVisibility(View.GONE);
            logo.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
        });

        // Get search query
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("searching this query", query);
                updateWorkoutList(query);
                workoutsAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        search.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                pWorkouts.clear();
                lastVisibleDocument = null;
                getWorkoutData();
                search.setVisibility(View.GONE);
                logo.setVisibility(View.VISIBLE);
                startSearch.setVisibility(View.VISIBLE);
                workoutsAdapter.notifyDataSetChanged();
            }
        });

        search.setOnCloseListener(() -> {
            if (pWorkouts.size() > 0) pWorkouts.clear();
            else pWorkouts = new ArrayList<>();
            lastVisibleDocument = null;
            getWorkoutData();
            search.setVisibility(View.GONE);
            logo.setVisibility(View.VISIBLE);
            startSearch.setVisibility(View.VISIBLE);
            return false;
        });

        // Handle "X" button press on the search bar
        int searchCloseButtonId = search.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.search.findViewById(searchCloseButtonId);
        closeButton.setOnClickListener(v -> {
            search.setQuery("", false);
            search.clearFocus();
        });

        // Handle creating a new workout
        fab.setOnClickListener(v -> {
            if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()) {
                Navigation.findNavController(view).navigate(R.id.action_Workouts_to_WorkoutCreation);
            } else {
                Toast.makeText(getContext(), "Verify your email before publishing a workout", Toast.LENGTH_LONG).show();
            }
        });

        // Refresh workout list
        swipe.setOnRefreshListener(() -> {
            if (pWorkouts.size() > 0) pWorkouts.clear();
            else pWorkouts = new ArrayList<>();
            lastVisibleDocument = null;
            getWorkoutData();
            workoutsAdapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
        });

        // Set layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Handle lazy loading on scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                if (!isLoading && dy > 0 && lastVisibleItemPosition == totalItemCount - 1) {
                    loadMoreData();
                }
            }
        });
    }

    // Retrieve workout data
    private void getWorkoutData() {

        if (NetworkUtils.isNetworkAvailable(getContext())) {

            if (lastVisibleDocument == null) {
                query = db.collection("workouts")
                        .orderBy("published", Query.Direction.DESCENDING)
                        .limit(5);
            } else {
                query = db.collection("workouts")
                        .orderBy("published", Query.Direction.DESCENDING)
                        .startAfter(lastVisibleDocument)
                        .limit(5);
            }

            retrieveData(query);
            workoutsAdapter.notifyDataSetChanged();

        } else Toast.makeText(getContext(), "No/Limited Network Connectivity, try again later please", Toast.LENGTH_LONG).show();

    }

    // Load more workouts
    private void loadMoreData() {
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            isLoading = true;
            loadingSpinner.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                getWorkoutData();
                isLoading = false;
                loadingSpinner.setVisibility(View.GONE);
            },500);
            workoutsAdapter.notifyDataSetChanged();
        } else Toast.makeText(getContext(), "No/Limited Network Connectivity, try again later please", Toast.LENGTH_LONG).show();
    }

    // Update workout list after searching
    private void updateWorkoutList(String searchPhrase) {
        if (pWorkouts.size() > 0) pWorkouts.clear();
        else pWorkouts = new ArrayList<>();
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            lastVisibleDocument = null;


//            if (lastVisibleDocument == null) {
            db.collection("workouts").get().addOnCompleteListener(task -> {
                List<QueryDocumentSnapshot> matchingDocuments = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String title = ((String) document.get("workout.title")).toLowerCase(Locale.ROOT);
                        if (title.contains(searchPhrase.toLowerCase(Locale.ROOT))) {
                            matchingDocuments.add(document);
                        }
                    }
                }
                if (matchingDocuments.size() > 0) displayDocuments(matchingDocuments);
                else Toast.makeText(getContext(), "No workouts matching your search", Toast.LENGTH_SHORT).show();
            });
//                query = db.collection("workouts")
//                        .whereGreaterThanOrEqualTo("workout.title", searchPhrase.toLowerCase(Locale.ROOT))
//                        .whereLessThan("workout.title", searchPhrase.toLowerCase(Locale.ROOT) + "\uf8ff")
////                        .orderBy(("workout.title".toLowerCase(Locale.ROOT)))
//                        .limit(5);
//            } else {
//                query = db.collection("workouts")
//                        .whereGreaterThanOrEqualTo("workout.title", searchPhrase.toLowerCase(Locale.ROOT))
//                        .whereLessThan("workout.title", searchPhrase.toLowerCase(Locale.ROOT) + "\uf8ff")
//                        .startAfter(lastVisibleDocument)
////                        .orderBy(("workout.title".toLowerCase(Locale.ROOT)))
//                        .limit(5);
//            }
//            retrieveData(query);
        } else Toast.makeText(getContext(), "No/Limited Network Connectivity, try again later please", Toast.LENGTH_LONG).show();

    }

    // Retrieve workout data
    private void retrieveData(Query query) {
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            query.get().addOnCompleteListener(task -> {
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
                                for (HashMap<String, Object> exercise : exercises) {
                                    WorkoutExercises workoutExercise = new WorkoutExercises((String) exercise.get("name"), (String) exercise.get("type"), (String) exercise.get("link"), Long.valueOf((String) String.valueOf(exercise.get("image1"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("image2"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("imageMini"))).intValue(), (List<String>) exercise.get("muscleGroup"), Long.valueOf((String) String.valueOf(exercise.get("sets"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("reps"))).intValue(), (Boolean) exercise.get("repstosec"));
                                    workoutExercises.add(workoutExercise);
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
                                Log.i("time", formattedDate);

                            }

                            // Add workout to list
                            pWorkouts.add(new PublishedWorkoutData(formattedDate, author, document.getId(), rating, w));
                            workoutsAdapter.notifyItemInserted(i);
                        }
                        if (task.getResult().size() > 0) {
                            lastVisibleDocument = task.getResult().getDocuments().get(task.getResult().size() - 1);
                        }
                    }
                }
            });
        }
    }

    // Display searched workouts
    private void displayDocuments(List<QueryDocumentSnapshot> matchingList) {
        for (QueryDocumentSnapshot document : matchingList) {
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
                    for (HashMap<String, Object> exercise : exercises) {
                        WorkoutExercises workoutExercise = new WorkoutExercises((String) exercise.get("name"), (String) exercise.get("type"), (String) exercise.get("link"), Long.valueOf((String) String.valueOf(exercise.get("image1"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("image2"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("imageMini"))).intValue(), (List<String>) exercise.get("muscleGroup"), Long.valueOf((String) String.valueOf(exercise.get("sets"))).intValue(), Long.valueOf((String) String.valueOf(exercise.get("reps"))).intValue(), (Boolean) exercise.get("repstosec"));
                        workoutExercises.add(workoutExercise);
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
                    Log.i("time", formattedDate);

                }

                // Add workout to list
                pWorkouts.add(new PublishedWorkoutData(formattedDate, author, document.getId(), rating, w));
                workoutsAdapter.notifyItemInserted(i);
            }
            if (matchingList.size() > 0) {
                lastVisibleDocument = matchingList.get(matchingList.size() - 1);
            }
        }
    }

    // Open workout detail
    private void createWorkoutDetail(String title, String targetedMG, List<WorkoutExercises> exercises) {
        WorkoutData data = new WorkoutData(title, targetedMG, exercises);
        workoutViewModel.updateWorkoutData(data);
        Navigation.findNavController(root).navigate(R.id.action_Workouts_to_WorkoutDetail);
    }

    // Check if user is signed in
    private void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            Log.i("workouts local", auth.getCurrentUser().getEmail());
        } else {
            if (NetworkUtils.isNetworkAvailable(getContext())) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String current = user.getEmail();
                db.collection("users")
                .whereEqualTo("email", current)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            currentNickname = document.getString("nickname");
                            workoutsAdapter = new WorkoutsAdapter(pWorkouts, currentNickname, false, getContext());
                            workoutsAdapter.setOnEntryClickListener((view, position) -> createWorkoutDetail(pWorkouts.get(position).getWorkout().getTitle(), pWorkouts.get(position).getWorkout().getTargetedMuscleGroups(), pWorkouts.get(position).getWorkout().getExercises()));
                            recyclerView.setAdapter(workoutsAdapter);
                            workoutsAdapter.notifyDataSetChanged();
                            getWorkoutData();
                        }
                    }
                });
            }
        }
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