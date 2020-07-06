package com.wtho.booklisting.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wtho.booklisting.R;

public class UserProfileActivity extends AppCompatActivity {
   private EditText edtOldEmail, edtNewEmail, edtOldPassword, edtNewPassword;
   private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
           changeEmail, changePassword, sendEmail, remove, btnSignout;
   private FirebaseAuth auth;
   private FirebaseAuth.AuthStateListener listener;
   private ProgressBar progressBar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_user_profile);

      auth = FirebaseAuth.getInstance();
      // Get current user
      final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      listener = new FirebaseAuth.AuthStateListener() {

         @Override
         public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser == null) {
               startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
               finish();
            }
         }
      };

      edtOldEmail = findViewById(R.id.old_email);
      edtNewEmail = findViewById(R.id.new_email);
      edtOldPassword = findViewById(R.id.old_password);
      edtNewPassword = findViewById(R.id.new_password);

      btnChangeEmail = findViewById(R.id.change_email_button);
      btnChangePassword = findViewById(R.id.change_password_button);
      btnSendResetEmail = findViewById(R.id.sending_pass_reset_button);
      btnRemoveUser = findViewById(R.id.remove_user_button);
      changeEmail = findViewById(R.id.changeEmail);
      changePassword = findViewById(R.id.change_password_btn);
      sendEmail = findViewById(R.id.send);
      remove = findViewById(R.id.remove);
      btnSignout = findViewById(R.id.sign_out);
      progressBar = findViewById(R.id.progressBar);

      edtOldEmail.setVisibility(View.GONE);
      edtNewEmail.setVisibility(View.GONE);
      edtOldPassword.setVisibility(View.GONE);
      edtNewPassword.setVisibility(View.GONE);
      changeEmail.setVisibility(View.GONE);
      changePassword.setVisibility(View.GONE);
      sendEmail.setVisibility(View.GONE);
      remove.setVisibility(View.GONE);

      if (progressBar != null)
         progressBar.setVisibility(View.GONE);

      btnChangeEmail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            edtOldEmail.setVisibility(View.GONE);
            edtNewEmail.setVisibility(View.VISIBLE);
            edtOldPassword.setVisibility(View.GONE);
            edtNewPassword.setVisibility(View.GONE);
            changeEmail.setVisibility(View.VISIBLE);
            changePassword.setVisibility(View.GONE);
            sendEmail.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
         }
      });

      changeEmail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            if (user != null && !edtNewEmail.getText().toString().trim().equals("")) {
               user.updateEmail(edtNewEmail.getText().toString().trim())
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                Toast.makeText(UserProfileActivity.this, "Email address is updated. Please sign in with new Email Id", Toast.LENGTH_SHORT).show();
                                signOut();
                                progressBar.setVisibility(View.GONE);
                             } else {
                                Toast.makeText(UserProfileActivity.this, "Failed to update email!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                             }
                          }
                       });
            } else if (edtNewEmail.getText().toString().trim().equals("")) {
               edtNewEmail.setError("Enter New Email!");
               progressBar.setVisibility(View.GONE);
            }
         }
      });

      btnChangePassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            edtOldEmail.setVisibility(View.GONE);
            edtNewEmail.setVisibility(View.GONE);
            edtOldPassword.setVisibility(View.GONE);
            edtNewPassword.setVisibility(View.VISIBLE);
            changeEmail.setVisibility(View.GONE);
            changePassword.setVisibility(View.VISIBLE);
            sendEmail.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
         }
      });

      changePassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            if (user != null && !edtNewPassword.getText().toString().trim().equals("")) {
               if (edtNewPassword.getText().toString().trim().length() < 6) {
                  Toast.makeText(UserProfileActivity.this, "Password too short. Please enter minimum 6 characters", Toast.LENGTH_SHORT).show();
                  progressBar.setVisibility(View.GONE);
               } else {
                  user.updatePassword(edtNewPassword.getText().toString().trim())
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   Toast.makeText(UserProfileActivity.this, "Password is updated. Sign in with new password!", Toast.LENGTH_SHORT).show();
                                   signOut();
                                   progressBar.setVisibility(View.GONE);
                                } else {
                                   Toast.makeText(UserProfileActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                   progressBar.setVisibility(View.GONE);
                                }
                             }
                          });
               }
            } else if (edtNewPassword.getText().toString().trim().equals("")) {
               edtNewPassword.setError("Enter new password");
               progressBar.setVisibility(View.GONE);
            }
         }
      });

      btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            edtOldEmail.setVisibility(View.VISIBLE);
            edtNewEmail.setVisibility(View.GONE);
            edtOldPassword.setVisibility(View.GONE);
            edtNewPassword.setVisibility(View.GONE);
            changeEmail.setVisibility(View.GONE);
            changePassword.setVisibility(View.GONE);
            sendEmail.setVisibility(View.VISIBLE);
            remove.setVisibility(View.GONE);
         }
      });

      sendEmail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            if (!edtOldEmail.getText().toString().trim().equals("")) {
               auth.sendPasswordResetEmail(edtOldEmail.getText().toString().trim())
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                Toast.makeText(UserProfileActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                             } else {
                                Toast.makeText(UserProfileActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                             }
                          }
                       });
            } else {
               edtOldEmail.setError("Enter Old Email");
               progressBar.setVisibility(View.GONE);
            }
         }
      });

      btnRemoveUser.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            progressBar.setVisibility(View.VISIBLE);
            if (user != null) {
               user.delete()
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                Toast.makeText(UserProfileActivity.this, "Your profile is successfully deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserProfileActivity.this, HomeActivity.class));
                                finish();
                                progressBar.setVisibility(View.GONE);
                             } else {
                                Toast.makeText(UserProfileActivity.this, "Failed to delete your profile!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                             }
                          }
                       });
            }
         }
      });

      btnSignout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            signOut();
         }
      });
   }

   public void signOut() {
      auth.signOut();
   }

   @Override
   protected void onResume() {
      super.onResume();
      progressBar.setVisibility(View.GONE);
   }

   @Override
   protected void onStart() {
      super.onStart();
      auth.addAuthStateListener(listener);
   }

   @Override
   protected void onStop() {
      super.onStop();
      if (listener != null)
         auth.removeAuthStateListener(listener);
   }
}
