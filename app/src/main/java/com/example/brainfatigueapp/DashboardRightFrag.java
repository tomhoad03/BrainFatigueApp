package com.example.brainfatigueapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        executorService.execute(() -> {
            SurveyDatabase surveyDatabase = SurveyDatabase.getDatabase(getContext().getApplicationContext());
            SurveyResultDao surveyResultDao = surveyDatabase.surveyResultDao();

            List<SurveyResult> surveyResults = surveyResultDao.getAll();
            Long timeInMillis = surveyResults.get(0).getSurveyResultId();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, hh:mmaaa", Locale.UK);
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
            calendar.setTimeInMillis(timeInMillis);
            System.out.println("CURRENTLY: " + sdf.format(calendar.getTime()));

            // Log.d("survey_results", surveyResults.toString());

            // Create and format the new button to include data from the database
            Button newButton = new Button(getActivity());
            newButton.setLayoutParams(new ConstraintLayout.LayoutParams(350, 84));
            newButton.setText(sdf.format(calendar.getTime()));

            // newButton.setId(111);

            //add button to the layout
            layout.addView(newButton);

        });
        executorService.shutdown();

    }
}