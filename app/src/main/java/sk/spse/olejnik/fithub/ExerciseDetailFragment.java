package sk.spse.olejnik.fithub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ExerciseDetailFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;

    StringBuilder muscleGroupContent;
    List<String> muscleGroups;
    int image1, image2, imageMini;
    TextView titleText, muscleGroupsText, typeText, linkText;
    ImageView imageView1, imageView2, goBack;
    Button addToWorkout;



    public ExerciseDetailFragment() {}

    public static ExerciseDetailFragment newInstance() {

        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleText = view.findViewById(R.id.exerciseTitle);
        muscleGroupsText = view.findViewById(R.id.muscleGroups);
        typeText = view.findViewById(R.id.type);
        linkText = view.findViewById(R.id.link);
        imageView1 = view.findViewById(R.id.image1);
        imageView2 = view.findViewById(R.id.image2);
        goBack = view.findViewById(R.id.goBack);
        addToWorkout = view.findViewById(R.id.AddToWorkout);

        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();

        goBack.setOnClickListener(v -> {
            exerciseViewModel.updateExerciseData(new ExerciseData(null, null, null, null, null, null, null));
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        addToWorkout.setOnClickListener(v -> {
            ExerciseData newData = new ExerciseData();
            newData.setNewTitle(titleText.getText().toString());
            newData.setNewMuscleGroups(muscleGroups);
            newData.setNewImage1(image1);
            if (imageView2.getVisibility() != View.GONE) newData.setNewImage2(image2);
            newData.setNewImageMini(imageMini);
            newData.setNewLink(linkText.getText().toString());
            newData.setNewType(typeText.getText().toString());

            exerciseViewModel.updateCreationData(newData);

            Navigation.findNavController(v).navigate(R.id.action_ExerciseDetail_to_WorkoutCreation);
            exerciseViewModel.updateExerciseData(new ExerciseData(null, null, null, null, null, null, null));

        });

        exerciseViewModel.getExerciseLiveData().observe(getViewLifecycleOwner(), new Observer<ExerciseData>() {
            @Override
            public void onChanged(@Nullable ExerciseData exerciseData) {
                if (exerciseData != null) {
                    if (exerciseData.getNewTitle() != null) {
                        titleText.setText(exerciseData.getNewTitle());
                        muscleGroups = exerciseData.getNewMuscleGroups();
                        muscleGroupContent = new StringBuilder();
                        for (String muscle : muscleGroups) {
                            muscleGroupContent.append(muscle + "\n");
                        }
                        muscleGroupsText.setText(muscleGroupContent);
                        typeText.setText(exerciseData.getNewType());
                        linkText.setText(exerciseData.getNewLink());
                        image1 = exerciseData.getNewImage1();
                        imageView1.setImageResource(image1);
                        image2 = exerciseData.getNewImage2();
                        imageMini = exerciseData.getNewImageMini();
                        if (image2 == 0) {
                            imageView2.setVisibility(View.GONE);
                        }
                        imageView2.setImageResource(image2);
                    }
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {super.onDetach();}


}