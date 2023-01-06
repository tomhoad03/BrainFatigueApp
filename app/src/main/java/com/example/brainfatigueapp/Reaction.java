package com.example.brainfatigueapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reaction {
    @PrimaryKey
    public Long reactionId; // Corresponds to the time (in milliseconds) the reaction time test is ended

    @ColumnInfo(name = "average_time")
    public Long averageTime;

    public Reaction() {
        this.reactionId = System.currentTimeMillis();
    }

    public Reaction(Long averageTime) {
        this.reactionId = System.currentTimeMillis();
        this.averageTime = averageTime;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "reactionId=" + reactionId +
                ", averageTime=" + averageTime +
                '}';
    }

    public Long getReactionId() {
        return reactionId;
    }

    public Long getAverageTime() {
        return averageTime;
    }
}
