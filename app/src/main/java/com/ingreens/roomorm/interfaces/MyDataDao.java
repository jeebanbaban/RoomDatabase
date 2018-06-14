package com.ingreens.roomorm.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ingreens.roomorm.models.MyDataModel;
import com.ingreens.roomorm.models.StudentModel;

import java.util.List;

/**
 * Created by jeeban on 4/5/18.
 */

@Dao
public interface MyDataDao {

    @Query("SELECT * FROM myDataModel")
    List<MyDataModel> getAll();

    @Query("SELECT * FROM studentModel")
    List<StudentModel> getAllStudents();

//    @Query("SELECT * FROM myDataModel ORDER BY UID DESC LIMIT 0,1")
//    MyDataModel last();
//
//    @Query("SELECT * FROM myDataModel ORDER BY UID ASC LIMIT 0,1")
//    MyDataModel first();

    @Insert
    void insertAll(MyDataModel... myDataModels);

    @Insert
    void insertStudentData(StudentModel studentModel);

    @Delete
    void delete(MyDataModel myDataModel);

    @Delete
    void deleteStudentData(StudentModel studentModel);
}
