package com.example.courses.UI.Fragments.Admin;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Models.Employee;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Employee.TraineeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddEmployeeFragment extends Fragment {

    EditText email , pass , name , position , number;
    TextView age;
    ProgressBar loading;
    AppCompatButton addBtn;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Employee employee;
    FirebaseAuth firebaseAuth;
    DatePickerDialog.OnDateSetListener mListener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        addBtn.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_employee, container, false);
    }
    public void initialization(){
        email = getActivity().findViewById(R.id.ce_email_man);
        pass = getActivity().findViewById(R.id.ce_pass_man);
        name = getActivity().findViewById(R.id.ce_name_man);
        age = getActivity().findViewById(R.id.ce_age_man);
        position = getActivity().findViewById(R.id.ce_position_man);
        number = getActivity().findViewById(R.id.ce_number_man);
        addBtn = getActivity().findViewById(R.id.ce_add_btn);
        loading = getActivity().findViewById(R.id.ce_progress_man);
        employee = new Employee();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void createProfile(){
        String u_name =name.getText().toString();
        String u_number = number.getText().toString();
        String u_position=position.getText().toString();
        String u_age=age.getText().toString();
        String u_email=email.getText().toString();
        String u_password = pass.getText().toString();

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_position) && !TextUtils.isEmpty(u_age) && !TextUtils.isEmpty(u_email) && !TextUtils.isEmpty(u_password) && !TextUtils.isEmpty(u_number)) {
            loading.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(u_email,u_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        putEmployeeData(firebaseUser.getUid());
                    } else {
                        loading.setVisibility(View.INVISIBLE);
                        String error = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(getContext(), "error :" + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else {
            Toast.makeText(getContext(), "برجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }

    public void putEmployeeData(String userId){
        String u_name =name.getText().toString();
        String u_number = number.getText().toString();
        String u_position=position.getText().toString();
        String u_age=age.getText().toString();
        String u_email=email.getText().toString();

        databaseReference=database.getReference("Employees");
        documentReference=db.collection("Employees").document(userId);

        employee.setEmail(u_email);
        employee.setAge(u_age);
        employee.setName(u_name);
        employee.setNumber(Integer.parseInt(u_number));
        employee.setPosition(u_position);
        employee.setUid(userId);


        Map<String ,String> profile=new HashMap<>();
        profile.put("name",u_name);
        profile.put("age",u_age);
        profile.put("position",u_position);
        profile.put("email",u_email);
        profile.put("number",u_number);
        profile.put("uid",userId);

        documentReference.set(profile);
        databaseReference.child(userId)
                .setValue(employee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "تم إضافة الموظف", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, new EmployeeFragment()).commit();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}