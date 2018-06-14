package com.ingreens.roomorm.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Arrays;

/**
 * Created by jeeban on 4/5/18.
 */

@Entity
public class MyDataModel {

    @PrimaryKey(autoGenerate = true)
    private int user_id;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="team")
    private String team;
    @ColumnInfo(name="rank")
    private int rank;
    @ColumnInfo(name="photo")
    private String photo;
    @ColumnInfo(name="image",typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public MyDataModel() {
    }

    public MyDataModel(String name, String team, int rank, String photo, byte[] image) {
        this.name = name;
        this.team = team;
        this.rank = rank;
        this.photo = photo;
        this.image = image;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "MyDataModel{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", rank=" + rank +
                ", photo='" + photo + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
