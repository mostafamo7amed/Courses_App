package com.example.courses.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.Models.TrainingProvider;
import com.example.courses.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateTrainingProviderAccountActivity extends AppCompatActivity {

    EditText name,phone,region, commerce , number;
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
    TrainingProvider provider;
    String currentUser_id;
    UploadTask uploadTask;
    private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training_provider_account);

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
    }
    public void initialization(){
        name = findViewById(R.id.cc_name);
        phone = findViewById(R.id.cc_phone);
        region = findViewById(R.id.cc_region);
        commerce = findViewById(R.id.cc_commerce);
        loading = findViewById(R.id.cc_progress);
        gender = findViewById(R.id.cc_gender);
        number = findViewById(R.id.cc_number);
        photo = findViewById(R.id.cc_picture);
        save = findViewById(R.id.cc_save);
        provider = new TrainingProvider();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        currentUser_id=auth.getCurrentUser().getUid();
    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(photo);
            }
        }catch (Exception ex) {
            Toast.makeText(this, "error"+ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount() {
        String u_name =name.getText().toString();
        String u_phone=phone.getText().toString();
        String u_commerce=commerce.getText().toString();
        String u_number=number.getText().toString();
        String u_region=region.getText().toString();
        String u_email=getIntent().getStringExtra("email");
        String u_gander;

        int selectedID = gender.getCheckedRadioButtonId();
        if (selectedID == R.id.male) {
            u_gander = "ذكر";
        }else {
            u_gander = "أنثى";
        }

        documentReference=db.collection("Training Provider").document(currentUser_id);
        storageReference= FirebaseStorage.getInstance().getReference("Profile images");
        databaseReference=database.getReference("Training Provider");

        if(!TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_number) && !TextUtils.isEmpty(u_commerce) && !TextUtils.isEmpty(u_email) && !TextUtils.isEmpty(u_region) && !TextUtils.isEmpty(u_phone) && imageUri!=null && gender.getCheckedRadioButtonId() != -1)
        {
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
                        profile.put("gender",u_gander);
                        profile.put("uid",currentUser_id);
                        profile.put("image",imageChild);
                        profile.put("type","Contacts");

                        provider.setName(u_name);
                        provider.setUid(currentUser_id);
                        provider.setContact_number(Integer.parseInt(u_number));
                        provider.setEmail(u_email);
                        provider.setPhone(u_phone);
                        provider.setGender(u_gander);
                        provider.setRegion(u_region);
                        provider.setType("Contacts");
                        provider.setCommercial_register(u_commerce);
                        if (downloadUri != null) {
                            provider.setImage(downloadUri.toString());
                        }
                        databaseReference.child(currentUser_id).setValue(provider)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loading.setVisibility(View.INVISIBLE);
                                        Toast.makeText(CreateTrainingProviderAccountActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        documentReference.set(profile)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(CreateTrainingProviderAccountActivity.this, "تم إنشاء الحساب", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreateTrainingProviderAccountActivity.this,ContactsActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(CreateTrainingProviderAccountActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                }
            });

        }else {
            Toast.makeText(this, "الرجاء إدخال كافة البيانات ", Toast.LENGTH_SHORT).show();
        }

    }






}