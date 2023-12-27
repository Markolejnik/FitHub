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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    FirebaseFirestore db;
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
        Log.i("sizes", usernames.size() + " " + workouts.size() + " " + times.size());
        holder.username.setText(usernames.get(position));
        holder.workoutTitle.setText(workouts.get(position).getTitle());
        holder.targetedMG.setText(workouts.get(position).getTargetedMuscleGroups());

        try {
            holder.published.setText(times.get(position));
        } catch (Exception e) {
            holder.published.setText("");
        }
        if (workouts.get(position).getExercises().size() > 1) holder.exerciseCount.setText(workouts.get(position).getExercises().size() + " Exercises");
        else holder.exerciseCount.setText(workouts.get(position).getExercises().size() + " Exercise");
        try {
            db = FirebaseFirestore.getInstance();
            db.collection("users").whereEqualTo("nickname", usernames.get(position)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult()) {

                        String imageUrl = document.getString("pfpUrl");
                        if (imageUrl != null) {
                            Glide.with(context)
                                    .load(imageUrl)
                                    .into(holder.profilePicture);
                            holder.profilePicture.setClipToOutline(true);
                        } else {
                            holder.profilePicture.setImageResource(R.mipmap.icon_logo_round);
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.i("WA", "error setting pfp on workout: " + position);
        }
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
