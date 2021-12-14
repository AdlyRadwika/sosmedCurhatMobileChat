package com.project.smmobilechat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.smmobilechat.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;



public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username,email,password;
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
        btn_register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if (txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }else{
                    auth.createUserWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                assert firebaseUser != null;
                                String userid = firebaseUser.getUid();
                                String imageURL = "default";

                                User user = new User(userid,txt_username, imageURL);

//                                HashMap<String, String> hashMap = new HashMap<>();
//                                hashMap.put("id", userid);
//                                hashMap.put("username", txt_username);
//                                hashMap.put("imageURL", "default");

                                FirebaseDatabase.getInstance("https://sosmedcurhatmobilechat-166c7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                        .getReference("Users")
                                        .child(userid)
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task){

                                                if (task.isSuccessful()){
                                                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                }else{
                                                    Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }
}