package com.project.smmobilechat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.smmobilechat.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Locale;


public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password,confPass;
    Button btn_register;

    FirebaseAuth auth;
    DatabaseReference reference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confPass = findViewById(R.id.confPass);
        btn_register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString().trim();
                String txt_email = email.getText().toString().trim();
                String txt_password = password.getText().toString().trim();
                String confirm = confPass.getText().toString().trim();

                if (txt_password.equals(confirm)){
                    if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                        Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    } else if (txt_password.length() < 6) {
                        Toast.makeText(RegisterActivity.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    assert firebaseUser != null;
                                    String userid = firebaseUser.getUid();
                                    String imageURL = "default";
                                    String search = txt_username.toLowerCase();

                                    User user = new User(userid, txt_username, imageURL, search);


                                    FirebaseDatabase.getInstance("https://chatting-app-d29ad-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                            .getReference("Users")
                                            .child(userid)
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });


                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
            }else{
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void toLogin(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}