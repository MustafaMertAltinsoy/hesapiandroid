package com.untygames.hesapimuhasebe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActicity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }



    public void girisYapBtn(View view) {
        String email = ((EditText)findViewById(R.id.et_Email)).getText().toString();
        String password = ((EditText)findViewById(R.id.et_pass)).getText().toString();

        email = "a@a.com";
        password="123456";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            MainActivity.user = user;
                            startActivity(new Intent(LoginActicity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActicity.this, "Giriş yapılamadı.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void SifreniMiUnuttun(View view) {

    }

    public void bilgiBtn(View view) {

    }
}