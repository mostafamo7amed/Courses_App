package com.example.courses.UI.Fragments.Trainee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Adapters.CoursesTraineeRegisterAdapter;
import com.example.Adapters.CoursesTraineeUnRegisterAdapter;
import com.example.Models.Course;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCoursesTFragment extends Fragment {

    CoursesTraineeUnRegisterAdapter coursesTraineeUnRegisterAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;
    DatabaseReference registerReference ,databaseReference;
    FirebaseAuth user = FirebaseAuth.getInstance();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.RecyclerTraineeMyCourses);
        String currentUserID = user.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");
        registerReference = FirebaseDatabase.getInstance().getReference("Registered Courses").child(currentUserID);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courses = new ArrayList<>();
        coursesTraineeUnRegisterAdapter = new CoursesTraineeUnRegisterAdapter(getContext(),courses);
        getTraineeCourses();

        coursesTraineeUnRegisterAdapter.setOnItemClickListener(new CoursesTraineeUnRegisterAdapter.OnItemClickListener() {
            @Override
            public void onItemUnRegister(String Key) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("سيتم إلغاء التسجيل في الدورة");
                builder.setTitle("تنبيه!");
                builder.setCancelable(false);
                builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateCourseState(Key);
                        registerReference.child(Key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              Toast.makeText(getContext(), "تم إلغاء التسجيل في الدورة", Toast.LENGTH_SHORT).show();
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
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_courses_trainee, container, false);
    }
    public void getTraineeCourses(){
        registerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                courses.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    Log.v("tagg", snapshot.getKey().toString());
                    registerReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
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

                            coursesTraineeUnRegisterAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(coursesTraineeUnRegisterAdapter);
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

    public void updateCourseState(String key){
        Course registerCourse = new Course();
        databaseReference.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    registerCourse.setKey(key);
                    registerCourse.setCourseMaterial(task.getResult().child("courseMaterial").getValue().toString());
                    registerCourse.setDescription(task.getResult().child("description").getValue().toString());
                    registerCourse.setCourseNumber(Integer.parseInt(task.getResult().child("courseNumber").getValue().toString()));
                    registerCourse.setTrainer(task.getResult().child("trainer").getValue().toString());
                    registerCourse.setContactNumber(Integer.parseInt(task.getResult().child("contactNumber").getValue().toString()));
                    registerCourse.setTime(task.getResult().child("time").getValue().toString());
                    registerCourse.setDate(task.getResult().child("date").getValue().toString());
                    registerCourse.setEnd(task.getResult().child("end").getValue().toString());
                    registerCourse.setAddress(task.getResult().child("address").getValue().toString());
                    registerCourse.setField(task.getResult().child("field").getValue().toString());
                    int y =Integer.parseInt(task.getResult().child("total").getValue().toString());
                    int x =Integer.parseInt(task.getResult().child("current").getValue().toString());
                    x-=1;

                    registerCourse.setCurrent(x);
                    registerCourse.setTotal(y);

                }
            }
        });


    }
}