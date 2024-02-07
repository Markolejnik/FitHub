package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WorkoutDetailFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private WorkoutViewModel workoutViewModel;

    ConstraintLayout root;
    ImageView goBack;
    TextView titleText, targetedMGText;
    Button startWorkout;

    LinearLayout tableLayout;
    List<WorkoutExercises> exercises;
    String titleValue, targetedMGValue;

    public WorkoutDetailFragment() {
    }

    public static WorkoutDetailFragment newInstance() {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.constraintLayout);
        goBack = view.findViewById(R.id.goBack);
        titleText = view.findViewById(R.id.wDetailTitle);
        targetedMGText = view.findViewById(R.id.wDetailGroups);
        tableLayout = view.findViewById(R.id.wDetailTable);
        startWorkout = view.findViewById(R.id.startWorkout);


        // Get View Models
        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();

        // Get workout data from Workout View Model
        workoutViewModel.getWorkoutLiveData().observe(getViewLifecycleOwner(), new Observer<WorkoutData>() {
            @Override
            public void onChanged(@Nullable WorkoutData workoutData) {
                if (workoutData != null) {
                    exercises = new ArrayList<>();
                    titleValue = workoutData.getTitle();
                    targetedMGValue = workoutData.getTargetedMuscleGroups();
                    titleText.setText(titleValue);
                    targetedMGText.setText(targetedMGValue);
                    exercises = workoutData.getExercises();
                    createExerciseList(exercises);
                }
            }
        });

        // Start workout viewing
        startWorkout.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_WorkoutDetail_to_WorkoutViewer);
        });

        // Handle back button press
        goBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });
    }

    // Create workout exercise list
    @SuppressLint("SetTextI18n")
    private void createExerciseList(List<WorkoutExercises> exercises) {
        for (WorkoutExercises exercise: exercises) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View layoutExercise = inflater.inflate(R.layout.layout_workout_exercise, null);
            TextView etitle = layoutExercise.findViewById(R.id.exercise);
            ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);
            TextView sets = layoutExercise.findViewById(R.id.sets);
            TextView reps = layoutExercise.findViewById(R.id.reps);
            TextView repsOrSec = layoutExercise.findViewById(R.id.repsOrSec);

            // Handle exercise clicks
            layoutExercise.setOnClickListener(v -> {
                createExerciseDetail(exercise.getTitle(), exercise.getMuscleGroupsUsed(), exercise.getImage1(),exercise.getImage2(), exercise.getImageMini(), exercise.getLink(), exercise.getType());
                Navigation.findNavController(root).navigate(R.id.action_WorkoutDetail_to_ExerciseDetail);
            });

            // Set widget content
            etitle.setText(exercise.getTitle());
            sets.setText(exercise.getSets().toString());
            reps.setText(exercise.getReps().toString());
            if (exercise.isTimeFormat()) repsOrSec.setText("Sec");
            thumbnail.setImageResource(exercise.getImageMini());

            // Add exercise to layout
            tableLayout.addView(layoutExercise);
        }
    }

    // Create exercise detail
    private void createExerciseDetail (String name, List < String > muscleGroup, int image1, int image2, int imageMini, String link, String type){

        ExerciseData newData = new ExerciseData();
        newData.setNewTitle(name);
        newData.setNewMuscleGroups(muscleGroup);
        newData.setNewImage1(image1);
        newData.setNewImage2(image2);
        newData.setNewImageMini(imageMini);
        newData.setNewLink(link);
        newData.setNewType(type);

        exerciseViewModel.updateExerciseData(newData);

    }
}