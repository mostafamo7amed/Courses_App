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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Models.Trainee;
import com.example.courses.R;
import com.example.courses.UI.Activities.CreateTraineeAccountActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditTraineeFragment extends Fragment {

    EditText name ;
    Spinner level ;
    TextView age;
    ProgressBar loading;
    AppCompatButton save;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Trainee trainee;
    String currentUserId,U_EMail;
    DatePickerDialog.OnDateSetListener mListener;
    String eduLevel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        getDate();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });
        educationLevel();
        ageUser();
    }

    public void initialization(){
        name = getActivity().findViewById(R.id.edit_name_trainee);
        level = getActivity().findViewById(R.id.edit_level_trainee);
        age = getActivity().findViewById(R.id.edit_age_trainee);
        loading = getActivity().findViewById(R.id.edit_progress_trainee);
        save = getActivity().findViewById(R.id.edit_save_trainee);

        currentUserId = getArguments().getString("uid");
        trainee = new Trainee();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_trainee, container, false);
    }
    public void editProfile(){
        String u_name =name.getText().toString();
        String u_level=eduLevel;
        String u_age=age.getText().toString();
        String u_email=U_EMail;

        databaseReference=database.getReference("Trainees");
        documentReference=db.collection("Trainees").document(currentUserId);

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_level) && !TextUtils.isEmpty(u_age) && !TextUtils.isEmpty(u_email)) {
            loading.setVisibility(View.VISIBLE);
            trainee.setAge(u_age);
            trainee.setEmail(u_email);
            trainee.setName(u_name);
            trainee.setEducationLevel(u_level);
            trainee.setUID(currentUserId);
            trainee.setType("Trainees");

            Map<String ,String> profile=new HashMap<>();
            profile.put("name",u_name);
            profile.put("age",u_age);
            profile.put("level",u_level);
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
                            Toast.makeText(getContext(), "تم تعديل الحساب", Toast.LENGTH_SHORT).show();
                            Fragment fragment;
                            Bundle bundle = new Bundle();
                            bundle.putInt("frame", getArguments().getInt("frame"));
                            fragment = new TraineeFragment();
                            fragment.setArguments(bundle);
                            getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), fragment).commit();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }else {
            Toast.makeText(getContext(), "برجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }

    public void getDate(){
        String currentUserId= getArguments().getString("uid");
        if (currentUserId != null) {
            documentReference=db.collection("Trainees").document(currentUserId);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    try {
                        if (task.getResult().exists()) {
                            String u_name = task.getResult().getString("name");
                            String u_level = task.getResult().getString("level");
                            String u_age = task.getResult().getString("age");
                            String u_email = task.getResult().getString("email");

                            name.setText(u_name);
                            age.setText(u_age);
                            U_EMail =u_email;

                        }
                    } catch (NullPointerException nullPointerException) {
                        Toast.makeText(getActivity(), "" + nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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

    public void ageUser(){
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

}