package com.ingreens.roomorm.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jeeban on 7/5/18.
 */
@Entity
public class StudentModel {

    @PrimaryKey(autoGenerate = true)
    private int u_id;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="qualification")
    private String qualification;
    @ColumnInfo(name="address")
    private String address;
    @ColumnInfo(name="email")
    private String email;
    @ColumnInfo(name="mobile_no")
    private String mobile_no;

    public StudentModel() {
    }

    public StudentModel(String name, String qualification, String address, String email, String mobile_no) {
        this.name = name;
        this.qualification = qualification;
        this.address = address;
        this.email = email;
        this.mobile_no = mobile_no;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "u_id=" + u_id +
                ", name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                '}';
    }
}
