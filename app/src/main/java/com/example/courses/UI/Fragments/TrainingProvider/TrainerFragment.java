package com.example.courses.UI.Fragments.TrainingProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Adapters.TrainerAdapter;
import com.example.Models.Trainer;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String currentUserID;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.RecyclerTrainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trainers = new ArrayList<>();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        databaseReference=database.getReference("ContactTrainers").child(currentUserID);
        databaseReference2=database.getReference("Trainers");
        trainerAdapter = new TrainerAdapter(getContext(),trainers);
        getTrainers();


        addTrainer  = getActivity().findViewById(R.id.add_trainer);
        addTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_cont, new Add_TrainerFragment()).addToBackStack(null).commitAllowingStateLoss();

            }
        });

        trainerAdapter.setOnItemClickListener(new TrainerAdapter.OnItemClickListener() {
            @Override
            public void onItemDeleted(String uid) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("سيتم حذف المتدرب !");
                builder.setTitle("تنبيه !");
                builder.setCancelable(false);
                builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child(uid).removeValue();
                        databaseReference2.child(uid).removeValue();
                    }
                });
                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainer, container, false);
    }
    public void getTrainers(){
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ContactTrainers").child(currentUserId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainers.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                trainers.add(new Trainer(
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("gender").getValue().toString(),
                                        snapshot.child("email").getValue().toString(),
                                        snapshot.child("specialization").getValue().toString(),
                                        snapshot.child("nationality").getValue().toString(),
                                        snapshot.child("uid").getValue().toString(),
                                        Integer.parseInt(snapshot.child("contact_number").getValue().toString())
                                ));
                            }
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