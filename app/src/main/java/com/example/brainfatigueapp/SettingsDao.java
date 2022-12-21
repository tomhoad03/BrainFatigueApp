package com.example.brainfatigueapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM Setting")
    List<Setting> getAll();

    @Insert
    void insert(Setting setting);

    @Query("DELETE FROM Setting")
    void deleteAll();
}
