package sk.spse.olejnik.fithub;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment implements ProgressAdapter.AlertDialogListener {
    ConstraintLayout root;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    CalendarView calendarView;
    RecyclerView entryList;
//    Button signOut, delete;


    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    Query query;
    Gson gson;
    Type type;

    List<ProgressData> progressList;
    Integer vDay, vMonth, vYear;
    Calendar date;

    private SharedPreferences preferences, preferences2;
    private ProgressViewModel progressViewModel;
    ProgressAdapter progressAdapter;

    public CalendarFragment() {}

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        signOut = view.findViewById(R.id.signOut);
//        delete = view.findViewById(R.id.deleteUser);
        root = view.findViewById(R.id.calendarRoot);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        calendarView = view.findViewById(R.id.calendarView);
        entryList = view.findViewById(R.id.entryList);
        fab = view.findViewById(R.id.fab);
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        progressViewModel = ((MainActivity) requireActivity()).getProgressViewModel();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.workouts) {
                    Navigation.findNavController(view).navigate(R.id.action_Calendar_to_Workouts);
                    return true;
                }
                else if (id == R.id.exercises) {
                    Navigation.findNavController(view).navigate(R.id.action_Calendar_to_Exercise);
                    return true;
                }
                else if (id == R.id.profile) {
                    Navigation.findNavController(view).navigate(R.id.action_Calendar_to_Profile);
                    return true;
                }
                else if (id == R.id.calendar) {

                    return true;
                }
                else{return false;}
            }
        });
        fab.setOnClickListener(v -> {
            Log.i("jiggas", "" + vDay + " " + vMonth + " " + vYear);
            if (vDay == null || vMonth == null || vYear == null) {
                getCurrentDate();
                Log.i("jiggas", "" + vDay + " " + vMonth + " " + vYear);
            }
            date = new Calendar() {
                @Override
                protected void computeTime() {

                }

                @Override
                protected void computeFields() {

                }

                @Override
                public void add(int field, int amount) {

                }

                @Override
                public void roll(int field, boolean up) {

                }

                @Override
                public int getMinimum(int field) {
                    return 0;
                }

                @Override
                public int getMaximum(int field) {
                    return 0;
                }

                @Override
                public int getGreatestMinimum(int field) {
                    return 0;
                }

                @Override
                public int getLeastMaximum(int field) {
                    return 0;
                }
            };
            date = Calendar.getInstance();
            date.set(Calendar.DATE, vDay);
            date.set(Calendar.MONTH, vMonth-1);
            date.set(Calendar.YEAR, vYear);

            Log.i("date", date.getTime().toString());
            ProgressData newData = new ProgressData(null, null, null, date, null, null, null, null);

            progressViewModel.updateProgressData(newData);
            Navigation.findNavController(root).navigate(R.id.action_Calendar_to_CalendarExercise);
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                vYear = year;
                vMonth = month + 1;
                vDay = dayOfMonth;
            }
        });

        progressAdapter = new ProgressAdapter(getProgressData(), getContext());
        progressAdapter.setAlertDialogListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        entryList.setLayoutManager(layoutManager);
        entryList.setAdapter(progressAdapter);
        progressAdapter.notifyDataSetChanged();
        Log.i("TAG", progressAdapter.getItemCount() + " ");



    }

    public void getCurrentDate() {
        LocalDate currentDate = LocalDate.now();

        vDay = currentDate.getDayOfMonth();

        // Get month from date
        vMonth = currentDate.getMonthValue();
        Log.i("month", vMonth.toString());
        // Get year from date
        vYear = currentDate.getYear();
    }

    @Override
    public void onResume() {
        super.onResume();
        vDay = null;
        vMonth = null;
        vYear = null;
    }

    public List<ProgressData> getProgressData() {
        progressList = new ArrayList<>();
        if (auth.getCurrentUser() == null) {
            Log.i("not logged in", auth.getCurrentUser().getEmail());
            Toast.makeText(getContext(), "Sign In To Save Or Load Progress", Toast.LENGTH_SHORT).show();
            return null;
        } else {
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String current = user.getEmail();
                db.collection("users").whereEqualTo("email", current).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot data = task.getResult();

                            for (QueryDocumentSnapshot document : data) {
//                            Log.i("map", document.toString());
//                            Log.d("Ahoj", document.getId() + " => " + document.getData());
                                List<Map<String, Object>> docdata = (List<Map<String, Object>>) document.get("progress");
//                            ArrayList<String> dataa = (ArrayList<String>) ahoj;
                                try {
                                    if (docdata.size() == 0) Log.i("ProgressData", "No progress data saved");
                                    else {
                                        for (Map<String, Object> entry : docdata) {
                                            Timestamp getDate = (Timestamp) entry.get("date");
                                            Date date = getDate.toDate();
                                            Calendar dateData = Calendar.getInstance();
                                            dateData.setTime(date);
                                            HashMap<String, Object> exerciseData = (HashMap<String, Object>) entry.get("exercise");

                                            Exercises exercise = new Exercises((String) exerciseData.get("name"), (String) exerciseData.get("type"), (String) exerciseData.get("link"), (List<String>) exerciseData.get("muscleGroup"), Long.valueOf((String) String.valueOf(exerciseData.get("image1"))).intValue(), Long.valueOf((String) String.valueOf(exerciseData.get("image2"))).intValue(), Long.valueOf((String) String.valueOf(exerciseData.get("imageMini"))).intValue());
                                            ProgressData entryData = new ProgressData(exercise, Long.valueOf((String) String.valueOf(entry.get("reps"))).intValue(), Long.valueOf((String) String.valueOf(entry.get("weight"))).intValue(), dateData, (Boolean) entry.get("repstosec"), (Boolean) entry.get("bodyweight"), (Boolean) entry.get("lbtokg"), (String) entry.get("note"));
                                            progressList.add(entryData);
                                            progressAdapter.notifyDataSetChanged();
                                        }
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                });
            return progressList;
        }
    }

    @Override
    public void onItemClicked(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Progress Entry?")
                .setMessage("Deleting will remove this entry forever!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final String current = user.getEmail();

                        Map<String, Object> entryExercise = new HashMap<>();
                        entryExercise.put("name", progressList.get(position).getExercise().getName());
                        entryExercise.put("type", progressList.get(position).getExercise().getType());
                        entryExercise.put("link", progressList.get(position).getExercise().getLink());
                        entryExercise.put("muscleGroup", progressList.get(position).getExercise().getMuscleGroup());
                        entryExercise.put("image1", progressList.get(position).getExercise().getImage1());
                        entryExercise.put("image2", progressList.get(position).getExercise().getImage2());
                        entryExercise.put("imageMini", progressList.get(position).getExercise().getImageMini());

                        Map<String, Object> entry = new HashMap<>();
                        entry.put("exercise", entryExercise);
                        entry.put("reps", progressList.get(position).getReps());
                        entry.put("weight", progressList.get(position).getWeight());
                        entry.put("date", progressList.get(position).getDate().getTime());
                        entry.put("repstosec", progressList.get(position).getRepstosec());
                        entry.put("bodyweight", progressList.get(position).getBodyweight());
                        entry.put("lbtokg", progressList.get(position).getLbtokg());
                        entry.put("note", progressList.get(position).getNote());


                        db.collection("users").whereEqualTo("email", current).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot data = task.getResult();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String documentId = document.getId();
                                        db.collection("users").document(documentId).update("progress", FieldValue.arrayRemove(entry)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressList.remove(progressList.get(position));
                                                progressAdapter.notifyItemRemoved(position);
                                            }
                                        });
                                    }
                                }
                            }
                        });

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


}