package com.example.courses.UI.Fragments.Contacts;

import android.app.Activity;
import android.content.ContentResolver;
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
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Models.Contacts;
import com.example.Models.Trainee;
import com.example.courses.R;
import com.example.courses.UI.Activities.ContactsActivity;
import com.example.courses.UI.Activities.CreateContactAccountActivity;
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

public class ContactProfileFragment extends Fragment {

    EditText name ,phone ,commerce,email,region,number;
    ImageButton editProfile;
    AppCompatButton editSave;
    ProgressBar loading;
    CardView cardView;
    Uri imageUri;
    UploadTask uploadTask;
    CircleImageView photo;
    StorageReference storageReference;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Contacts contacts;
    String currentUser_id , UrlDeleted ,Email ,Number;
    private static final int PICK_IMAGE=1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialization();

        getDate();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                phone.setEnabled(true);
                commerce.setEnabled(true);
                region.setEnabled(true);
                photo.setEnabled(true);
                photo.setClickable(true);
                photo.setFocusable(true);
                cardView.setVisibility(View.VISIBLE);
                editSave.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "يمكنك التعديل علي عناصر محدودة", Toast.LENGTH_SHORT).show();
            }
        });

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_profile, container, false);
    }

    public void initialization(){
        name = getActivity().findViewById(R.id.cp_name);
        phone = getActivity().findViewById(R.id.cp_phone);
        commerce = getActivity().findViewById(R.id.cp_commerce);
        email = getActivity().findViewById(R.id.cp_email);
        region = getActivity().findViewById(R.id.cp_region);
        number = getActivity().findViewById(R.id.cp_number);
        editProfile = getActivity().findViewById(R.id.edit_profile_contact);
        editSave = getActivity().findViewById(R.id.cp_save);
        loading = getActivity().findViewById(R.id.cp_progress);
        cardView = getActivity().findViewById(R.id.add_image);
        photo = getActivity().findViewById(R.id.image_contact);
        contacts = new Contacts();
    }

    public void getDate(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId= null;
        if (user != null) {
            currentUserId = user.getUid();
        }
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
                            String u_email = task.getResult().getString("email");
                            String urls = task.getResult().getString("uri");
                            String u_region = task.getResult().getString("region");

                            ////
                            UrlDeleted = task.getResult().getString("image");
                            Email =task.getResult().getString("email");
                            Number =task.getResult().getString("number");

                            /////
                            Picasso.get().load(urls).into(photo);
                            name.setText(u_names);
                            number.setText(u_number);
                            email.setText(u_email);
                            commerce.setText(u_commerce);
                            phone.setText(u_phones);
                            region.setText(u_region);


                        } else {
                            Intent intent = new Intent(getActivity(), CreateContactAccountActivity.class);
                            startActivity(intent);
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

    public void editProfile(){


        FirebaseAuth auth=FirebaseAuth.getInstance();
        currentUser_id=auth.getCurrentUser().getUid();

        documentReference=db.collection("Contacts").document(currentUser_id);
        storageReference= FirebaseStorage.getInstance().getReference("Profile images");
        databaseReference=database.getReference("Contacts");

        if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(phone.getText().toString()) && !TextUtils.isEmpty(commerce.getText().toString()) && !TextUtils.isEmpty(region.getText().toString()) && imageUri!=null)
        {
            String image_current = System.currentTimeMillis()+"."+getFileExt(imageUri);
            loading.setVisibility(View.VISIBLE);
            final StorageReference reference=storageReference.child(image_current);
            uploadTask= reference.putFile(imageUri);

            final StorageReference reference2=storageReference.child(UrlDeleted);
            reference2.delete();

            Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUri=task.getResult();
                        Map<String ,String> profile=new HashMap<>();
                        profile.put("name",name.getText().toString());
                        profile.put("number",Number);
                        if (downloadUri != null) {
                            profile.put("uri",downloadUri.toString());
                        }
                        profile.put("email",Email);
                        profile.put("commerce",commerce.getText().toString());
                        profile.put("phone",phone.getText().toString());
                        profile.put("uid",currentUser_id);
                        profile.put("image",image_current);
                        profile.put("region",region.getText().toString());

                        contacts.setName(name.getText().toString());
                        contacts.setUid(currentUser_id);
                        contacts.setContact_number(Integer.parseInt(Number));
                        contacts.setEmail(Email);
                        contacts.setPhone(phone.getText().toString());
                        contacts.setRegion(region.getText().toString());
                        contacts.setCommercial_register(commerce.getText().toString());
                        if (downloadUri != null) {
                            contacts.setImage(downloadUri.toString());
                        }
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
                                        name.setEnabled(false);
                                        phone.setEnabled(false);
                                        commerce.setEnabled(false);
                                        region.setEnabled(false);
                                        photo.setEnabled(false);
                                        photo.setClickable(false);
                                        photo.setFocusable(false);
                                        cardView.setVisibility(View.GONE);
                                        editSave.setVisibility(View.INVISIBLE);
                                        loading.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "تم تعديل الحساب", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });

        }else {
            Toast.makeText(getContext(), "برجاء إدخال كافة البيانات", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver= getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE || resultCode == Activity.RESULT_OK || data != null || data.getData() != null) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(photo);
            }
        }catch (Exception ex)
        {
            Toast.makeText(getContext(), "error"+ex, Toast.LENGTH_SHORT).show();
        }

    }


}