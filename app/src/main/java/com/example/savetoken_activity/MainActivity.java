package com.example.savetoken_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name,password;
    Button signin;
    TextView signup;
    FirebaseAuth auth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.userName_id);
        password=findViewById(R.id.password_id);
        signin=findViewById(R.id.login_id);
        signup=findViewById(R.id.signup_id);

        auth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("TokenUser");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username=name.getText().toString();
                String Password=password.getText().toString();

                auth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            ProfileActivity();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                          signupMethode(Username,Password);
                    }



                });
            }
        });

    }
    private void signupMethode(String username, String password) {
        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                    ProfileActivity();
                }
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ProfileActivity() {
        Intent intent=new Intent(MainActivity.this,SaveToken_Activity.class);
        startActivity(intent);
    }


}