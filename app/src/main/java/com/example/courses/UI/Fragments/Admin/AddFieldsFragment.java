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

import com.example.Adapters.FieldsAdapter;
import com.example.Models.Fields;
import com.example.courses.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddFieldsFragment extends Fragment {
    FloatingActionButton addFields;
    FieldsAdapter fieldsAdapter;
    ArrayList<Fields> fields ;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerFields);
        addFields = getActivity().findViewById(R.id.add_fields);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fields = new ArrayList<>();
        fields.add(new Fields("الفيزياء"));
        fields.add(new Fields("كيمياء حيوية"));
        fields.add(new Fields("الكيمياء"));
        fields.add(new Fields("علوم الحاسب"));
        fields.add(new Fields("الاحياء"));

        fieldsAdapter = new FieldsAdapter(getContext(),fields);
        fieldsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(fieldsAdapter);
        addFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, new fieldsFragment()).addToBackStack(null).commitAllowingStateLoss();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_fields, container, false);
    }
}