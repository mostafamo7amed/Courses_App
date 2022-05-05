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
import com.example.courses.UI.Fragments.Trainee.CoursesTraineeFragment;
import com.example.courses.UI.Fragments.Trainee.MyCoursesTFragment;
import com.example.courses.UI.Fragments.Trainee.TraineeProfileFragment;
import com.example.courses.UI.Fragments.Trainer.ContactPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class TraineeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    TextView userName;
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee);

        userName = findViewById(R.id.userNameTr);
        imageView = findViewById(R.id.userImageTr);
        bottomNavigationView = findViewById(R.id.bottom_nav_trainee);
        auth = FirebaseAuth.getInstance();
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_trainee, new CoursesTraineeFragment()).commit();
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
                    selected = new ContactPageFragment();
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
                    auth.signOut();
                    startActivity(new Intent(TraineeActivity.this,StartActivity.class));
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

    public void getData(){
        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        databaseReference= database.getReference("Trainees");
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