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
import com.example.courses.UI.Fragments.TrainingProvider.ContactProfileFragment;
import com.example.courses.UI.Fragments.Admin.CoursesFragment;
import com.example.courses.UI.Fragments.TrainingProvider.TrainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView userName;
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        userName = findViewById(R.id.userNameCon);
        imageView = findViewById(R.id.userImageCon);
        bottomNavigationView = findViewById(R.id.bottom_nav_cont);
        auth = FirebaseAuth.getInstance();
        bottomNavigationView.setOnItemSelectedListener(OnSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new TrainerFragment()).commit();
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
                case R.id.trainer_cont: {
                    selected = new TrainerFragment();
                    select =1;
                }
                break;
                case R.id.courses_cont: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",R.id.frame_layout_cont);
                    selected = new CoursesFragment();
                    selected.setArguments(bundle);
                    select = 2;
                }
                break;
                case R.id.profile_cont: {
                    selected = new ContactProfileFragment();
                    select = 3;
                }
                break;
                case R.id.exit_cont: {
                    auth.signOut();
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

    public void getData(){
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference= database.getReference("Training Provider");
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    userName.setText(name);
                    String gender = snapshot.child("image").getValue().toString();
                    Picasso.get().load(gender).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}