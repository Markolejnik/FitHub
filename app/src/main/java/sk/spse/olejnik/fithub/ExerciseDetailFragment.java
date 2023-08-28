package sk.spse.olejnik.fithub;

import android.content.ContentResolver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;

    String title, link, type;
    StringBuilder muscleGroupContent;
    List<String> muscleGroups;
    int image1, image2;
    TextView titleText, muscleGroupsText, typeText, linkText;
    ImageView imageView1, imageView2, goBack;
    Button addToWorkout;



    public ExerciseDetailFragment() {}

    public static ExerciseDetailFragment newInstance(String title, List<String> muscleGroups, String link, String type, int image1, int image2) {

        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        args.putStringArrayList("groups", (ArrayList<String>) muscleGroups);
//        args.putString("link", link);
//        args.putString("type", type);
//        args.putInt("img1", image1);
//        args.putInt("img2", image2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Bundle args = getArguments();
//        if (args != null) {
//            title = args.getString("title", title);
//
//
//            image1 = args.getInt("img1");
//            image2 = args.getInt("img2");
//            link = args.getString("link");
//            type = args.getString("type");
//        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();


        titleText = view.findViewById(R.id.exerciseTitle);
        muscleGroupsText = view.findViewById(R.id.muscleGroups);
        typeText = view.findViewById(R.id.type);
        linkText = view.findViewById(R.id.link);
        imageView1 = view.findViewById(R.id.image1);
        imageView2 = view.findViewById(R.id.image2);
        goBack = view.findViewById(R.id.goBack);
        addToWorkout = view.findViewById(R.id.AddToWorkout);

        goBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_ExerciseDetail_to_Exercise);
        });

        addToWorkout.setOnClickListener(v -> {
            // Update the data in one go
            ExerciseData newData = new ExerciseData();
            newData.setNewTitle(titleText.getText().toString());
            newData.setNewMuscleGroups(muscleGroups);
            newData.setNewImage1(image1);
            if(imageView2.getVisibility() != View.GONE) newData.setNewImage2(image2);

            newData.setNewLink(linkText.getText().toString());
            newData.setNewType(typeText.getText().toString());

            exerciseViewModel.updateCreationData(newData);
            Navigation.findNavController(v).navigate(R.id.action_ExerciseDetail_to_WorkoutCreation);

        });

        // Observe the LiveData for changes
        exerciseViewModel.getExerciseLiveData().observe(getViewLifecycleOwner(), new Observer<ExerciseData>() {
            @Override
            public void onChanged(@Nullable ExerciseData exerciseData) {
                if (exerciseData != null) {
                    titleText.setText(exerciseData.getNewTitle());
                    muscleGroups = exerciseData.getNewMuscleGroups();
                    muscleGroupContent = new StringBuilder();
                    for (String muscle: muscleGroups) {
                        muscleGroupContent.append(muscle + "\n");
                    }
                    muscleGroupsText.setText(muscleGroupContent);
                    typeText.setText(exerciseData.getNewType());
                    linkText.setText(exerciseData.getNewLink());
                    image1 = exerciseData.getNewImage1();
                    imageView1.setImageResource(image1);
                    image2 = exerciseData.getNewImage2();
                    if (image2 == 0) {
                        imageView2.setVisibility(View.GONE);
                    }
                    imageView2.setImageResource(image2);
                }
            }
        });
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout); // Replace with your container's ID
//        Log.i("fragment", currentFragment);
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();

        for (int i = 0; i < backStackEntryCount; i++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            String fragmentName = entry.getName();
            Log.d("BackStack", "Fragment in back stack: " + fragmentName);
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

    @Override
    public void onDetach() {
        super.onDetach();
//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
    }


}