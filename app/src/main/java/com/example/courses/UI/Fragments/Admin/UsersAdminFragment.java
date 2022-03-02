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

import com.example.Adapters.UsersAdapter;
import com.example.Models.Trainer;
import com.example.courses.R;

import java.util.ArrayList;


public class UsersAdminFragment extends Fragment {
    UsersAdapter usersAdapter;
    ArrayList<Trainer> trainers ;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.RecyclerUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trainers = new ArrayList<>();
        trainers.add(new Trainer("علي محمود","ذكر","مدرب ","فيزياء","سعودي",2));
        trainers.add(new Trainer("احمد محمود","ذكر","مدرب ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمد","ذكر","متدرب ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","متدرب ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","مدرب ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","واجهة ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","متدرب ","فيزياء","سعودي",2));
        trainers.add(new Trainer("علي محمود","ذكر","واجهة ","فيزياء","سعودي",2));
        usersAdapter = new UsersAdapter(getContext(),trainers);
        usersAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(usersAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users_admin, container, false);
    }
}