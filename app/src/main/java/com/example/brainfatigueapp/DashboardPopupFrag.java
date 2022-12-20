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
        TextView titleText = view.findViewById(R.id.fragment_dashboard_popup_title);
        titleText.setText(this.title);

        TextView question1Text = view.findViewById(R.id.fragment_dashboard_popup_question_1);
        question1Text.setText(this.result.getQuestion1String());
        // Question 2 also merged into '3 and 4'
        TextView question3and4Text = view.findViewById(R.id.fragment_dashboard_popup_question_3_and_4);
        question3and4Text.setText(this.result.getQuestion3and4String(getActivity()));
        TextView question5Text = view.findViewById(R.id.fragment_dashboard_popup_question_5);
        question5Text.setText(this.result.getQuestion5String());
        TextView question6Text = view.findViewById(R.id.fragment_dashboard_popup_question_6);
        question6Text.setText(this.result.getQuestion6String());
        TextView question7Text = view.findViewById(R.id.fragment_dashboard_popup_question_7);
        question7Text.setText(this.result.getQuestion7String());
        TextView question8Text = view.findViewById(R.id.fragment_dashboard_popup_question_8);
        question8Text.setText(this.result.getQuestion8String());
        TextView question9Text = view.findViewById(R.id.fragment_dashboard_popup_question_9);
        question9Text.setText(this.result.getQuestion9String());

    }
}