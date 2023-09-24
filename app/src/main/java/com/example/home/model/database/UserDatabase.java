package com.example.home.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.home.model.bean.HomeResult;

@Database(entities = {HomeResult.Data.Datas.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase INSTANCE;
    public static synchronized UserDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext() , UserDatabase.class , "UserDatabase")
                    .build();
        }
        return INSTANCE;
    }

    abstract public HomeDao getHomeDao();
}