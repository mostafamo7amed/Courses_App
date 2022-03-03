package com.example.courses.UI.Fragments.Trainee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Adapters.ContactsAdapter;
import com.example.Models.Contacts;
import com.example.courses.R;

import java.util.ArrayList;

public class ViewContactFragment extends Fragment {

    ContactsAdapter contactsAdapter;
    RecyclerView recyclerContacts;
    ArrayList<Contacts> contacts;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerContacts = getActivity().findViewById(R.id.RecyclerContacts);
        recyclerContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        contacts = new ArrayList<>();
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));
        contacts.add(new Contacts("الاكيديمية التعليمية","academy@gmail.com","جدة","21345",2));

        contactsAdapter = new ContactsAdapter(getContext(),contacts);
        contactsAdapter.notifyDataSetChanged();
        recyclerContacts.setAdapter(contactsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_contact, container, false);
    }
}