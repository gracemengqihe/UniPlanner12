package com.example.uniplanner.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = CourseSelection.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    private static Repository REPOSITORY;

    public abstract Access getAccess();

    public static void exit() {
        if (INSTANCE != null && INSTANCE.isOpen())
            INSTANCE.close();

        INSTANCE = null;
    }

    public static AppDatabase get() {
        if (INSTANCE == null)
            throw new IllegalStateException();

        return INSTANCE;
    }

    public static AppDatabase get(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "uniplanner").build();

        return INSTANCE;
    }

    public Repository getRepository() {
        if (REPOSITORY == null)
            REPOSITORY = new Repository(getAccess());

        return REPOSITORY;
    }
}