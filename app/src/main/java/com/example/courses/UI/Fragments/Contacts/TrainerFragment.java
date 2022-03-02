package com.example.courses.UI.Fragments.Contacts;

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
        trainers.add(new Trainer("علي محمود","ذكر","alia@gmail.com ","فيزياء","سعودي",2));
        trainers.add(new Trainer("احمد محمود","ذكر","alia@gmail.com","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمد","ذكر","alia@gmail.com","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","alia@gmail.com ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","alia@gmail.com ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","alia@gmail.com ","فيزياء","سعودي",2));
        trainerAdapter = new TrainerAdapter(getContext(),trainers);
        trainerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(trainerAdapter);

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
}