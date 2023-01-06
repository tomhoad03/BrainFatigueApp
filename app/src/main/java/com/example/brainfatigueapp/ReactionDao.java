package com.example.brainfatigueapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReactionDao {
    @Query("SELECT * FROM Reaction")
    List<Reaction> getAll();

    @Insert
    void insert(Reaction reaction);
}
