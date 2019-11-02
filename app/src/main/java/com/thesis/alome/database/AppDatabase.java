package com.thesis.alome.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.thesis.alome.dao.AddressDao;
import com.thesis.alome.model.Address;

@Database(entities = {Address.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "alome_db";
    private static AppDatabase INSTANCE;
    public abstract AddressDao addressDao();
    public static synchronized AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
        }
        return  INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

}
