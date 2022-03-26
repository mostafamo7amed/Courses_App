package com.example.courses.UI.Fragments.Contacts;

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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.Models.Trainer;
import com.example.courses.R;
import com.example.courses.UI.Activities.CreateContactAccountActivity;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Add_TrainerFragment extends Fragment {

    EditText email , pass ,name ,special,nationality;
    AppCompatButton addTrainerBtn;
    RadioGroup gender;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ProgressBar loading;
    Trainer trainer;
    FirebaseAuth firebaseAuth;
    String contactNumber;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();

        addTrainerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add__trainer, container, false);
    }

    public void initialization(){
        email  = getActivity().findViewById(R.id.cr_email);
        pass  = getActivity().findViewById(R.id.cr_pass);
        name  = getActivity().findViewById(R.id.cr_name);
        special  = getActivity().findViewById(R.id.cr_special);
        nationality  = getActivity().findViewById(R.id.cr_nationality);
        addTrainerBtn = getActivity().findViewById(R.id.cr_add_trainer);
        loading = getActivity().findViewById(R.id.cr_progress);
        firebaseAuth = FirebaseAuth.getInstance();
        gender = getActivity().findViewById(R.id.cr_gender);
        trainer = new Trainer();
    }
    public void createProfile(){
        String u_name =name.getText().toString();
        String u_special=special.getText().toString();
        String u_nationality=nationality.getText().toString();
        String u_email=email.getText().toString();
        String u_password = pass.getText().toString();


        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_nationality) && !TextUtils.isEmpty(u_special) && !TextUtils.isEmpty(u_email) && !TextUtils.isEmpty(u_password) && gender.getCheckedRadioButtonId() != -1) {
            loading.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(u_email,u_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        putTrainerData(firebaseUser.getUid());
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

    public void putTrainerData(String userId){
        String u_name =name.getText().toString();
        String u_special=special.getText().toString();
        String u_nationality=nationality.getText().toString();
        String u_email=email.getText().toString();
        String u_gander;
        int selectedID = gender.getCheckedRadioButtonId();
        if (selectedID == R.id.male) {
            u_gander = "male";
        }else {
            u_gander = "female";
        }

        databaseReference=database.getReference("Trainers");
        documentReference=db.collection("Trainers").document(userId);
        trainer.setEmail(u_email);
        trainer.setName(u_name);
        trainer.setGender(u_gander);
        trainer.setNationality(u_nationality);
        trainer.setSpecialization(u_special);
        trainer.setUid(userId);
        trainer.setContact_number(Integer.parseInt(contactNumber));

        Map<String ,String> profile=new HashMap<>();
        profile.put("name",u_name);
        profile.put("gender",u_gander);
        profile.put("nationality",u_nationality);
        profile.put("email",u_email);
        profile.put("specialization",u_special);
        profile.put("uid",userId);
        profile.put("contact_number",contactNumber);

        documentReference.set(profile);
        databaseReference.child(userId)
                .setValue(trainer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "تم إنشاء الحساب", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new TrainerFragment()).commit();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getDate(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId= null;
        if (user != null) {
            currentUserId = user.getUid();
        }
        DocumentReference reference;
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        if (currentUserId != null) {
            reference = firestore.collection("Contacts").document(currentUserId);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    try {
                        if (task.getResult().exists()) {
                            contactNumber =task.getResult().getString("number");
                        }
                    } catch (NullPointerException nullPointerException) {
                        Toast.makeText(getActivity(), "" + nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            getActivity().finish();
            System.exit(0);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        getDate();
    }
}