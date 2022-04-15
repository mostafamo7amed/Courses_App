package com.example.courses.UI.Fragments.Trainer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Models.Comments;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCommentFragment extends Fragment {
    EditText commentET;
    ProgressBar loading;
    AppCompatButton addComment;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String currentUser_id;
    String Email="",Name="";
    Comments comments;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialization();
        AddComment();

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                String commentText = commentET.getText().toString();
                uploadComment(commentText,Email,Name);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_comment, container, false);
    }


    public void initialization(){
        commentET = getActivity().findViewById(R.id.comments);
        addComment = getActivity().findViewById(R.id.addComments);
        auth = FirebaseAuth.getInstance();
        currentUser_id = auth.getCurrentUser().getUid();
        comments = new Comments();
        loading = getActivity().findViewById(R.id.comment_progress);

    }

    public void AddComment(){

        String table ;
        int user = getArguments().getInt("frame");
        if(user == R.id.frame_layout_trainer){
            table = "Trainers";
            getDate(table);
        }else if (user == R.id.frame_layout_trainee){
            table = "Trainees";
            getDate(table);
        }else if(user == R.id.frame_layout_cont){
            table = "Contacts";
            getDate(table);
        }else if (user == R.id.frame_layout_employee){
            table = "Employees";
            getDate(table);
        }else if (user == R.id.frame_layout){
            table = "Admins";
            Name = "مدير النظام";
            getDate(table);
        }
    }

    public void uploadComment(String comment  , String email , String name){
        Calendar cDate =Calendar.getInstance();
        SimpleDateFormat currentDate =new SimpleDateFormat("dd-MMMM-yyyy");
        final String saveDate=currentDate.format(cDate.getTime());

        Calendar cTime =Calendar.getInstance();
        SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss");
        final String saveTime=currentTime.format(cTime.getTime());


        databaseReference=database.getReference("All Comments");
        String child=databaseReference.push().getKey();



        if (!TextUtils.isEmpty(comment)) {
            comments.setComment(comment);
            comments.setEmail(email);
            comments.setName(name);
            comments.setDate(saveDate);
            comments.setTime(saveTime);
            comments.setKey(child);
            databaseReference.child(child).setValue(comments).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "تم التعليق", Toast.LENGTH_SHORT).show();
                    commentET.setText("");
                    Fragment selected;
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",getArguments().getInt("frame"));
                    selected = new ContactPageFragment();
                    selected.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"),selected).commit();

                }
            });

        } else {
            Toast.makeText(getContext(), "لا يمكن ان يكون التعليق فارغ", Toast.LENGTH_SHORT).show();
        }
    }

    public void getDate(String table){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId= null;
        if (user != null) {
            currentUserId = user.getUid();
        }
        DocumentReference reference;
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        if (currentUserId != null) {
            reference = firestore.collection(table).document(currentUserId);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    try {
                        if (task.getResult().exists()) {
                            Email =task.getResult().getString("email");
                            Name =task.getResult().getString("name");
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

    @Override
    public void onStart() {
        super.onStart();
    }
}