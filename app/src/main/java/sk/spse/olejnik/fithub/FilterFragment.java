package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends Fragment {

    ImageView backButton;
    Button clearFilters;
    TextView saveFilters, filterTypeAny, filterType1, filterType2, filterGroupAny, filterGroup1, filterGroup2, filterGroup3, filterGroup4, filterGroup5, filterGroup6, filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11, filterGroup12, filterGroup13, filterGroup14, filterGroup15, filterGroup16;

    List<TextView> groupFilterViews;
    String typeFilter;
    List<String> groupFilter, filterOutput;

    private FilterViewModel filterViewModel;


    public FilterFragment() {
    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.goBack);
        clearFilters = view.findViewById(R.id.clearfilters);
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

        groupFilterViews = new ArrayList<>();
        groupFilterViews.add(filterGroup1);
        groupFilterViews.add(filterGroup2);
        groupFilterViews.add(filterGroup3);
        groupFilterViews.add(filterGroup4);
        groupFilterViews.add(filterGroup5);
        groupFilterViews.add(filterGroup6);
        groupFilterViews.add(filterGroup7);
        groupFilterViews.add(filterGroup8);
        groupFilterViews.add(filterGroup9);
        groupFilterViews.add(filterGroup10);
        groupFilterViews.add(filterGroup11);
        groupFilterViews.add(filterGroup12);
        groupFilterViews.add(filterGroup13);
        groupFilterViews.add(filterGroup14);
        groupFilterViews.add(filterGroup15);
        groupFilterViews.add(filterGroup16);

        // Handle back button press
        backButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_Filter_to_Exercise);
        });

        FilterData filterData = new FilterData();

        // Get ViewModel
        filterViewModel = ((MainActivity) requireActivity()).getFilterViewModel();

        // Get filter data from Filter View Model
        filterViewModel.getFilterLiveData().observe(getViewLifecycleOwner(), new Observer<FilterData>() {
            @Override
            public void onChanged(FilterData filterData) {
                if (filterData != null) {
                    if (filterData.getFilterType() != null ) {
                        typeFilter = filterData.getFilterType();
                        if (typeFilter.equals("Any")) {
                            filterTypeAny.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                        } else if (typeFilter.equals("Bodybuilding")) {
                            filterType1.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                            filterTypeAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                        } else {
                            filterType2.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                            filterTypeAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                        }
                    }
                    if (filterData.getFilterMuscleGroups() != null) {
                        groupFilter = filterData.getFilterMuscleGroups();
                        for (TextView view : groupFilterViews) {
                            if (groupFilter.contains(view.getText().toString())) {
                                view.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                            } else {
                                view.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                            }
                        }
                    }
                }
            }
        });

        // Handle filter cleaning
        clearFilters.setOnClickListener(v -> {

            // Clear View Model data
            filterData.setFilterType(null);
            filterData.setFilterMuscleGroups(null);

            // Update view colors
            performViewColorChange();
            filterTypeAny.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            filterType1.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
            filterType2.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));

        });

        // Handle saving filters
        saveFilters.setOnClickListener(v -> {

            filterData.setFilterType(null);
            filterData.setFilterMuscleGroups(null);

            // Get type filters
            if (filterTypeAny.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterData.setFilterType("Any");
            } else if (filterType1.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)){
                filterData.setFilterType("Bodybuilding");
            } else if (filterType2.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)){
                filterData.setFilterType("Bodyweight");
            }

            // Get muscle group filters
            filterOutput = new ArrayList<>();
            if (filterGroupAny.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Any");
            } else if (filterGroup1.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Abdominals");
            } if (filterGroup2.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Biceps");
            } if (filterGroup3.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Calves");
            } if (filterGroup4.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Chest");
            } if (filterGroup5.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Forearms");
            } if (filterGroup6.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Front Delts");
            } if (filterGroup7.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Glutes");
            } if (filterGroup8.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Hamstrings");
            } if (filterGroup9.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    filterOutput.add("Lats");
            } if (filterGroup10.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Lower Back");
            } if (filterGroup11.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Quads");
            } if (filterGroup12.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Rear Delts");
            } if (filterGroup13.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Rhomboids");
            } if (filterGroup14.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Shoulders");
            } if (filterGroup15.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Traps");
            } if (filterGroup16.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterOutput.add("Triceps");
            }

            // Update View Model data
            filterData.setFilterMuscleGroups(filterOutput);
            filterViewModel.updateExerciseData(filterData);

            // Navigate to exercise fragment
            Navigation.findNavController(view).navigate(R.id.action_Filter_to_Exercise);

        });

        // Handle type filter button presses
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

        // Handle muscle group filter button presses
        filterGroupAny.setOnClickListener(v2 -> {
            if (filterGroupAny.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {}
            else {
                performViewColorChange();
            }
        });

        filterGroup1.setOnClickListener(v2 -> {
            if (filterGroup1.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup1.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup1.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup2.setOnClickListener(v2 -> {
            if (filterGroup2.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup2.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup2.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup3.setOnClickListener(v2 -> {
            if (filterGroup3.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup3.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup3.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup4.setOnClickListener(v2 -> {
            if (filterGroup4.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup4.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup4.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup5.setOnClickListener(v2 -> {
            if (filterGroup5.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup5.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup5.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup6.setOnClickListener(v2 -> {
            if (filterGroup6.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup6.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup6.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup7.setOnClickListener(v2 -> {
            if (filterGroup7.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup7.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup7.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup8.setOnClickListener(v2 -> {
            if (filterGroup8.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup8.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup8.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup9.setOnClickListener(v2 -> {
            if (filterGroup9.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup9.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup9.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup10.setOnClickListener(v2 -> {
            if (filterGroup10.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup10.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup10.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup11.setOnClickListener(v2 -> {
            if (filterGroup11.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup11.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup11.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup12.setOnClickListener(v2 -> {
            if (filterGroup12.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup12.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup12.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup13.setOnClickListener(v2 -> {
            if (filterGroup13.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup13.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup13.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup14.setOnClickListener(v2 -> {
            if (filterGroup14.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup14.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup14.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup15.setOnClickListener(v2 -> {
            if (filterGroup15.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup15.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup15.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

        filterGroup16.setOnClickListener(v2 -> {
            if (filterGroup16.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                filterGroup16.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                if (!performViewColorCheck()) {
                    performViewColorChange();
                }

            } else {
                filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
                filterGroup16.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            }
        });

    }

    // Check view colors
    private boolean performViewColorCheck() {
        boolean result = false;
        for (TextView filter : groupFilterViews) {
            if (filter.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) result = true;
        }
        return result;
    }

    // Change view colors
    private void performViewColorChange() {
        filterGroupAny.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        for (TextView filter: groupFilterViews) {
            filter.setTextColor(ContextCompat.getColor(getContext(), R.color.foreground));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}