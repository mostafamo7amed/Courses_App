package com.example.courses.UI.Fragments.Contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.Adapters.ContactPageAdapter;
import com.example.Adapters.CoursesAdapter;
import com.example.Models.Comments;
import com.example.Models.Course;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Admin.EditContactsFragment;
import com.example.courses.UI.Fragments.Admin.EditCoursesFragment;
import com.example.courses.UI.Fragments.Contacts.Add_CourseFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    DatabaseReference databaseReference;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initialization();
        coursesAdapter = new CoursesAdapter(getContext(),courses);
        databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");

        getAllCourses();

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
                                        snapshot.child("time").getValue().toString(),
                                        snapshot.child("key").getValue().toString()
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

    public void deleteCourse(String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("سيتم حذف الدورة !");
        builder.setTitle("تنبيه !");
        builder.setCancelable(false);
        builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");
                databaseReference.child(key).removeValue();
                Toast.makeText(getContext(), "تم حذف الدورة", Toast.LENGTH_SHORT).show();
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
}