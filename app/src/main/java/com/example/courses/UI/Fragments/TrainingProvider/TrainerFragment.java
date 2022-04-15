package com.example.courses.UI.Fragments.TrainingProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Adapters.TrainerAdapter;
import com.example.Models.Trainer;
import com.example.courses.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrainerFragment extends Fragment {

    FloatingActionButton addTrainer;
    TrainerAdapter trainerAdapter;
    ArrayList<Trainer> trainers;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.RecyclerTrainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trainers = new ArrayList<>();

        getTrainers();


        addTrainer  = getActivity().findViewById(R.id.add_trainer);
        addTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new Add_TrainerFragment()).addToBackStack(null).commitAllowingStateLoss();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainer, container, false);
    }
    public void getTrainers(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trainers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            trainers.add(new Trainer(
                                    snapshot.child("name").getValue().toString(),
                                    snapshot.child("gender").getValue().toString(),
                                    snapshot.child("email").getValue().toString(),
                                    snapshot.child("specialization").getValue().toString(),
                                    snapshot.child("nationality").getValue().toString(),
                                    snapshot.child("uid").getValue().toString(),
                                    Integer.parseInt(snapshot.child("contact_number").getValue().toString())
                            ));
                            trainerAdapter = new TrainerAdapter(getContext(),trainers);
                            trainerAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(trainerAdapter);

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