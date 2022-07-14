package com.project.smmobilechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.smmobilechat.Model.Chat;


public class EditActivity extends AppCompatActivity{

    FirebaseUser fuser;
    private DatabaseReference reference;

    private Button btSubmit;
    private EditText etMessage;

    private Chat chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etMessage =  findViewById(R.id.et_message);
        btSubmit =  findViewById(R.id.bt_submit);

        reference = FirebaseDatabase.getInstance("https://chatting-app-d29ad-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Chats");

        chat = (Chat) getIntent().getSerializableExtra("data");
        if (chat != null) {
            etMessage.setText(chat.getMessage());
        }
    }

    public void save(View v){
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat.setMessage(etMessage.getText().toString());

                MessageActivity msg = new MessageActivity();
                msg.updateMessages(chat);

                Toast.makeText(EditActivity.this, "Message edited!", Toast.LENGTH_SHORT).show();
                EditActivity.this.finish();
                startActivity(new Intent(EditActivity.this, MessageActivity.class));


            }
        });


    }
}