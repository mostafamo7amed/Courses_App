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
import com.example.Models.Employee;
import com.example.courses.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


        employeeAdapter = new EmployeeAdapter(getContext(),employees);
        getEmployees();

        employeeAdapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemEdit(String position) {
                editEmployee(position);
            }
        });

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

    public void getEmployees(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employees");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employees.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                employees.add(new Employee(snapshot.child("email").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("position").getValue().toString(),
                                        snapshot.child("uid").getValue().toString(),
                                        snapshot.child("age").getValue().toString(),
                                        Integer.parseInt(snapshot.child("number").getValue().toString())

                                ));

                            }
                            employeeAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(employeeAdapter);
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

    public void editEmployee(String uid){
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        fragment = new EditEmployeeFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commitAllowingStateLoss();
    }


}