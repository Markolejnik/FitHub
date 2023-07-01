package sk.spse.olejnik.fithub;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class ExerciseFragment extends Fragment {
    
    TableLayout tableLayout;
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
            TableRow tableRow = new TableRow(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            TextView textView = new TextView(getContext());
            textView.setText(item.getName());
            textView.setTextSize(20);
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.days_one);
            textView.setTypeface(typeface);
            textView.setTextColor(getResources().getColor(R.color.foreground));
            textView.setPadding(10, 15, 0, 15);

            tableRow.addView(textView);
            tableRow.setPadding(10, 15, 0, 15);

            tableLayout.addView(tableRow, layoutParams);
        }
    }
}