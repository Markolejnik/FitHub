package sk.spse.olejnik.fithub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragment extends Fragment {
    
    TableLayout tableLayout;
    ConstraintLayout layoutExercise;
    List<Exercises> exercises;

    public ExerciseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExerciseRepository repository = new ExerciseRepository();
        exercises = repository.allExercises;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableLayout = view.findViewById(R.id.tableLayout);

        for (Exercises item: exercises) {
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), item.getImage1());

            int originalWidth = originalBitmap.getWidth();
            int originalHeight = originalBitmap.getHeight();

            int desiredWidth = originalWidth / 4; // Scale down to half of the original width
            int desiredHeight = originalHeight / 4; // Scale down to half of the original height
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);
            TableRow tableRow = new TableRow(getContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View layoutExercise = inflater.inflate(R.layout.layout_exercise, null);
            TextView title = layoutExercise.findViewById(R.id.exercise);
            ImageView thumbnail = layoutExercise.findViewById(R.id.thumbnail);
            layoutExercise.setOnClickListener( v -> {
                createExerciseDetail(item.getName(), item.getMuscleGroup(), item.getImage1(), item.getImage2(), item.getLink(), item.getType());
            });

            title.setText(item.getName());
//            title.setWidth(mainActivity.width - (mainActivity.width/4));
            thumbnail.setImageBitmap(scaledBitmap);
            tableRow.addView(layoutExercise);
            tableLayout.addView(tableRow);
            Log.i("jo", item.getName());
        }

    }
    void createExerciseDetail(String name, List<String> muscleGroup, int image1, int image2, String link, String type) {

        Log.i("mi", name);
        Bundle args = new Bundle();
        args.putString("title", name);
        args.putStringArrayList("groups", (ArrayList<String>) muscleGroup);
        args.putInt("img1", image1);
        args.putInt("img2", image2);
        args.putString("link", link);
        args.putString("type", type);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        MainActivity mainActivity = (MainActivity) getActivity();
        intent.putExtras(args);
        startActivity(intent);
        mainActivity.saveData();

        }
}