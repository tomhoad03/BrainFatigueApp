package com.example.brainfatigueapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    @PrimaryKey
    public Long settingsId;

    @ColumnInfo(name = "frequency")
    public Long frequency;

    @ColumnInfo(name = "day_start")
    public Long dayStart;

    @ColumnInfo(name = "day_End")
    public Long dayEnd;

    @ColumnInfo(name = "work_start")
    public Long workStart;

    @ColumnInfo(name = "work_End")
    public Long workEnd;

    @ColumnInfo(name = "summary")
    public Long summary;

    @ColumnInfo(name = "dark_mode")
    public boolean darkMode;

    public Settings(Long settingsId, Long frequency, Long dayStart, Long dayEnd, Long workStart, Long workEnd, Long summary, boolean darkMode) {
        this.settingsId = settingsId;
        this.frequency = frequency;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.summary = summary;
        this.darkMode = darkMode;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "settingsId=" + settingsId +
                ", frequency=" + frequency +
                ", dayStart=" + dayStart +
                ", dayEnd=" + dayEnd +
                ", workStart=" + workStart +
                ", workEnd=" + workEnd +
                ", summary=" + summary +
                ", darkMode=" + darkMode +
                '}';
    }

    public Long getSettingsId() {
        return settingsId;
    }

    public Long getFrequency() {
        return frequency;
    }

    public Long getDayStart() {
        return dayStart;
    }

    public Long getDayEnd() {
        return dayEnd;
    }

    public Long getWorkStart() {
        return workStart;
    }

    public Long getWorkEnd() {
        return workEnd;
    }

    public Long getSummary() {
        return summary;
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}
