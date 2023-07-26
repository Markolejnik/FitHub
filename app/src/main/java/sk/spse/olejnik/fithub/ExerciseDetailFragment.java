package sk.spse.olejnik.fithub;

import android.content.ContentResolver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailFragment extends Fragment {

    String title, link, type, muscleGroupContent;
    List<String> muscleGroups;
    int image1, image2;
    TextView titleText, muscleGroupsText, typeText, linkText;
    ImageView imageView1, imageView2;



    public ExerciseDetailFragment() {}

    public static ExerciseDetailFragment newInstance(String title, List<String> muscleGroups, String link, String type, int image1, int image2) {

        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArrayList("groups", (ArrayList<String>) muscleGroups);
        args.putString("link", link);
        args.putString("type", type);
        args.putInt("img1", image1);
        args.putInt("img2", image2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title", title);
            muscleGroups = args.getStringArrayList("groups");
            muscleGroupContent = "";
            for (String muscle : muscleGroups) {
                muscleGroupContent = muscleGroupContent + muscle + "\n";
            }

            image1 = args.getInt("img1");
            image2 = args.getInt("img2");
            link = args.getString("link");
            type = args.getString("type");
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleText = view.findViewById(R.id.exerciseTitle);
        muscleGroupsText = view.findViewById(R.id.muscleGroups);
        typeText = view.findViewById(R.id.type);
        linkText = view.findViewById(R.id.link);
        imageView1 = view.findViewById(R.id.image1);
        imageView2 = view.findViewById(R.id.image2);






        titleText.setText(title);
        muscleGroupsText.setText(muscleGroupContent);
        typeText.setText(type);
        linkText.setText(link);
        imageView1.setImageResource(image1);
        imageView2.setImageResource(image2);



    }
}