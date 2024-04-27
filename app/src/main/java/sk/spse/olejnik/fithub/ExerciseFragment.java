package sk.spse.olejnik.fithub;

import android.graphics.Rect;
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
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExerciseFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    TableLayout tableLayout;
    ConstraintLayout layoutExercise, root;

    private ExerciseViewModel exerciseViewModel;
    private FilterViewModel filterViewModel;

    List<Exercises> exercises, filteredExercises, filteredExercisesFinal;
    List<String> groupFilter;

    String typeFilter;
    SearchView searchBar;
    Button filterButton;



    public ExerciseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the offline exercise database
        ExerciseRepository repository = new ExerciseRepository();
        exercises = repository.allExercises;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.conLayout);
        searchBar = view.findViewById(R.id.searchBar);
        filterButton = view.findViewById(R.id.filterbutton);
        tableLayout = view.findViewById(R.id.tableLayout);
        layoutExercise = view.findViewById(R.id.conLayout);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.exercises);


        // Handle bottom navigation clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            if (id == R.id.workouts) {
                Navigation.findNavController(view).navigate(R.id.action_Exercise_to_Workouts);
                return true;
            }
            else if (id == R.id.exercises) {

                return true;
            }
            else if (id == R.id.profile) {
                Navigation.findNavController(view).navigate(R.id.action_Exercise_to_Profile);
                return true;
            }
            else if (id == R.id.calendar) {
                Navigation.findNavController(view).navigate(R.id.action_Exercise_to_Calendar);
                return true;
            }
            else{return false;}
        });

        layoutExercise.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            layoutExercise.getWindowVisibleDisplayFrame(r);

            int screenHeight = layoutExercise.getHeight();
            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) {
                filterButton.setVisibility(View.GONE);
            } else {
                filterButton.setVisibility(View.VISIBLE);
            }
        });

        tableLayout.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                searchBar.setIconified(true);
                searchBar.setQueryHint("Search Exercises");
            }
        });

        // Handle clicks on search bar
        searchBar.setOnClickListener(v -> searchBar.setIconified(false));

        // Handle search bar text queries
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Reset current filters
                if (filteredExercises != null) filteredExercises.removeAll(filteredExercises);
                if (filteredExercisesFinal != null) filteredExercisesFinal.removeAll(filteredExercisesFinal);
                Log.i("searching this query", query);

                // Update the exercise list
                updateExerciseList(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Handle "X" button press on the search bar
        int searchCloseButtonId = searchBar.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.searchBar.findViewById(searchCloseButtonId);
        closeButton.setOnClickListener(v -> {
            searchBar.setQuery("", false);
            searchBar.clearFocus();
            updateExerciseList("");

        });

        // Handle filter button press
        filterButton.setOnClickListener(v -> {

            // Navigate to filter fragment
            Navigation.findNavController(view).navigate(R.id.action_Exercise_to_Filter);
        });

        // Get View Models
        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        filterViewModel = ((MainActivity) requireActivity()).getFilterViewModel();

        // Get filter data from Filter View Model
        filterViewModel.getFilterLiveData().observe(getViewLifecycleOwner(), filterData -> {
            if (filterData != null) {
                typeFilter = filterData.getFilterType();
                groupFilter = filterData.getFilterMuscleGroups();
                StringBuilder result = new StringBuilder();
                for (String group: groupFilter) {
                    result.append(group + " ");
                }
                if (filteredExercises != null) filteredExercises.removeAll(filteredExercises);
                if (filteredExercisesFinal != null) filteredExercisesFinal.removeAll(filteredExercisesFinal);
            }
            updateExerciseList(searchBar.getQuery().toString());
        });

        // Create list of all exercises
        createExerciseList(exercises);

    }

    // Display exercise list
    private void createExerciseList(List<Exercises> exercises) {

        if (tableLayout.getChildCount() != exercises.size()) {
            tableLayout.removeAllViews();

            for (Exercises item : exercises) {

                TableRow tableRow = new TableRow(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layoutExercise = inflater.inflate(R.layout.layout_exercise, null);
                TextView title = layoutExercise.findViewById(R.id.exercise);
                ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);

                // Open detail of each exercise on click
                layoutExercise.setOnClickListener(v -> {
                    createExerciseDetail(item.getName(), item.getMuscleGroup(), item.getImage1(), item.getImage2(), item.getImageMini(), item.getLink(), item.getType());

                });

                // Set widget content
                title.setText(item.getName());
                thumbnail.setImageResource(item.getImageMini());
                tableRow.addView(layoutExercise);
                tableLayout.addView(tableRow);

            }
        }
    }

    // Filter and search in exercise list
    private void updateExerciseList(String searchPhrase) {

        // Edit filtered exercise list according to search phrase
        filteredExercises = new ArrayList<>();
        if (!searchPhrase.equals("")) {
            for (Exercises item : exercises) {
                if (item.getName().toLowerCase(Locale.ROOT).contains(searchPhrase.toLowerCase(Locale.ROOT))) {
                    Log.i("exercise search", item.getName() + searchPhrase);
                    filteredExercises.add(item);
                }
            }
            if (filteredExercises.size() == 0) Toast.makeText(getContext(), "No exercises match your search", Toast.LENGTH_SHORT).show();
        }

        // Edit filtered exercise list according to type filter
        if (typeFilter != null && !typeFilter.equals("Any")) {
            if (filteredExercises.size() != 0) {
                filteredExercises.removeIf(item -> !item.getType().toLowerCase(Locale.ROOT).equals(typeFilter.toLowerCase(Locale.ROOT)));
            } else {
                for (Exercises item : exercises) {
                    if (item.getType().toLowerCase(Locale.ROOT).equals(typeFilter.toLowerCase(Locale.ROOT))) {
                        filteredExercises.add(item);
                    }
                }
            }

        }

        // Edit filtered exercise list according to muscle group filter
        filteredExercisesFinal = new ArrayList<>();
        if (groupFilter != null && !groupFilter.contains("Any")) {
            for (String groupFilter : groupFilter) {
                if (filteredExercises.size() != 0) {
                    for (Exercises item : filteredExercises) {
                        for (String muscleGroup : item.getMuscleGroup()) {
                            if (muscleGroup.toLowerCase(Locale.ROOT).equals(groupFilter.toLowerCase(Locale.ROOT))) filteredExercisesFinal.add(item);
                        }
                    }
                } else {
                    for (Exercises item : exercises) {
                        for (String muscleGroup : item.getMuscleGroup()) {
                            if (muscleGroup.toLowerCase(Locale.ROOT).equals(groupFilter.toLowerCase(Locale.ROOT))) filteredExercisesFinal.add(item);
                        }
                    }
                }
            }
        }

        // Refresh and display exercises
        tableLayout.removeAllViews();
        if (filteredExercisesFinal.size() != 0) createExerciseList(filteredExercisesFinal);
        else if (filteredExercises.size() != 0) createExerciseList(filteredExercises);
        else createExerciseList(exercises);

    }

    void createExerciseDetail(String name, List<String> muscleGroup, int image1, int image2, int imageMini, String link, String type) {

        // Update the data in one go
        ExerciseData newData = new ExerciseData();
        newData.setNewTitle(name);
        newData.setNewMuscleGroups(muscleGroup);
        newData.setNewImage1(image1);
        newData.setNewImage2(image2);
        newData.setNewImageMini(imageMini);
        newData.setNewLink(link);
        newData.setNewType(type);

        exerciseViewModel.updateExerciseData(newData);

        // Navigate to Exercise Detail Fragment
        Navigation.findNavController(root).navigate(R.id.action_Exercise_to_ExerciseDetail);

        }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.exercises);


    }

    @Override
    public void onPause() {
        super.onPause();
        bottomNavigationView.setVisibility(View.GONE);
    }
}