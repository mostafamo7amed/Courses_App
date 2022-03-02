package com.example.courses.UI.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.Adapters.ContactPageStartAdapter;
import com.example.Adapters.CoursesStartAdapter;
import com.example.Models.Comments;
import com.example.Models.Course;
import com.example.courses.R;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    AppCompatButton login ,register;
    RecyclerView recyclerCourses,recyclerPage;
    CoursesStartAdapter coursesStartAdapter;
    ContactPageStartAdapter contactPageStartAdapter;
    ArrayList<Course> courses;
    ArrayList<Comments> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        login = findViewById(R.id.login_start);
        register = findViewById(R.id.register_start);



        Courses();
        Comments();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
            }
        });
    }
    public void Courses(){
        recyclerCourses = findViewById(R.id.recyclerCourses_Start);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(this));
        courses = new ArrayList<>();
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        courses.add(new Course("علوم الحاسب","هذه الدورة مقدمة لطلاب علوم الحاسب.......","احمد علي","",2,"",""));
        coursesStartAdapter = new CoursesStartAdapter(this,courses);
        coursesStartAdapter.notifyDataSetChanged();
        recyclerCourses.setAdapter(coursesStartAdapter);
    }
    public void Comments(){
        recyclerPage = findViewById(R.id.recyclerContactPage_Start);
        recyclerPage.setLayoutManager(new LinearLayoutManager(this));
        comments = new ArrayList<>();
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        contactPageStartAdapter = new ContactPageStartAdapter(this,comments);
        contactPageStartAdapter.notifyDataSetChanged();
        recyclerPage.setAdapter(contactPageStartAdapter);
    }
}