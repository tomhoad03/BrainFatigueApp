package com.example.brainfatigueapp;

import java.io.Serializable;

public class SurveyResult implements Serializable {
    public Integer question1; // 0 to 100 in 5 point intervals (slider)
    public Integer question2; // 1 or 2
    public Integer question3; // 1 to 7
    public Integer question3Extended;
    public Integer question4; // 1 to 8
    public Integer question4Extended;
    public Integer question5; // 0 to 10 in 1 point intervals (slider)
    public Integer question6; // 0 to 10 in 1 point intervals (slider)
    public Integer question7; // 0 to 10 in 1 point intervals (slider)
    public Integer question8; // 1 to 4
    public Integer question9; // 1 to 3

    public SurveyResult() {
    }

    @Override
    public String toString() {
        return "SurveyResult{" +
                "question1=" + question1 +
                ", question2=" + question2 +
                ", question3=" + question3 +
                ", question3Extended=" + question3Extended +
                ", question4=" + question4 +
                ", question4Extended=" + question4Extended +
                ", question5=" + question5 +
                ", question6=" + question6 +
                ", question7=" + question7 +
                ", question8=" + question8 +
                ", question9=" + question9 +
                '}';
    }

    public Integer getQuestion1() {
        return question1;
    }

    public void setQuestion1(Integer question1) {
        this.question1 = question1;
    }

    public Integer getQuestion2() {
        return question2;
    }

    public void setQuestion2(Integer question2) {
        this.question2 = question2;
    }

    public Integer getQuestion3() {
        return question3;
    }

    public void setQuestion3(Integer question3) {
        this.question3 = question3;
    }

    public Integer getQuestion3Extended() {
        return question3Extended;
    }

    public void setQuestion3Extended(Integer question3Extended) {
        this.question3Extended = question3Extended;
    }

    public Integer getQuestion4() {
        return question4;
    }

    public void setQuestion4(Integer question4) {
        this.question4 = question4;
    }

    public Integer getQuestion4Extended() {
        return question4Extended;
    }

    public void setQuestion4Extended(Integer question4Extended) {
        this.question4Extended = question4Extended;
    }

    public Integer getQuestion5() {
        return question5;
    }

    public void setQuestion5(Integer question5) {
        this.question5 = question5;
    }

    public Integer getQuestion6() {
        return question6;
    }

    public void setQuestion6(Integer question6) {
        this.question6 = question6;
    }

    public Integer getQuestion7() {
        return question7;
    }

    public void setQuestion7(Integer question7) {
        this.question7 = question7;
    }

    public Integer getQuestion8() {
        return question8;
    }

    public void setQuestion8(Integer question8) {
        this.question8 = question8;
    }

    public Integer getQuestion9() {
        return question9;
    }

    public void setQuestion9(Integer question9) {
        this.question9 = question9;
    }
}
