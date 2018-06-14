package com.ingreens.roomorm.activities;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ingreens.roomorm.DataBase.AppDatabase;
import com.ingreens.roomorm.R;
import com.ingreens.roomorm.constants.AppConstants;
import com.ingreens.roomorm.models.StudentModel;

public class StudentActivity extends AppCompatActivity {

    AppDatabase appDatabase;
    StudentModel studentModel;
    private EditText etStudentName,etQualification,etAddress,etEmailId,etMobileNo;
    private TextView tvStudentName,tvQualification,tvAddress,tvEmailId,tvMobileNo;
    private Button btnSubmitShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        appDatabase= Room.databaseBuilder(StudentActivity.this,AppDatabase.class, AppConstants.DB_NAME)
                .allowMainThreadQueries()
                .build();

        etStudentName=findViewById(R.id.etStudentName);
        etQualification=findViewById(R.id.etQualification);
        etAddress=findViewById(R.id.etAddress);
        etEmailId=findViewById(R.id.etEmailId);
        etMobileNo=findViewById(R.id.etMobileNo);

        tvStudentName=findViewById(R.id.tvStudentName);
        tvQualification=findViewById(R.id.tvQualification);
        tvAddress=findViewById(R.id.tvAddress);
        tvEmailId=findViewById(R.id.tvEmailId);
        tvMobileNo=findViewById(R.id.tvMobileNo);

        btnSubmitShow=findViewById(R.id.btnSubmitShow);
        btnSubmitShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sName=etStudentName.getText().toString();
                String qualification=etQualification.getText().toString();
                String address=etAddress.getText().toString();
                String email=etEmailId.getText().toString();
                String mobileNo=etMobileNo.getText().toString();

                System.out.println("#####  BEFORE INSERT FROM FIELD #####");
                System.out.println("student name=="+sName);
                System.out.println("qualification=="+qualification);
                System.out.println("address=="+address);
                System.out.println("email id=="+email);
                System.out.println("mobile no=="+mobileNo);
                System.out.println("######  BEFORE INSERT FROM FIELD #######@");

                ////insert student data into room database.
                studentModel=new StudentModel(sName,qualification,address,email,mobileNo);
                appDatabase.myDataDao().insertStudentData(studentModel);

                System.out.println("###### AFTER INSERT FROM DATABASE #####");
                System.out.println("get all data  using database query"+appDatabase.myDataDao().getAllStudents());
                System.out.println("get all data  using database query (toString)"+appDatabase.myDataDao().getAllStudents().toString());
                System.out.println("######  AFTER INSERT FROM DATABASE ######");

                System.out.println("######  AFTER INSERT FROM MODEL #####");
                System.out.println("student name=="+studentModel.getName());
                System.out.println("qualification=="+studentModel.getQualification());
                System.out.println("address=="+studentModel.getAddress());
                System.out.println("email id=="+studentModel.getEmail());
                System.out.println("mobile no=="+studentModel.getMobile_no());
                System.out.println("######  AFTER INSERT FROM MODEL ######");

                tvStudentName.setText(studentModel.getName());
                tvQualification.setText(studentModel.getQualification());
                tvAddress.setText(studentModel.getAddress());
                tvEmailId.setText(studentModel.getEmail());
                tvMobileNo.setText(studentModel.getMobile_no());

                etStudentName.getText().clear();
                etQualification.getText().clear();
                etAddress.getText().clear();
                etEmailId.getText().clear();
                etMobileNo.getText().clear();


            }
        });

    }
}
