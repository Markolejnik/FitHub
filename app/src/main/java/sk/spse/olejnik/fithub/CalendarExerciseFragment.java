package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.Calendar;
import java.util.List;

public class CalendarExerciseFragment extends Fragment {

    private List<Exercises> exercises, filteredExercises;
    private RecyclerView recyclerView;
    private SearchView searchBar;
    private LinearLayout root;
    private ExerciseAdapter exerciseAdapter;
    private ImageView goBack;

    private ExerciseViewModel exerciseViewModel;
    private ProgressViewModel progressViewModel;

    private Calendar date;

    public CalendarExerciseFragment() {}

    public static CalendarExerciseFragment newInstance() {
        CalendarExerciseFragment fragment = new CalendarExerciseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the offline exercise database
        ExerciseRepository repository = new ExerciseRepository();
        exercises = repository.allExercises;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.constraintLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchBar = view.findViewById(R.id.searchBar);
        goBack = view.findViewById(R.id.goBack);

        // Get ViewModels
        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        progressViewModel = ((MainActivity) requireActivity()).getProgressViewModel();

        // Handle back button press
        goBack.setOnClickListener(v -> {

            // Navigate to last fragment
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filteredExercises = exerciseAdapter.filter(query);
                exerciseAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredExercises = exerciseAdapter.filter(newText);
                exerciseAdapter.notifyDataSetChanged();
                return false;
            }
        });

        searchBar.setOnFocusChangeListener((view1, hasFocus) -> {
            if (hasFocus) {
                searchBar.setQueryHint("Search Exercises");
                exerciseAdapter.notifyDataSetChanged();
            } else {
                searchBar.setQueryHint("Search Exercises");
                filteredExercises = exercises;
                exerciseAdapter.notifyDataSetChanged();

            }
        });
        filteredExercises = exercises;
        exerciseAdapter = new ExerciseAdapter(filteredExercises, getContext());
//        exerciseAdapter.filter("");
        exerciseAdapter.setOnEntryClickListener((view12, position) -> createProgressEntry(filteredExercises.get(position)));

        progressViewModel.getProgressLiveData().observe(getViewLifecycleOwner(), progressData -> {
            if (progressData != null) {
                date = progressData.getDate();
                Log.i("date got", date.getTime().toString());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(exerciseAdapter);

    }

    private void updateExerciseList(String searchPhrase) {
        exercises.removeIf(exercise -> !exercise.getName().contains(searchPhrase));
        exerciseAdapter.notifyDataSetChanged();
        exerciseAdapter = new ExerciseAdapter(exercises, getContext());
    }


    private void createProgressEntry(Exercises exercise) {
        Exercises dataExercise = new Exercises(exercise.getName(), exercise.getType(), exercise.getLink(), exercise.getMuscleGroup(), exercise.getImage1(), exercise.getImage2(), exercise.getImageMini());
        ProgressData newData = new ProgressData(dataExercise, null, null, date, null, null, null, null);


        progressViewModel.updateProgressData(newData);
        Navigation.findNavController(root).navigate(R.id.action_CalendarExercise_to_ProgressEntry);
    }
}