package com.example.courses.UI.Fragments.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Models.Employee;
import com.example.courses.R;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditEmployeeFragment extends Fragment {
    EditText   name , age ,position , number;
    ProgressBar loading;
    AppCompatButton addBtn;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Employee employee;
    String currentUserID , U_EMail;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();

        getDate();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEmployeeData(currentUserID);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_employee, container, false);
    }
    public void initialization(){
        name = getActivity().findViewById(R.id.edit_name_man);
        age = getActivity().findViewById(R.id.edit_age_man);
        position = getActivity().findViewById(R.id.edit_position_man);
        number = getActivity().findViewById(R.id.edit_number_man);
        addBtn = getActivity().findViewById(R.id.edit_add_btn);
        loading = getActivity().findViewById(R.id.edit_progress_man);
        employee = new Employee();
        currentUserID = getArguments().getString("uid").toString();
    }

    public void EditEmployeeData(String userId){
        String u_name =name.getText().toString();
        String u_number = number.getText().toString();
        String u_position=position.getText().toString();
        String u_age=age.getText().toString();

        databaseReference=database.getReference("Employees");
        documentReference=db.collection("Employees").document(userId);

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_position) && !TextUtils.isEmpty(u_age) && !TextUtils.isEmpty(U_EMail) && !TextUtils.isEmpty(u_number)) {

            loading.setVisibility(View.VISIBLE);
            employee.setEmail(U_EMail);
            employee.setAge(Integer.parseInt(u_age));
            employee.setName(u_name);
            employee.setNumber(Integer.parseInt(u_number));
            employee.setPosition(u_position);
            employee.setUid(userId);


            Map<String, String> profile = new HashMap<>();
            profile.put("name", u_name);
            profile.put("age", u_age);
            profile.put("position", u_position);
            profile.put("email", U_EMail);
            profile.put("number", u_number);
            profile.put("uid", userId);

            documentReference.set(profile);
            databaseReference.child(userId)
                    .setValue(employee)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            loading.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "تم تعديل بيانات الموظف", Toast.LENGTH_SHORT).show();
                            getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, new EmployeeFragment()).commit();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    public void getDate(){
        String currentUserId= currentUserID;
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

                            name.setText(u_name);
                            age.setText(u_age);
                            position.setText(u_position);
                            number.setText(u_number);
                            U_EMail =u_email;

                        }
                    } catch (NullPointerException nullPointerException) {
                        Toast.makeText(getActivity(), "" + nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}