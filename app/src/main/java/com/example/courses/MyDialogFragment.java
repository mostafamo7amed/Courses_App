package com.example.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapters.ContactPageAdapter;
import com.example.Adapters.ContactsAdapter;
import com.example.Adapters.CoursesAdapter;
import com.example.Adapters.EmployeeAdapter;
import com.example.Adapters.TraineeAdapter;
import com.example.Adapters.TrainerAdapter;
import com.example.Models.Comments;
import com.example.Models.Course;
import com.example.Models.Employee;
import com.example.Models.Trainee;
import com.example.Models.Trainer;
import com.example.Models.TrainingProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDialogFragment extends DialogFragment {
    RecyclerView recyclerView;
    TextView textView;
    EmployeeAdapter employeeAdapter;
    ContactsAdapter contactsAdapter;
    TraineeAdapter traineeAdapter;
    CoursesAdapter coursesAdapter;
    TrainerAdapter trainerAdapter;
    ContactPageAdapter contactPageAdapter;
    ArrayList<Employee> employees;
    ArrayList<Comments> comments;
    ArrayList<Trainee> trainees;
    ArrayList<Course> courses;
    ArrayList<Trainer> trainers;
    ArrayList<TrainingProvider> providers;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         return inflater.inflate(R.layout.dialog_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.analysisRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textView = getView().findViewById(R.id.analysisTV);
        employees = new ArrayList<>();
        providers = new ArrayList<>();
        comments = new ArrayList<>();
        courses = new ArrayList<>();
        trainees = new ArrayList<>();
        trainers = new ArrayList<>();

        if(getArguments().getString("type").equals("Employees")){
            textView.setText("الموظفين");
            employeeAdapter = new EmployeeAdapter(getContext(),employees,true);
            getEmployees();
        }else if(getArguments().getString("type").equals("Training Provider")){
            textView.setText("مزود التدريبات");
            contactsAdapter = new ContactsAdapter(getContext(), providers, true);
            getContacts();
        }else if(getArguments().getString("type").equals("All Comments")){
            textView.setText("التعليقات");
            contactPageAdapter = new ContactPageAdapter(getContext(),comments , true);
            getAllComments();
        }else if(getArguments().getString("type").equals("Trainees")){
            textView.setText("المتدربين");
            traineeAdapter = new TraineeAdapter(getContext(),trainees , true);
            getTrainees();
        }else if(getArguments().getString("type").equals("Courses")){
            textView.setText("الدورات");
            coursesAdapter = new CoursesAdapter(getContext(),courses , true);
            getAllCourses();
        }else if(getArguments().getString("type").equals("Trainer")){
            textView.setText("المدربين");
            trainerAdapter = new TrainerAdapter(getContext(),trainers, true);
            getTrainers();
        }


    }
    public void getEmployees(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employees");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employees.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                employees.add(new Employee(snapshot.child("email").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("position").getValue().toString(),
                                        snapshot.child("uid").getValue().toString(),
                                        snapshot.child("age").getValue().toString(),
                                        snapshot.child("number").getValue().toString()

                                ));

                            }
                            employeeAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(employeeAdapter);

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
    }
    public void getContacts(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Training Provider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                providers.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists()) {
                                providers.add(new TrainingProvider(snapshot.child("name").getValue().toString(),
                                        snapshot.child("email").getValue().toString(),
                                        snapshot.child("phone").getValue().toString(),
                                        snapshot.child("region").getValue().toString(),
                                        snapshot.child("commercial_register").getValue().toString(),
                                        Integer.parseInt(snapshot.child("contact_number").getValue().toString()),
                                        snapshot.child("uid").getValue().toString(),
                                        snapshot.child("image").getValue().toString()
                                ));
                            }

                            recyclerView.setAdapter(contactsAdapter);
                            contactsAdapter.notifyDataSetChanged();
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
    }
    public void getAllComments(){
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("All Comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                comments.add(new Comments(snapshot.child("email").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("comment").getValue().toString(),
                                        snapshot.child("time").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("key").getValue().toString(),
                                        snapshot.child("uid").getValue().toString(),
                                        snapshot.child("type").getValue().toString()
                                ));


                            }

                            contactPageAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(contactPageAdapter);
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



    }
    public void getTrainees(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trainees");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainees.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                trainees.add(new Trainee(snapshot.child("name").getValue().toString(),
                                        snapshot.child("email").getValue().toString(),
                                        snapshot.child("educationLevel").getValue().toString(),
                                        snapshot.child("age").getValue().toString(),
                                        snapshot.child("uid").getValue().toString()
                                ));

                            }
                            recyclerView.setAdapter(traineeAdapter);
                            traineeAdapter.notifyDataSetChanged();
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
    }
    public void getAllCourses(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                courses.add(new Course(snapshot.child("field").getValue().toString(),
                                        snapshot.child("courseMaterial").getValue().toString(),
                                        snapshot.child("description").getValue().toString(),
                                        snapshot.child("trainer").getValue().toString(),
                                        snapshot.child("address").getValue().toString(),
                                        Integer.parseInt(snapshot.child("contactNumber").getValue().toString()),
                                        Integer.parseInt(snapshot.child("courseNumber").getValue().toString()),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("end").getValue().toString(),
                                        snapshot.child("time").getValue().toString(),
                                        snapshot.child("key").getValue().toString(),
                                        Integer.parseInt(snapshot.child("total").getValue().toString()),
                                        Integer.parseInt(snapshot.child("current").getValue().toString())
                                ));
                            }

                            coursesAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(coursesAdapter);

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
    }
    public void getTrainers(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trainers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainers.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                trainers.add(new Trainer(
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("gender").getValue().toString(),
                                        snapshot.child("email").getValue().toString(),
                                        snapshot.child("specialization").getValue().toString(),
                                        snapshot.child("nationality").getValue().toString(),
                                        snapshot.child("uid").getValue().toString(),
                                        Integer.parseInt(snapshot.child("contact_number").getValue().toString())
                                ));
                            }
                            trainerAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(trainerAdapter);

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
    }




}
