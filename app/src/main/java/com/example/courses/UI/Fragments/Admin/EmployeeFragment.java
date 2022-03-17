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

import com.example.Adapters.EmployeeAdapter;
import com.example.Adapters.TrainerAdapter;
import com.example.Models.Employee;
import com.example.Models.Trainer;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Contacts.Add_TrainerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class EmployeeFragment extends Fragment {

    FloatingActionButton addEmployee;
    EmployeeAdapter employeeAdapter;
    ArrayList<Employee> employees;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.RecyclerEmployee);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        employees = new ArrayList<>();
        employees.add(new Employee("ali@gamil.com","علي محمد" ,"موظف عام",23,2));
        employees.add(new Employee("ali@gamil.com","علي محمد" ,"موظف عام",23,2));
        employees.add(new Employee("ali@gamil.com","علي محمد" ,"موظف عام",23,2));
        employees.add(new Employee("ali@gamil.com","علي محمد" ,"موظف عام",23,2));
        employees.add(new Employee("ali@gamil.com","علي محمد" ,"موظف عام",23,2));
        employees.add(new Employee("ali@gamil.com","علي محمد" ,"موظف عام",23,2));


        employeeAdapter = new EmployeeAdapter(getContext(),employees);
        employeeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(employeeAdapter);

        addEmployee = getActivity().findViewById(R.id.add_Employee);
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, new AddEmployeeFragment()).addToBackStack(null).commitAllowingStateLoss();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }
}