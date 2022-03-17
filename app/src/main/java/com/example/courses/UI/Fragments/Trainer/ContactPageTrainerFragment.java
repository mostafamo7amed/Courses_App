package com.example.courses.UI.Fragments.Trainer;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactPageTrainerFragment extends Fragment {

    FloatingActionButton addComment;
    ContactPageAdapter contactPageStartAdapter;
    RecyclerView recyclerView;
    ArrayList<Comments> comments;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addComment = getActivity().findViewById(R.id.add_comment);
        recyclerView = getActivity().findViewById(R.id.RecyclerContactPage_Trainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        comments = new ArrayList<>();
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        comments.add(new Comments("alimahmoud@gmail.com","علي محمود","هل في شهادة في نهاية الدورة؟",3,4));
        contactPageStartAdapter = new ContactPageAdapter(getContext(),comments);
        contactPageStartAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(contactPageStartAdapter);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), new AddCommentFragment()).addToBackStack(null).commitAllowingStateLoss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_page_trainer, container, false);
    }
}