package sk.spse.olejnik.fithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder> {


    public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePicture, upvote, downvote, delete;
        TextView username, workoutTitle, targetedMG, published, exerciseCount, rating;


        WorkoutViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            profilePicture = (ImageView) itemView.findViewById(R.id.profilePicture);
            username = (TextView) itemView.findViewById(R.id.username);
            published = (TextView) itemView.findViewById(R.id.published);
            delete = (ImageView) itemView.findViewById(R.id.deleteWorkout);
            workoutTitle = (TextView) itemView.findViewById(R.id.workoutTitle);
            targetedMG = (TextView) itemView.findViewById(R.id.targetedMG);
            exerciseCount = (TextView) itemView.findViewById(R.id.exerciseCount);
            upvote = (ImageView) itemView.findViewById(R.id.upvote);
            downvote = (ImageView) itemView.findViewById(R.id.downvote);
            rating = (TextView) itemView.findViewById(R.id.rating);

        }

        @Override
        public void onClick(View v) {
            if (mOnEntryClickListener != null) {
                mOnEntryClickListener.onEntryClick(v, getLayoutPosition());
            }
        }
    }
    private List<PublishedWorkoutData> pWorkouts;
    private String user;
    private Boolean isDeletable;
    FirebaseFirestore db;
    Context context;

    public WorkoutsAdapter(List<PublishedWorkoutData> pWorkouts, String user, Boolean isDeletable, Context context) {
        notifyDataSetChanged();
        this.pWorkouts = pWorkouts;
        this.user = user;
        this.isDeletable = isDeletable;
        this.context = context;
        notifyDataSetChanged();
    }

    // Get context and inflate layout
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
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.i("sizes", pWorkouts.size() + "");

        // Set widget content
        holder.username.setText(pWorkouts.get(position).getAuthor());
        holder.workoutTitle.setText(pWorkouts.get(position).getWorkout().getTitle());
        holder.targetedMG.setText(pWorkouts.get(position).getWorkout().getTargetedMuscleGroups());
        if (pWorkouts.get(position).getRatings() == null) {
            holder.upvote.setVisibility(View.GONE);
            holder.downvote.setVisibility(View.GONE);
            holder.rating.setVisibility(View.GONE);
        }
        if (isDeletable) holder.delete.setVisibility(View.VISIBLE);
        else holder.delete.setVisibility(View.GONE);

        // Handle delete button press
        holder.delete.setOnClickListener(v -> {
            AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(context);
            deleteBuilder.setTitle("Delete Workout?");
            deleteBuilder.setMessage("Are you sure you want to delete this workout?");
            deleteBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (pWorkouts.get(position).getRatings() != null) {
                            db.collection("workouts").document(pWorkouts.get(position).getId()).delete();
                            holder.itemView.setVisibility(View.GONE);
                        }
                        else {
                            db.collection("users").whereEqualTo("nickname", pWorkouts.get(position).getAuthor()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String id = document.getId();
                                        Log.i("pos", " " + position);
                                        HashMap<String, Object> workout = new HashMap<>();
                                        ArrayList<HashMap<String, Object>> exeList = new ArrayList<>();
                                        List<WorkoutExercises> e = pWorkouts.get(position).getWorkout().getExercises();
                                        for (WorkoutExercises exercise: e) {
                                            HashMap<String, Object> exercises = new HashMap<>();
                                            exercises.put("image1", exercise.getImage1());
                                            exercises.put("image2", exercise.getImage2());
                                            exercises.put("imageMini", exercise.getImageMini());
                                            exercises.put("link", exercise.getLink());
                                            exercises.put("muscleGroup", exercise.getMuscleGroupsUsed());
                                            exercises.put("name", exercise.getTitle());
                                            exercises.put("reps", exercise.getReps());
                                            exercises.put("repstosec", exercise.isTimeFormat());
                                            exercises.put("sets", exercise.getSets());
                                            exercises.put("type", exercise.getType());
                                            exeList.add(exercises);
                                        }
                                        workout.put("author", pWorkouts.get(position).getAuthor());
                                        workout.put("exercises", exeList);
                                        workout.put("rating", new HashMap<>());
                                        workout.put("targetMuscleGroups", pWorkouts.get(position).getWorkout().getTargetedMuscleGroups());
                                        workout.put("title", pWorkouts.get(position).getWorkout().getTitle());
                                        db.collection("users").document(id).update("workouts", FieldValue.arrayRemove(workout));
                                        holder.itemView.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }
                        notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(context, "Workout Deletion Failed", Toast.LENGTH_SHORT).show();
                        Log.i("WA", "157 - workout deletion failed");
                    }
                    dialog.dismiss();
                }
            });
            deleteBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            deleteBuilder.show();
        });

        // Handle upvoting
        holder.upvote.setOnClickListener(v -> {
            try {
                DocumentReference ref = db.collection("workouts").document(pWorkouts.get(position).getId());
                db.collection("workouts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getId().equals(pWorkouts.get(position).getId())) {

                                pWorkouts.get(position).setRatings((HashMap<String, Boolean>) document.get("rating"));
                                if (pWorkouts.get(position).getRatings() == null) pWorkouts.get(position).setRatings(new HashMap<>());
                                notifyDataSetChanged();
                                if (hasVoted(position, user)) {
                                    try {
                                        if (getVote(position, user)) pWorkouts.get(position).getRatings().remove(user);
                                        else pWorkouts.get(position).getRatings().put(user, true);
                                    } catch (Exception e) {
                                        pWorkouts.get(position).getRatings().put(user, true);
                                    }
                                } else pWorkouts.get(position).getRatings().put(user, true);
                                Map<String, Object> updateMap = new HashMap<>();
                                updateMap.put("rating", pWorkouts.get(position).getRatings());

                                for (Map.Entry<String, Object> entry : updateMap.entrySet()) Log.i("map", "Key: " + entry.getKey() + ", Value: " + entry.getValue());

                                ref.update(updateMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("upvote", pWorkouts.get(position).getId());
                                    }
                                });
                            }
                        }
                    }
                });
                notifyDataSetChanged();
                holder.rating.setText(getRatingValue(position).toString());
            } catch (Exception e) {
                Log.i("upvote", "failed");
            }
        });

        // Handle downvoting
        holder.downvote.setOnClickListener(v -> {
            try {
                DocumentReference ref = db.collection("workouts").document(pWorkouts.get(position).getId());
                db.collection("workouts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (document.getId().equals(pWorkouts.get(position).getId())) {
                                pWorkouts.get(position).setRatings((HashMap<String, Boolean>) document.get("rating"));

                                if (pWorkouts.get(position).getRatings() == null) pWorkouts.get(position).setRatings(new HashMap<>());

                                notifyDataSetChanged();

                                if (hasVoted(position, user)) {
                                    try {
                                        if (!getVote(position, user)) pWorkouts.get(position).getRatings().remove(user);
                                        else pWorkouts.get(position).getRatings().put(user, false);
                                    } catch (Exception e) {
                                        pWorkouts.get(position).getRatings().put(user, false);
                                    }
                                } else pWorkouts.get(position).getRatings().put(user, false);

                                Map<String, Object> updateMap = new HashMap<>();
                                updateMap.put("rating", pWorkouts.get(position).getRatings());

                                for (Map.Entry<String, Object> entry : updateMap.entrySet()) Log.i("map", "Key: " + entry.getKey() + ", Value: " + entry.getValue());

                                ref.update(updateMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("downvote", pWorkouts.get(position).getId());
                                    }
                                });
                            }
                        }
                    }
                });

                notifyDataSetChanged();
                holder.rating.setText(getRatingValue(position).toString());

            } catch (Exception e) {
                Log.i("downvote", "failed");
            }
        });

        holder.rating.setText(getRatingValue(position).toString());

        try {
            holder.published.setText(pWorkouts.get(position).getTime());
        } catch (Exception e) {
            holder.published.setText("");
        }

        if (pWorkouts.get(position).getWorkout().getExercises().size() > 1) holder.exerciseCount.setText(pWorkouts.get(position).getWorkout().getExercises().size() + " Exercises");
        else holder.exerciseCount.setText(pWorkouts.get(position).getWorkout().getExercises().size() + " Exercise");

        // Load user pfp
        try {
            db = FirebaseFirestore.getInstance();
            db.collection("users").whereEqualTo("nickname", pWorkouts.get(position).getAuthor()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    // Get workout rating
    public Integer getRatingValue(Integer position) {
        Integer ratingValue = 0;
        HashMap<String, Boolean> ratings = pWorkouts.get(position).getRatings();
        if (ratings != null) {
            for (Map.Entry<String, Boolean> entry : ratings.entrySet()) {
                ratingValue += entry.getValue() ? 1 : -1;
            }
        }
        return ratingValue;
    }

    // Check if user already voted
    public Boolean hasVoted(Integer position, String user) {
        HashMap<String, Boolean> ratings = pWorkouts.get(position).getRatings();
        if (ratings.containsKey(user)) return true;
        else return false;
    }

    // Get users vote
    public Boolean getVote(Integer position, String user) {
        notifyDataSetChanged();
        HashMap<String, Boolean> ratings = pWorkouts.get(position).getRatings();
        for (Map.Entry<String, Boolean> entry : ratings.entrySet()) {
            Log.i("map", "Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        return ratings.get(user);
    }

    @Override
    public int getItemCount() {
        Log.i("workouts size", pWorkouts.size() + "");
        return pWorkouts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // On entry click listener
    private OnEntryClickListener mOnEntryClickListener;

    public interface OnEntryClickListener {
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        mOnEntryClickListener = onEntryClickListener;
    }
}
