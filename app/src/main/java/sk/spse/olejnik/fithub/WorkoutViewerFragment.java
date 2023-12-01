package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WorkoutViewerFragment extends Fragment {

    LinearLayout root;
    ImageView goBack, prevExercise, nextExercise, thumbnail;
    TextView workoutTitle, exerciseTitle, muscleGroups, setInfo, setCounter;
    Button addSet;

    List<WorkoutExercises> exercises;
    String titleValue, targetedMGValue;
    Integer sets, current;

    private ExerciseViewModel exerciseViewModel;
    private WorkoutViewModel workoutViewModel;


    public WorkoutViewerFragment() {
    }

    public static WorkoutViewerFragment newInstance() {
        WorkoutViewerFragment fragment = new WorkoutViewerFragment();
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
        return inflater.inflate(R.layout.fragment_workout_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view.findViewById(R.id.root);
        goBack = view.findViewById(R.id.goBack);
        workoutTitle = view.findViewById(R.id.wViewerTitle);
        muscleGroups = view.findViewById(R.id.wViewerGroups);
        exerciseTitle = view.findViewById(R.id.exerciseTitle);
        thumbnail = view.findViewById(R.id.image);
        prevExercise = view.findViewById(R.id.prevExercise);
        nextExercise = view.findViewById(R.id.nextExercise);
        addSet = view.findViewById(R.id.setCounterButton);
        setCounter = view.findViewById(R.id.setCounter);
        setInfo = view.findViewById(R.id.setInfo);

        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();


        workoutViewModel.getWorkoutLiveData().observe(getViewLifecycleOwner(), new Observer<WorkoutData>() {
            @Override
            public void onChanged(@Nullable WorkoutData workoutData) {
                if (workoutData != null) {
                    exercises = new ArrayList<>();
                    titleValue = workoutData.getTitle();
                    targetedMGValue = workoutData.getTargetedMuscleGroups();
                    workoutTitle.setText(titleValue);
                    muscleGroups.setText(targetedMGValue);
                    exercises = workoutData.getExercises();
                    current = 0;
                    displayExercise(current);
                }
            }
        });

        goBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        addSet.setOnClickListener(v -> {
            if (exercises.get(current).getSets() > sets) {
                setCounter.setText((++sets).toString());
            } else Toast.makeText(getContext(), "You did all the sets for this exericse", Toast.LENGTH_SHORT).show();

        });

        prevExercise.setOnClickListener(v -> {
            try {
                displayExercise(--current);
            } catch (Exception e) {
                current++;
                Toast.makeText(getContext(), "You are on the first exercise", Toast.LENGTH_SHORT).show();
            }

        });

        nextExercise.setOnClickListener(v -> {
            try {
                displayExercise(++current);
            } catch (Exception e) {
                current--;
                Toast.makeText(getContext(), "You are on the last exercise", Toast.LENGTH_SHORT).show();
            }

        });

    }
    @SuppressLint("SetTextI18n")
    private void displayExercise(Integer current) {
        WorkoutExercises exercise = exercises.get(current);
        exerciseTitle.setText(exercise.getTitle());
        thumbnail.setImageResource(exercise.getImage1());
        if (exercise.isTimeFormat()) setInfo.setText(exercise.getSets() + "Sets Of " + exercise.getReps() + " Secs");
        else setInfo.setText(exercise.getSets() + " Sets Of " + exercise.getReps() + " Reps");
        setCounter.setText("0");
        sets = 0;


    }
}
