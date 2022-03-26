package com.example.courses.UI.Fragments.Contacts;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.Models.Course;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Trainer.ContactPageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_CourseFragment extends Fragment {

    EditText field , description , address,material,trainer,number,contact;
    AppCompatButton addCourse;
    DatePicker date;
    TimePicker time;
    ProgressBar loading;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Course course;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadCourse();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add__course, container, false);
    }

    public void initialization(){
        field = getActivity().findViewById(R.id.add_field_course);
        description = getActivity().findViewById(R.id.add_description_course);
        date = getActivity().findViewById(R.id.add_date_course);
        time = getActivity().findViewById(R.id.add_time_course);
        time.setIs24HourView(true);
        address = getActivity().findViewById(R.id.add_address_course);
        material = getActivity().findViewById(R.id.add_material_course);
        trainer = getActivity().findViewById(R.id.add_trainer_course);
        number = getActivity().findViewById(R.id.add_number_course);
        contact = getActivity().findViewById(R.id.add_contact_course);
        addCourse = getActivity().findViewById(R.id.add_save_course);
        loading = getActivity().findViewById(R.id.add_progress_course);

        course = new Course();

    }

    public void uploadCourse(){
        String c_field  = field.getText().toString();
        String c_description = description.getText().toString();
        String c_date = date.getDayOfMonth()+"/"+date.getMonth()+"/"+date.getYear();
        String c_time =showTime(time.getHour(),time.getMinute());;
        String c_address = address.getText().toString();
        String c_material = material.getText().toString();
        String c_trainer = trainer.getText().toString();
        String c_number = number.getText().toString();
        String c_contact = contact.getText().toString();


        databaseReference=database.getReference("All Courses");
        String child=databaseReference.push().getKey();


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
                    Toast.makeText(getContext(), "تم إضافة الدورة", Toast.LENGTH_SHORT).show();
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
        time = hour+":"+min+" "+format;
        return  time;


    }

}