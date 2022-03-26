package com.example.courses.UI.Fragments.Employee;

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
import com.example.courses.UI.Activities.StartActivity;
import com.example.courses.UI.Fragments.Admin.AnalysisFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MoreEmployeeFragment extends Fragment {
    TextView logout,profile , trainee;
    FirebaseAuth auth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile = getActivity().findViewById(R.id.profile);
        logout = getActivity().findViewById(R.id.emp_logout);
        trainee = getActivity().findViewById(R.id.trainee);
        auth = FirebaseAuth.getInstance();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_employee, new EmployeeProfileFragment()).addToBackStack(null).commitAllowingStateLoss();

            }
        });
        trainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selected;
                Bundle bundle = new Bundle();
                bundle.putInt("frame",R.id.frame_layout_employee);
                selected = new TraineeFragment();
                selected.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout_employee, selected).addToBackStack(null).commitAllowingStateLoss();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getContext(), StartActivity.class));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_employee, container, false);
    }
}