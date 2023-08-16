package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class FilterFragment extends Fragment {

    ImageView backButton;
    TextView saveFilters, filterTypeAny, filterType1, filterType2, filterGroupAny, filterGroup1, filterGroup2, filterGroup3, filterGroup4, filterGroup5, filterGroup6, filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11, filterGroup12, filterGroup13, filterGroup14, filterGroup15, filterGroup16;
    Boolean typeAny, type1, type2, groupAny, group1, group2, group3, group4, group5, group6, group7, group8, group9, group10, group11, group12, group13, group14, group15, group16;

    private ExerciseViewModel exerciseViewModel;


    public FilterFragment() {
    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.goBack);
        saveFilters = view.findViewById(R.id.filterSave);
        filterTypeAny = view.findViewById(R.id.filterTypeAny);
        filterType1 = view.findViewById(R.id.filterType1);
        filterType2 = view.findViewById(R.id.filterType2);
        filterGroupAny = view.findViewById(R.id.filterGroupAny);
        filterGroup1 = view.findViewById(R.id.filterGroup1);
        filterGroup2 = view.findViewById(R.id.filterGroup2);
        filterGroup3 = view.findViewById(R.id.filterGroup3);
        filterGroup4 = view.findViewById(R.id.filterGroup4);
        filterGroup5 = view.findViewById(R.id.filterGroup5);
        filterGroup6 = view.findViewById(R.id.filterGroup6);
        filterGroup7 = view.findViewById(R.id.filterGroup7);
        filterGroup8 = view.findViewById(R.id.filterGroup8);
        filterGroup9 = view.findViewById(R.id.filterGroup9);
        filterGroup10 = view.findViewById(R.id.filterGroup10);
        filterGroup11 = view.findViewById(R.id.filterGroup11);
        filterGroup12 = view.findViewById(R.id.filterGroup12);
        filterGroup13 = view.findViewById(R.id.filterGroup13);
        filterGroup14 = view.findViewById(R.id.filterGroup14);
        filterGroup15 = view.findViewById(R.id.filterGroup15);
        filterGroup16 = view.findViewById(R.id.filterGroup16);
        typeAny = groupAny = true;
        type1 = type2 = group1 = group2 = group3 = group4 = group5 = group6 = group7 = group8 = group9 = group10 = group11 = group12 = group13 = group14 = group15 = group16 = false;
        backButton.setOnClickListener(v -> {
//            ExerciseFragment exerciseFragment = new ExerciseFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frameLayout2, exerciseFragment, "findThisFragment")
//                    .commit();

            fragmentManager.popBackStack();
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        });
        saveFilters.setOnClickListener(v -> {

            ExerciseData typeData = new ExerciseData();
            exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
            exerciseViewModel.getExerciseLiveData().observe(getViewLifecycleOwner(), new Observer<ExerciseData>() {
                @Override
                public void onChanged(@Nullable ExerciseData exerciseData) {
                    if (exerciseData != null) {
                        typeData.setNewTitle(null);
                        typeData.setNewMuscleGroups(null);
                        typeData.setNewImage1(0);
                        typeData.setNewImage2(0);
                        typeData.setNewLink(null);
                        typeData.setNewType(null);
                    }
                }
            });

            if (filterTypeAny.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                typeData.setNewType("");
            } else if (filterType1.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)){
                typeData.setNewType("Bodybuilding");
            } else if (filterType2.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)){
                typeData.setNewType("Bodyweight");
            }

            exerciseViewModel.updateExerciseData(typeData);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        });
        filterTypeAny.setOnClickListener(v1 -> {
            filterTypeAny.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            if (filterType1.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent) || filterType2.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterType1.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterType2.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
            }
        });
        filterType1.setOnClickListener(v1 -> {
            filterType1.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            if (filterTypeAny.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent) || filterType2.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterTypeAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterType2.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
            }
        });
        filterType2.setOnClickListener(v1 -> {
            filterType2.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            if (filterTypeAny.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent) || filterType1.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterTypeAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterType1.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
            }

        });
    }
}