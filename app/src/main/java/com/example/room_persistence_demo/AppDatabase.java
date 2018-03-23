package com.example.room_persistence_demo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version=2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UserDao userDao();

    private static AppDatabase databaseInstance;

    public static AppDatabase getDatabaseInstance(Context context)
    {
        if(databaseInstance == null)
        {
            //if you update your database schema all data is lost with fallbackToDestructiveMigration() since this is a demo it's no big deal
            databaseInstance = Room.databaseBuilder(context,AppDatabase.class,"demo-database").fallbackToDestructiveMigration().build();
        }
        return databaseInstance;
    }

    public void destroyInstance()
    {
        databaseInstance = null;
    }
}
