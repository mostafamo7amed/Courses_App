package com.example.courses.UI.Fragments.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Adapters.TraineeAdapter;
import com.example.Models.Trainee;
import com.example.courses.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TraineeFragment extends Fragment {

    RecyclerView recyclerView;
    TraineeAdapter traineeAdapter;
    ArrayList<Trainee> trainees;
    FloatingActionButton AddTrainees;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.RecyclerTrainee);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trainees= new ArrayList<>();

        traineeAdapter = new TraineeAdapter(getContext(),trainees,false);
        getTrainees();

        traineeAdapter.setOnItemClickListener(new TraineeAdapter.OnItemClickListener() {
            @Override
            public void onItemEdit(String UID) {
                editTrainee(UID);
            }
        });

        AddTrainees = getActivity().findViewById(R.id.add_trainee);
        AddTrainees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selected = null;
                Bundle bundle = new Bundle();
                bundle.putInt("frame",getArguments().getInt("frame"));
                selected = new Add_traineeFragment();
                selected.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"),selected ).addToBackStack(null).commitAllowingStateLoss();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainee, container, false);
    }


    public void getTrainees(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trainees");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainees.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                trainees.add(new Trainee(snapshot.child("name").getValue().toString(),
                                        snapshot.child("email").getValue().toString(),
                                        snapshot.child("educationLevel").getValue().toString(),
                                        snapshot.child("age").getValue().toString(),
                                        snapshot.child("uid").getValue().toString()
                                ));

                            }
                            recyclerView.setAdapter(traineeAdapter);
                            traineeAdapter.notifyDataSetChanged();
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

    public void editTrainee(String uid){
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        bundle.putInt("frame",getArguments().getInt("frame"));
        fragment = new EditTraineeFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), fragment).addToBackStack(null).commitAllowingStateLoss();
    }

}