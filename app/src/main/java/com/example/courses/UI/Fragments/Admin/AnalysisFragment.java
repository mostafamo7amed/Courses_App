package com.example.courses.UI.Fragments.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courses.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnalysisFragment extends Fragment {
    TextView trainers,contacts , employees, trainees,courses;
    ProgressBar traineeProgress,trainerProgress,contactProgress,employeeProgress, coursesProgress;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference coursesPresent;
    DatabaseReference trainee , trainer , contact , employee;
    long traineeNumber , trainerNumber,contactsNumber,employeeNumber;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trainees = getActivity().findViewById(R.id.trainee_present);
        trainers = getActivity().findViewById(R.id.trainer_present);
        contacts = getActivity().findViewById(R.id.contact_present);
        employees = getActivity().findViewById(R.id.employee_present);
        courses = getActivity().findViewById(R.id.courses_present);

        traineeProgress = getActivity().findViewById(R.id.trainee_progress);
        trainerProgress = getActivity().findViewById(R.id.trainer_progress);
        contactProgress = getActivity().findViewById(R.id.contact_progress);
        employeeProgress = getActivity().findViewById(R.id.employee_progress);
        coursesProgress = getActivity().findViewById(R.id.courses_progress);

        coursesPresent = database.getReference("All Courses");
        coursesPresent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    double present = (snapshot.getChildrenCount()*100.0)/100;
                    courses.setText(present+" %");
                    coursesProgress.setProgress((int) present);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        trainee = database.getReference("Trainees");
        trainee.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    traineeNumber = dataSnapshot.getChildrenCount();
                    double present = (traineeNumber*100.0)/100;
                    trainees.setText(present+" %");
                    traineeProgress.setProgress((int) present);
                }
            }
        });

        trainer = database.getReference("Trainers");
        trainer.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    trainerNumber = dataSnapshot.getChildrenCount();
                    double present = (trainerNumber*100.0)/100;
                    trainers.setText(present+" %");
                    trainerProgress.setProgress((int) present);
                }
            }
        });

        contact = database.getReference("Training Provider");
        contact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    contactsNumber = snapshot.getChildrenCount();
                    double present = (contactsNumber*100.0)/100;
                    contacts.setText(present+" %");
                    contactProgress.setProgress((int) present);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        employee = database.getReference("Employees");
        employee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    employeeNumber = snapshot.getChildrenCount();
                    double present = (employeeNumber*100.0)/100;
                    employees.setText(present+" %");
                    employeeProgress.setProgress((int) present);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }
}