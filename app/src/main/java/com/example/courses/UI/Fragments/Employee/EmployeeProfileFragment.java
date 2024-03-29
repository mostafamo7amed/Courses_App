package com.example.courses.UI.Fragments.Employee;

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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Models.Employee;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EmployeeProfileFragment extends Fragment {
    EditText name ,position,email , number , gender;
    TextView age;
    ImageButton editProfile;
    AppCompatButton editSave;
    ProgressBar loading;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Employee employee;
    String currentUserId;
    DatePickerDialog.OnDateSetListener mListener;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialization();
        getDate();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                age.setEnabled(true);
                position.setEnabled(true);
                editSave.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "يمكنك التعديل علي عناصر محدودة", Toast.LENGTH_SHORT).show();
            }
        });
        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
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
        return inflater.inflate(R.layout.fragment_employee_profile, container, false);
    }

    public void initialization(){
        name = getActivity().findViewById(R.id.emb_name);
        age = getActivity().findViewById(R.id.emb_age);
        position = getActivity().findViewById(R.id.emb_position);
        email = getActivity().findViewById(R.id.emb_email);
        editProfile = getActivity().findViewById(R.id.emb_edit_profile);
        editSave = getActivity().findViewById(R.id.emb_edit_save);
        gender = getActivity().findViewById(R.id.edit_gender_man);
        loading = getActivity().findViewById(R.id.emb_progress);
        number = getActivity().findViewById(R.id.emb_number);
        employee = new Employee();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserId=user.getUid();
        }

        databaseReference=database.getReference("Employees");
    }

    public void getDate(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId= null;
        if (user != null) {
            currentUserId = user.getUid();
        }
        if (currentUserId != null) {
            documentReference=db.collection("Employees").document(currentUserId);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    try {
                        if (task.getResult().exists()) {
                            String u_name = task.getResult().getString("name");
                            String u_position = task.getResult().getString("position");
                            String u_age = task.getResult().getString("age");
                            String u_email = task.getResult().getString("email");
                            String u_number = task.getResult().getString("number");
                            String u_gender = task.getResult().getString("gender");

                            name.setText(u_name);
                            age.setText(u_age);
                            email.setText(u_email);
                            gender.setText(u_gender);
                            position.setText(u_position);
                            number.setText(u_number);

                        }
                    } catch (NullPointerException nullPointerException) {
                        Toast.makeText(getActivity(), "" + nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    public void updateProfile(){
        String u_name =name.getText().toString();
        String u_position=position.getText().toString();
        String u_age=age.getText().toString();
        String u_email=email.getText().toString();
        String u_number = number.getText().toString();
        String u_gender = gender.getText().toString();

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_position) && !TextUtils.isEmpty(u_age) && !TextUtils.isEmpty(u_email) && !TextUtils.isEmpty(u_number)) {
            loading.setVisibility(View.VISIBLE);

            employee.setUid(currentUserId);
            employee.setName(u_name);
            employee.setEmail(u_email);
            employee.setAge(u_age);
            employee.setPosition(u_position);
            employee.setGender(u_gender);
            employee.setType("Employees");

            employee.setNumber(u_number);

            Map<String ,String> profile=new HashMap<>();
            profile.put("name",u_name);
            profile.put("age",u_age);
            profile.put("number",u_number);
            profile.put("gender",u_gender);
            profile.put("position",u_position);
            profile.put("email",u_email);
            profile.put("uid",currentUserId);
            profile.put("type","Employees");

            documentReference.set(profile);
            databaseReference.child(currentUserId)
                    .setValue(employee)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            loading.setVisibility(View.INVISIBLE);
                            name.setEnabled(false);
                            age.setEnabled(false);
                            position.setEnabled(false);
                            editSave.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "تم تعديل الملف الشخصي", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}