package com.example.courses.UI.Fragments.Trainer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Adapters.CoursesAdapter;
import com.example.Adapters.CoursesTrainerAdapter;
import com.example.Models.Course;
import com.example.courses.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursesTrainerFragment extends Fragment {
    CoursesTrainerAdapter coursesTrainerAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;
    DatabaseReference databaseReference;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");

        recyclerView = getActivity().findViewById(R.id.RecyclerTrainerCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courses = new ArrayList<>();
        coursesTrainerAdapter = new CoursesTrainerAdapter(getContext(),courses);
        getAllCourses();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_courses_trainer, container, false);
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

                            coursesTrainerAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(coursesTrainerAdapter);

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