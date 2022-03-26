package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    ProgressBar loading;
    AppCompatButton login;
    RadioGroup radioGroup;
    FirebaseAuth firebaseAuth;
    CheckBox showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkUser();
            }
        });
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });
    }

    public void initialization(){
        username = findViewById(R.id.user_login);
        password = findViewById(R.id.pass_login);
        loading = findViewById(R.id.progress_login);
        login = findViewById(R.id.btu_login);
        radioGroup = findViewById(R.id.radio_group_login);
        showPass = findViewById(R.id.show_pass);
        firebaseAuth= FirebaseAuth.getInstance();
    }

    public void checkUser(){
        String email= username.getText().toString();
        String pass= password.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            if(radioGroup.getCheckedRadioButtonId() != -1) {
                int selectedID = radioGroup.getCheckedRadioButtonId();
                if(selectedID == R.id.radio_contact_login){
                    checkAuth("Contacts");
                }else if(selectedID == R.id.radio_trainee_login){
                    checkAuth("Trainees");
                }else if(selectedID == R.id.radio_trainer_login) {
                    checkAuth("Trainers");
                }else if (selectedID == R.id.radio_employee_login){
                    checkAuth("Employees");
                }else if (selectedID == R.id.radio_admin_login){
                    checkAuth("Admins");
                }

            }else{
                Toast.makeText(this, "برجاء إختيار نوع المستخدم", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            loading.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "برجاء إدخال كافة العناصر", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkAuth(String table) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(table).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int state = 0;
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.v("taggg", ""+document.getData().get("email"));
                        if(username.getText().toString().equals(document.getData().get("email"))){

                            loginToSystem(table);
                            state=1;
                            break;
                        }
                    }
                    if(state==0) {
                        Toast.makeText(LoginActivity.this, "المستخدم غير مسجل بالنظام", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("taggg", "Error getting documents.", task.getException());
                }
            }
        });


    }

    public void loginToSystem(String user) {
        String email= username.getText().toString();
        String pass= password.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
        {
            loading.setVisibility(View.VISIBLE);
               firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loading.setVisibility(View.INVISIBLE);
                            howUser(user);
                        } else {
                            loading.setVisibility(View.INVISIBLE);
                            String error = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(LoginActivity.this, "error :" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
        else {
            loading.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "برجاء إدخال كافة العناصر", Toast.LENGTH_SHORT).show();
        }
    }

    public void howUser(String user){
        switch (user){
            case "Contacts":
                startActivity(new Intent(LoginActivity.this,ContactsActivity.class));
                break;
            case "Trainers":
                startActivity(new Intent(LoginActivity.this,TrainerActivity.class));
                break;
            case "Trainees":
                startActivity(new Intent(LoginActivity.this,TraineeActivity.class));
                break;
            case "Employees":
                startActivity(new Intent(LoginActivity.this,EmployeeActivity.class));
                break;
            case "Admins":
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                break;
        }
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}