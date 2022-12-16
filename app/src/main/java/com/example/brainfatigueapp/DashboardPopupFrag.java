package com.example.brainfatigueapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardPopupFrag extends DialogFragment {

    private SurveyResult result;
    private String title;

    public DashboardPopupFrag() {
        // Required empty public constructor
    }

    public DashboardPopupFrag(SurveyResult result, String title) {
        this.result = result;
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_popup, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        // Update the text in the popup to display the full information of the survey report
        TextView titleText = (TextView) view.findViewById(R.id.fragment_dashboard_popup_title);
        titleText.setText(this.title);

        TextView question1Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_1);
        question1Text.setText(this.result.getQuestion1().toString());
        TextView question2Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_2);
        question2Text.setText(this.result.getQuestion2().toString());
        TextView question3Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_3);
        question3Text.setText(this.result.getQuestion3().toString());
        TextView question4Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_4);
        question4Text.setText(this.result.getQuestion4().toString());
        TextView question5Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_5);
        question5Text.setText(this.result.getQuestion5().toString());
        TextView question6Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_6);
        question6Text.setText(this.result.getQuestion6().toString());
        TextView question7Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_7);
        question7Text.setText(this.result.getQuestion7().toString());
        TextView question8Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_8);
        question8Text.setText(this.result.getQuestion8().toString());
        TextView question9Text = (TextView) view.findViewById(R.id.fragment_dashboard_popup_question_9);
        question9Text.setText(this.result.getQuestion9().toString());

    }
}