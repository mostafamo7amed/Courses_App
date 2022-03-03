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

import com.example.Adapters.CoursesTraineeRegisterAdapter;
import com.example.Adapters.CoursesTraineeUnRegisterAdapter;
import com.example.Models.Course;
import com.example.courses.R;

import java.util.ArrayList;

public class MyCoursesTraineeFragment extends Fragment {

    CoursesTraineeUnRegisterAdapter coursesTraineeUnRegisterAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.RecyclerTraineeMyCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courses = new ArrayList<>();
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","طيبة",2,"22/3/2023","5:30AM"));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","الرياض",2,"22/3/2023",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","طيبة",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","مكة",2,"","5:30AM"));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","الرياض",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","طيبة",2,"22/3/2023","5:30AM"));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","مكة",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","الرياض",2,"22/3/2023","5:30AM"));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","طيبة",2,"",""));
        coursesTraineeUnRegisterAdapter = new CoursesTraineeUnRegisterAdapter(getContext(),courses);
        coursesTraineeUnRegisterAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(coursesTraineeUnRegisterAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_courses_trainee, container, false);
    }
}