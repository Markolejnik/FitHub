package sk.spse.olejnik.fithub;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressEntryFragment extends Fragment {

    ImageView goBack, thumbnail;
    EditText reps, weight;
    TextView exerciseTitle, date, x, lbtokgtext;
    Button saveEntry;
    ToggleButton repsToSec, weighted, lbtokg;
    TextInputEditText note;
    LinearLayout root;

    private ExerciseViewModel exerciseViewModel;
    private ProgressViewModel progressViewModel;

    String title, types, link, noteValue;
    Integer image1, image2;
    List<String> muscleGroups;
    Boolean repstosecValue, weightedValue, lbtokgValue;
    Calendar dateValue;
    Exercises exercise;
    Integer repsValue, weightValue;
    Map<String, Object> entry, entryExercise;

    Gson gson;
    Type type;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    public ProgressEntryFragment() {}

    public static ProgressEntryFragment newInstance() {
        ProgressEntryFragment fragment = new ProgressEntryFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = view.findViewById(R.id.root);
        goBack = view.findViewById(R.id.goBack);
        date = view.findViewById(R.id.date);
        thumbnail = view.findViewById(R.id.exercise_thumbnail);
        exerciseTitle = view.findViewById(R.id.exercise_title);
        reps = view.findViewById(R.id.reps);
        x = view.findViewById(R.id.x);
        weight = view.findViewById(R.id.weight);
        lbtokgtext = view.findViewById(R.id.lbtokgText);
        repsToSec = view.findViewById(R.id.repstosec);
        weighted = view.findViewById(R.id.without_weight);
        lbtokg = view.findViewById(R.id.lbtokg);
        note = view.findViewById(R.id.note);
        saveEntry = view.findViewById(R.id.SaveEntry);
        repstosecValue = false;
        weightedValue = false;
        lbtokgValue = false;


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
                    Log.i("the date", dateValue.toString());

                    // Format the Date to a String
                    String formattedDate = dateFormat.format(dateValue.getTime());
                    date.setText(formattedDate);
                    exerciseTitle.setText(exercise.getName());
                    thumbnail.setImageResource(exercise.getImage1());

                }
            }
        });

        repsToSec.setOnClickListener(v -> {
            repstosecValue = repsToSec.isChecked();
            if (repstosecValue) {
                reps.setHint("Secs");
            } else {
                reps.setHint("Reps");
            }
        });

        weighted.setOnClickListener(v -> {
            weightedValue = weighted.isChecked();
            if (weightedValue) {
                weight.setVisibility(View.GONE);
                x.setVisibility(View.GONE);
                lbtokg.setVisibility(View.GONE);
                lbtokgtext.setVisibility(View.GONE);
            } else {
                weight.setVisibility(View.VISIBLE);
                x.setVisibility(View.VISIBLE);
                lbtokg.setVisibility(View.VISIBLE);
                lbtokgtext.setVisibility(View.VISIBLE);
            }
        });

        lbtokg.setOnClickListener(v -> {
            lbtokgValue = lbtokg.isChecked();
            if (lbtokgValue) {
                lbtokgtext.setText("lbs");
            } else {
                lbtokgtext.setText("kg");
            }
        });

        saveEntry.setOnClickListener(v -> {
            if (!weightedValue) {
                try {
                    repsValue = Integer.parseInt(reps.getText().toString());
                    weightValue = Integer.parseInt(weight.getText().toString());

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Make sure to add all required values before saving", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                try {
                    repsValue = Integer.parseInt(reps.getText().toString());

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Make sure to add all required values before saving", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            noteValue = note.getText().toString();
            ProgressData entryData = new ProgressData(exercise, repsValue, weightValue, dateValue, repstosecValue, weightedValue, lbtokgValue, noteValue);

            publishEntry(entryData);

        });


    }

    public void publishEntry(ProgressData data) {

        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Log in to Create a Progress Entry", Toast.LENGTH_SHORT).show();

        } else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            final String current = user.getEmail();


            entryExercise = new HashMap<>();
            entryExercise.put("name", data.getExercise().getName());
            entryExercise.put("type", data.getExercise().getType());
            entryExercise.put("link", data.getExercise().getLink());
            entryExercise.put("muscleGroup", data.getExercise().getMuscleGroup());
            entryExercise.put("image1", data.getExercise().getImage1());
            entryExercise.put("image2", data.getExercise().getImage2());

            entry = new HashMap<>();
            entry.put("exercise", entryExercise);
            entry.put("reps", data.getReps());
            entry.put("weight", data.getWeight());
            entry.put("date", data.getDate().getTime());
            entry.put("repstosec", data.getRepstosec());
            entry.put("bodyweight", data.getBodyweight());
            entry.put("lbtokg", data.getLbtokg());
            entry.put("note", data.getNote());


            db.collection("users").whereEqualTo("email", current)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                gson = new Gson();
                                String jsonData = gson.toJson(data);
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // You can access the matched document here
                                    String documentId = document.getId();
                                    Log.i("TAG", entry.toString());
                                    db.collection("users").document(documentId).update("progress", FieldValue.arrayUnion(entry)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Navigation.findNavController(root).navigate(R.id.action_ProgressEntry_to_Calendar);
                                        }
                                    });
                                }
                                Log.i("progress user: ", auth.getCurrentUser().getEmail());
                            }
                        }
                    });
        }
    }


}