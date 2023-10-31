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
import java.util.Date;
import java.util.List;

public class CalendarExerciseFragment extends Fragment {

    List<Exercises> exercises;
    RecyclerView recyclerView;
    SearchView searchBar;
    LinearLayout root;
    ExerciseAdapter exerciseAdapter;
    ImageView goBack;

    private ExerciseViewModel exerciseViewModel;
    private ProgressViewModel progressViewModel;

    Calendar date;

    public CalendarExerciseFragment() {}

    public static CalendarExerciseFragment newInstance() {
        CalendarExerciseFragment fragment = new CalendarExerciseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExerciseRepository repository = new ExerciseRepository();
        exercises = repository.allExercises;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view.findViewById(R.id.linearLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchBar = view.findViewById(R.id.searchBar);
        goBack = view.findViewById(R.id.goBack);

        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        progressViewModel = ((MainActivity) requireActivity()).getProgressViewModel();

        goBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                exerciseAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                exerciseAdapter.filter(newText);
                return false;
            }
        });

        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    searchBar.setQueryHint("Search Exercises");
                } else {
                    searchBar.setQueryHint("Search Exercises");
                    exerciseAdapter.filter("");

                }
            }
        });

        exerciseAdapter = new ExerciseAdapter(exercises, getContext());
        exerciseAdapter.setOnEntryClickListener(new ExerciseAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position) {
                createProgressEnrty(exercises.get(position));
            }
        });

        progressViewModel.getProgressLiveData().observe(getViewLifecycleOwner(), new Observer<ProgressData>() {
            @Override
            public void onChanged(ProgressData progressData) {
                if (progressData != null) {
                    date = progressData.getDate();
                    Log.i("date got", date.getTime().toString());
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(exerciseAdapter);

    }

    void updateExerciseList(String searchPhrase) {
        exercises.removeIf(exercise -> !exercise.getName().contains(searchPhrase));
        exerciseAdapter.notifyDataSetChanged();
        exerciseAdapter = new ExerciseAdapter(exercises, getContext());
    }


    void createProgressEnrty(Exercises exercise) {
        Exercises dataExercise = new Exercises(exercise.getName(), exercise.getType(), exercise.getLink(), exercise.getMuscleGroup(), exercise.getImage1(), exercise.getImage2());
        ProgressData newData = new ProgressData(dataExercise, null, null, date, null, null, null);


        progressViewModel.updateProgressData(newData);
        Navigation.findNavController(root).navigate(R.id.action_CalendarExercise_to_ProgressEntry);
    }

//    void createExerciseDetail(String name, List<String> muscleGroup, int image1, int image2, String link, String type) {
//
//        // Update the data in one go
//        ExerciseData newData = new ExerciseData();
//        newData.setNewTitle(name);
//        newData.setNewMuscleGroups(muscleGroup);
//        newData.setNewImage1(image1);
//        newData.setNewImage2(image2);
//        newData.setNewLink(link);
//        newData.setNewType(type);
//
//        exerciseViewModel.updateExerciseData(newData);
//        Navigation.findNavController(root).navigate(R.id.action_Exercise_to_ExerciseDetail);
//    }
}