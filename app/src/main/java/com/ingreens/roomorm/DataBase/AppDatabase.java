package com.ingreens.roomorm.DataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.ingreens.roomorm.interfaces.MyDataDao;
import com.ingreens.roomorm.models.MyDataModel;
import com.ingreens.roomorm.models.StudentModel;

/**
 * Created by jeeban on 4/5/18.
 */

@Database(entities = {MyDataModel.class, StudentModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

   /* static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };*/
   /* database =  Room.databaseBuilder(context.getApplicationContext(),
    UsersDatabase.class, "Sample.db")
            .addMigrations(MIGRATION_1_2)
        .build();*/
    public abstract MyDataDao myDataDao();
}
