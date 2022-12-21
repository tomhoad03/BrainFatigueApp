package com.example.brainfatigueapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reaction {
    @PrimaryKey
    public Long reactionId; // Corresponds to the time (in milliseconds) the reaction time test is started

    public Reaction() {
        this.reactionId = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "reactionId=" + reactionId +
                '}';
    }

    public Long getReactionId() {
        return reactionId;
    }
}
