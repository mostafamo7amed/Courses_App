package com.example.courses.UI.Fragments.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.courses.R;
import com.example.courses.UI.Activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MoreFragment extends Fragment {
    TextView contact,trainee,analysis,logout;
    FirebaseAuth auth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contact = getActivity().findViewById(R.id.manage_contact);
        trainee = getActivity().findViewById(R.id.manage_trainee);
        analysis = getActivity().findViewById(R.id.manage_analysis);
        logout = getActivity().findViewById(R.id.manage_logout);
        auth = FirebaseAuth.getInstance();

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreFrag(new ContactsFragment());
            }
        });

        trainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreFrag(new TraineeFragment());
            }
        });
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreFrag(new AnalysisFragment());
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }
    public void moreFrag(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putInt("frame",R.id.frame_layout);
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }
}