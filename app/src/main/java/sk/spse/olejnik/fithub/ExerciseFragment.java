package sk.spse.olejnik.fithub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class ExerciseFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    TableLayout tableLayout;
    TableRow filler;
    ConstraintLayout layoutExercise, filterContainer, root;

    private ExerciseViewModel exerciseViewModel;
    private FilterViewModel filterViewModel;

    List<Exercises> exercises, filteredExercises, filteredExercisesFinal;
    List<String> groupFilter;

    String searchPhrase, typeFilter;
    SearchView searchBar;
    Button filterButton;



    public ExerciseFragment() {}

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
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        root = view.findViewById(R.id.conLayout);
        searchBar = view.findViewById(R.id.searchBar);
        filterButton = view.findViewById(R.id.filterbutton);
        tableLayout = view.findViewById(R.id.tableLayout);
        layoutExercise = view.findViewById(R.id.conLayout);
        filterContainer = view.findViewById(R.id.filterContainer);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.exercises);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
            }});
//        filler = view.findViewById(R.id.filler);

        layoutExercise.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                layoutExercise.getWindowVisibleDisplayFrame(r);

                int screenHeight = layoutExercise.getHeight();
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) {
                    filterContainer.setVisibility(View.GONE);
                } else {
                    filterContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setIconified(false);
            }
        });

        tableLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchBar.setIconified(true);
                    searchBar.setQueryHint("Search Exercises");
                }
            }
        });

        searchBar.setQueryHint("Search Exercises");

        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    searchBar.setQueryHint("Search Exercises");
                } else {
                    searchBar.setQueryHint("Search Exercises");
                }
            }
        });


        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (filteredExercises != null) filteredExercises.removeAll(filteredExercises);
                if (filteredExercisesFinal != null) filteredExercisesFinal.removeAll(filteredExercisesFinal);
                Log.i("searching this query", query);
                updateExerciseList(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//
//                updateExerciseList(newText);
//                Log.i("searching this", newText);
                return false;
            }
        });



        filterButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_Exercise_to_Filter);
            filterExercises();
        });

        int searchCloseButtonId = searchBar.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.searchBar.findViewById(searchCloseButtonId);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setQuery("", false);
                searchBar.clearFocus();
                updateExerciseList("");

            }
        });

        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        filterViewModel = ((MainActivity) requireActivity()).getFilterViewModel();

        createExerciseList(exercises);



    }

    void createExerciseList(List<Exercises> exercises) {
        if (tableLayout.getChildCount() != exercises.size()) {
            tableLayout.removeAllViews();
            for (Exercises item : exercises) {
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), item.getImage1());

                int originalWidth = originalBitmap.getWidth();
                int originalHeight = originalBitmap.getHeight();

                int desiredWidth = originalWidth / 4; // Scale down to half of the original width
                int desiredHeight = originalHeight / 4; // Scale down to half of the original height
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);
                TableRow tableRow = new TableRow(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layoutExercise = inflater.inflate(R.layout.layout_exercise, null);
                TextView title = layoutExercise.findViewById(R.id.exercise);
                ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);
                layoutExercise.setOnClickListener(v -> {
                    createExerciseDetail(item.getName(), item.getMuscleGroup(), item.getImage1(), item.getImage2(), item.getLink(), item.getType());

                });

                title.setText(item.getName());
//            title.setWidth(mainActivity.width - (mainActivity.width/4));
                thumbnail.setImageBitmap(scaledBitmap);
                tableRow.addView(layoutExercise);
                tableLayout.addView(tableRow);
//            Log.i("jo", item.getMuscleGroup().toString());
            }
        }
    }

    void updateExerciseList(String searchPhrase) {
        filteredExercises = new ArrayList<>();
        if (!searchPhrase.equals("")) {
            for (Exercises item : exercises) {
                if (item.getName().toLowerCase(Locale.ROOT).contains(searchPhrase.toLowerCase(Locale.ROOT))) {
                    Log.i("exercise search", item.getName() + searchPhrase);
                    filteredExercises.add(item);
                }
            }
        }
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


        tableLayout.removeAllViews();
        for (Exercises item : filteredExercises) {
            Log.i("filtered list item: ", item.getName());
        }

        if (filteredExercisesFinal.size() != 0) createExerciseList(filteredExercisesFinal);
        else if (filteredExercises.size() != 0) createExerciseList(filteredExercises);
        else createExerciseList(exercises);

//        for (Exercises item: filteredExercises) {
//            Log.i("displayed item", item.getName());
//            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), item.getImage1());
//
//            int originalWidth = originalBitmap.getWidth();
//            int originalHeight = originalBitmap.getHeight();
//
//            int desiredWidth = originalWidth / 4; // Scale down to half of the original width
//            int desiredHeight = originalHeight / 4; // Scale down to half of the original height
//            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);
//            TableRow tableRow = new TableRow(getContext());
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            View layoutExercise = inflater.inflate(R.layout.layout_exercise, null);
//            TextView title = layoutExercise.findViewById(R.id.exercise);
//            ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);
//            layoutExercise.setOnClickListener( v -> {
//                createExerciseDetail(item.getName(), item.getMuscleGroup(), item.getImage1(), item.getImage2(), item.getLink(), item.getType());
//            });
//
//            title.setText(item.getName());
////            title.setWidth(mainActivity.width - (mainActivity.width/4));
//            thumbnail.setImageBitmap(scaledBitmap);
//            tableRow.addView(layoutExercise);
//            tableLayout.addView(tableRow);
////            Log.i("jo", item.getMuscleGroup().toString());
//        }

    }
    void filterExercises() {

//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.bottomNavigationView.setVisibility(View.GONE);

//        FilterFragment filterFragment = new FilterFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.conLayout, filterFragment, "FilterFragment")
//                .addToBackStack("FragmentExercise")
//                .commit();


//        FilterFragment filterFragment = new FilterFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.filterContainer, filterFragment);
//        transaction.commit();
//        filterContainer.setVisibility(View.VISIBLE);
//        layoutExercise.setVisibility(View.GONE);
//        searchBar.setVisibility(View.GONE);
//        filterContainer.setElevation(10);
    }

    void createExerciseDetail(String name, List<String> muscleGroup, int image1, int image2, String link, String type) {

        // Update the data in one go
        ExerciseData newData = new ExerciseData();
        newData.setNewTitle(name);
        newData.setNewMuscleGroups(muscleGroup);
        newData.setNewImage1(image1);
        newData.setNewImage2(image2);
        newData.setNewLink(link);
        newData.setNewType(type);

        exerciseViewModel.updateExerciseData(newData);
        Navigation.findNavController(root).navigate(R.id.action_Exercise_to_ExerciseDetail);

//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.bottomNavigationView.setVisibility(View.GONE);
//        ExerciseDetailFragment exerciseDetailFragment = new ExerciseDetailFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.conLayout, exerciseDetailFragment, "ExerciseDetailFragment")
//                .addToBackStack("ExerciseFragment")
//                .commit();
//        Navigation.findNavController(v).navigate(R.id.action_Exercise_to_ExerciseDetail);


//        Log.i("mi", name);
//        Bundle args = new Bundle();
//        args.putString("title", name);
//        args.putStringArrayList("groups", (ArrayList<String>) muscleGroup);
//        args.putInt("img1", image1);
//        args.putInt("img2", image2);
//        args.putString("link", link);
//        args.putString("type", type);
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        MainActivity mainActivity = (MainActivity) getActivity();
//        intent.putExtras(args);
//        startActivity(intent);
//        mainActivity.saveData();

        }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.exercises);
        filterViewModel = ((MainActivity) requireActivity()).getFilterViewModel();
        filterViewModel.getFilterLiveData().observe(getViewLifecycleOwner(), filterData -> {
            if (filterData != null) {
                typeFilter = filterData.getFilterType();
                groupFilter = filterData.getFilterMuscleGroups();
                StringBuilder result = new StringBuilder();
                for (String group: groupFilter
                     ) {
                    result.append(group + " ");
                }
                Log.i("jiggas", typeFilter);
                Log.i("groupfilter", result.toString());
                if (filteredExercises != null) filteredExercises.removeAll(filteredExercises);
                if (filteredExercisesFinal != null) filteredExercisesFinal.removeAll(filteredExercisesFinal);
                Log.i("searching this query when filter", searchBar.getQuery().toString());
                updateExerciseList(searchBar.getQuery().toString());
            }
        });


//        exerciseViewModel.getExerciseLiveData().observe(getViewLifecycleOwner(), exerciseData -> {
//            if (exerciseData != null) {
//                typeFilter = exerciseData.getNewType();
//                Log.i("jiggas", typeFilter);
//                if (filteredExercises != null) filteredExercises.removeAll(filteredExercises);
//                Log.i("searching this query when filter", searchBar.getQuery().toString());
//                updateExerciseList(searchBar.getQuery().toString());
//            }
//        });

    }

    @Override
    public void onPause() {
        super.onPause();
        bottomNavigationView.setVisibility(View.GONE);
    }
}