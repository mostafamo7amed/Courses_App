package com.example.courses.UI.Fragments.Admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.Models.Course;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditCoursesFragment extends Fragment {
    EditText field , description , address,material,trainer,number,contact;
    AppCompatButton addCourse;
    TextView date;
    TextView time;
    ProgressBar loading;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Course course;
    String child;
    int Hour ,Minute;
    DatePickerDialog.OnDateSetListener mListener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialization();

        getDate();

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCourse();
            }
        });
        getDateFromUser();
        getTimeFromUser();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_coures, container, false);
    }
    public void initialization(){
        field = getActivity().findViewById(R.id.edit_field_course);
        description = getActivity().findViewById(R.id.edit_description_course);
        date = getActivity().findViewById(R.id.edit_date_course);
        time = getActivity().findViewById(R.id.edit_time_course);
        address = getActivity().findViewById(R.id.edit_address_course);
        material = getActivity().findViewById(R.id.edit_material_course);
        trainer = getActivity().findViewById(R.id.edit_trainer_course);
        number = getActivity().findViewById(R.id.edit_number_course);
        contact = getActivity().findViewById(R.id.edit_contact_course);
        addCourse = getActivity().findViewById(R.id.edit_save_course);
        loading = getActivity().findViewById(R.id.edit_progress_course);

        course = new Course();
        child= getArguments().get("key").toString();
        databaseReference=database.getReference("All Courses");
    }
    public void editCourse(){
        String c_field  = field.getText().toString();
        String c_description = description.getText().toString();
        String c_date = date.getText().toString();
        String c_time =time.getText().toString();
        String c_address = address.getText().toString();
        String c_material = material.getText().toString();
        String c_trainer = trainer.getText().toString();
        String c_number = number.getText().toString();
        String c_contact = contact.getText().toString();


        if (!TextUtils.isEmpty(c_field) && !TextUtils.isEmpty(c_description)&& !TextUtils.isEmpty(c_date) && !TextUtils.isEmpty(c_time) && !TextUtils.isEmpty(c_address) && !TextUtils.isEmpty(c_material) && !TextUtils.isEmpty(c_number) && !TextUtils.isEmpty(c_trainer) && !TextUtils.isEmpty(c_contact)) {

            course.setField(c_field);
            course.setAddress(c_address);
            course.setCourseMaterial(c_material);
            course.setCourseNumber(Integer.parseInt(c_number));
            course.setDate(c_date);
            course.setTime(c_time);
            course.setTrainer(c_trainer);
            course.setContactNumber(Integer.parseInt(c_contact));
            course.setDescription(c_description);
            course.setKey(child);


            databaseReference.child(child).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "تم تعديل الدورة", Toast.LENGTH_SHORT).show();
                    field.setText("");
                    description.setText("");
                    address.setText("");
                    material.setText("");
                    trainer.setText("");
                    contact.setText("");
                    number.setText("");
                    Fragment selected;
                    Bundle bundle = new Bundle();
                    bundle.putInt("frame",getArguments().getInt("frame"));
                    selected = new CoursesFragment();
                    selected.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"),selected).commit();

                }
            });

        } else {
            Toast.makeText(getContext(), "لا يمكن ان تكون بيانات الدورة فارغة", Toast.LENGTH_SHORT).show();
        }
    }
    public String showTime(int hour , int min ) {
        String format, time ;
        if(hour > 12) {
            format = "PM";
            hour = hour - 12;
        }
        else
        {
            format="AM";
        }
        if(min == 0){
            time = hour+":"+min+"0 "+format;
        }else {
            time = hour+":"+min+" "+format;
        }
        return  time;


    }

    public void getDateFromUser(){
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1+1;
                String datee = i2+"/"+i1+"/"+i;
                date.setText(datee);
            }
        };
    }
    public void getTimeFromUser(){

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        Hour= i;
                        Minute = i1;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,Hour,Minute);
                        String timee = showTime(Hour,Minute);
                        time.setText(timee);
                    }
                },12,0,false);

                timePickerDialog.updateTime(Hour,Minute);
                timePickerDialog.show();
            }
        });
    }
    public void getDate(){
        databaseReference=database.getReference("All Courses");
        databaseReference.child(child).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()){
                    String c_field  = task.getResult().child("field").getValue().toString();
                    String c_description = task.getResult().child("description").getValue().toString();
                    String c_date = task.getResult().child("date").getValue().toString();
                    String c_time =task.getResult().child("time").getValue().toString();
                    String c_address = task.getResult().child("address").getValue().toString();
                    String c_material = task.getResult().child("courseMaterial").getValue().toString();
                    String c_trainer = task.getResult().child("trainer").getValue().toString();
                    String c_number = task.getResult().child("courseNumber").getValue().toString();
                    String c_contact = task.getResult().child("contactNumber").getValue().toString();


                    field.setText(c_field);
                    description.setText(c_description);
                    date.setText(c_date);
                    time.setText(c_time);
                    trainer.setText(c_trainer);
                    address.setText(c_address);
                    material.setText(c_material);
                    number.setText(c_number);
                    contact.setText(c_contact);

                }
            }
        });
    }

}