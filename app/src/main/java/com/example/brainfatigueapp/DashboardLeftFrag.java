package com.example.brainfatigueapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class DashboardLeftFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_left, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        // Add report boxes with data from the database to the scrollable list

        // Get the layouts
        ScrollView vertical = getView().findViewById(R.id.activity_left_fragment_reports_vertical);
        vertical.setScrollbarFadingEnabled(false); // Make the scrollbar always visible
        ConstraintLayout layout = getView().findViewById(R.id.activity_left_fragment_reports_container);

        // Get the data from the database
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<SurveyResult>> surveyResultsFuture = executorService.submit(() -> {
            SurveyDatabase surveyDatabase = SurveyDatabase.getDatabase(getContext());
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

        // Fill the fragment with a report box with the database entries from today only
        int boxCount = 0;
        // Get the current time
        LocalTime now = LocalTime.now();
        // Get the time of the daily summary notification from the settings database
        Float summaryTimeFloat = 22f; // Hardcoded until Tom shows me how to get stuff from the database
        LocalTime summaryTime = LocalTime.parse("22:00"); // parse whatever the database stores into 'summaryTime'
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, hh:mmaaa", Locale.UK);

        if (surveyResults != null) {
            for (SurveyResult nextResult : surveyResults) {
                // Get the time that the survey was taken
                GregorianCalendar surveyCalendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
                surveyCalendar.setTimeInMillis(nextResult.getSurveyResultId());

                if (now.compareTo(summaryTime) > 0) {
                    // If the current time is after the notification time, show only reports from today
                    // "today's reports" defined by surveys that were taken AFTER the time now
                    // MINUS the time now in hours (around 00:00am)
                    GregorianCalendar lowerBoundCalendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
                    lowerBoundCalendar.setTimeInMillis(System.currentTimeMillis());
                    lowerBoundCalendar.add(Calendar.HOUR, -1 * (int) summaryTimeFloat.longValue());
                    System.out.println("(TODAY) Displaying only times after: " + sdf.format(lowerBoundCalendar.getTime()));
                    if (surveyCalendar.compareTo(lowerBoundCalendar) > 0) {
                        // Create a report box for this survey result
                        formatButton(nextResult, boxCount, layout);
                        boxCount++;
                    }
                } else {
                    // If the current time is after the notification time, show only reports from today
                    // "yesterday's reports" defined by the surveys taken BEFORE
                    // the (current date and time) MINUS the time now in hours (around 00:00am) and also AFTER
                    // the (current date and time) MINUS (the time now in hours + 24) (around 00:00am the day before)
                    GregorianCalendar lowerBoundCalendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
                    lowerBoundCalendar.setTimeInMillis(System.currentTimeMillis());
                    lowerBoundCalendar.add(Calendar.HOUR, -24 + (-1 * (int) summaryTimeFloat.longValue()));
                    GregorianCalendar upperBoundCalendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
                    upperBoundCalendar.setTimeInMillis(System.currentTimeMillis());
                    upperBoundCalendar.add(Calendar.HOUR, -1 * (int) summaryTimeFloat.longValue());
                    System.out.println("(YDAY) Displaying only times after: " + sdf.format(lowerBoundCalendar.getTime()));
                    System.out.println("(YDAY) and also only times before : " + sdf.format(upperBoundCalendar.getTime()));
                    if ((surveyCalendar.compareTo(lowerBoundCalendar) > 0) &&
                            (surveyCalendar.compareTo(upperBoundCalendar) < 0)) {
                        // Create a report box for this survey result
                        formatButton(nextResult, boxCount, layout);
                        boxCount++;
                    }
                }
            }
        }
    }

    private void formatButton (SurveyResult result, int count, ConstraintLayout layout) {
        // Set the strings for the text
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, hh:mmaaa", Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
        calendar.setTimeInMillis(result.getSurveyResultId());
        //System.out.println("CURRENTLY: " + sdf.format(calendar.getTime()));
        String mainText = sdf.format(calendar.getTime());
        String subText = "You reported a fatigue level of " + result.getQuestion1().toString() + ".";

        // This function takes the required data to create a report and makes it look like it should
        Button newButton = new Button(getActivity());
        layout.addView(newButton);

        // Set the id of the new button so that it can be referred to later
        newButton.setId(10000000 + count);

        // Apply the drawable for a report box to the button
        newButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.report_box));

        // Align and constrain the button into position
        ConstraintSet constrain = new ConstraintSet();
        constrain.clone(layout);
        // Horizontally
        constrain.connect(newButton.getId(), ConstraintSet.LEFT,
                layout.getId(),    ConstraintSet.LEFT);
        constrain.connect(newButton.getId(), ConstraintSet.RIGHT,
                layout.getId(),    ConstraintSet.RIGHT);
        // Vertically
        // If statement here, if not the first box -> set the top of box to the bottom of the previous box
        if (count == 0) {
            constrain.connect(newButton.getId(), ConstraintSet.TOP,
                    layout.getId(), ConstraintSet.TOP); // Constrain the box to the top of the layout
        } else {
            constrain.connect(newButton.getId(), ConstraintSet.TOP,
                    (int) newButton.getId() - 1, ConstraintSet.BOTTOM); // Constrain the box to the bottom of the previous box
        }
        constrain.setMargin(newButton.getId(), ConstraintSet.TOP, 60);
        constrain.applyTo(layout);

        // Set the functionality of the buttons so that they open a popup when clicked
        newButton.setOnClickListener(v -> {
            FragmentManager manager = getFragmentManager();
            DashboardPopupFrag popup = new DashboardPopupFrag(result, mainText);
            popup.show(manager, "popup");
        });

        // Create the text that goes in the report boxes (styles applied in constructor)
        TextView newMainText = new TextView(getActivity(), null, 0, R.style.report_box_main_text);
        layout.addView(newMainText);
        TextView newSubText = new TextView(getActivity(), null, 0, R.style.report_box_sub_text);
        layout.addView(newSubText);

        // Give the texts ids
        newMainText.setId(20000000 + count);
        newSubText.setId(30000000 + count);

        ConstraintSet constrainText = new ConstraintSet();
        constrainText.clone(layout);

        newMainText.setText(mainText); // Time could be formatted differently
        newSubText.setText(subText);

        newMainText.bringToFront();
        newSubText.bringToFront();

        // Set the constraints on the text
        constrainText.connect(newMainText.getId(), ConstraintSet.TOP,
                newButton.getId(),   ConstraintSet.TOP, 28);
        constrainText.connect(newMainText.getId(), ConstraintSet.LEFT,
                newButton.getId(),   ConstraintSet.LEFT, 36);
        constrainText.connect(newSubText.getId(),  ConstraintSet.TOP,
                newMainText.getId(), ConstraintSet.BOTTOM, 16);
        constrainText.connect(newSubText.getId(),  ConstraintSet.LEFT,
                newMainText.getId(),   ConstraintSet.LEFT);
        constrainText.applyTo(layout);
    }

    // Get the string for the corresponding survey result
    public String getSurveyString(SurveyResult surveyResult, Integer questionId, Integer questionResult, @Nullable Boolean extended) {
        if (questionId == 2 || (questionId == 3 && extended != null) || (questionId == 4 && extended != null) || questionId == 8 || questionId == 9) {
            return surveyResult.getSurveyString(getContext(), questionId, questionResult, extended);
        } else {
            return null;
        }
    }
}