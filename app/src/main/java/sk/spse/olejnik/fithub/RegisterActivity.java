package sk.spse.olejnik.fithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth;

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
        FirebaseAuth auth = FirebaseAuth.getInstance();
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
                 nickname = editNickname.getText().toString();
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