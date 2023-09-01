package sk.spse.olejnik.fithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;


    TextView signIn;
    EditText editPassword, editEmail, editNickname;
    Button signUp;

    public String nickname;

    int MIN_PASS_LEN = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPass);
        editNickname = findViewById(R.id.editTextNickname);
        signUp = findViewById(R.id.signUpButton);
        signIn = findViewById(R.id.goSignIn);
    }
    public void userRegistration(View view) {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userEmail = editEmail.getText().toString();
        String userPassword = editPassword.getText().toString();

        if (userEmail.isEmpty()) {
            editEmail.setError("Please enter your email");
            editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            editPassword.setError("Please enter a password");
            editPassword.requestFocus();
            return;
        }
        if (userPassword.length() < MIN_PASS_LEN) {
            editPassword.setError("Password has to be at least 8 characters long");
            editPassword.requestFocus();
            return;
        }


        auth.createUserWithEmailAndPassword(editEmail.getText().toString(),
                editPassword.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                Toast.makeText(RegisterActivity.this, "Sign Up Successful!", Toast.LENGTH_LONG).show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userUid = user.getUid();
                CollectionReference usersCollection = db.collection("users");
                nickname = editNickname.getText().toString();
                LocalDate currentDate = LocalDate.now();
                List<String> workouts = new ArrayList<>();
                List<String> drafts = new ArrayList<>();
                Map<String, Object> newuser = new HashMap<>();
                newuser.put("nickname", nickname);
                newuser.put("email", userEmail);
                newuser.put("password", userPassword);
                newuser.put("joined", currentDate.toString());
                newuser.put("workouts", workouts);
                newuser.put("drafts", drafts);
                db.collection("users")
                        .add(newuser)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("newUser", newuser.get(1) + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("newUser", "Error adding document", e);
                            }
                        });
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nickname).build();


                user.updateProfile(profileUpdates);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Sign Up failed, try again later.", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }

    public void signIn(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

}