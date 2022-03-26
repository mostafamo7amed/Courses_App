package com.example.courses.UI.Fragments.Trainer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Adapters.ContactPageAdapter;
import com.example.Models.Comments;
import com.example.courses.R;
import com.example.courses.UI.Activities.SplashActivity;
import com.example.courses.UI.Activities.StartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactPageFragment extends Fragment {

    FloatingActionButton addComment;
    ContactPageAdapter contactPageAdapter;
    RecyclerView recyclerView;
    ArrayList<Comments> comments;
    DatabaseReference databaseReference;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialization();

        getAllComments();

        contactPageAdapter = new ContactPageAdapter(getContext(),comments);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment selected;
                Bundle bundle = new Bundle();
                bundle.putInt("frame",getArguments().getInt("frame"));
                selected = new AddCommentFragment();
                selected.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), selected).addToBackStack(null).commitAllowingStateLoss();
            }
        });
    }

    public void initialization(){
        addComment = getActivity().findViewById(R.id.add_comment);
        recyclerView = getActivity().findViewById(R.id.RecyclerContactPage_Trainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        comments = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("All Comments");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_page, container, false);
    }

    public void getAllComments(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                comments.add(new Comments(snapshot.child("email").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("comment").getValue().toString(),
                                        snapshot.child("time").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("key").getValue().toString()
                                ));


                            }

                            contactPageAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(contactPageAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}