package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProgressEntryFragment extends Fragment {

    ImageView goBack, thumbnail;
    EditText reps, weight;
    TextView exerciseTitle, date;


    private ExerciseViewModel exerciseViewModel;
    private ProgressViewModel progressViewModel;

    String title, type, link;
    Integer image1, image2;
    List<String> muscleGroups;
    Boolean repstosec, weighted;
    Calendar dateValue;
    Exercises exercise;
    Integer repsValue, weightValue;


    public ProgressEntryFragment() {}

    public static ProgressEntryFragment newInstance() {
        ProgressEntryFragment fragment = new ProgressEntryFragment();
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
        return inflater.inflate(R.layout.fragment_progress_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goBack = view.findViewById(R.id.goBack);
        date = view.findViewById(R.id.date);
        thumbnail = view.findViewById(R.id.exercise_thumbnail);
        exerciseTitle = view.findViewById(R.id.exercise_title);
        reps = view.findViewById(R.id.reps);
        weight = view.findViewById(R.id.weight);


        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        progressViewModel = ((MainActivity) requireActivity()).getProgressViewModel();


        goBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        progressViewModel.getProgressLiveData().observe(getViewLifecycleOwner(), new Observer<ProgressData>() {
            @Override
            public void onChanged(ProgressData progressData) {
                if (progressData != null) {
                    exercise = progressData.getExercise();
                    dateValue = progressData.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");

                    // Format the Date to a String
                    String formattedDate = dateFormat.format(dateValue.getTime());
                    date.setText(formattedDate);
                    exerciseTitle.setText(exercise.getName());
                    thumbnail.setImageResource(exercise.getImage1());

                }
            }
        });


    }


}