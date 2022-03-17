package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.courses.Constants;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Trainee.CoursesTraineeFragment;
import com.example.courses.UI.Fragments.Trainee.MyCoursesTFragment;
import com.example.courses.UI.Fragments.Trainee.TraineeProfileFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageTrainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TraineeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee);
        bottomNavigationView = findViewById(R.id.bottom_nav_trainee);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_trainee, new CoursesTraineeFragment()).commit();
        Constants.current=1;
    }
    private final BottomNavigationView.OnItemSelectedListener OnSelect = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            int select=0;

            switch (item.getItemId()) {
                case R.id.courses_trainee: {
                    selected = new CoursesTraineeFragment();
                    select =1;
                }
                break;
                case R.id.myCourses_trainee: {
                    selected = new MyCoursesTFragment();
                    select = 2;
                }
                break;
                case R.id.contactPage_trainee: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_trainee);
                    selected = new ContactPageTrainerFragment();
                    selected.setArguments(bundle);
                    select = 3;
                }
                break;
                case R.id.profile_trainee: {
                    selected = new TraineeProfileFragment();
                    select = 4;
                }
                break;
                case R.id.exit_trainee: {
                    startActivity(new Intent(TraineeActivity.this,LoginActivity.class));
                    finish();
                    select = 5;
                }
                break;
            }
            if (selected != null) {
                if(select != Constants.current) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_trainee, selected).commit();
                    Constants.current = select;
                }
                else {
                    return true;
                }
            }
            return true;
        }
    };

}