package com.example.courses.UI.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.courses.UI.Fragments.Admin.AddFieldsFragment;
import com.example.courses.UI.Fragments.Admin.AnalysisFragment;
import com.example.courses.Constants;
import com.example.courses.UI.Fragments.Admin.ContactPageAdminFragment;
import com.example.courses.UI.Fragments.Admin.UsersAdminFragment;
import com.example.courses.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new UsersAdminFragment()).commit();
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
                    selected = new UsersAdminFragment();
                    select =1;
                }
                break;
                case R.id.fields: {
                    selected = new AddFieldsFragment();
                    select = 3;
                }
                break;
                case R.id.contactPage: {
                    selected = new ContactPageAdminFragment();
                    select = 2;
                }
                break;
                case R.id.analyses: {
                    selected = new AnalysisFragment();
                    select = 4;
                }
                break;
                case R.id.logout: {
                    Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
                    startActivity(intent);
                    select = 5;
                }
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