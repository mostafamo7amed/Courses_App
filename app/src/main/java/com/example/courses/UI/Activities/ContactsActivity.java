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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContactsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        bottomNavigationView = findViewById(R.id.bottom_nav_cont);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new TrainerFragment()).commit();
        Constants.current=1;
    }
    private final BottomNavigationView.OnItemSelectedListener OnSelect = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            int select=0;

            switch (item.getItemId()) {
                case R.id.trainer_cont: {
                    selected = new TrainerFragment();
                    select =1;
                }
                break;
                case R.id.courses_cont: {
                    selected = new CoursesFragment();
                    select = 2;
                }
                break;
                case R.id.profile_cont: {
                    selected = new ContactProfileFragment();
                    select = 3;
                }
                break;
                case R.id.exit_cont: {
                    startActivity(new Intent(ContactsActivity.this,LoginActivity.class));
                    finish();
                    select = 4;
                }
                break;
            }
            if (selected != null) {
                if(select != Constants.current) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, selected).commit();
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