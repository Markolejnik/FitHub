package sk.spse.olejnik.fithub;

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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    List<Integer> images1, images2, imagesMini;
    List<List<String>> muscleGroupList;
    List<EditText> setsList, repsList;
    List<Button> repstosecList;
    List<Integer> indexList;
    List<List<String>> setsReps;
    List<Integer> finalIndices;
    Map<String, Object> data;

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
        addExercise = view.findViewById(R.id.setCounterButton);
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

        json = preferences.getString("imagesMiniJson", "");
        type = new TypeToken<List<Integer>>() {
        }.getType();
        imagesMini = gson.fromJson(json, type);

        json = preferences.getString("muscleGroupsJson", "");
        type = new TypeToken<List<List<String>>>() {
        }.getType();
        muscleGroupList = gson.fromJson(json, type);

        json = preferences.getString("detailsJson", "");
        type = new TypeToken<List<WCExerciseData>>() {
        }.getType();
        exerciseList = gson.fromJson(json, type);

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


                    try {
                        int sets = Integer.parseInt(setsEditText.getText().toString());
                        int reps = Integer.parseInt(repsEditText.getText().toString());
                        if (sets == 0 || reps == 0) {
                            Toast.makeText(getContext(), "Add sets and reps to exercises before saving", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            boolean isTimeFormat = repsTosec.isChecked(); // true for seconds, false for reps
                            exerciseList.get(i).setSets(sets);
                            exerciseList.get(i).setReps(reps);
                            exerciseList.get(i).setRepstosec(isTimeFormat);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Add sets and reps to exercises before saving", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

            }
            List<WorkoutExercises> workoutExercises = new ArrayList<>();
            for (int i = 0; i < exercises.size(); i++) {
                workoutExercises.add(new WorkoutExercises(exercises.get(i).getName(), exercises.get(i).getType(), exercises.get(i).getLink(), exercises.get(i).getImage1(), exercises.get(i).getImage2(), exercises.get(i).getImageMini(), exercises.get(i).getMuscleGroup(), exerciseList.get(i).getSets(), exerciseList.get(i).getReps(), exerciseList.get(i).getRepstosec()));
            }


            WorkoutData newData = new WorkoutData(valueTitle, valueTargetedMG, workoutExercises);

            workoutViewModel.updateWorkoutData(newData);

            showPublishDialog(newData);


            exerciseViewModel.updateCreationData(new ExerciseData(null, null, null, null, null, null, null));
            tableLayout.removeAllViews();
            exerciseList.removeAll(exerciseList);
            exercises.removeAll(exercises);
            preferences.edit().clear().apply();

        });

        addExercise.setOnClickListener(v -> {
            try {
                exerciseList.removeAll(exerciseList);
            } catch (Exception e) {
                Log.i("WCF", "258: no data to be deleted");
            }
            preferences.edit().clear().apply();

            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                View tableRow = tableLayout.getChildAt(i);
                if (tableRow instanceof TableRow) {
                    ConstraintLayout constraintLayout = tableRow.findViewById(R.id.constraintLayout); // Get ConstraintLayout inside TableRow

                    EditText setsEditText = constraintLayout.findViewById(R.id.sets);
                    EditText repsEditText = constraintLayout.findViewById(R.id.reps);
                    ToggleButton repsTosec = constraintLayout.findViewById(R.id.repstosec);
                    Integer sets, reps;
                    try {
                        sets = Integer.parseInt(setsEditText.getText().toString());
                    } catch (Exception e) {
                        sets = null;
                        Log.i("E4 + " + i, "onClick: ");
                    }

                    try {
                        reps = Integer.parseInt(repsEditText.getText().toString());
                    } catch (Exception e) {
                        reps = null;
                        Log.i("E5 + " + i, "onClick: ");
                    }

                    boolean isTimeFormat = repsTosec.isChecked();
                    WCExerciseData data = new WCExerciseData(sets, reps, isTimeFormat);
                    exerciseList.add(data);
                }
            }
            valueTitle = title.getText().toString();
            valueTargetedMG = targetedMG.getText().toString();
            SharedPreferences.Editor editor = preferences.edit();

            gson = new Gson();
            String titlesJson = gson.toJson(titles);
            String typesJson = gson.toJson(types);
            String linksJson = gson.toJson(links);
            String images1Json = gson.toJson(images1);
            String images2Json = gson.toJson(images2);
            String imagesMiniJson = gson.toJson(imagesMini);
            String muscleGroupsJson = gson.toJson(muscleGroupList);
            String detailsJson = gson.toJson(exerciseList);

            editor.putString("Title", valueTitle);
            editor.putString("TitleMG", valueTargetedMG);
            editor.putString("titlesJson", titlesJson);
            editor.putString("typesJson", typesJson);
            editor.putString("linksJson", linksJson);
            editor.putString("images1Json", images1Json);
            editor.putString("images2Json", images2Json);
            editor.putString("imagesMiniJson", imagesMiniJson);
            editor.putString("muscleGroupsJson", muscleGroupsJson);
            editor.putString("detailsJson", detailsJson);
            editor.apply();

//            valueTitle = title.getText().toString();
//            valueTargetedMG = targetedMG.getText().toString();
//            SharedPreferences.Editor editor = preferences.edit();
//
//            editor.putString("Title", valueTitle);
//            editor.putString("TitleMG", valueTargetedMG);
//            if (titles != null && titles.size() != 0) {
//                gson = new Gson();
//                String titlesJson = gson.toJson(titles);
//                String typesJson = gson.toJson(types);
//                String linksJson = gson.toJson(links);
//                String images1Json = gson.toJson(images1);
//                String images2Json = gson.toJson(images2);
//                String imagesMiniJson = gson.toJson(imagesMini);
//                String muscleGroupsJson = gson.toJson(muscleGroupList);
//                String detailsJson = gson.toJson(exerciseList);
//
//
//                editor.putString("titlesJson", titlesJson);
//                editor.putString("typesJson", typesJson);
//                editor.putString("linksJson", linksJson);
//                editor.putString("images1Json", images1Json);
//                editor.putString("images2Json", images2Json);
//                editor.putString("imagesMiniJson", imagesMiniJson);
//                editor.putString("muscleGroupsJson", muscleGroupsJson);
//                editor.putString("detailsJson", detailsJson);
//
//            } else {
//                editor.putString("titlesJson", null);
//                editor.putString("typesJson", null);
//                editor.putString("linksJson", null);
//                editor.putString("images1Json", null);
//                editor.putString("images2Json", null);
//                editor.putString("imagesMiniJson", null);
//                editor.putString("muscleGroupsJson", null);
//                editor.putString("detailsJson", null);
//            }
//            editor.apply();
            Navigation.findNavController(v).navigate(R.id.action_WorkoutCreation_to_Exercise);

        });

        exerciseViewModel.getCreationLiveData().observe(getViewLifecycleOwner(), new Observer<ExerciseData>() {
            @Override
            public void onChanged(@Nullable ExerciseData exerciseData) {
                if (exerciseData != null) {
                    if (exerciseData.getNewTitle() != null) {
                        if (titles == null) {
                            titles = new ArrayList<>();
                            types = new ArrayList<>();
                            links = new ArrayList<>();
                            images1 = new ArrayList<>();
                            images2 = new ArrayList<>();
                            imagesMini = new ArrayList<>();
                            muscleGroupList = new ArrayList<>();
                            Log.i("ExerciseViewModel", "creating arrays");
                        }
                        if (titles.size() == 0 || !Objects.equals(titles.get(titles.size() - 1), exerciseData.getNewTitle()) ) {
                            titles.add(exerciseData.getNewTitle());
                            types.add(exerciseData.getNewType());
                            links.add(exerciseData.getNewLink());
                            images1.add(exerciseData.getNewImage1());
                            images2.add(exerciseData.getNewImage2());
                            imagesMini.add(exerciseData.getNewImageMini());
                            muscleGroupList.add(exerciseData.getNewMuscleGroups());
                            Log.i("ExerciseViewModel", exerciseData.getNewTitle() + " added to workout");
                        }
                        exerciseViewModel.updateCreationData(new ExerciseData(null, null, null, null, null, null, null));
                        Log.i("ExerciseViewModel", "data deleted");
                        if (tableLayout.getChildCount() != titles.size()) {
                            createExerciseList();
                        }
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

        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View tableRow = tableLayout.getChildAt(i);
            if (tableRow instanceof TableRow) {
                ConstraintLayout constraintLayout = tableRow.findViewById(R.id.constraintLayout); // Get ConstraintLayout inside TableRow

                EditText setsEditText = constraintLayout.findViewById(R.id.sets);
                EditText repsEditText = constraintLayout.findViewById(R.id.reps);
                ToggleButton repsTosec = constraintLayout.findViewById(R.id.repstosec);

                try {
                    setsEditText.setText(exerciseList.get(i).getSets().toString());
                } catch (Exception e) {
                    Log.i("E2", "onViewCreated: ");
                }
                try {
                    repsEditText.setText(exerciseList.get(i).getReps().toString());
                } catch (Exception e) {
                    Log.i("E3", "onViewCreated: ");
                }
                try {
                    if (exerciseList.get(i).getRepstosec()) repsTosec.setChecked(true);
                } catch (Exception e) {
                    Log.i("catch", "no button state to load on pos: " + i);
                }
            }
        }


    }

    void createExerciseList() {
        exercises = new ArrayList<>();
        displayList = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            Exercises exercise = new Exercises(titles.get(i), types.get(i), links.get(i), muscleGroupList.get(i), images1.get(i), images2.get(i), imagesMini.get(i));
            exercises.add(exercise);
        }
        repsList = new ArrayList<>();
        setsList = new ArrayList<>();
        repstosecList = new ArrayList<>();

        if (tableLayout.getChildCount() != exercises.size()) {
            tableLayout.removeAllViews();
            int index = 0;
            for (Exercises item : exercises) {
                TableRow tableRow = new TableRow(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layoutExercise = inflater.inflate(R.layout.layout_exercise_wc, tableRow);
                TextView etitle = layoutExercise.findViewById(R.id.exercise);
                ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);
                ImageView remove = layoutExercise.findViewById(R.id.removeEntry);
                EditText sets = layoutExercise.findViewById(R.id.sets);
                EditText reps = layoutExercise.findViewById(R.id.reps);
                ToggleButton repstosec = layoutExercise.findViewById(R.id.repstosec);
                remove.setElevation(10);
                layoutExercise.setOnClickListener(v -> {

                    exerciseList.removeAll(exerciseList);
                    exerciseList = new ArrayList<>();
                    preferences.edit().clear().apply();

                    for (int i = 0; i < tableLayout.getChildCount(); i++) {
                        View tableR = tableLayout.getChildAt(i);
                        if (tableR instanceof TableRow) {
                            ConstraintLayout constraintLayout = tableR.findViewById(R.id.constraintLayout); // Get ConstraintLayout inside TableRow

                            EditText setsEditText = constraintLayout.findViewById(R.id.sets);
                            EditText repsEditText = constraintLayout.findViewById(R.id.reps);
                            ToggleButton repsTosec = constraintLayout.findViewById(R.id.repstosec);
                            Integer set, rep;
                            try {
                                set = Integer.parseInt(setsEditText.getText().toString());
                            } catch (Exception e) {
                                set = null;
                                Log.i("E4 + " + i, "onClick: ");
                            }

                            try {
                                rep = Integer.parseInt(repsEditText.getText().toString());
                            } catch (Exception e) {
                                rep = null;
                                Log.i("E5 + " + i, "onClick: ");
                            }

                            boolean isTimeFormat = repsTosec.isChecked();
                            WCExerciseData data = new WCExerciseData(set, rep, isTimeFormat);
                            exerciseList.add(data);
                        }

                    }
                    valueTitle = title.getText().toString();
                    valueTargetedMG = targetedMG.getText().toString();
                    SharedPreferences.Editor editor = preferences.edit();

                    gson = new Gson();
                    String titlesJson = gson.toJson(titles);
                    String typesJson = gson.toJson(types);
                    String linksJson = gson.toJson(links);
                    String images1Json = gson.toJson(images1);
                    String images2Json = gson.toJson(images2);
                    String imagesMiniJson = gson.toJson(imagesMini);
                    String muscleGroupsJson = gson.toJson(muscleGroupList);
                    String detailsJson = gson.toJson(exerciseList);

                    editor.putString("Title", valueTitle);
                    editor.putString("TitleMG", valueTargetedMG);
                    editor.putString("titlesJson", titlesJson);
                    editor.putString("typesJson", typesJson);
                    editor.putString("linksJson", linksJson);
                    editor.putString("images1Json", images1Json);
                    editor.putString("images2Json", images2Json);
                    editor.putString("imagesMiniJson", imagesMiniJson);
                    editor.putString("muscleGroupsJson", muscleGroupsJson);
                    editor.putString("detailsJson", detailsJson);
                    editor.apply();

//                    valueTitle = title.getText().toString();
//                    valueTargetedMG = targetedMG.getText().toString();
//                    for (int i = 0; i < tableLayout.getChildCount(); i++) {
//                        View tablerow = tableLayout.getChildAt(i);
//                        if (tablerow instanceof TableRow) {
//                            ConstraintLayout constraintLayout = tablerow.findViewById(R.id.constraintLayout); // Get ConstraintLayout inside TableRow
//
//                            EditText setsEditText = constraintLayout.findViewById(R.id.sets);
//                            EditText repsEditText = constraintLayout.findViewById(R.id.reps);
//                            ToggleButton repsTosec = constraintLayout.findViewById(R.id.repstosec);
//                            Integer set, rep;
//                            try {
//                                set = Integer.parseInt(setsEditText.getText().toString());
//                            } catch (Exception e) {
//                                set = null;
//                            }
//
//                            try {
//                                rep = Integer.parseInt(repsEditText.getText().toString());
//                            } catch (Exception e) {
//                                rep = null;
//                            }
//
//                            boolean isTimeFormat = repsTosec.isChecked();
//                            WCExerciseData data = new WCExerciseData(set, rep, isTimeFormat);
//                            exerciseList.add(data);
//                        }
//
//                    }
//
//                    if (titles != null && titles.size() != 0) {
//                        preferences.edit().clear().apply();
//                        SharedPreferences.Editor editor = preferences.edit();
//
//                        gson = new Gson();
//                        String titlesJson = gson.toJson(titles);
//                        String typesJson = gson.toJson(types);
//                        String linksJson = gson.toJson(links);
//                        String images1Json = gson.toJson(images1);
//                        String images2Json = gson.toJson(images2);
//                        String imagesMiniJson = gson.toJson(imagesMini);
//                        String muscleGroupsJson = gson.toJson(muscleGroupList);
//                        String detailsJson = gson.toJson(exerciseList);
//
//                        editor.putString("Title", valueTitle);
//                        editor.putString("TitleMG", valueTargetedMG);
//                        editor.putString("titlesJson", titlesJson);
//                        editor.putString("typesJson", typesJson);
//                        editor.putString("linksJson", linksJson);
//                        editor.putString("images1Json", images1Json);
//                        editor.putString("images2Json", images2Json);
//                        editor.putString("imagesMiniJson", imagesMiniJson);
//                        editor.putString("muscleGroupsJson", muscleGroupsJson);
//                        editor.putString("detailsJson", detailsJson);
//                        editor.apply();
//                    }
                    createExerciseDetail(item.getName(), item.getMuscleGroup(), item.getImage1(), item.getImage2(), item.getImageMini(), item.getLink(), item.getType());
                    Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_ExerciseDetail);
                });
                if (exerciseList != null) {
                    if (index >= exerciseList.size()) {
                        exerciseList.add(new WCExerciseData(null, null, false));
                    } else {
                        try {
                            Log.i("hahaahah", exerciseList.get(index).getSets().toString());
                            Log.i("hahaahah", exerciseList.get(index).getReps().toString());
                            Log.i("hahaahah", exerciseList.get(index).getRepstosec().toString());
                            sets.setText(exerciseList.get(index).getSets().toString());
                            reps.setText(exerciseList.get(index).getReps().toString());
//                            if (exerciseList.get(index).getRepstosec()) repstosec.setChecked(true);
//                            else repstosec.setChecked(false);
                        } catch (Exception e) {
                            Log.i("E1", "createExerciseList: ");
                        }
                    }
                } else {
                    exerciseList = new ArrayList<>();
                    exerciseList.add(new WCExerciseData(null, null, false));
                }
                repstosec.setChecked(exerciseList.get(index).getRepstosec());
                if (exerciseList.get(index).getRepstosec()) {
                    reps.setHint("Secs");
                } else {
                    reps.setHint("Reps");
                }
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
                        imagesMini.remove(indexToRemove);
                        muscleGroupList.remove(indexToRemove);
                        preferences.edit().clear().apply();
                        SharedPreferences.Editor editor = preferences.edit();
                        gson = new Gson();
                        String titlesJson = gson.toJson(titles);
                        String typesJson = gson.toJson(types);
                        String linksJson = gson.toJson(links);
                        String images1Json = gson.toJson(images1);
                        String images2Json = gson.toJson(images2);
                        String imagesMiniJson = gson.toJson(imagesMini);
                        String muscleGroupsJson = gson.toJson(muscleGroupList);
                        String detailsJson = gson.toJson(exerciseList);

                        editor.putString("Title", valueTitle);
                        editor.putString("TitleMG", valueTargetedMG);
                        editor.putString("titlesJson", titlesJson);
                        editor.putString("typesJson", typesJson);
                        editor.putString("linksJson", linksJson);
                        editor.putString("images1Json", images1Json);
                        editor.putString("images2Json", images2Json);
                        editor.putString("imagesMiniJson", imagesMiniJson);
                        editor.putString("muscleGroupsJson", muscleGroupsJson);
                        editor.putString("detailsJson", detailsJson);
                        editor.apply();
                    }
                });

                repstosec.setOnClickListener(v -> {
                    int indexToUpdate = tableLayout.indexOfChild(tableRow);
                    if (repstosec.isChecked()) {
                        exerciseList.get(indexToUpdate).setRepstosec(true);
                        updateViews(indexToUpdate);

                    } else {
                        exerciseList.get(indexToUpdate).setRepstosec(false);
                        updateViews(indexToUpdate);

                    }
                });
                repstosecList.add(repstosec);
                setsList.add(sets);
                repsList.add(reps);

                etitle.setText(item.getName());
                thumbnail.setImageResource(item.getImageMini());
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
    void createExerciseDetail (String name, List <String> muscleGroup, int image1, int image2, int imageMini, String link, String type){

        // Update the data in one go
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

    public void makePrivate(WorkoutData workoutData) {

        if (auth.getCurrentUser() == null) {
            Log.i("workouts offline", auth.getCurrentUser().getEmail());
            Toast.makeText(getContext(), "Log in to Make a Workout", Toast.LENGTH_SHORT).show();

        } else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            final String current = user.getEmail();

            List<HashMap<String, Object>> exercises = new ArrayList<>();
            for (WorkoutExercises exercise: workoutData.getExercises()) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", exercise.getTitle());
                map.put("type", exercise.getType());
                map.put("link", exercise.getLink());
                map.put("muscleGroup", exercise.getMuscleGroupsUsed());
                map.put("image1", exercise.getImage1());
                map.put("image2", exercise.getImage2());
                map.put("imageMini", exercise.getImageMini());
                map.put("reps", exercise.getReps());
                map.put("sets", exercise.getSets());
                map.put("repstosec", exercise.isTimeFormat());
                exercises.add(map);
            }

            db.collection("users")
                    .whereEqualTo("email", current)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String documentId = document.getId();
                                    HashMap<String, Object> data = new HashMap<>();
                                    data.put("title", workoutData.getTitle());
                                    data.put("targetMuscleGroups", workoutData.getTargetedMuscleGroups());
                                    data.put("exercises", exercises);
                                    data.put("author", document.get("nickname"));
                                    db.collection("users").document(documentId).update("workouts", FieldValue.arrayUnion(data)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_Workouts);
                                        }
                                    });
                                }
                                Log.i("workout user: ", auth.getCurrentUser().getEmail());
                            }
                        }
                    });
        }
    }

    public void publishWorkout(WorkoutData workoutData) {

        if (auth.getCurrentUser() == null) {
            Log.i("workouts local", auth.getCurrentUser().getEmail());
            Toast.makeText(getContext(), "Log in to Publish a Workout", Toast.LENGTH_SHORT).show();

        } else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            final String current = user.getEmail();

            List<HashMap<String, Object>> exercises = new ArrayList<>();
            for (WorkoutExercises exercise: workoutData.getExercises()) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", exercise.getTitle());
                map.put("type", exercise.getType());
                map.put("link", exercise.getLink());
                map.put("muscleGroup", exercise.getMuscleGroupsUsed());
                map.put("image1", exercise.getImage1());
                map.put("image2", exercise.getImage2());
                map.put("imageMini", exercise.getImageMini());
                map.put("reps", exercise.getReps());
                map.put("sets", exercise.getSets());
                map.put("repstosec", exercise.isTimeFormat());
                exercises.add(map);
            }

            data = new HashMap<>();
            data.put("title", workoutData.getTitle());
            data.put("targetMuscleGroups", workoutData.getTargetedMuscleGroups());
            data.put("exercises", exercises);

            db.collection("users")
                    .whereEqualTo("email", current)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // You can access the matched document here
                                    String documentId = document.getId();
                                    HashMap<String, Object> finalData = new HashMap<>();
                                    finalData.put("author", document.get("nickname"));
                                    finalData.put("workout", data);
                                    finalData.put("published", FieldValue.serverTimestamp());
                                    db.collection("workouts").add(finalData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_Workouts);
                                            }
                                        }
                                    });
                                }

                                Log.i("workout user: ", auth.getCurrentUser().getEmail());

                            }
                        }
                    });
        }
    }

    private void showPublishDialog(WorkoutData workoutData) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Make Workout Public Or Private?");
        builder.setMessage("Private is only accessible by the creator and public is accessible for anyone");
        builder.setPositiveButton("Public", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                publishWorkout(workoutData);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Private", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makePrivate(workoutData);
                dialog.dismiss();

            }

        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Save or Delete Workout Draft");
        builder.setMessage("Do you want to save or delete this workout draft?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    exerciseList.removeAll(exerciseList);
                } catch (Exception e) {
                }
                exerciseList = new ArrayList<>();
                preferences.edit().clear().apply();

                for (int i = 0; i < tableLayout.getChildCount(); i++) {
                    View tableRow = tableLayout.getChildAt(i);
                    if (tableRow instanceof TableRow) {
                        ConstraintLayout constraintLayout = tableRow.findViewById(R.id.constraintLayout); // Get ConstraintLayout inside TableRow

                        EditText setsEditText = constraintLayout.findViewById(R.id.sets);
                        EditText repsEditText = constraintLayout.findViewById(R.id.reps);
                        ToggleButton repsTosec = constraintLayout.findViewById(R.id.repstosec);
                        Integer sets, reps;
                        try {
                            sets = Integer.parseInt(setsEditText.getText().toString());
                        } catch (Exception e) {
                            sets = null;
                            Log.i("E4 + " + i, "onClick: ");
                        }

                        try {
                            reps = Integer.parseInt(repsEditText.getText().toString());
                        } catch (Exception e) {
                            reps = null;
                            Log.i("E5 + " + i, "onClick: ");
                        }

                        boolean isTimeFormat = repsTosec.isChecked();
                        WCExerciseData data = new WCExerciseData(sets, reps, isTimeFormat);
                        exerciseList.add(data);
                    }

                }
                valueTitle = title.getText().toString();
                valueTargetedMG = targetedMG.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();

                gson = new Gson();
                String titlesJson = gson.toJson(titles);
                String typesJson = gson.toJson(types);
                String linksJson = gson.toJson(links);
                String images1Json = gson.toJson(images1);
                String images2Json = gson.toJson(images2);
                String imagesMiniJson = gson.toJson(imagesMini);
                String muscleGroupsJson = gson.toJson(muscleGroupList);
                String detailsJson = gson.toJson(exerciseList);

                editor.putString("Title", valueTitle);
                editor.putString("TitleMG", valueTargetedMG);
                editor.putString("titlesJson", titlesJson);
                editor.putString("typesJson", typesJson);
                editor.putString("linksJson", linksJson);
                editor.putString("images1Json", images1Json);
                editor.putString("images2Json", images2Json);
                editor.putString("imagesMiniJson", imagesMiniJson);
                editor.putString("muscleGroupsJson", muscleGroupsJson);
                editor.putString("detailsJson", detailsJson);
                editor.apply();
                exerciseViewModel.updateCreationData(null);
                Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_Workouts);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                titles = null;
                types = null;
                links = null;
                images1 = null;
                images2 = null;
                imagesMini = null;
                muscleGroupList = null;
                exerciseList = null;

                exerciseViewModel.updateCreationData(null);
                tableLayout.removeAllViews();
                try {
                    exerciseList.removeAll(exerciseList);
                    exercises.removeAll(exercises);

                } catch(Exception e) {
                    Log.i("Error", "No exercises to delete");
                }
                SharedPreferences.Editor editor = preferences.edit().clear();
                editor.apply();
                dialog.dismiss();
                Navigation.findNavController(root).navigate(R.id.action_WorkoutCreation_to_Workouts);
            }

        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
