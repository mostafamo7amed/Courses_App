package com.example.courses.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.courses.R;

public class CreateTraineeAccountActivity extends AppCompatActivity {

    AppCompatButton create_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trainee_account);

        create_account = findViewById(R.id.ctrainee_save);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateTraineeAccountActivity.this,TraineeActivity.class));
            }
        });
    }
}