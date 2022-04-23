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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.Models.Course;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Admin.CoursesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Add_CourseFragment extends Fragment {

    EditText  description , address,material,trainer,number;
    Spinner field;
    AppCompatButton addCourse;
    TextView date , dateEnd;
    TextView time;
    ProgressBar loading;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,databaseReference2;
    Course course;
    int Hour ,Minute;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatePickerDialog.OnDateSetListener mListener,zListener;
    String cField,contactNumber;


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
        getDate();
        courseField();
        getTimeFromUser();
        getDateUser();
        getDateEndUser();

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
        dateEnd = getActivity().findViewById(R.id.add_date_end_course);
        time = getActivity().findViewById(R.id.add_time_course);
        address = getActivity().findViewById(R.id.add_address_course);
        material = getActivity().findViewById(R.id.add_material_course);
        trainer = getActivity().findViewById(R.id.add_trainer_course);
        number = getActivity().findViewById(R.id.add_number_course);
        addCourse = getActivity().findViewById(R.id.add_save_course);
        loading = getActivity().findViewById(R.id.add_progress_course);

        course = new Course();

    }

    public void uploadCourse(){
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        String c_field  = cField;
        String c_description = description.getText().toString();
        String c_date = date.getText().toString();
        String c_date_end = dateEnd.getText().toString();
        String c_time =time.getText().toString();
        String c_address = address.getText().toString();
        String c_material = material.getText().toString();
        String c_trainer = trainer.getText().toString();
        String c_number = number.getText().toString();
        String c_contact = contactNumber;


        databaseReference=database.getReference("All Courses");
        databaseReference2 = database.getReference("ContactCourses").child(currentUserId);
        String child=databaseReference.push().getKey();


        if (!TextUtils.isEmpty(c_field)&& !TextUtils.isEmpty(c_date_end) && !TextUtils.isEmpty(c_description)&& !TextUtils.isEmpty(c_date) && !TextUtils.isEmpty(c_time) && !TextUtils.isEmpty(c_address) && !TextUtils.isEmpty(c_material) && !TextUtils.isEmpty(c_number) && !TextUtils.isEmpty(c_trainer) && !TextUtils.isEmpty(c_contact)) {

            course.setField(c_field);
            course.setAddress(c_address);
            course.setCourseMaterial(c_material);
            course.setCourseNumber(Integer.parseInt(c_number));
            course.setDate(c_date);
            course.setTime(c_time);
            course.setEnd(c_date_end);
            course.setTrainer(c_trainer);
            course.setContactNumber(Integer.parseInt(c_contact));
            course.setDescription(c_description);
            course.setKey(child);

            int user = getArguments().getInt("frame");
            if(user == R.id.frame_layout_cont){
               databaseReference2.child(child).setValue(course);
            }
            databaseReference.child(child).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "تم إضافة الدورة", Toast.LENGTH_SHORT).show();
                    description.setText("");
                    address.setText("");
                    material.setText("");
                    trainer.setText("");
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
    public void getDateUser(){
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
    public void getDateEndUser(){
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        zListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        zListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1+1;
                String datee = i2+"/"+i1+"/"+i;
                dateEnd.setText(datee);
            }
        };
    }
    public void courseField(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.Fields
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        field.setAdapter(adapter);
        field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cField = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
            reference = firestore.collection("Training Provider").document(currentUserId);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    try {
                        if (task.getResult().exists()) {
                            contactNumber =task.getResult().getString("number");
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


}