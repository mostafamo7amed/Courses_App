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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

    EditText name ;
    Spinner level ;
    TextView age;
    RadioGroup gender;
    ProgressBar loading;
    AppCompatButton create_account;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Trainee trainee;
    String currentUserId;
    DatePickerDialog.OnDateSetListener mListener;
    String eduLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trainee_account);

        initialization();

        educationLevel();
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
        gender = findViewById(R.id.gender_ca_trainee);


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
        String u_level=eduLevel;
        String u_age=age.getText().toString();
        String u_email=getIntent().getStringExtra("email");
        String u_gander;

        int selectedID = gender.getCheckedRadioButtonId();
        if (selectedID == R.id.male) {
            u_gander = "ذكر";
        }else {
            u_gander = "أنثى";
        }

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_level) && !TextUtils.isEmpty(u_age) && !TextUtils.isEmpty(u_email) && gender.getCheckedRadioButtonId() != -1) {
            loading.setVisibility(View.VISIBLE);
            trainee.setAge(u_age);
            trainee.setEmail(u_email);
            trainee.setName(u_name);
            trainee.setEducationLevel(u_level);
            trainee.setUID(currentUserId);
            trainee.setGender(u_gander);
            trainee.setType("Trainees");

            Map<String ,String> profile=new HashMap<>();
            profile.put("name",u_name);
            profile.put("age",u_age);
            profile.put("level",u_level);
            profile.put("gender",u_gander);
            profile.put("email",u_email);
            profile.put("uid",currentUserId);
            profile.put("type","Trainees");

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
            Toast.makeText(this, "الرجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }
    public void educationLevel(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateTraineeAccountActivity.this,R.array.Education_level
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level.setAdapter(adapter);
        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eduLevel = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}