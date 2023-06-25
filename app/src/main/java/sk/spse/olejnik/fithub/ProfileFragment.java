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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    BottomNavigationView bottomNavigationView;

    Button signOut;

    TextView nickname;

    FirebaseAuth auth;

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();


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
        nickname = view.findViewById(R.id.nicknameText);
        checkSignedIn();
    }

    public void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            signOut.setText("Sign In");
            signOut.setOnClickListener(v1 -> {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            });
        } else {
            signOut.setText("Sign Out");
            signOut.setOnClickListener(v1 -> {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            });

            Log.i("here is the user", auth.getCurrentUser().getEmail());
            nickname.setText(auth.getCurrentUser().getEmail());
        }
    }
}