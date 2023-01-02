package com.example.brainfatigueapp;

import android.graphics.Typeface;
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
    private String mode;

    public DashboardPopupFrag(String mode) {
        // 'mode' string specifies what the popup is for
        this.mode = mode;
        if (mode.equals("faq")) {
            this.result = null;
            this.title = "FAQs";
        } else if (mode.equals("about")) {
            this.result = null;
            this.title = "About";
        } else {
            System.out.println("Popup created with unexpected mode. Default to 'faq'");
            this.result = null;
            this.title = "FAQs";
        }
    }

    public DashboardPopupFrag(SurveyResult result, String title) {
        // Constructor for the popup as a report box
        this.result = result;
        this.title = title;
        this.mode = "report";
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
        TextView question3and4Text = view.findViewById(R.id.fragment_dashboard_popup_question_3_and_4);
        TextView question5Text = view.findViewById(R.id.fragment_dashboard_popup_question_5);
        TextView question6Text = view.findViewById(R.id.fragment_dashboard_popup_question_6);
        TextView question7Text = view.findViewById(R.id.fragment_dashboard_popup_question_7);
        TextView question8Text = view.findViewById(R.id.fragment_dashboard_popup_question_8);
        TextView question9Text = view.findViewById(R.id.fragment_dashboard_popup_question_9);
        TextView reactionTimeText = view.findViewById(R.id.fragment_dashboard_popup_reaction_time);
        TextView fitbitText = view.findViewById(R.id.fragment_dashboard_popup_fitbit);
        if (this.mode.equals("faq")) {
            // Update the text in the popup to display the FAQs
            // Q and A 1
            question1Text.setText(getResources().getString(R.string.activity_settings_button_faq_1));
            question1Text.setTypeface(question1Text.getTypeface(), Typeface.BOLD_ITALIC);
            question3and4Text.setText(getResources().getString(R.string.activity_settings_button_faq_2));

            // Q and A 2
            question5Text.setText(getResources().getString(R.string.activity_settings_button_faq_3));
            question5Text.setTypeface(question5Text.getTypeface(), Typeface.BOLD_ITALIC);
            question6Text.setText(getResources().getString(R.string.activity_settings_button_faq_4));

            // Q and A 3
            question7Text.setText(getResources().getString(R.string.activity_settings_button_faq_5));
            question7Text.setTypeface(question7Text.getTypeface(), Typeface.BOLD_ITALIC);
            question8Text.setText(getResources().getString(R.string.activity_settings_button_faq_6));

            // Q and A 4
            question9Text.setText(getResources().getString(R.string.activity_settings_button_faq_7));
            question9Text.setTypeface(question9Text.getTypeface(), Typeface.BOLD_ITALIC);
            reactionTimeText.setText(getResources().getString(R.string.activity_settings_button_faq_8));

            // Don't need final box
            fitbitText.setText("");
        } else if (this.mode.equals("report")) {
            question1Text.setText(this.result.getQuestion1String());
            // Question 2 also merged into '3 and 4'
            question3and4Text.setText(this.result.getQuestion3and4String(getActivity()));
            question5Text.setText(this.result.getQuestion5String());
            question6Text.setText(this.result.getQuestion6String());
            question7Text.setText(this.result.getQuestion7String());
            question8Text.setText(this.result.getQuestion8String());
            question9Text.setText(this.result.getQuestion9String());
            // reactionTimeText.setText(this.result.getReactionTimeString());
            // fitbitText.setText(this.result.getFitbitString());
        } else {
            // Update the text in the popup to display the About information
            // Main message
            question1Text.setTypeface(question1Text.getTypeface(), Typeface.BOLD_ITALIC);
            question1Text.setText("Current version: v1.1.1");
            // question1Text.setText(getResources().getString(R.string.activity_settings_about_1));

            /*
            question3and4Text.setText(getResources().getString());
            question5Text.setText(getResources().getString());
            question6Text.setText(getResources().getString());
            question7Text.setText(getResources().getString());
            question8Text.setText(getResources().getString());
            question9Text.setText(getResources().getString());
            reactionTimeText.setText(getResources().getString());
            fitbitText.setText("");
             */
        }
    }
}