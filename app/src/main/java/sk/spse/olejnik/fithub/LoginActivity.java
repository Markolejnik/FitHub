package sk.spse.olejnik.fithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;

    EditText editEmail, editPassword;
    Button signIn;

    int MIN_PASS_LEN = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPass);
        signIn = findViewById(R.id.signInButton);
        auth = FirebaseAuth.getInstance();
    }

    public void signIn(View view) {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        if (email.isEmpty()) {
            editEmail.setError("Please enter your email");
            editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Please enter a password");
            editPassword.requestFocus();
            return;
        }
        if (password.length() < MIN_PASS_LEN) {
            editPassword.setError("Password has to be at least 8 characters long");
            editPassword.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(editEmail.getText().toString(),
                editPassword.getText().toString()).addOnCompleteListener(LoginActivity.this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (Objects.requireNonNull(firebaseUser).isEmailVerified()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(LoginActivity.this, "Sign In successful!", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseUser.sendEmailVerification();
                    Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Sign In failed, try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void forgotPassword(View view) {
        String email = editEmail.getText().toString();
        if (email.length() == 0) Toast.makeText(this, "Enter the email to the forgotten password", Toast.LENGTH_SHORT).show();
        else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(LoginActivity.this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Check your email to reset the password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Password reset failed, try again later.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(this, e ->
                    Toast.makeText(
                            LoginActivity.this,
                            e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show()
            );
        }
    }
}