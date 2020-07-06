package com.wtho.booklisting.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wtho.booklisting.R;

public class LoginActivity extends AppCompatActivity {

   private EditText edtEmail, edtPassword;
   private Button btnLogin, btnResetPassword, btnSignup;
   private ProgressBar progressBar;
   private FirebaseAuth auth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      auth = FirebaseAuth.getInstance();
      if (auth.getCurrentUser() != null) {
         startActivity(new Intent(LoginActivity.this, BookShowActivity.class));
         finish();
      }

      setContentView(R.layout.activity_login);

      edtEmail = findViewById(R.id.email);
      edtPassword = findViewById(R.id.password);
      btnLogin = findViewById(R.id.btn_login);
      btnResetPassword = findViewById(R.id.btn_reset_password);
      btnSignup = findViewById(R.id.btn_signup);
      progressBar = findViewById(R.id.progressBar);

      auth = FirebaseAuth.getInstance();
      btnSignup.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
         }
      });
      btnResetPassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
         }
      });

      btnLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            String email = edtEmail.getText().toString();
            final String password = edtPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
               Toast.makeText(LoginActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
               return;
            }
            if (TextUtils.isEmpty(password)) {
               Toast.makeText(LoginActivity.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
               return;
            }
            progressBar.setVisibility(View.VISIBLE);

            // Authenticate user
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                  progressBar.setVisibility(View.GONE);
                                  if (!task.isSuccessful()) {
                                     if (password.length() < 6) {
                                        edtPassword.setError(getString(R.string.minimum_password));
                                     } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                     }
                                  } else {
                                     Intent intent = new Intent(LoginActivity.this, BookShowActivity.class);
                                     startActivity(intent);
                                     finish();
                                  }
                               }
                            });
         }
      });
   }
}
