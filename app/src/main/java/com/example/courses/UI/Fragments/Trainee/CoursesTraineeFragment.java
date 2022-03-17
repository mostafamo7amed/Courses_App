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
import com.example.Adapters.CoursesTrainerAdapter;
import com.example.Models.Course;
import com.example.courses.R;

import java.util.ArrayList;

public class CoursesTraineeFragment extends Fragment {
    CoursesTraineeRegisterAdapter coursesRegisterAdapter;
    RecyclerView recyclerView;
    ArrayList<Course> courses;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.RecyclerTraineeCourses);
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

        coursesRegisterAdapter = new CoursesTraineeRegisterAdapter(getContext(),courses);
        coursesRegisterAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(coursesRegisterAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses_trainee, container, false);
    }
}