package com.example.courses.UI.Fragments.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.Adapters.CoursesAdapter;
import com.example.Models.Course;
import com.example.courses.R;
import com.example.courses.UI.Activities.AdminActivity;
import com.example.courses.UI.Activities.ContactsActivity;
import com.example.courses.UI.Activities.EmployeeActivity;
import com.example.courses.UI.Activities.LoginActivity;
import com.example.courses.UI.Activities.TraineeActivity;
import com.example.courses.UI.Activities.TrainerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursesFragment extends Fragment {

    FloatingActionButton addCourse;
    CoursesAdapter coursesAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;
    DatabaseReference databaseReference , databaseReference2;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String currentUserId;
    int x = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initialization();
        coursesAdapter = new CoursesAdapter(getContext(),courses, false);
        checkUser();

        coursesAdapter.setOnItemClickListener(new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemEdit(String key) {
                editCourses(key);
            }

            @Override
            public void onItemDeleted(String key) {
                deleteCourse(key);
            }
        });


        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selected;
                Bundle bundle = new Bundle();
                bundle.putInt("frame",getArguments().getInt("frame"));
                selected = new Add_CourseFragment();
                selected.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"),selected).addToBackStack(null).commitAllowingStateLoss();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    private void initialization(){
        addCourse = getActivity().findViewById(R.id.add_Course);
        recyclerView = getActivity().findViewById(R.id.RecyclerCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courses = new ArrayList<>();
        currentUserId= firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("ContactCourses").child(currentUserId);


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
    public void getContactCourses(){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();

                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    x=0;
                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.child("All Courses").child(snapshot1.getKey().toString()).exists()) {
                                x =1;
                                deleteUnReachableCourse(snapshot1.getKey().toString());
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                    if(x==1){
                        continue;
                    }
                    databaseReference2.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
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


    public void deleteUnReachableCourse(String key){
        databaseReference2.child(key).removeValue();
    }
    public void deleteCourse(String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("سيتم حذف الدورة !");
        builder.setTitle("تنبيه !");
        builder.setCancelable(false);
        builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int user = getArguments().getInt("frame");
                if(user == R.id.frame_layout_cont){
                    databaseReference2.child(key).removeValue();
                }
                databaseReference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "تم حذف الدورة", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void editCourses(String key){
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        bundle.putInt("frame",getArguments().getInt("frame"));
        fragment = new EditCoursesFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), fragment).addToBackStack(null).commitAllowingStateLoss();
    }
    public void checkUser(){
        int user = getArguments().getInt("frame");
        if(user == R.id.frame_layout_cont){
            getContactCourses();
        }else if (user == R.id.frame_layout_employee){
            getAllCourses();
        }else if (user == R.id.frame_layout){
            getAllCourses();
        }
    }

}