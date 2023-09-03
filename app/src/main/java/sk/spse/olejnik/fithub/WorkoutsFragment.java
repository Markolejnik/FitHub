package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.Timestamp;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkoutsFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    ConstraintLayout content, root;
    TextView duration, muscleGroup, title;
    ImageView thumbnail;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;

    List<WorkoutData> workouts;
    List<String> usernames, times;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    Gson gson;
    Type type;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workouts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view.findViewById(R.id.workoutsLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipe = view.findViewById(R.id.swipeRefreshLayout);
        thumbnail = view.findViewById(R.id.thumbnail);
        fab = view.findViewById(R.id.fab);
        if (usernames == null) {
            workouts = new ArrayList<>();
            usernames = new ArrayList<>();
            times = new ArrayList<>();
        }
        getWorkoutData();

        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();


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

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                workoutsAdapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });

        workoutsAdapter = new WorkoutsAdapter(workouts, usernames, times, getContext());
        workoutsAdapter.setOnEntryClickListener(new WorkoutsAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position) {
                createWorkoutDetail(workouts.get(position).getTitle(), workouts.get(position).getTargetedMuscleGroups(), workouts.get(position).getExercises());
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(workoutsAdapter);
//        workoutsAdapter.notifyDataSetChanged();
    }

    void getWorkoutData() {
        db.collection("workouts").get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    int g = document.getData().size();

                    Map<String, Object> data = document.getData();
//                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                    for (int i = 0; i < g/3; i++) {
                        String author = (String) data.get("author");
                        String workout = (String) data.get("workout");
                        Timestamp time = (Timestamp) data.get("published");

                        if (workout != null) {
                            Log.i("asgds", workout);
                            gson = new Gson();
                            type = new TypeToken<WorkoutData>() {
                            }.getType();
                            WorkoutData w = gson.fromJson(workout, type);
                            workouts.add(w);
//                            workouts.add((WorkoutData) entry.getValue());
                        }
                        if (author != null) {
                            usernames.add(author);

                        }
                        if (time != null) {
                            Instant instant = time.toDate().toInstant();
                            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm", Locale.getDefault());
                            String formattedDate = formatter.format(localDateTime);
                            times.add(formattedDate);
                        }
                        workoutsAdapter.notifyItemInserted(i);
                    }
                }

            }
        });


    }

    void createWorkoutDetail(String title, String targetedMG, List<WorkoutExercises> exercises) {
        WorkoutData data = new WorkoutData(title, targetedMG, exercises);
        workoutViewModel.updateWorkoutData(data);
        Navigation.findNavController(root).navigate(R.id.action_Workouts_to_WorkoutDetail);
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