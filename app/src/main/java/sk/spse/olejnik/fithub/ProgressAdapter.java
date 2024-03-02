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

import java.text.SimpleDateFormat;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {


    public interface AlertDialogListener {
        void onItemClicked(int position);
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView thumbnail, removeEntry;
    TextView title, reps, repsOrSec, weight, date, lbkg, note, x;

    ProgressViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        title = (TextView) itemView.findViewById(R.id.title);
        date = (TextView) itemView.findViewById(R.id.date);
        reps = (TextView) itemView.findViewById(R.id.reps);
        repsOrSec = (TextView) itemView.findViewById(R.id.repsOrSec);
        weight = (TextView) itemView.findViewById(R.id.weight);
        lbkg = (TextView) itemView.findViewById(R.id.lbkg);
        note = (TextView) itemView.findViewById(R.id.note);
        x = (TextView) itemView.findViewById(R.id.x);
        removeEntry = (ImageView) itemView.findViewById(R.id.removeEntry);
    }

    @Override
    public void onClick(View v) {
        if (mOnEntryClickListener != null) {
            mOnEntryClickListener.onEntryClick(v, getLayoutPosition());
        }
    }
}
    public AlertDialogListener alertDialogListener;

    public void setAlertDialogListener(AlertDialogListener listener) {
        this.alertDialogListener = listener;
    }

    private List<ProgressData> progress;

    Context context;

    // Adapter constructor
    public ProgressAdapter(List<ProgressData> progress, Context context) {
        this.progress = progress;
        this.context = context;
    }

    // Get context and inflate layout
    @NonNull
    @Override
    public ProgressAdapter.ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_progress_entry, parent, false);
        ProgressAdapter.ProgressViewHolder viewHolder = new ProgressAdapter.ProgressViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProgressAdapter.ProgressViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // Set widget content
        holder.title.setText(progress.get(position).getExercise().getName());
        holder.reps.setText(progress.get(position).getReps().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = dateFormat.format(progress.get(position).getDate().getTime());
        holder.date.setText(formattedDate);
        holder.thumbnail.setImageResource(progress.get(position).getExercise().getImage1());
        holder.note.setText(progress.get(position).getNote());
        if (progress.get(position).getBodyweight()) {
            holder.lbkg.setVisibility(View.GONE);
            holder.weight.setVisibility(View.GONE);
            holder.x.setVisibility(View.GONE);
            if (progress.get(position).getRepstosec()) holder.repsOrSec.setText("Seconds");
            else holder.repsOrSec.setText("Reps");
        } else {
            holder.weight.setText(progress.get(position).getWeight().toString());
            if (progress.get(position).getLbtokg()) holder.lbkg.setText("lbs");
            else holder.lbkg.setText("kg");
            if (progress.get(position).getRepstosec()) holder.repsOrSec.setText("Seconds");
            else holder.repsOrSec.setText("Reps");
        }

        // Handle progress entry removing
        holder.removeEntry.setOnClickListener(v -> {
            if (alertDialogListener != null) {
                alertDialogListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("progress entries count", progress.size() + "");
        return progress.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private ProgressAdapter.OnEntryClickListener mOnEntryClickListener;


public interface OnEntryClickListener {
    void onEntryClick(View view, int position);
}

    public void setOnEntryClickListener(ProgressAdapter.OnEntryClickListener onEntryClickListener) {
        mOnEntryClickListener = onEntryClickListener;
    }
}