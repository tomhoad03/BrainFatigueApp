package com.example.brainfatigueapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SurveyResultDao {
    @Query("SELECT * FROM SurveyResult")
    List<SurveyResult> getAll();

    @Insert
    void insert(SurveyResult surveyResult);
}
