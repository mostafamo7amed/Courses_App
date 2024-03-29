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
import android.widget.TextView;
import android.widget.Toast;

import com.example.courses.R;
import com.example.courses.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    TextView forgetPassword;
    ProgressBar loading;
    AppCompatButton login;
    FirebaseAuth firebaseAuth;
    CheckBox showPass;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              loginToSystem();
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
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPassword.class));
            }
        });
    }

    public void initialization(){
        username = findViewById(R.id.user_login);
        password = findViewById(R.id.pass_login);
        loading = findViewById(R.id.progress_login);
        login = findViewById(R.id.btu_login);
        showPass = findViewById(R.id.show_pass);
        firebaseAuth= FirebaseAuth.getInstance();
        forgetPassword = findViewById(R.id.forget_password);
        sharedPref = new SharedPref(LoginActivity.this);
    }



    public void loginToSystem() {
        String email= username.getText().toString();
        String pass= password.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
        {
            loading.setVisibility(View.VISIBLE);
            sharedPref.setEmail(email);
            sharedPref.setPassword(pass);
            signIn(email,pass);
        }
        else {
            loading.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "الرجاء إدخال كافة العناصر", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }


    public void signIn(String email, String pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uId = firebaseAuth.getCurrentUser().getUid();
                    System.out.println(uId);
                    if (uId != null) {
                        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                loading.setVisibility(View.INVISIBLE);
                                if (snapshot.child("Admins").child(uId).exists()) {
                                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                    finish();
                                }
                                else if (snapshot.child("Trainees").child(uId).exists()) {
                                    startActivity(new Intent(LoginActivity.this, TraineeActivity.class));
                                    finish();
                                } else if (snapshot.child("Training Provider").child(uId).exists()) {
                                    startActivity(new Intent(LoginActivity.this, ContactsActivity.class));
                                    finish();
                                } else if (snapshot.child("Trainers").child(uId).exists()) {
                                    startActivity(new Intent(LoginActivity.this, TrainerActivity.class));
                                    finish();
                                }else if (snapshot.child("Employees").child(uId).exists()){
                                    startActivity(new Intent(LoginActivity.this, EmployeeActivity.class));
                                    finish();
                                }else {
                                    loading.setVisibility(View.INVISIBLE);
                                    username.setText("");
                                    password.setText("");
                                    Toast.makeText(LoginActivity.this, "لا يوجد حساب قم بالتسجيل", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else {
                        loading.setVisibility(View.INVISIBLE);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, "تأكد من صحة البيانات", Toast.LENGTH_SHORT).show();
            }
        });
    }
}