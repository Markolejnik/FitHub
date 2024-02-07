package sk.spse.olejnik.fithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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

        // Changing color of top status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));
        }
    }


    public void forgotPassword(View view) {

        // Get email to reset
        String email = editEmail.getText().toString();

        if (email.length() == 0) Toast.makeText(this, "Enter the email to the forgotten password", Toast.LENGTH_SHORT).show();
        else {
            // Reset password through firebase
            auth.sendPasswordResetEmail(email).addOnCompleteListener(LoginActivity.this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Check your email to reset the password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Password reset failed, try again later.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(this, e ->
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show()
            );
        }
    }

    public void signIn(View view) {

        // Get user input
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        // Validate user inputs
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

        // Perform sign-in
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (task.isSuccessful()) {
                if (Objects.requireNonNull(firebaseUser).isEmailVerified()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(LoginActivity.this, "Sign In successful!", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseUser.sendEmailVerification();
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(LoginActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Sign In failed, try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}