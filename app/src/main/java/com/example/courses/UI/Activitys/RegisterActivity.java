package com.example.courses.UI.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.courses.R;

public class RegisterActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        radioGroup = findViewById(R.id.radio_group_register);
        register = findViewById(R.id.btu_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser();
            }
        });
    }
    public void checkUser(){
        int selectedID = radioGroup.getCheckedRadioButtonId();
        if(selectedID == R.id.contact_radio){
            Intent intent= new Intent(RegisterActivity.this, CreateContactAccountActivity.class);
            startActivity(intent);
        }else if(selectedID == R.id.trainee_radio) {

        }
    }
}