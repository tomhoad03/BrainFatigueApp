package com.example.brainfatigueapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    @PrimaryKey
    public Long settingsId;

    @ColumnInfo(name = "day_start")
    public Long dayStart;

    public Settings(Long settingsId) {
        this.settingsId = settingsId;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "settingsId=" + settingsId +
                ", dayStart=" + dayStart +
                '}';
    }

    public Long getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }

    public Long getDayStart() {
        return dayStart;
    }

    public void setDayStart(Long dayStart) {
        this.dayStart = dayStart;
    }
}
