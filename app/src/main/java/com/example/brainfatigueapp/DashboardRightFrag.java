package com.example.brainfatigueapp;

import android.graphics.Typeface;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
        LineChart chart1 = getView().findViewById(R.id.activity_right_fragment_graph_1);
        LineChart chart2 = getView().findViewById(R.id.activity_right_fragment_graph_2);

        // Retrieve the stored data from the database
        List<SurveyResult> surveyResults = retrieveDatabaseData();

        // Format the graphs
        formatGraph(surveyResults, chart1);
        formatGraph(surveyResults, chart2);

        // Draw a graph from the data
        drawLineGraph1(surveyResults, chart1);

        // Draw a second graph from the data
        drawLineGraph2(surveyResults, chart2);

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
        // Extract the information relevant to the line graph from the database

        ArrayList<Entry> chartData = new ArrayList<Entry>();
        float dateCount = 0; // Hardcoded for now, need conversion from date (stored as long) to date label for x axis
        for (SurveyResult result : database) {
            System.out.println("GRAPH CAPTION: " + result.getSurveyResultIdAsString());
            chartData.add(new Entry(dateCount, result.getQuestion1()));
            dateCount++;
        }

        /*
        chartData.add(new Entry(1, 15));
        chartData.add(new Entry(2, 50));
        chartData.add(new Entry(3, 25));
         */
        return chartData;
    }

    private void formatGraph(List<SurveyResult> database, LineChart chart) {
        // Format the appearance of the graphs

        // Set the background colour - actually did this by setting the colour of the LinearLayout in the xml instead
        // chart.setBackgroundColor(getResources().getColor(R.color.off_white));
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(getResources().getColor(R.color.custom_light_blue_A)); // Does this look ok???
        chart.setDrawBorders(true);
        chart.setBorderWidth(2f);

        // set min x visible...


        // Set the text that appears if the user hasn't taken any surveys yet
        chart.setNoDataText("Data will appear here after you take your first survey!");
        // Disable the legend - this could be used as the label for each of the graphs later (energy, steps, fitbit...)
        chart.getLegend().setEnabled(false);
        // Disable the description
        chart.getDescription().setEnabled(false);
        // Give the graph a nudge to the left and right so that the first x axis labels don't get cutoff
        chart.setExtraLeftOffset(7f);
        chart.setExtraRightOffset(14f); // Doesn't work?

        // Format the label of data points on the x axis
        ArrayList<String> resultDatetimes = new ArrayList<String>();
        for (SurveyResult result : database) {
            // System.out.println("GRAPH CAPTION: " + result.getSurveyResultIdAsString());
            resultDatetimes.add(result.getSurveyResultIdAsString());
        }

        ValueFormatter floatToString = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return resultDatetimes.get((int) value);
            }
        };

        // Format the x axis
        XAxis x = chart.getXAxis();
        x.setGranularity(1f); // set min step/interval to 1
        x.setTextSize(12f);
        // Typeface tf = ResourcesCompat.getFont(getContext(), R.font.spartan);
        // x.setTypeface(tf); Maybe this works for other people?
        x.setValueFormatter(floatToString); // apply the format/conversion function to the chart axis
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);

        // Format the y axes
        YAxis yLeft = chart.getAxis(YAxis.AxisDependency.LEFT);
        yLeft.setDrawGridLines(false);
        yLeft.setDrawZeroLine(true);
        yLeft.setAxisMinimum(0f);
        yLeft.setAxisMaximum(100f);
        yLeft.setLabelCount(5, true);
        yLeft.setTextSize(12f);
        // y.setTypeface(tf); Maybe this works for other people?
        YAxis yRight = chart.getAxisRight();
        yRight.setEnabled(false);

        chart.invalidate(); // Don't think I need to update the graph, but might as well at the end of this function
    }

    private void drawLineGraph1(List<SurveyResult> surveyResults, LineChart lineChart) {
        LineDataSet lineChartData = new LineDataSet(getChartData(surveyResults), "LINE CHART 1!");
        lineChartData.setDrawValues(false);
        lineChartData.setCircleColor(getResources().getColor(R.color.custom_purple_B));
        lineChartData.setCircleSize(5f);
        lineChartData.setCircleHoleColor(getResources().getColor(R.color.off_white));
        lineChartData.setCircleHoleRadius(2f);
        lineChartData.setLineWidth(3f);
        lineChartData.setColor(getResources().getColor(R.color.custom_purple_B));
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<ILineDataSet>();
        iLineDataSets.add(lineChartData);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void drawLineGraph2(List<SurveyResult> surveyResults, LineChart lineChart2) {
        LineDataSet lineChartData2 = new LineDataSet(getChartData(surveyResults), "LINE CHART 2!");
        lineChartData2.setLineWidth(3f);
        lineChartData2.setColor(getResources().getColor(R.color.custom_purple_A));
        ArrayList<ILineDataSet> iLineDataSets2 = new ArrayList<ILineDataSet>();
        iLineDataSets2.add(lineChartData2);

        LineData lineData2 = new LineData(iLineDataSets2);
        lineChart2.setData(lineData2);
        lineChart2.invalidate();
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, HH:mmaaa", Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
        calendar.setTimeInMillis(result.getSurveyResultId());
        // System.out.println("CURRENTLY: " + sdf.format(calendar.getTime()));
        String mainText = sdf.format(calendar.getTime());
        String subText = "You reported an energy level of " + result.getQuestion1().toString() + "."; // Update to new string method

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