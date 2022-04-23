package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity {
    EditText email;
    AppCompatButton resetPassword;
    ProgressBar loading;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initialization();
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassword();
            }
        });

    }

    private void forgetPassword() {
        String email_r = email.getText().toString().trim();
        if(email_r.isEmpty()){
            email.setError("يجب إدخال البريد الإلكتروني");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_r).matches()){
            email.setError("تأكد من صحة البريد الإلكتروني");
            email.requestFocus();
            return;
        }
        loading.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email_r).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "تحقق من بريدك الوارد لإستعادة كلمة المرور", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(ForgetPassword.this, ""+task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void initialization(){
        email = findViewById(R.id.email_reset);
        resetPassword = findViewById(R.id.btu_reset);
        loading = findViewById(R.id.progress_reset);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}