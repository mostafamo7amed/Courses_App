package com.example.courses.UI.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.courses.R;

public class LoginActivity extends AppCompatActivity {
    Button login;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        radioGroup = findViewById(R.id.radio_group_login);
        login = findViewById(R.id.btu_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkUser();
            }
        });
    }

    public void checkUser(){
        int selectedID = radioGroup.getCheckedRadioButtonId();
        if(selectedID == R.id.radio_contact_login){
            Intent intent= new Intent(LoginActivity.this, ContactsActivity.class);
            startActivity(intent);
        }else if(selectedID == R.id.radio_trainee_login){
            startActivity(new Intent(LoginActivity.this,TraineeActivity.class));
        }else if(selectedID == R.id.radio_trainer_login) {
            startActivity(new Intent(LoginActivity.this,TrainerActivity.class));
        }else if (selectedID == R.id.radio_admin_login){
            startActivity(new Intent(LoginActivity.this,AdminActivity.class));
        }
    }
}