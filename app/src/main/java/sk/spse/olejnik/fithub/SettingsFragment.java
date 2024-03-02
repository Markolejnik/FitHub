package sk.spse.olejnik.fithub;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    Button signOut, delete, upload, verify;
    ImageView goBack, imageView;
    LinearLayout root;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseStorage storage;

    private SharedPreferences preferences, preferences2;

    static final Integer imageRequest = 1;

    public SettingsFragment() {}

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.constraintLayout);
        goBack = view.findViewById(R.id.goBack);
        signOut = view.findViewById(R.id.signOut);
        delete = view.findViewById(R.id.deleteUser);
        upload = view.findViewById(R.id.upload);
        verify = view.findViewById(R.id.verify);
        imageView = view.findViewById(R.id.imageView2);

        preferences = getActivity().getBaseContext().getSharedPreferences("database", Context.MODE_PRIVATE);
        preferences2 = getActivity().getBaseContext().getSharedPreferences("workouts", Context.MODE_PRIVATE);

        // Handle back button press
        goBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        // Handle delete user button press
        delete.setOnClickListener(v -> {
            deleteUser();
        });

        // Handle pfp uploading button
        upload.setOnClickListener(v -> {
            if (auth.getCurrentUser() != null) openGallery();
            else Toast.makeText(getContext(), "Sign in to upload a profile picture", Toast.LENGTH_SHORT).show();
        });

        // Handle email verification
        verify.setOnClickListener(v -> {
            if (auth.getCurrentUser() != null) {
                if (!auth.getCurrentUser().isEmailVerified()) {
                    auth.getCurrentUser().sendEmailVerification();
                    Toast.makeText(getContext(), "Verification email sent, check your email.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "You're already verified! Yay!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkSignedIn();

    }

    // Check if a user is signed in
    public void checkSignedIn() {

        if (auth.getCurrentUser() == null) {
            signOut.setText("Sign In");
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
                        signOut.setText("Sign Out");
                        signOut.setOnClickListener(v1 -> {
                            FirebaseAuth.getInstance().signOut();
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), RegisterActivity.class);
                            startActivity(intent);
                        });
                        Log.i("here is the user", auth.getCurrentUser().getEmail());
                    }
                }
            });
        }
    }

    // Delete user
    public void deleteUser(){
        try {
            user.delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        preferences = getActivity().getBaseContext().getSharedPreferences("database", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        preferences2 = getActivity().getBaseContext().getSharedPreferences("workouts", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = preferences2.edit();
                        editor2.clear();
                        editor2.apply();
                        Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_LONG).show();
                        checkSignedIn();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Account deletion failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imageRequest && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadProfileImage(imageUri);
        }
    }

    // Open users phone gallery
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, imageRequest);
    }

    // Upload pfp to database
    private void uploadProfileImage(Uri imageUri) {
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        StorageReference imageReference = storage.getReference().child("profilePictures").child(userId + ".jpg");

        imageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            imageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                assert user != null;
                final String current = user.getEmail();
                db.collection("users")
                .whereEqualTo("email", current)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId();
                                db.collection("users").document(documentId).update("pfpUrl", imageUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(), "Profile Picture Changed Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });
            });
        });
    }

}