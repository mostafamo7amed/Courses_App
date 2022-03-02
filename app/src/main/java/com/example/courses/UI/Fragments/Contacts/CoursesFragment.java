package com.example.courses.UI.Fragments.Contacts;

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
import com.example.Models.Course;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Contacts.Add_CourseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CoursesFragment extends Fragment {

    FloatingActionButton addCourse;
    CoursesAdapter coursesAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addCourse = getActivity().findViewById(R.id.add_Course);
        recyclerView = getActivity().findViewById(R.id.RecyclerCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courses = new ArrayList<>();
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        coursesAdapter = new CoursesAdapter(getContext(),courses);
        coursesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(coursesAdapter);


        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new Add_CourseFragment()).addToBackStack(null).commitAllowingStateLoss();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }
}