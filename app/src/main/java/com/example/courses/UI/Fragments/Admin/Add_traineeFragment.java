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
import com.example.courses.SharedPref;
import com.example.courses.UI.Activities.CreateTraineeAccountActivity;
import com.example.courses.UI.Fragments.Admin.TraineeFragment;
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

public class Add_traineeFragment extends Fragment {

    EditText name  ,  email , password;
    Spinner level;
    TextView age ;
    RadioGroup gender;
    ProgressBar loading;
    AppCompatButton create_account;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Trainee trainee;
    FirebaseAuth firebaseAuth;
    DatePickerDialog.OnDateSetListener mListener;
    String eduLevel;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        return inflater.inflate(R.layout.fragment_add_trainee, container, false);
    }

    public void initialization(){
        name = getActivity().findViewById(R.id.ct_name_man);
        level = getActivity().findViewById(R.id.ct_level_man);
        age = getActivity().findViewById(R.id.ct_age_man);
        loading = getActivity().findViewById(R.id.ct_progress_man);
        email = getActivity().findViewById(R.id.ct_email_man);
        gender = getActivity().findViewById(R.id.ct_gender_man);
        password = getActivity().findViewById(R.id.ct_pass_man);
        create_account = getActivity().findViewById(R.id.ct_addTrainee_man);

        firebaseAuth = FirebaseAuth.getInstance();

        trainee = new Trainee();

    }
    public void createProfile(){
        String u_name =name.getText().toString();
        String u_level=eduLevel;
        String u_age=age.getText().toString();
        String u_email=email.getText().toString();
        String u_password = password.getText().toString();

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_level) && !TextUtils.isEmpty(u_age) && !TextUtils.isEmpty(u_email) && !TextUtils.isEmpty(u_password) && gender.getCheckedRadioButtonId() != -1) {
            loading.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(u_email,u_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        putTraineeData(firebaseUser.getUid());
                    } else {
                        loading.setVisibility(View.INVISIBLE);
                        String error = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(getContext(), "error :" + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else {
            Toast.makeText(getContext(), "الرجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }

    public void putTraineeData(String userId){
        String u_name =name.getText().toString();
        String u_level=eduLevel;
        String u_age=age.getText().toString();
        String u_email=email.getText().toString();
        String u_gander;

        int selectedID = gender.getCheckedRadioButtonId();
        if (selectedID == R.id.male) {
            u_gander = "ذكر";
        }else {
            u_gander = "أنثى";
        }

        databaseReference=database.getReference("Trainees");
        documentReference=db.collection("Trainees").document(userId);
        trainee.setAge(u_age);
        trainee.setEmail(u_email);
        trainee.setName(u_name);
        trainee.setEducationLevel(u_level);
        trainee.setUID(userId);
        trainee.setGender(u_gander);
        trainee.setType("Trainees");

        Map<String ,String> profile=new HashMap<>();
        profile.put("name",u_name);
        profile.put("age",u_age);
        profile.put("level",u_level);
        profile.put("email",u_email);
        profile.put("gender",u_gander);
        profile.put("uid",userId);
        profile.put("type","Trainees");

        documentReference.set(profile);
        databaseReference.child(userId)
                .setValue(trainee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        loginToSystem();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginToSystem(){
        SharedPref sharedPref = new SharedPref(getContext());
        String email = sharedPref.loadEmail();
        String pass = sharedPref.loadPassword();
        if(!email.isEmpty() && !pass.isEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "تم إنشاء الحساب", Toast.LENGTH_SHORT).show();
                    Fragment selected;
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",getArguments().getInt("frame"));
                    selected = new TraineeFragment();
                    selected.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"),selected).commit();
                }
            });
        }

    }

    public void educationLevel(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.Education_level
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