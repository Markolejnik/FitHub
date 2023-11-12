package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class WorkoutDetailFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private WorkoutViewModel workoutViewModel;

    List<WorkoutExercises> exercises;
    LinearLayout root;
    ImageView goBack;
    TextView titleText, targetedMGText;
    Button startWorkout;
    TableLayout tableLayout;

    String titleValue, targetedMGValue;




    public WorkoutDetailFragment() {
    }

    public static WorkoutDetailFragment newInstance(String param1, String param2) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
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
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view.findViewById(R.id.linearLayout);
        goBack = view.findViewById(R.id.goBack);
        titleText = view.findViewById(R.id.wDetailTitle);
        targetedMGText = view.findViewById(R.id.wDetailGroups);
        tableLayout = view.findViewById(R.id.wDetailTable);
        startWorkout = view.findViewById(R.id.startWorkout);

        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();

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

        startWorkout.setOnClickListener(v -> {
            
        });

        goBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });
    }

    @SuppressLint("SetTextI18n")
    void createExerciseList(List<WorkoutExercises> exercises) {
        for (WorkoutExercises exercise: exercises) {
                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), exercise.getImage1());

                    int originalWidth = originalBitmap.getWidth();
                    int originalHeight = originalBitmap.getHeight();

                    int desiredWidth = originalWidth / 4; // Scale down to half of the original width
                    int desiredHeight = originalHeight / 4; // Scale down to half of the original height
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);
                    TableRow tableRow = new TableRow(getContext());
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View layoutExercise = inflater.inflate(R.layout.layout_workout_exercise, null);
                    TextView etitle = layoutExercise.findViewById(R.id.exercise);
                    ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);
                    TextView sets = layoutExercise.findViewById(R.id.sets);
                    TextView reps = layoutExercise.findViewById(R.id.reps);
                    TextView repsOrSec = layoutExercise.findViewById(R.id.repsOrSec);
                    layoutExercise.setOnClickListener(v -> {
                        createExerciseDetail(exercise.getTitle(), exercise.getMuscleGroupsUsed(), exercise.getImage1(),exercise.getImage2(), exercise.getLink(), exercise.getType());
                        Navigation.findNavController(root).navigate(R.id.action_WorkoutDetail_to_ExerciseDetail);
                    });
                    etitle.setText(exercise.getTitle());
                    sets.setText(exercise.getSets().toString());
                    reps.setText(exercise.getReps().toString());
                    if (exercise.isTimeFormat()) repsOrSec.setText("Sec");
                    thumbnail.setImageBitmap(scaledBitmap);
                    tableRow.addView(layoutExercise);
                    tableLayout.addView(tableRow);
        }
    }
    void createExerciseDetail (String name, List < String > muscleGroup, int image1, int image2, String link, String type){

        // Update the data in one go
        ExerciseData newData = new ExerciseData();
        newData.setNewTitle(name);
        newData.setNewMuscleGroups(muscleGroup);
        newData.setNewImage1(image1);
        newData.setNewImage2(image2);
        newData.setNewLink(link);
        newData.setNewType(type);

        exerciseViewModel.updateExerciseData(newData);

    }
}