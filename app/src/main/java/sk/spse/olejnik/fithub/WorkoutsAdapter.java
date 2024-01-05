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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder> {


    public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profilePicture, upvote, downvote;
        TextView username, workoutTitle, targetedMG, published, exerciseCount, rating;


        WorkoutViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            profilePicture = (ImageView) itemView.findViewById(R.id.profilePicture);
            username = (TextView) itemView.findViewById(R.id.username);
            published = (TextView) itemView.findViewById(R.id.published);
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
    FirebaseFirestore db;
    Context context;

    public WorkoutsAdapter(List<PublishedWorkoutData> pWorkouts, String user, Context context) {
        notifyDataSetChanged();
        this.pWorkouts = pWorkouts;
        this.user = user;
        this.context = context;
        notifyDataSetChanged();
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
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.i("sizes", pWorkouts.size() + "");
        Integer ratingValue = 0;
        holder.username.setText(pWorkouts.get(position).getAuthor());
        holder.workoutTitle.setText(pWorkouts.get(position).getWorkout().getTitle());
        holder.targetedMG.setText(pWorkouts.get(position).getWorkout().getTargetedMuscleGroups());
        if (pWorkouts.get(position).getRatings() == null) {
            holder.upvote.setVisibility(View.GONE);
            holder.downvote.setVisibility(View.GONE);
            holder.rating.setVisibility(View.GONE);
        }
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

    public Boolean hasVoted(Integer position, String user) {
        HashMap<String, Boolean> ratings = pWorkouts.get(position).getRatings();
        if (ratings.containsKey(user)) return true;
        else return false;
    }

    public Boolean getVote(Integer position, String user) {
        notifyDataSetChanged();
        HashMap<String, Boolean> ratings = pWorkouts.get(position).getRatings();
        Log.i("got vote", position + "");
        Log.i("TAG", user);
        for (Map.Entry<String, Boolean> entry : ratings.entrySet()) {
            Log.i("map", "Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        Log.i("TAG", ratings.toString());
        return ratings.get(user);
    }

    @Override
    public int getItemCount() {
        Log.i("gettings", pWorkouts.size() + "");
        return pWorkouts.size();
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
