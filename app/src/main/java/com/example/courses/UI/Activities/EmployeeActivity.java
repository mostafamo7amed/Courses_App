package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.courses.Constants;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Admin.CoursesFragment;
import com.example.courses.UI.Fragments.Admin.ContactsFragment;
import com.example.courses.UI.Fragments.Employee.MoreEmployeeFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class EmployeeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth ;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView userName;
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        userName = findViewById(R.id.userNamEmp);
        imageView = findViewById(R.id.userImageEmp);
        bottomNavigationView = findViewById(R.id.bottom_nav_employee);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        auth = FirebaseAuth.getInstance();
        Fragment selected = null;
        Bundle bundle = new Bundle();
        bundle.putInt("frame",R.id.frame_layout_employee);
        selected = new ContactsFragment();
        selected.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_employee, selected).commit();
        Constants.current=1;

        getData();
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
                    selected = new ContactPageFragment();
                    selected.setArguments(bundle);
                    select = 3;
                }
                break;

                case R.id.more: {
                    selected = new MoreEmployeeFragment();
                    select = 6;
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
    public void getData(){
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference= database.getReference("Employees");
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    userName.setText(name);
                    String gender = snapshot.child("gender").getValue().toString();
                    if(gender.equals("ذكر")){
                        imageView.setImageResource(R.drawable.ic_person_men);
                    }else {
                        imageView.setImageResource(R.drawable.ic_person_weman);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
    }


}