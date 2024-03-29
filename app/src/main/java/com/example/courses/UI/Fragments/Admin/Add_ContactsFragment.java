package com.example.courses.UI.Fragments.Admin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Models.TrainingProvider;
import com.example.courses.R;
import com.example.courses.SharedPref;
import com.example.courses.UI.Fragments.TrainingProvider.TrainerFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class Add_ContactsFragment extends Fragment {

    EditText name1,name2,phone,region, commerce ,email,password;
    TextView name;
    ProgressBar loading;
    CircleImageView photo;
    RadioGroup gender;
    Uri imageUri;
    AppCompatButton save;
    StorageReference storageReference;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    TrainingProvider contacts;
    UploadTask uploadTask;
    FirebaseAuth firebaseAuth;
    String numberContact;
    private static final int PICK_IMAGE=1;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialization();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
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


        name1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String n1 = name1.getText().toString();
                String n2 = name2.getText().toString();

                name.setText(n1+" "+n2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        name2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String n1 = name1.getText().toString();
                String n2 = name2.getText().toString();

                name.setText(n1+" "+n2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add__contacts, container, false);
    }

    public void initialization(){
        name = getActivity().findViewById(R.id.cc_name_man);
        name1 = getActivity().findViewById(R.id.cc_name1_man);
        name2 = getActivity().findViewById(R.id.cc_name2_man);
        phone = getActivity().findViewById(R.id.cc_phone_man);
        region = getActivity().findViewById(R.id.cc_region_man);
        commerce = getActivity().findViewById(R.id.cc_commerce_man);
        loading = getActivity().findViewById(R.id.cc_progress_man);
        photo = getActivity().findViewById(R.id.cc_picture_man);
        email = getActivity().findViewById(R.id.cc_email_man);
        password = getActivity().findViewById(R.id.cc_password_man);
        save = getActivity().findViewById(R.id.cc_save_man);
        gender = getActivity().findViewById(R.id.cc_gender_man);
        firebaseAuth = FirebaseAuth.getInstance();
        contacts = new TrainingProvider();
        contactNumber();

    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver= getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void createAccount() {
        String u_name =name.getText().toString();
        String u_phone=phone.getText().toString();
        String u_commerce=commerce.getText().toString();
        String u_number=numberContact;
        String u_region=region.getText().toString();
        String u_email=email.getText().toString();
        String u_password = password.getText().toString();
        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_number) && !TextUtils.isEmpty(u_commerce) && !TextUtils.isEmpty(u_email) && !TextUtils.isEmpty(u_region) && !TextUtils.isEmpty(u_phone) && !TextUtils.isEmpty(u_password) && imageUri!=null && gender.getCheckedRadioButtonId()!= -1)
        {
            firebaseAuth.createUserWithEmailAndPassword(u_email,u_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        putData(firebaseUser.getUid());
                    } else {
                        loading.setVisibility(View.INVISIBLE);
                        String error = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(getContext(), "error :" + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else {
            Toast.makeText(getContext(), "الرجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE || resultCode == Activity.RESULT_OK || data != null || data.getData() != null) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(photo);
            }
        }catch (Exception ex) {
            Toast.makeText(getContext(), "error"+ex, Toast.LENGTH_SHORT).show();
        }
    }

    public void putData(String userId){
        String u_name =name.getText().toString();
        String u_phone=phone.getText().toString();
        String u_commerce=commerce.getText().toString();
        String u_number=numberContact;
        String u_region=region.getText().toString();
        String u_email=email.getText().toString();

        String u_gander;

        int selectedID = gender.getCheckedRadioButtonId();
        if (selectedID == R.id.male) {
            u_gander = "ذكر";
        }else {
            u_gander = "أنثى";
        }

        documentReference=db.collection("Training Provider").document(userId);
        storageReference= FirebaseStorage.getInstance().getReference("Profile images");
        databaseReference=database.getReference("Training Provider");


        String imageChild =System.currentTimeMillis()+"."+getFileExt(imageUri);
        loading.setVisibility(View.VISIBLE);
        final StorageReference reference=storageReference.child(imageChild);
        uploadTask= reference.putFile(imageUri);

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
                    profile.put("name",u_name);
                    profile.put("number",u_number);
                    if (downloadUri != null) {
                        profile.put("uri",downloadUri.toString());
                    }
                    profile.put("email",u_email);
                    profile.put("commerce",u_commerce);
                    profile.put("region",u_region);
                    profile.put("phone",u_phone);
                    profile.put("uid",userId);
                    profile.put("gender",u_gander);
                    profile.put("image",imageChild);
                    profile.put("type","Contacts");

                    contacts.setName(u_name);
                    contacts.setUid(userId);
                    contacts.setContact_number(Integer.parseInt(u_number));
                    contacts.setEmail(u_email);
                    contacts.setPhone(u_phone);
                    contacts.setGender(u_gander);
                    contacts.setRegion(u_region);
                    contacts.setType("Contacts");
                    contacts.setCommercial_register(u_commerce);
                    if (downloadUri != null) {
                        contacts.setImage(downloadUri.toString());
                    }
                    databaseReference.child(userId).setValue(contacts)
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
                                    loginToSystem();
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
    }
    public void loginToSystem(){
        SharedPref sharedPref = new SharedPref(getContext());
        String email = sharedPref.loadEmail();
        String pass = sharedPref.loadPassword();
        if(!email.isEmpty() && !pass.isEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "تم إنشاء الحساب مزود التدريبات", Toast.LENGTH_SHORT).show();
                    Fragment selected = null;
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",getArguments().getInt("frame"));
                    selected = new ContactsFragment();
                    selected.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"),selected ).addToBackStack(null).commitAllowingStateLoss();

                }
            });
        }

    }

    public void contactNumber(){
        String mill = ""+System.currentTimeMillis();
        numberContact = mill.substring(mill.length()-4);
    }

}