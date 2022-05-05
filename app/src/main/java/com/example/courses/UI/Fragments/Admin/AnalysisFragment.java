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

import com.example.courses.MyDialogFragment;
import com.example.courses.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AnalysisFragment extends Fragment {
    TextView trainers,trainerWo,contacts , employees,employeesWo, trainees,traineesWo,courses , comments;
    ProgressBar commentProgress,trainerProgress,trainerProgressWo,contactProgress,employeeProgress,employeeProgressWo, coursesProgress;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference coursesPresent;
    DatabaseReference trainee , trainer , contact , employee , commentReference;
    long commentNumber=0 ,traineeNumber=0 ,traineeNumberWo=0, trainerNumber=0,trainerNumberWo=0,contactsNumber,employeeNumber=0,employeeNumberWo=0;
    TextView tv15_20,tv20_35,tv35_45,tv45_55,tv55_65;
    ProgressBar pr15_20,pr20_35,pr35_45,pr45_55,pr55_65;
    long trn15=0,trn20=0,trn35=0,trn45=0,trn55=0;

    TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7,tv_8,tv_9,tv_10;
    ProgressBar pr_1,pr_2,pr_3,pr_4,pr_5,pr_6,pr_7,pr_8,pr_9,pr_10;
    long n1=0,n2=0,n3=0,n4=0,n5=0,n6=0,n7=0,n8=0,n9=0,n10=0;

    TextView embAnalysis , conAnalysis, comAnalysis , trrAnalysis , couAnalysis , triAnalysis;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


       initialization();




        coursesPresent = database.getReference("All Courses");
        coursesPresent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    coursesPresent.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                if (snapshot.child("field").getValue().toString().equals("الكمبيوتر والتكنولوجيا"))
                                    n1+=1;
                                else if(snapshot.child("field").getValue().toString().equals("ادارة الأعمال")){
                                    n2+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("الامن والسلامة")){
                                    n3+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("علم النفس")){
                                    n4+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("التنمية الذاتية")){
                                    n5+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("المالية والمحاسبة")){
                                    n6+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("المبيعات والتسويق")){
                                    n7+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("الطب")){
                                    n8+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("لغات")){
                                    n9+=1;

                                }else if(snapshot.child("field").getValue().toString().equals("اخرى")){
                                    n10+=1;

                                }

                                tv_1.setText( ((n1*100.0)/100)+"%");
                                pr_1.setProgress((int)n1);

                                tv_2.setText( ((n2*100.0)/100)+"%");
                                pr_2.setProgress((int)n2);

                                tv_3.setText( ((n3*100.0)/100)+"%");
                                pr_3.setProgress((int)n3);

                                tv_4.setText( ((n4*100.0)/100)+"%");
                                pr_4.setProgress((int)n4);

                                tv_5.setText( ((n5*100.0)/100)+"%");
                                pr_5.setProgress((int)n5);

                                tv_6.setText( ((n6*100.0)/100)+"%");
                                pr_6.setProgress((int)n6);

                                tv_7.setText( ((n7*100.0)/100)+"%");
                                pr_7.setProgress((int)n7);

                                tv_8.setText( ((n8*100.0)/100)+"%");
                                pr_8.setProgress((int)n8);

                                tv_9.setText( ((n9*100.0)/100)+"%");
                                pr_9.setProgress((int)n9);

                                tv_10.setText( ((n10*100.0)/100)+"%");
                                pr_10.setProgress((int)n10);

                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        trainee = database.getReference("Trainees");
        trainee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    trainee.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                String age = snapshot.child("age").getValue().toString();

                                if(snapshot.child("gender").getValue().toString().equals("ذكر")){
                                    traineeNumber+=1;
                                }else{
                                    traineeNumberWo+=1;
                                }
                                trainees.setText(((traineeNumber*100.0)/100)+"%");
                                traineesWo.setText(((traineeNumberWo*100.0)/100)+"%");

                                String year;
                                int traineeYear = 0;
                                if(age.length() == 8){
                                    year = age.substring(4);
                                    traineeYear =Integer.parseInt(year);
                                }else if (age.length() ==9){
                                    year = age.substring(5);
                                    traineeYear =Integer.parseInt(year);
                                }else if(age.length() == 10){
                                    year = age.substring(6);
                                    traineeYear =Integer.parseInt(year);
                                }

                                Calendar calendar = Calendar.getInstance();
                                int currentYear = calendar.get(Calendar.YEAR);

                                int traineeAge = currentYear - traineeYear;
                                if(traineeAge>=15 && traineeAge<20){
                                    trn15+=1;
                                }else if (traineeAge>=20 && traineeAge<35){
                                    trn20+=1;
                                }else if (traineeAge>=35 && traineeAge<45){
                                    trn35+=1;
                                }else if (traineeAge>=45 && traineeAge<55){
                                    trn45+=1;
                                }else if (traineeAge>=55 && traineeAge<65){
                                    trn55+=1;
                                }

                                tv15_20.setText( ((trn15*100.0)/100)+"%");
                                pr15_20.setProgress((int)trn15);
                                tv20_35.setText( ((trn20*100.0)/100)+"%");
                                pr20_35.setProgress((int)trn20);
                                tv35_45.setText( ((trn35*100.0)/100)+"%");
                                pr35_45.setProgress((int)trn35);
                                tv45_55.setText( ((trn45*100.0)/100)+"%");
                                pr45_55.setProgress((int)trn45);
                                tv55_65.setText( ((trn55*100.0)/100)+"%");
                                pr55_65.setProgress((int)trn55);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        trainer = database.getReference("Trainers");
        trainer.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1 :dataSnapshot.getChildren()){
                    trainer.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                if(snapshot.child("gender").getValue().toString().equals("ذكر")){
                                    trainerNumber+=1;
                                }else{
                                    trainerNumberWo+=1;
                                }
                                trainers.setText( ((trainerNumber*100.0)/100)+"%");
                                trainerWo.setText(((trainerNumberWo*100.0)/100)+"%");
                                trainerProgress.setProgress((int)trainerNumber);
                                trainerProgressWo.setProgress((int)trainerNumberWo);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    employee.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                               if(snapshot.child("gender").getValue().toString().equals("ذكر")){
                                   employeeNumber+=1;
                               }else{
                                   employeeNumberWo+=1;
                               }
                                employees.setText( ((employeeNumber*100.0)/100)+"%");
                                employeesWo.setText(((employeeNumberWo*100.0)/100)+"%");
                                employeeProgress.setProgress((int)employeeNumber);
                                employeeProgressWo.setProgress((int)employeeNumberWo);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        commentReference = database.getReference("All Comments");
        commentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    commentNumber = snapshot.getChildrenCount();
                    double present = (commentNumber*100.0)/100;
                    comments.setText(present+" %");
                    commentProgress.setProgress((int) present);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        embAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnalysis("Employees");
            }
        });

        conAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnalysis("Training Provider");
            }
        });
        comAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnalysis("All Comments");
            }
        });
        trrAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnalysis("Trainees");
            }
        });
        couAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnalysis("Courses");
            }
        });
        triAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnalysis("Trainer");
            }
        });
    }
    public void initialization(){
        comments = getActivity().findViewById(R.id.comments_present);
        trainees = getActivity().findViewById(R.id.tr_male_tv);
        traineesWo = getActivity().findViewById(R.id.tr_women_tv);
        trainers = getActivity().findViewById(R.id.trainer_present);
        trainerWo = getActivity().findViewById(R.id.trainer_present_woman);
        contacts = getActivity().findViewById(R.id.contact_present);
        employees = getActivity().findViewById(R.id.employee_present);
        employeesWo = getActivity().findViewById(R.id.employee_present_woman);

        commentProgress = getActivity().findViewById(R.id.comments_progress);
        trainerProgress = getActivity().findViewById(R.id.trainer_progress);
        trainerProgressWo = getActivity().findViewById(R.id.trainer_progress_woman);
        contactProgress = getActivity().findViewById(R.id.contact_progress);
        employeeProgress = getActivity().findViewById(R.id.employee_progress);
        employeeProgressWo = getActivity().findViewById(R.id.employee_progress_woman);

        tv15_20 = getActivity().findViewById(R.id.tr_1520_tv);
        tv20_35 = getActivity().findViewById(R.id.tr_2035_tv);
        tv35_45 = getActivity().findViewById(R.id.tr_3545_tv);
        tv45_55 = getActivity().findViewById(R.id.tr_4555_tv);
        tv55_65 = getActivity().findViewById(R.id.tr_5565_tv);

        pr15_20 = getActivity().findViewById(R.id.tr_1520_progress);
        pr20_35 = getActivity().findViewById(R.id.tr_2035_progress);
        pr35_45 = getActivity().findViewById(R.id.tr_3545_progress);
        pr45_55 = getActivity().findViewById(R.id.tr_4555_progress);
        pr55_65 = getActivity().findViewById(R.id.tr_5565_progress);

        tv_1 = getActivity().findViewById(R.id.courses_present1);
        tv_2 = getActivity().findViewById(R.id.courses_present2);
        tv_3 = getActivity().findViewById(R.id.courses_present3);
        tv_4 = getActivity().findViewById(R.id.courses_present4);
        tv_5 = getActivity().findViewById(R.id.courses_present5);
        tv_6 = getActivity().findViewById(R.id.courses_present6);
        tv_7 = getActivity().findViewById(R.id.courses_present7);
        tv_8 = getActivity().findViewById(R.id.courses_present8);
        tv_9 = getActivity().findViewById(R.id.courses_present9);
        tv_10 = getActivity().findViewById(R.id.courses_present10);

        pr_1 = getActivity().findViewById(R.id.courses_progress1);
        pr_2 = getActivity().findViewById(R.id.courses_progress2);
        pr_3 = getActivity().findViewById(R.id.courses_progress3);
        pr_4 = getActivity().findViewById(R.id.courses_progress4);
        pr_5 = getActivity().findViewById(R.id.courses_progress5);
        pr_6 = getActivity().findViewById(R.id.courses_progress6);
        pr_7 = getActivity().findViewById(R.id.courses_progress7);
        pr_8 = getActivity().findViewById(R.id.courses_progress8);
        pr_9 = getActivity().findViewById(R.id.courses_progress9);
        pr_10 = getActivity().findViewById(R.id.courses_progress10);

        embAnalysis = getActivity().findViewById(R.id.emb);
        conAnalysis = getActivity().findViewById(R.id.cont);
        comAnalysis = getActivity().findViewById(R.id.comm);
        trrAnalysis = getActivity().findViewById(R.id.ttrainee);
        couAnalysis = getActivity().findViewById(R.id.courses);
        triAnalysis = getActivity().findViewById(R.id.tainer);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }

    public void startAnalysis(String table){
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",table);
        myDialogFragment.setArguments(bundle);
        myDialogFragment.show(getParentFragmentManager(),"MyFragment");
    }
}