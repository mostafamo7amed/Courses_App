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

import java.util.ArrayList;

public class CoursesTrainerFragment extends Fragment {
    CoursesTrainerAdapter coursesTrainerAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.RecyclerTrainerCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courses = new ArrayList<>();
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));
        courses.add(new Course("علوم الحاسب","لغة برمجة 1","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,1,"",""));

        coursesTrainerAdapter = new CoursesTrainerAdapter(getContext(),courses);
        coursesTrainerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(coursesTrainerAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_courses_trainer, container, false);
    }
}