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
import com.example.courses.UI.Fragments.Trainee.MyCoursesTFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageTrainerFragment;
import com.example.courses.UI.Fragments.Trainer.CoursesTrainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrainerActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        bottomNavigationView = findViewById(R.id.bottom_nav_trainer);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_trainer, new CoursesTrainerFragment()).commit();
        Constants.current=1;
    }
    private final BottomNavigationView.OnItemSelectedListener OnSelect = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            int select=0;

            switch (item.getItemId()) {
                case R.id.courses_trainer: {
                    selected = new CoursesTrainerFragment();
                    select =1;
                }
                break;
                case R.id.contactPage_trainer: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_trainer);
                    selected = new ContactPageTrainerFragment();
                    selected.setArguments(bundle);
                    select = 2;
                }
                break;
                case R.id.exit_trainer: {
                    startActivity(new Intent(TrainerActivity.this,LoginActivity.class));
                    finish();
                    select = 3;
                }
                break;
            }
            if (selected != null) {
                if(select != Constants.current) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_trainer, selected).commit();
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