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
import com.example.courses.UI.Fragments.Admin.EmployeeFragment;
import com.example.courses.UI.Fragments.Admin.MoreFragment;
import com.example.courses.UI.Fragments.Admin.CoursesFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userName = findViewById(R.id.userName);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new EmployeeFragment()).commit();
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

    public void getData(){
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference= database.getReference("Admins");
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    userName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}