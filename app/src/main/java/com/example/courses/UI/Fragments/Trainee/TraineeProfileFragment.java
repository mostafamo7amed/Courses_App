package com.example.courses.UI.Fragments.Trainee;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Models.Trainee;
import com.example.courses.R;
import com.example.courses.UI.Activities.CreateTraineeAccountActivity;
import com.example.courses.UI.Activities.TraineeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TraineeProfileFragment extends Fragment {

    EditText name ,age ,level,email;
    ImageButton editProfile;
    AppCompatButton editSave;
    ProgressBar loading;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Trainee trainee;
    String currentUserId;
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
                level.setEnabled(true);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainee_profile, container, false);
    }

    public void initialization(){
        name = getActivity().findViewById(R.id.pt_name);
        age = getActivity().findViewById(R.id.pt_age);
        level = getActivity().findViewById(R.id.pt_level);
        email = getActivity().findViewById(R.id.pt_email);
        editProfile = getActivity().findViewById(R.id.pt_edit);
        editSave = getActivity().findViewById(R.id.pt_edit_save);
        loading = getActivity().findViewById(R.id.pt_progress);
        trainee = new Trainee();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserId=user.getUid();
        }

        databaseReference=database.getReference("Trainees");
    }

    public void getDate(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId= null;
        if (user != null) {
            currentUserId = user.getUid();
        }
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
                            email.setText(u_email);
                            level.setText(u_level);

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
        String u_level=level.getText().toString();
        String u_age=age.getText().toString();
        String u_email=email.getText().toString();

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
                            name.setEnabled(false);
                            age.setEnabled(false);
                            level.setEnabled(false);
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