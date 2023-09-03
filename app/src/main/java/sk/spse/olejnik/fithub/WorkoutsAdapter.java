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

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder> {


    public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profilePicture;
        TextView username, workoutTitle, targetedMG, published, exerciseCount;
        View view;

        WorkoutViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            profilePicture = (ImageView) itemView.findViewById(R.id.profilePicture);
            username = (TextView) itemView.findViewById(R.id.username);
            published = (TextView) itemView.findViewById(R.id.published);
            workoutTitle = (TextView) itemView.findViewById(R.id.workoutTitle);
            targetedMG = (TextView) itemView.findViewById(R.id.targetedMG);
            exerciseCount = (TextView) itemView.findViewById(R.id.exerciseCount);
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

    private List<WorkoutData> workouts;
    private List<String> usernames, times;

    Context context;

    public WorkoutsAdapter(List<WorkoutData> workouts, List<String> usernames, List<String> times, Context context) {
        this.workouts = workouts;
        this.usernames = usernames;
        this.times = times;
        this.context = context;
    }
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_workout, parent, false);
        WorkoutViewHolder viewHolder = new WorkoutViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.username.setText(usernames.get(position));
        holder.workoutTitle.setText(workouts.get(position).getTitle());
        holder.targetedMG.setText(workouts.get(position).getTargetedMuscleGroups());
        holder.profilePicture.setImageResource(R.drawable.ic_profile_foreground);
        holder.published.setText(times.get(position));
        if (workouts.get(position).getExercises().size() > 1) holder.exerciseCount.setText(workouts.get(position).getExercises().size() + " Exercises");
        else holder.exerciseCount.setText(workouts.get(position).getExercises().size() + " Exercise");
    }

    @Override
    public int getItemCount() {
        Log.i("gettings", workouts.size() + "");
        return workouts.size();
    }



        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        private OnEntryClickListener mOnEntryClickListener;

        public interface OnEntryClickListener {
            void onEntryClick(View view, int position);
        }

        public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
            mOnEntryClickListener = onEntryClickListener;
        }
    }
    //    @NonNull
//    @Override
//    public WorkoutsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull WorkoutsAdapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
//        Context context
//                = parent.getContext();
//        LayoutInflater inflater
//                = LayoutInflater.from(context);
//
//        // Inflate the layout
//
//        View photoView
//                = inflater
//                .inflate(R.layout.card_exam,
//                        parent, false);
//
//        workoutViewHolder viewHolder
//                = new workoutViewHolder(photoView);
//        return viewHolder;
//    }
