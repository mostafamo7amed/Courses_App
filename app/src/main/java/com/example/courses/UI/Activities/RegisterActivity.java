package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
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

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText username,password,confirm_password;
    CheckBox show_password;
    ProgressBar loading;
    RadioGroup radioGroup;
    AppCompatButton register;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationMethod();
            }
        });

        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });
    }
    public void initialization(){
        username = findViewById(R.id.username_register);
        password = findViewById(R.id.pass_register);
        confirm_password = findViewById(R.id.pass_register_confirm);
        show_password = findViewById(R.id.show_pass_register);
        loading = findViewById(R.id.progress_register);
        radioGroup = findViewById(R.id.radio_group_register);
        register = findViewById(R.id.btu_register);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void checkUser(){
        int selectedID = radioGroup.getCheckedRadioButtonId();
        Intent intent ;
        if(selectedID == R.id.contact_radio){
            intent = new Intent(RegisterActivity.this, CreateTrainingProviderAccountActivity.class);
            intent.putExtra("email",username.getText().toString());
            startActivity(intent);
        }else if(selectedID == R.id.trainee_radio) {
            intent = new Intent(RegisterActivity.this,CreateTraineeAccountActivity.class);
            intent.putExtra("email",username.getText().toString());
            startActivity(intent);
        }
        finish();
    }
    public void registrationMethod(){
        String email= username.getText().toString();
        String pass= password.getText().toString();
        String confirm_pass=confirm_password.getText().toString();
        if( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)) {
            if(pass.equals(confirm_pass)) {
                if(radioGroup.getCheckedRadioButtonId() != -1) {
                    loading.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkUser();
                                loading.setVisibility(View.INVISIBLE);
                                finish();
                            } else {
                                loading.setVisibility(View.INVISIBLE);
                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(RegisterActivity.this, "error :" + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, "برجاء إختيار نوع المستخدم(جهة - متدرب)", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                loading.setVisibility(View.INVISIBLE);
                Toast.makeText(RegisterActivity.this,"تأكيد كلمة المرور خاطئ",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RegisterActivity.this,"برجاء إدخال كافة العناصر ",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

}