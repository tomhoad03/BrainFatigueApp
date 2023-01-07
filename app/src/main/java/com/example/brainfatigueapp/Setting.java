package com.example.brainfatigueapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Setting {
    @PrimaryKey
    public Long settingsId;

    @ColumnInfo(name = "interval")
    public Long interval;

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

    public Setting() {
        this.settingsId = System.currentTimeMillis();
        this.interval = 10800000L / 2;
        this.dayStart = 32400000L;
        this.dayEnd = 79200000L;
        this.workStart = 39600000L;
        this.workEnd = 54000000L;
        this.summary = 54000000L;
        this.darkMode = false;
    }

    public Setting(Long settingsId, Long interval, Long dayStart, Long dayEnd, Long workStart, Long workEnd, Long summary, boolean darkMode) {
        this.settingsId = settingsId;
        this.interval = interval;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.summary = summary;
        this.darkMode = darkMode;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "settingsId=" + settingsId +
                ", interval=" + interval +
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

    public Long getInterval() {
        return interval;
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

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public void setDayStart(Long dayStart) {
        this.dayStart = dayStart;
    }

    public void setDayEnd(Long dayEnd) {
        this.dayEnd = dayEnd;
    }

    public void setWorkStart(Long workStart) {
        this.workStart = workStart;
    }

    public void setWorkEnd(Long workEnd) {
        this.workEnd = workEnd;
    }

    public void setSummary(Long summary) {
        this.summary = summary;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
