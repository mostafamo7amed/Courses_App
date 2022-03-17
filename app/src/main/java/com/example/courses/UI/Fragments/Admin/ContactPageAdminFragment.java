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

import com.example.Adapters.ContactPageAdapter;
import com.example.Models.Comments;
import com.example.courses.R;

import java.util.ArrayList;

public class ContactPageAdminFragment extends Fragment {
    ContactPageAdapter contactPageAdapter;
    ArrayList<Comments> comments ;
    RecyclerView recyclerView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.RecyclerContactPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        comments = new ArrayList<>();
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));

        contactPageAdapter = new ContactPageAdapter(getContext(),comments);
        contactPageAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(contactPageAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_page_admin, container, false);
    }
}