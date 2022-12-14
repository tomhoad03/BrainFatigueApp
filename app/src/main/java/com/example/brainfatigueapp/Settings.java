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

    public Settings(Long settingsId) {
        this.settingsId = settingsId;
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

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public Long getDayStart() {
        return dayStart;
    }

    public void setDayStart(Long dayStart) {
        this.dayStart = dayStart;
    }

    public Long getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(Long dayEnd) {
        this.dayEnd = dayEnd;
    }

    public Long getWorkStart() {
        return workStart;
    }

    public void setWorkStart(Long workStart) {
        this.workStart = workStart;
    }

    public Long getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(Long workEnd) {
        this.workEnd = workEnd;
    }

    public Long getSummary() {
        return summary;
    }

    public void setSummary(Long summary) {
        this.summary = summary;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
