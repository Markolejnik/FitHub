package sk.spse.olejnik.fithub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    BottomNavigationView bottomNavigationView;

    Button signOut, delete;

    TextView nickname, joined;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signOut = view.findViewById(R.id.signOut);
        delete = view.findViewById(R.id.deleteUser);
        nickname = view.findViewById(R.id.nicknameText);
        joined = view.findViewById(R.id.joinedText);

        checkSignedIn();

        delete.setOnClickListener(v1 -> deleteUser(view));
    }

    public void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            signOut.setText("Sign In");
            nickname.setText("Not logged in");
            signOut.setOnClickListener(v1 -> {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            });
        } else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            final String current = user.getEmail();
            db.collection("users")
                    .whereEqualTo("email", current)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    nickname.setText((CharSequence) document.get("nickname"));
                                    joined.setText("Joined: " + (CharSequence) document.get("joined"));
                                }
                            }
                            signOut.setText("Sign Out");
            signOut.setOnClickListener(v1 -> {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            });

            Log.i("here is the user", auth.getCurrentUser().getEmail());

        }
    });}
    }

    public void deleteUser(View view){
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_LONG).show();
                                checkSignedIn();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Account deletion failed", Toast.LENGTH_SHORT).show();
        }
    }
}