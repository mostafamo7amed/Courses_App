package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.courses.Constants;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Admin.EmployeeFragment;
import com.example.courses.UI.Fragments.Admin.MoreFragment;
import com.example.courses.UI.Fragments.Contacts.CoursesFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new EmployeeFragment()).commit();
        Constants.current=1;

    }
    private final BottomNavigationView.OnItemSelectedListener OnSelect = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            int select=0;

            switch (item.getItemId()) {
                case R.id.users: {
                    selected = new EmployeeFragment();
                    select =1;
                }
                break;
                case R.id.courses_adm: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout);
                    selected = new CoursesFragment();
                    selected.setArguments(bundle);
                    select = 2;
                }
                break;
                case R.id.comments_adm:
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout);
                    selected = new ContactPageFragment();
                    selected.setArguments(bundle);
                    select = 3;
                break;
                case R.id.more: {
                    selected = new MoreFragment();
                    select = 4;
                }
                break;
            }
            if (selected != null) {
                if(select != Constants.current) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selected).commit();
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