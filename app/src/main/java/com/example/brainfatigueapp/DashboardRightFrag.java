package com.example.brainfatigueapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.*;
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
        // Get the layouts
        ScrollView vertical = getView().findViewById(R.id.activity_right_fragment_reports_vertical);
        vertical.setScrollbarFadingEnabled(false); // Make the scrollbar always visible
        ConstraintLayout layout = getView().findViewById(R.id.activity_right_fragment_reports_container);

        // Retrieve the stored data from the database
        List<SurveyResult> surveyResults = retrieveDatabaseData();

        // Draw a graph from the data
        drawLineGraph1(surveyResults);

        // Draw a second graph from the data
        drawLineGraph2(surveyResults);

        // Fill the fragment with a report box for every entry in the database
        drawReportBoxes(surveyResults, layout);
    }

    private List<SurveyResult> retrieveDatabaseData() {
        // Get the data from the database
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<SurveyResult>> surveyResultsFuture = executorService.submit(() -> {
            FatigueDatabase surveyDatabase = FatigueDatabase.getDatabase(getContext());
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
        return surveyResults;
    }

    private ArrayList<Entry> getChartData(List<SurveyResult> database) {
        ArrayList<Entry> chartData = new ArrayList<Entry>();

        chartData.add(new Entry(1, 15));
        chartData.add(new Entry(2, 50));
        chartData.add(new Entry(3, 25));

        return chartData;
    }

    private void drawLineGraph1(List<SurveyResult> surveyResults) {
        LineChart lineChart = getView().findViewById(R.id.activity_right_fragment_graph_1);
        LineDataSet lineChartData = new LineDataSet(getChartData(surveyResults), "LINE CHART 1!");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<ILineDataSet>();
        iLineDataSets.add(lineChartData);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate(); // ??? what do this do
    }

    private void drawLineGraph2(List<SurveyResult> surveyResults) {
        LineChart lineChart2 = getView().findViewById(R.id.activity_right_fragment_graph_2);
        LineDataSet lineChartData2 = new LineDataSet(getChartData(surveyResults), "LINE CHART 2!");
        ArrayList<ILineDataSet> iLineDataSets2 = new ArrayList<ILineDataSet>();
        iLineDataSets2.add(lineChartData2);

        LineData lineData2 = new LineData(iLineDataSets2);
        lineChart2.setData(lineData2);
        lineChart2.invalidate(); // ??? what do this do
    }

    private void drawReportBoxes(List<SurveyResult> surveyResults, ConstraintLayout layout) {
        int boxCount = 0;
        if (surveyResults != null) {
            for (SurveyResult nextResult : surveyResults) {
                // Create a report box for this survey result
                formatButton(nextResult, boxCount, layout);
                boxCount++;
            }
        }
    }

    private void formatButton (SurveyResult result, int count, ConstraintLayout layout) {
        // Set the strings for the text
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, hh:mmaaa", Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
        calendar.setTimeInMillis(result.getSurveyResultId());
        System.out.println("CURRENTLY: " + sdf.format(calendar.getTime()));
        String mainText = sdf.format(calendar.getTime());
        String subText = "You reported a fatigue level of " + result.getQuestion1().toString() + "."; // Update to new string method

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
                          layout.getId(),    ConstraintSet.LEFT); // ConstraintSet.PARENT_ID / layout.getId() both work
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