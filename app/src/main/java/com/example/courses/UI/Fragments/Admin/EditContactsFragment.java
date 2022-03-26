package com.example.courses.UI.Fragments.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Models.Contacts;
import com.example.courses.R;
import com.example.courses.UI.Activities.CreateContactAccountActivity;
import com.example.courses.UI.Fragments.Employee.ContactsFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditContactsFragment extends Fragment {
    EditText name ,phone ,commerce,region,number;
    AppCompatButton editSave;
    ProgressBar loading;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Contacts contacts;
    String currentUser_id ,Email ,Image ,Url;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        getDate();

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editContact();
            }
        });
    }

    public void initialization(){
        name = getActivity().findViewById(R.id.edit_name_con);
        phone = getActivity().findViewById(R.id.edit_phone_con);
        commerce = getActivity().findViewById(R.id.edit_commerce_con);
        region = getActivity().findViewById(R.id.edit_region_con);
        number = getActivity().findViewById(R.id.edit_number_con);
        editSave = getActivity().findViewById(R.id.edit_add_con);
        loading = getActivity().findViewById(R.id.edit_progress_con);
        contacts = new Contacts();
        currentUser_id = getArguments().getString("uid");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_contacts, container, false);
    }

    public void getDate(){
        String currentUserId= currentUser_id;
        DocumentReference reference;
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        if (currentUserId != null) {
            reference = firestore.collection("Contacts").document(currentUserId);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    try {
                        if (task.getResult().exists()) {
                            String u_names = task.getResult().getString("name");
                            String u_number = task.getResult().getString("number");
                            String u_commerce = task.getResult().getString("commerce");
                            String u_phones = task.getResult().getString("phone");
                            String u_region = task.getResult().getString("region");

                            ////
                            Email =task.getResult().getString("email");
                            Image =task.getResult().getString("image");
                            Url = task.getResult().getString("uri");
                            /////
                            name.setText(u_names);
                            number.setText(u_number);
                            commerce.setText(u_commerce);
                            phone.setText(u_phones);
                            region.setText(u_region);


                        }
                    } catch (NullPointerException nullPointerException) {
                        Toast.makeText(getActivity(), "" + nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            getActivity().finish();
            System.exit(0);
        }
    }

    public void editContact(){
        documentReference=db.collection("Contacts").document(currentUser_id);
        databaseReference=database.getReference("Contacts");

        if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(phone.getText().toString()) && !TextUtils.isEmpty(commerce.getText().toString()) && !TextUtils.isEmpty(region.getText().toString()))
        {
            loading.setVisibility(View.VISIBLE);

                Map<String ,String> profile=new HashMap<>();
                profile.put("name",name.getText().toString());
                profile.put("number",number.getText().toString());
                profile.put("uri",Url);
                profile.put("email",Email);
                profile.put("commerce",commerce.getText().toString());
                profile.put("phone",phone.getText().toString());
                profile.put("uid",currentUser_id);
                profile.put("image",Image);
                profile.put("region",region.getText().toString());

                contacts.setName(name.getText().toString());
                contacts.setUid(currentUser_id);
                contacts.setContact_number(Integer.parseInt(number.getText().toString()));
                contacts.setEmail(Email);
                contacts.setPhone(phone.getText().toString());
                contacts.setRegion(region.getText().toString());
                contacts.setCommercial_register(commerce.getText().toString());
                contacts.setImage(Url);
                databaseReference.child(currentUser_id).setValue(contacts)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                documentReference.set(profile)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "تم تعديل الحساب", Toast.LENGTH_SHORT).show();
                                Fragment fragment;
                                Bundle bundle = new Bundle();
                                bundle.putInt("frame", getArguments().getInt("frame"));
                                fragment = new ContactsFragment();
                                fragment.setArguments(bundle);
                                getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), fragment).commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



        }else {
            Toast.makeText(getContext(), "برجاء إدخال كافة البيانات", Toast.LENGTH_SHORT).show();
        }
    }
}