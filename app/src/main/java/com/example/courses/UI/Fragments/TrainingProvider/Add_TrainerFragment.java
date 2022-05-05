package com.example.courses.UI.Fragments.TrainingProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Models.Trainer;
import com.example.courses.R;
import com.example.courses.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import java.util.Objects;

public class Add_TrainerFragment extends Fragment {

    EditText email , pass ,name ,special;
    Spinner nationality;
    AppCompatButton addTrainerBtn;
    RadioGroup gender;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,databaseReference2;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    ProgressBar loading;
    Trainer trainer;
    FirebaseAuth firebaseAuth;
    String contactNumber , currentUserId , nation;
    long count ;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();

        nationality();
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
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        gender = getActivity().findViewById(R.id.cr_gender);
        trainer = new Trainer();
        databaseReference=database.getReference("ContactTrainers").child(currentUserId);
        databaseReference2=database.getReference("Trainers");
        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    count = dataSnapshot.getChildrenCount();
                    if(count==15){
                        alertCount();
                    }
                }
            }
        });


    }
    public void createProfile(){
        String u_name =name.getText().toString();
        String u_special=special.getText().toString();
        String u_nationality=nation;
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
            Toast.makeText(getContext(), "الرجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }

    public void putTrainerData(String userId){
        String u_name =name.getText().toString();
        String u_special=special.getText().toString();
        String u_nationality=nation;
        String u_email=email.getText().toString();
        String u_gander;
        int selectedID = gender.getCheckedRadioButtonId();
        if (selectedID == R.id.male) {
            u_gander = "ذكر";
        }else {
            u_gander = "أنثى";
        }

        trainer.setEmail(u_email);
        trainer.setName(u_name);
        trainer.setGender(u_gander);
        trainer.setNationality(u_nationality);
        trainer.setSpecialization(u_special);
        trainer.setUid(userId);
        trainer.setType("Trainers");
        trainer.setContact_number(Integer.parseInt(contactNumber));

        databaseReference2.child(userId).setValue(trainer);
        databaseReference.child(userId)
                .setValue(trainer)
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
    public void getDate(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId= null;
        if (user != null) {
            currentUserId = user.getUid();
        }
        DocumentReference reference;
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        if (currentUserId != null) {
            reference = firestore.collection("Training Provider").document(currentUserId);
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
                     getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new TrainerFragment()).commit();
                 }
             });
         }

    }
    public void alertCount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("لقد تجاوزت العدد المحدد من المدربين لا يمكنك إضافة مدربين جدد");
        builder.setTitle("تنبيه !");
        builder.setCancelable(false);
        builder.setPositiveButton("خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new TrainerFragment()).commit();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onStart() {
        super.onStart();
        getDate();
    }

    public void nationality(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.Nationality
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nationality.setAdapter(adapter);
        nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 nation = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}