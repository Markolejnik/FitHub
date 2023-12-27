package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {


    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;
        TextView exercise, add;

        ExerciseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            exercise = (TextView) itemView.findViewById(R.id.exercise);
            add = (TextView) itemView.findViewById(R.id.add);
        }

        @Override
        public void onClick(View v) {
            // The user may not set a click listener for list items, in which case our listener
            // will be null, so we need to check for this
            if (mOnEntryClickListener != null) {
                mOnEntryClickListener.onEntryClick(v, getLayoutPosition());
            }
        }
    }

    private List<Exercises> exercises, filteredExercises;
    CalendarExerciseFragment calendarExerciseFragment = new CalendarExerciseFragment();

    Context context;
    private OnEntryClickListener onEntryClickListener;
    public ExerciseAdapter(List<Exercises> exercises, Context context) {
        this.exercises = exercises;
        this.filteredExercises = new ArrayList<>(exercises);
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_calendar_exercise, parent, false);
        ExerciseAdapter.ExerciseViewHolder viewHolder = new ExerciseAdapter.ExerciseViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ExerciseViewHolder holder, int position) {
        holder.thumbnail.setImageResource(filteredExercises.get(position).getImageMini());
        holder.exercise.setText(filteredExercises.get(position).getName());
//        holder.itemView.setOnClickListener(v -> {
//            calendarExerciseFragment.createProgressEntry(filteredExercises.get(position));
//        });
    }

    @Override
    public int getItemCount() {
        return filteredExercises.size();
    }

    public List<Exercises> filter(String query) {
        filteredExercises.clear();
        if (query.isEmpty()) {
            filteredExercises.addAll(exercises);
        } else {
            for (Exercises exercise : exercises) {
                if (exercise.getName().toLowerCase(Locale.ROOT).contains(query)) {
                    filteredExercises.add(exercise);
                }
            }
        }
        return filteredExercises;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private ExerciseAdapter.OnEntryClickListener mOnEntryClickListener;

    public interface OnEntryClickListener {
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(ExerciseAdapter.OnEntryClickListener onEntryClickListener) {
        mOnEntryClickListener = onEntryClickListener;
    }
}
