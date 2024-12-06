package com.example.seminar4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Insula.class}, version = 1)

public abstract class InsuleDataBase extends RoomDatabase {
    public abstract InsuleDAO getInsuleDAO();

}