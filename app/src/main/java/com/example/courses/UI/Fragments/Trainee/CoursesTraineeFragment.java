package com.example.courses.UI.Fragments.Trainee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Adapters.CoursesTraineeRegisterAdapter;
import com.example.Adapters.CoursesTrainerAdapter;
import com.example.Models.Course;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursesTraineeFragment extends Fragment {
    CoursesTraineeRegisterAdapter coursesRegisterAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;
    DatabaseReference databaseReference, registerReference;
    FirebaseAuth user = FirebaseAuth.getInstance();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.RecyclerTraineeCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");
        registerReference = FirebaseDatabase.getInstance().getReference("Registered Courses");
        courses = new ArrayList<>();
        coursesRegisterAdapter = new CoursesTraineeRegisterAdapter(getContext(),courses);
        getAllCourses();


        coursesRegisterAdapter.setOnItemClickListener(new CoursesTraineeRegisterAdapter.OnItemClickListener() {
            @Override
            public void onItemRegister(String Key) {
                Course registerCourse = new Course();
                String childKey = Key;
                databaseReference.child(Key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            registerCourse.setKey(Key);
                            registerCourse.setCourseMaterial(task.getResult().child("courseMaterial").getValue().toString());
                            registerCourse.setDescription(task.getResult().child("description").getValue().toString());
                            registerCourse.setCourseNumber(Integer.parseInt(task.getResult().child("courseNumber").getValue().toString()));
                            registerCourse.setTrainer(task.getResult().child("trainer").getValue().toString());
                            registerCourse.setContactNumber(Integer.parseInt(task.getResult().child("contactNumber").getValue().toString()));
                            registerCourse.setTime(task.getResult().child("time").getValue().toString());
                            registerCourse.setDate(task.getResult().child("date").getValue().toString());
                            registerCourse.setAddress(task.getResult().child("address").getValue().toString());
                            registerCourse.setField(task.getResult().child("field").getValue().toString());
                            RegisterCourse(registerCourse,childKey);
                        }
                    }
                });
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses_trainee, container, false);
    }
    public void getAllCourses(){
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
                                        snapshot.child("key").getValue().toString()
                                ));
                            }

                            coursesRegisterAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(coursesRegisterAdapter);

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
    public void RegisterCourse(Course course , String key){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        String currentUserID = user.getCurrentUser().getUid();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child("Registered Courses").child(currentUserID).child(key).exists()) {
                    registerReference.child(currentUserID).child(key).setValue(course).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "تم التسجيل بالدورة", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }else {

                    Toast.makeText(getContext(), "الدورة مسجلة بالفعل", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}