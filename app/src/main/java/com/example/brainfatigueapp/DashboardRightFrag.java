package com.example.brainfatigueapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.*;


public class DashboardRightFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_right, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        addReportBox(view);
    }

    private void addReportBox(View view) {
        // Adds a new report box to the scrollable list

        // Get the layout
        ConstraintLayout layout = getView().findViewById(R.id.activity_right_fragment_reports_container);

        // Get the data from the database
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<SurveyResult>> surveyResultsFuture = executorService.submit(() -> {
            SurveyDatabase surveyDatabase = SurveyDatabase.getDatabase(getContext().getApplicationContext());
            SurveyResultDao surveyResultDao = surveyDatabase.surveyResultDao();

            return surveyResultDao.getAll();
        });
        executorService.shutdown();

        List<SurveyResult> surveyResults = null;
        try {
            surveyResults = surveyResultsFuture.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        // Log.d("survey_results", surveyResults.toString());

        // Calculate the date from milliseconds
        Long timeInMillis = surveyResults.get(0).getSurveyResultId();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, hh:mmaaa", Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
        calendar.setTimeInMillis(timeInMillis);
        System.out.println("CURRENTLY: " + sdf.format(calendar.getTime()));

        // Create a new button to add to the page
        formatButton(sdf.format(calendar.getTime()), layout);
    }

    private void formatButton (String time, ConstraintLayout layout) {
        // This function takes the required data to create a report and makes it look like it should
        Button newButton = new Button(getActivity());
        layout.addView(newButton);

        // Set the id of the new button so that it can be referred to later
        newButton.setId((int) 1234); // Needs to be done programmatically

        // Apply the drawable for a report box to the button
        newButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.report_box));
        // newButton.setText(time); // Placeholder

        // Align and constrain the button into position
        ConstraintSet constrain = new ConstraintSet();
        constrain.clone(layout);
        // Horizontally
        constrain.connect(newButton.getId(), ConstraintSet.LEFT,
                          layout.getId(),    ConstraintSet.LEFT); // ConstraintSet.PARENT_ID / layout.getId() both work
        constrain.connect(newButton.getId(), ConstraintSet.RIGHT,
                          layout.getId(),    ConstraintSet.RIGHT);
        // Vertically
        // If statement here, if not the first box -> set the top of box to the bottom of the previous box
        constrain.connect(newButton.getId(), ConstraintSet.TOP,
                          layout.getId(),    ConstraintSet.TOP);
        constrain.setMargin(newButton.getId(), ConstraintSet.TOP, 10);
        constrain.applyTo(layout);

        // Create the text that goes in the report boxes (styles applied in constructor)
        TextView newMainText = new TextView(getActivity(), null, 0, R.style.report_box_main_text);
        layout.addView(newMainText);
        TextView newSubText = new TextView(getActivity(), null, 0, R.style.report_box_sub_text);
        layout.addView(newSubText);

        // Give the texts ids
        newMainText.setId((int) 1235); // Also needs to be programmatic
        newSubText.setId((int) 1236);

        ConstraintSet constrainText = new ConstraintSet();
        constrainText.clone(layout);

        // Set the strings for the text
        newMainText.setText(time); // Time could be formatted differently
        newSubText.setText(R.string.activity_dashboard_right_sub); // What data to show before the full popup?

        newMainText.bringToFront();
        newSubText.bringToFront();

        // Set the constraints on the text
        constrainText.connect(newMainText.getId(), ConstraintSet.TOP,
                          newButton.getId(),   ConstraintSet.TOP, 24);
        constrainText.connect(newMainText.getId(), ConstraintSet.LEFT,
                          newButton.getId(),   ConstraintSet.LEFT, 36);
        constrainText.connect(newSubText.getId(),  ConstraintSet.TOP,
                          newMainText.getId(), ConstraintSet.BOTTOM, 12);
        constrainText.connect(newSubText.getId(),  ConstraintSet.LEFT,
                          newMainText.getId(),   ConstraintSet.LEFT);
        constrainText.applyTo(layout);
    }
}