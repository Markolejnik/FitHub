package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WorkoutCreationFragment extends Fragment {

    ConstraintLayout root;
    TableLayout tableLayout;
    Button addExercise;
    ImageView goBack;
    EditText title, targetedMG;
    TextView save;
    List<Exercises> exercises;
    String valueTitle, valueTargetedMG, json;
    Boolean isTimeFormat;
    Integer setsVal, repsVal;


    List<WCExerciseData> exerciseList;
    List<WCExerciseData> displayList;
    List<String> titles, links, types;
    List<Integer> images1, images2;
    List<List<String>> muscleGroupList;
    List<EditText> setsList, repsList;
    List<Button> repstosecList;
    List<Integer> indexList;
    List<List<String>> setsReps;
    List<Integer> finalIndices;

    Gson gson;
    Type type;

    private int originalSoftInputMode;

    private ExerciseViewModel exerciseViewModel;
    private WorkoutViewModel workoutViewModel;
    private SharedPreferences preferences;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    public WorkoutCreationFragment() {
    }

    public static WorkoutCreationFragment newInstance() {
        WorkoutCreationFragment fragment = new WorkoutCreationFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout_creation, container, false);
        originalSoftInputMode = getActivity().getWindow().getAttributes().softInputMode;

        // Set the adjusted softInputMode
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view.findViewById(R.id.creationLayout);
        tableLayout = view.findViewById(R.id.tableLayoutWorkout);
        save = view.findViewById(R.id.workoutSave);
        goBack = view.findViewById(R.id.goBack);
        addExercise = view.findViewById(R.id.AddExercise);
        title = view.findViewById(R.id.titleInput);
        targetedMG = view.findViewById(R.id.targetedMGInput);


        exerciseViewModel = ((MainActivity) requireActivity()).getExerciseViewModel();
        workoutViewModel = ((MainActivity) requireActivity()).getWorkoutViewModel();
        preferences = getActivity().getBaseContext().getSharedPreferences("database", Context.MODE_PRIVATE);
        exerciseList = new ArrayList<>();

        valueTitle = preferences.getString("Title", "");
        valueTargetedMG = preferences.getString("TitleMG", "");
        gson = new Gson();

        titles = new ArrayList<>();
        json = preferences.getString("titlesJson", "");
        type = new TypeToken<List<String>>() {
        }.getType();
        titles = gson.fromJson(json, type);

        json = preferences.getString("typesJson", "");
        type = new TypeToken<List<String>>() {
        }.getType();
        types = gson.fromJson(json, type);

        json = preferences.getString("linksJson", "");
        type = new TypeToken<List<String>>() {
        }.getType();
        links = gson.fromJson(json, type);

        json = preferences.getString("images1Json", "");
        type = new TypeToken<List<Integer>>() {
        }.getType();
        images1 = gson.fromJson(json, type);

        json = preferences.getString("images2Json", "");
        type = new TypeToken<List<Integer>>() {
        }.getType();
        images2 = gson.fromJson(json, type);

        json = preferences.getString("muscleGroupsJson", "");
        type = new TypeToken<List<List<String>>>() {
        }.getType();
        muscleGroupList = gson.fromJson(json, type);

        json = preferences.getString("detailsJson", "");
        type = new TypeToken<List<WCExerciseData>>() {
        }.getType();
        exerciseList = gson.fromJson(json, type);
//        for (int i = 0; i < exerciseList.size(); i++) {
//            String thi = exerciseList.get(i).getReps() + " " + exerciseList.get(i).getSets() + " " + exerciseList.get(i).getRepstosec().toString();
//            Log.i("no", thi);
//        }

        title.setText(valueTitle);
        targetedMG.setText(valueTargetedMG);




        goBack.setOnClickListener(v -> {
            showExitDialog();
        });
        save.setOnClickListener(v -> {
            if (title.getText().toString().equals("")) {
                title.setError("Input a title before saving");
                return;
            }
            if (targetedMG.getText().toString().equals("")) {
                targetedMG.setError("Input muscle groups before saving");
                return;
            }
            if (tableLayout.getChildCount() == 0) {
                Toast.makeText(getContext(), "Add exercises before saving", Toast.LENGTH_SHORT).show();
                return;
            }

            valueTitle = title.getText().toString();
            valueTargetedMG = targetedMG.getText().toString();
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                View tableRow = tableLayout.getChildAt(i);
                if (tableRow instanceof TableRow) {
                    ConstraintLayout constraintLayout = tableRow.findViewById(R.id.constraintLayout); // Get ConstraintLayout inside TableRow

                    EditText setsEditText = constraintLayout.findViewById(R.id.sets);
                    EditText repsEditText = constraintLayout.findViewById(R.id.reps);
                    ToggleButton repsTosec = constraintLayout.findViewById(R.id.repstosec);

                    // Now you can retrieve the values from these views
                    int sets = Integer.parseInt(setsEditText.getText().toString());
                    int reps = Integer.parseInt(repsEditText.getText().toString());
                    if (sets == 0 || reps == 0) {
                        Toast.makeText(getContext(), "Add sets and reps to exercises before saving", Toast.LENGTH_LONG).show();
                        return;
                    }
                    boolean isTimeFormat = repsTosec.isChecked(); // true for seconds, false for reps
                    exerciseList.get(i).setSets(sets);
                    exerciseList.get(i).setReps(reps);
                    exerciseList.get(i).setRepstosec(isTimeFormat);

                }

            }
            List<WorkoutExercises> workoutExercises = new ArrayList<>();
            for (int i = 0; i < exercises.size(); i++) {
                workoutExercises.add(new WorkoutExercises(exercises.get(i).getName(), exercises.get(i).getType(), exercises.get(i).getLink(), exercises.get(i).getImage1(), exercises.get(i).getImage2(), exercises.get(i).getMuscleGroup(), exerciseList.get(i).getSets(), exerciseList.get(i).getReps(), exerciseList.get(i).getRepstosec()));
            }


            WorkoutData newData = new WorkoutData(valueTitle, valueTargetedMG, workoutExercises);

            workoutViewModel.updateWorkoutData(newData);

            checkSignedIn(newData);

            SharedPreferences.Editor editor = preferences.edit();

            gson = new Gson();
            String titlesJson = gson.toJson(titles);
            String typesJson = gson.toJson(types);
            String linksJson = gson.toJson(links);
            String images1Json = gson.toJson(images1);
            String images2Json = gson.toJson(images2);
            String muscleGroupsJson = gson.toJson(muscleGroupList);
            String detailsJson = gson.toJson(exerciseList);

            editor.putString("Title", valueTitle);
            editor.putString("TitleMG", valueTargetedMG);
            editor.putString("titlesJson", titlesJson);
            editor.putString("typesJson", typesJson);
            editor.putString("linksJson", linksJson);
            editor.putString("images1Json", images1Json);
            editor.putString("images2Json", images2Json);
            editor.putString("muscleGroupsJson", muscleGroupsJson);
            editor.putString("detailsJson", detailsJson);
            editor.apply();
            Navigation.findNavController(v).navigate(R.id.action_WorkoutCreation_to_Workouts);
        });
        addExercise.setOnClickListener(v -> {


            valueTitle = title.getText().toString();
            valueTargetedMG = targetedMG.getText().toString();
            if (titles != null && titles.size() != 0) {
                SharedPreferences.Editor editor = preferences.edit();

                gson = new Gson();
                String titlesJson = gson.toJson(titles);
                String typesJson = gson.toJson(types);
                String linksJson = gson.toJson(links);
                String images1Json = gson.toJson(images1);
                String images2Json = gson.toJson(images2);
                String muscleGroupsJson = gson.toJson(muscleGroupList);
                String detailsJson = gson.toJson(exerciseList);

                editor.putString("Title", valueTitle);
                editor.putString("TitleMG", valueTargetedMG);
                editor.putString("titlesJson", titlesJson);
                editor.putString("typesJson", typesJson);
                editor.putString("linksJson", linksJson);
                editor.putString("images1Json", images1Json);
                editor.putString("images2Json", images2Json);
                editor.putString("muscleGroupsJson", muscleGroupsJson);
                editor.putString("detailsJson", detailsJson);
                editor.apply();
            }
            Navigation.findNavController(v).navigate(R.id.action_WorkoutCreation_to_Exercise);
//            ExerciseFragment exerciseFragment = new ExerciseFragment();
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.creationLayout, exerciseFragment, "ExerciseFragment")
//                    .addToBackStack("WorkoutCreationFragment")
//                    .commit();
        });
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        Fragment currentFragment = fragmentManager.findFragmentById(R.id.creationLayout); // Replace with your container's ID
////        Log.i("fragment", currentFragment);
//        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
//
//        for (int i = 0; i < backStackEntryCount; i++) {
//            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
//            String fragmentName = entry.getName();
//            Log.d("BackStack", "Fragment in back stack: " + fragmentName);
//        }
        exerciseViewModel.getCreationLiveData().observe(getViewLifecycleOwner(), new Observer<ExerciseData>() {
            @Override
            public void onChanged(@Nullable ExerciseData exerciseData) {
                if (exerciseData != null) {
                    if (titles == null){
                        titles = new ArrayList<>();
                        types = new ArrayList<>();
                        links = new ArrayList<>();
                        images1 = new ArrayList<>();
                        images2 = new ArrayList<>();
                        muscleGroupList = new ArrayList<>();
                    }
                    titles.add(exerciseData.getNewTitle());
                    types.add(exerciseData.getNewType());
                    links.add(exerciseData.getNewLink());
                    images1.add(exerciseData.getNewImage1());
                    images2.add(exerciseData.getNewImage2());
                    muscleGroupList.add(exerciseData.getNewMuscleGroups());
                    Log.i("size", titles.size() + " ");
                    Log.i("size", tableLayout.getChildCount() + " ");
                    if (tableLayout.getChildCount() != titles.size()) {

                        createExerciseList();
                        Log.i("cought", "onChanged: ");
                    }
                }
            }
        });

        if (titles != null) {
            if (tableLayout.getChildCount() != titles.size()) {
                createExerciseList();
                Log.i("coughtONCreated", "onChanged: ");
            }
        }

    }

    @SuppressLint("SetTextI18n")
    void createExerciseList() {
        exercises = new ArrayList<>();
        displayList = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            Exercises exercise = new Exercises(titles.get(i), types.get(i), links.get(i), muscleGroupList.get(i), images1.get(i), images2.get(i));
            exercises.add(exercise);
        }
        repsList = new ArrayList<>();
        setsList = new ArrayList<>();
        repstosecList = new ArrayList<>();

        if(tableLayout.getChildCount() != exercises.size()) {
            tableLayout.removeAllViews();
            int index = 0;
            for (Exercises item : exercises) {
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), item.getImage1());

                int originalWidth = originalBitmap.getWidth();
                int originalHeight = originalBitmap.getHeight();

                int desiredWidth = originalWidth / 4; // Scale down to half of the original width
                int desiredHeight = originalHeight / 4; // Scale down to half of the original height
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);
                TableRow tableRow = new TableRow(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layoutExercise = inflater.inflate(R.layout.layout_exercise_wc, null);
                TextView etitle = layoutExercise.findViewById(R.id.exercise);
                ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);
                ImageView remove = layoutExercise.findViewById(R.id.removeExercise);
                EditText sets = layoutExercise.findViewById(R.id.sets);
                EditText reps = layoutExercise.findViewById(R.id.reps);
                ToggleButton repstosec = layoutExercise.findViewById(R.id.repstosec);
                remove.setElevation(10);
                layoutExercise.setOnClickListener(v -> {
                    createExerciseDetail(item.getName(), item.getMuscleGroup(), item.getImage1(), item.getImage2(), item.getLink(), item.getType());
                    Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_ExerciseDetail);
                    valueTitle = title.getText().toString();
                    valueTargetedMG = targetedMG.getText().toString();
                    if (titles != null && titles.size() != 0) {
                        SharedPreferences.Editor editor = preferences.edit();

                        gson = new Gson();
                        String titlesJson = gson.toJson(titles);
                        String typesJson = gson.toJson(types);
                        String linksJson = gson.toJson(links);
                        String images1Json = gson.toJson(images1);
                        String images2Json = gson.toJson(images2);
                        String muscleGroupsJson = gson.toJson(muscleGroupList);

                        editor.putString("Title", valueTitle);
                        editor.putString("TitleMG", valueTargetedMG);
                        editor.putString("titlesJson", titlesJson);
                        editor.putString("typesJson", typesJson);
                        editor.putString("linksJson", linksJson);
                        editor.putString("images1Json", images1Json);
                        editor.putString("images2Json", images2Json);
                        editor.putString("muscleGroupsJson", muscleGroupsJson);
                        editor.apply();
                    }
                });
                if (exerciseList != null) {
                    if (index >= exerciseList.size()) {
                        exerciseList.add(new WCExerciseData(setsVal, repsVal, isTimeFormat));
                    } else {
                        try {
                            Log.i("hahaahah", exerciseList.get(index).getSets().toString());
                            Log.i("hahaahah", exerciseList.get(index).getReps().toString());
                            Log.i("hahaahah", exerciseList.get(index).getRepstosec().toString());
                            sets.setText(exerciseList.get(index).getSets().toString());
                            reps.setText(exerciseList.get(index).getReps().toString());
//                            isTimeFormat = exerciseList.get(index).getRepstosec();
//                            if (isTimeFormat) repstosec.setChecked(true);
//                            else repstosec.setChecked(false);
                        } catch (Exception e) {}
                    }
                }else {
                    exerciseList = new ArrayList<>();
                    exerciseList.add(new WCExerciseData(setsVal, repsVal, isTimeFormat));
                }
                if (exerciseList.get(index).getRepstosec() != null) { isTimeFormat = exerciseList.get(index).getRepstosec(); }
                else {isTimeFormat = false;}
                repstosec.setChecked(isTimeFormat);
                if (isTimeFormat) {reps.setHint("Secs");}
                else {reps.setHint("Reps");}
                remove.setOnClickListener(v -> {
                    int indexToRemove = tableLayout.indexOfChild(tableRow);
                    if (indexToRemove >= 0) {
                        exerciseList.remove(indexToRemove);
                        tableLayout.removeViewAt(indexToRemove);
                        exercises.remove(indexToRemove);
                        titles.remove(indexToRemove);
                        links.remove(indexToRemove);
                        types.remove(indexToRemove);
                        images1.remove(indexToRemove);
                        images2.remove(indexToRemove);
                        muscleGroupList.remove(indexToRemove);

                    }
                });

                repstosec.setOnClickListener(v -> {
                    int indexToUpdate = tableLayout.indexOfChild(tableRow);
                    if (repstosec.isChecked()) {
                        exerciseList.get(indexToUpdate).setRepstosec(true);
                         updateViews(indexToUpdate);
//                        repsList.get(indexToUpdate).setHint("Secs");
////                        updateViews();
////                        repstosec.setText("Use Reps");
//                        for (WCExerciseData he: exerciseList) {
//                            Log.i("list", he.toString());
//                        }



                    } else {
                        exerciseList.get(indexToUpdate).setRepstosec(false);
                        updateViews(indexToUpdate);
//                        repsList.get(indexToUpdate).setHint("Reps");
////                        updateViews();
////                        repstosec.setText("Use Seconds");
//                        for (WCExerciseData he: exerciseList) {
//                            Log.i("list", he.getRepstosec().toString());
//                        }
                    }
                });
                repstosecList.add(repstosec);
                setsList.add(sets);
                repsList.add(reps);

                etitle.setText(item.getName());
                thumbnail.setImageBitmap(scaledBitmap);
                tableRow.addView(layoutExercise);
                tableLayout.addView(tableRow);
                index++;

            Log.i("ni", exerciseList.size() + " " + exercises.size() + " " + tableLayout.getChildCount());
            }
        }
    }

    void updateViews(int i) {
        WCExerciseData exercise = exerciseList.get(i);
        View tableRow = tableLayout.getChildAt(i); // Get the exercise row view

        EditText reps = tableRow.findViewById(R.id.reps);
        if (exercise.getRepstosec()) {
            reps.setHint("Sec");
        } else {
            reps.setHint("Reps");
        }
        Log.i("succes on", i + "");
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

    void localSave() {
        valueTitle = title.getText().toString();
        valueTargetedMG = targetedMG.getText().toString();
        if (titles != null && titles.size() != 0) {
            SharedPreferences.Editor editor = preferences.edit();

            gson = new Gson();
            String titlesJson = gson.toJson(titles);
            String typesJson = gson.toJson(types);
            String linksJson = gson.toJson(links);
            String images1Json = gson.toJson(images1);
            String images2Json = gson.toJson(images2);
            String muscleGroupsJson = gson.toJson(muscleGroupList);

            editor.putString("Title", valueTitle);
            editor.putString("TitleMG", valueTargetedMG);
            editor.putString("titlesJson", titlesJson);
            editor.putString("typesJson", typesJson);
            editor.putString("linksJson", linksJson);
            editor.putString("images1Json", images1Json);
            editor.putString("images2Json", images2Json);
            editor.putString("muscleGroupsJson", muscleGroupsJson);
            editor.apply();
        }
    }

    public void checkSignedIn(WorkoutData data) {

        if (auth.getCurrentUser() == null) {
            Log.i("workouts local", auth.getCurrentUser().getEmail());
            Toast.makeText(getContext(), "Log in to Publish a Workout", Toast.LENGTH_SHORT).show();

        } else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            final String current = user.getEmail();
            db.collection("users")
                    .whereEqualTo("email", current)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                    gson = new Gson();
                                    String jsonData = gson.toJson(data);
                                    DocumentReference user = db.collection("users").document("QB8Elr8HLtHZZv6IgS7h");
                                    user.update("workouts", FieldValue.arrayUnion(jsonData));
                            }

                            Log.i("here is the user", auth.getCurrentUser().getEmail());
                            Log.i("workouts public", auth.getCurrentUser().getEmail());

                        }
                    });}
    }


    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Save or Delete Workout Draft");
        builder.setMessage("Do you want to save or delete this workout draft?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (titles.size() != 0) {
                    SharedPreferences.Editor editor = preferences.edit();

                    gson = new Gson();
                    String titlesJson = gson.toJson(titles);
                    String typesJson = gson.toJson(types);
                    String linksJson = gson.toJson(links);
                    String images1Json = gson.toJson(images1);
                    String images2Json = gson.toJson(images2);
                    String muscleGroupsJson = gson.toJson(muscleGroupList);

                    editor.putString("Title", valueTitle);
                    editor.putString("TitleMG", valueTargetedMG);
                    editor.putString("titlesJson", titlesJson);
                    editor.putString("typesJson", typesJson);
                    editor.putString("linksJson", linksJson);
                    editor.putString("images1Json", images1Json);
                    editor.putString("images2Json", images2Json);
                    editor.putString("muscleGroupsJson", muscleGroupsJson);
                    editor.apply();
                    Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_Workouts);
                }
                dialog.dismiss();

                // Exit the fragment (e.g., popBackStack or finish activity)
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                titles = null;
//                types = null;
//                links = null;
//                images1 = null;
//                images2 = null;
//                muscleGroupList = null;
                dialog.dismiss();
                tableLayout.removeAllViews();
                exerciseList.removeAll(exerciseList);
                exercises.removeAll(exercises);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_Workouts);

            }

        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean output;
                dialog.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Restore the original softInputMode when the fragment is destroyed
        getActivity().getWindow().setSoftInputMode(originalSoftInputMode);
    }
    @Override
    public void onPause () {
        super.onPause();
    }

    @Override
    public void onResume () {
        super.onResume();
    }
}
