package com.example.courses.UI.Fragments.Employee;

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
import com.example.courses.UI.Fragments.Trainer.AddCommentFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));
        trainees.add(new Trainee("زين خالد","all","أكديمي ",22));

        traineeAdapter = new TraineeAdapter(getContext(),trainees);
        recyclerView.setAdapter(traineeAdapter);
        traineeAdapter.notifyDataSetChanged();

        AddTrainees = getActivity().findViewById(R.id.add_trainee);
        AddTrainees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), new Add_traineeFragment()).addToBackStack(null).commitAllowingStateLoss();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainee, container, false);
    }
}