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
import com.example.courses.UI.Fragments.Contacts.ContactProfileFragment;
import com.example.courses.UI.Fragments.Contacts.CoursesFragment;
import com.example.courses.UI.Fragments.Contacts.TrainerFragment;
import com.example.courses.UI.Fragments.Employee.ContactsFragment;
import com.example.courses.UI.Fragments.Employee.TraineeFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageTrainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmployeeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        bottomNavigationView = findViewById(R.id.bottom_nav_employee);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);

        Fragment selected = null;
        Bundle bundle = new Bundle();
        bundle.putInt("frame",R.id.frame_layout_employee);
        selected = new ContactsFragment();
        selected.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_employee, selected).commit();
        Constants.current=1;
    }
    private final BottomNavigationView.OnItemSelectedListener OnSelect = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            int select=0;

            switch (item.getItemId()) {
                case R.id.contacts_emp: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_employee);
                    selected = new ContactsFragment();
                    selected.setArguments(bundle);
                    select =1;
                }
                break;
                case R.id.courses_emp: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_employee);
                    selected = new CoursesFragment();
                    selected.setArguments(bundle);
                    select = 2;
                }
                break;
                case R.id.comments_emp: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_employee);
                    selected = new ContactPageTrainerFragment();
                    selected.setArguments(bundle);
                    select = 3;
                }
                break;
                case R.id.trainee_emp: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_employee);
                    selected = new TraineeFragment();
                    selected.setArguments(bundle);
                    select =4;
                }
                break;
                case R.id.logout_emp: {
                    startActivity(new Intent(EmployeeActivity.this,LoginActivity.class));
                    finish();
                    select = 5;
                }
                break;
            }
            if (selected != null) {
                if(select != Constants.current) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_employee, selected).commit();
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