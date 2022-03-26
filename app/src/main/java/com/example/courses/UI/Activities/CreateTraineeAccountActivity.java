package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Models.Trainee;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateTraineeAccountActivity extends AppCompatActivity {

    EditText name , level ;
    TextView age;
    ProgressBar loading;
    AppCompatButton create_account;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Trainee trainee;
    String currentUserId;
    DatePickerDialog.OnDateSetListener mListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trainee_account);

        initialization();

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
            }
        });



        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTraineeAccountActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1+1;
                String date = i2+"/"+i1+"/"+i;
                age.setText(date);
            }
        };
    }

    public void initialization(){
        name = findViewById(R.id.name_ca_trainee);
        level = findViewById(R.id.level_ca_trainee);
        age = findViewById(R.id.age_ca_trainee);
        loading = findViewById(R.id.progress_ca_trainee);
        create_account = findViewById(R.id.ctrainee_save);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserId=user.getUid();
        }
        databaseReference=database.getReference("Trainees");
        documentReference=db.collection("Trainees").document(currentUserId);
        trainee = new Trainee();


    }

    public void createProfile(){
        String u_name =name.getText().toString();
        String u_level=level.getText().toString();
        String u_age=age.getText().toString();
        String u_email=getIntent().getStringExtra("email");

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_level) && !TextUtils.isEmpty(u_age) && !TextUtils.isEmpty(u_email)) {
            loading.setVisibility(View.VISIBLE);
            trainee.setAge(u_age);
            trainee.setEmail(u_email);
            trainee.setName(u_name);
            trainee.setEducationLevel(u_level);
            trainee.setUID(currentUserId);

            Map<String ,String> profile=new HashMap<>();
            profile.put("name",u_name);
            profile.put("age",u_age);
            profile.put("level",u_level);
            profile.put("email",u_email);
            profile.put("uid",currentUserId);

            documentReference.set(profile);
            databaseReference.child(currentUserId)
                    .setValue(trainee)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(CreateTraineeAccountActivity.this, "تم إنشاء الحساب", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateTraineeAccountActivity.this,TraineeActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateTraineeAccountActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }else {
            Toast.makeText(this, "برجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }
}