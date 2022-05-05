package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.courses.Constants;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Trainer.ContactPageFragment;
import com.example.courses.UI.Fragments.Trainer.CoursesTrainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    TextView userName;
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

        userName = findViewById(R.id.userNameTrr);
        imageView = findViewById(R.id.userImageTrr);
        bottomNavigationView = findViewById(R.id.bottom_nav_trainer);
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        auth = FirebaseAuth.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_trainer, new CoursesTrainerFragment()).commit();
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
                case R.id.courses_trainer: {
                    selected = new CoursesTrainerFragment();
                    select =1;
                }
                break;
                case R.id.contactPage_trainer: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_trainer);
                    selected = new ContactPageFragment();
                    selected.setArguments(bundle);
                    select = 2;
                }
                break;
                case R.id.exit_trainer: {
                    auth.signOut();
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

    public void getData(){
        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        databaseReference= database.getReference("Trainers");
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
}