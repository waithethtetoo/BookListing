package com.wtho.booklisting;

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

public class HomeActivity extends AppCompatActivity {
   private EditText edtEmail, edtPassword;
   private Button btnSignIn, btnSignUp, btnRestPassword;
   private ProgressBar progressBar;
   private FirebaseAuth auth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home);

      // Get Firebase auth instance
      auth = FirebaseAuth.getInstance();

      btnSignIn = findViewById(R.id.sign_in_button);
      btnSignUp = findViewById(R.id.sign_up_button);
      btnRestPassword = findViewById(R.id.btn_reset_password);
      edtEmail = findViewById(R.id.email);
      edtPassword = findViewById(R.id.password);
      progressBar = findViewById(R.id.progressBar);

      btnSignIn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
         }
      });

      btnRestPassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this, ResetPasswordActivity.class));
         }
      });

      btnSignUp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
               Toast.makeText(HomeActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
               return;
            }
            if (TextUtils.isEmpty(password)) {
               Toast.makeText(HomeActivity.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
               return;
            }
            if (password.length() < 6) {
               Toast.makeText(HomeActivity.this, getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
               return;
            }
            progressBar.setVisibility(View.VISIBLE);

            // Create User
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(HomeActivity.this,
                            new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                  Toast.makeText(HomeActivity.this, getString(R.string.create_user) +
                                          task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                  progressBar.setVisibility(View.GONE);
                                  if (!task.isSuccessful()) {
                                     Toast.makeText(HomeActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                  } else {
                                     startActivity(new Intent(HomeActivity.this, BookShowActivity.class));
                                     finish();
                                  }
                               }
                            });
         }
      });
   }

   @Override
   protected void onResume() {
      super.onResume();
      progressBar.setVisibility(View.GONE);
   }
}
