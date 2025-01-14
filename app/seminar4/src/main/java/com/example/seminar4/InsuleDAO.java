package com.example.seminar4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InsuleDAO {

    @Insert
    void insertInsula(Insula insula);

    @Query("SELECT * FROM Insula")
    List<Insula> getAllInsule();

    @Delete
    void deleteInsula(Insula insula);

}