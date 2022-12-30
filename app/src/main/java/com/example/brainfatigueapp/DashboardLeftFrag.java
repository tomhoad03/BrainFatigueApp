package com.example.brainfatigueapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
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
        // Get the layouts
        ScrollView vertical = getView().findViewById(R.id.activity_left_fragment_reports_vertical);
        vertical.setScrollbarFadingEnabled(true); // Make the scrollbar always visible                                did this make a difference? investigate
        ConstraintLayout layout = getView().findViewById(R.id.activity_left_fragment_reports_container);
        LineChart chart1 = getView().findViewById(R.id.activity_left_fragment_graph_1);
        LineChart chart2 = getView().findViewById(R.id.activity_left_fragment_graph_2);
        LineChart chart3 = getView().findViewById(R.id.activity_left_fragment_graph_3);

        // Retrieve the stored data from the database
        List<SurveyResult> surveyResults = retrieveDatabaseData();

        // Draw a graph from the energy level data
        drawLineGraph1(surveyResults, chart1);

        // Draw a second graph from the reaction time data
        drawLineGraph2(surveyResults, chart2);

        // Draw a third graph from the fitbit data
        drawLineGraph3(surveyResults, chart3);

        // Format the graphs
        formatGraph(surveyResults, chart1);
        formatGraph(surveyResults, chart2);
        formatGraph(surveyResults, chart3);

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

        int boxCount = 0;
        // Get the current time
        LocalTime now = LocalTime.now();
        // Get the time of the daily summary notification from the settings database
        Float summaryTimeFloat = 20f; // Hardcoded until Tom shows me how to get stuff from the database
        LocalTime summaryTime = LocalTime.parse("20:00"); // parse whatever the database stores into 'summaryTime'
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, HH:mmaaa", Locale.UK);

        ArrayList<Entry> chartData = new ArrayList<Entry>();
        float dateCount = 0;
        if (database != null) {
            for (SurveyResult result : database) {
                // Get the time that the survey was taken
                GregorianCalendar surveyCalendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
                surveyCalendar.setTimeInMillis(result.getSurveyResultId());

                if (now.compareTo(summaryTime) > 0) {
                    // If the current time is after the notification time, show only reports from today
                    // "today's reports" defined by surveys that were taken AFTER the time now
                    // MINUS the time now in hours (around 00:00am)
                    GregorianCalendar lowerBoundCalendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
                    lowerBoundCalendar.setTimeInMillis(System.currentTimeMillis());
                    lowerBoundCalendar.add(Calendar.HOUR, -1 * (int) summaryTimeFloat.longValue());
                    System.out.println("(TODAY) Displaying only times after: " + sdf.format(lowerBoundCalendar.getTime()));
                    if (surveyCalendar.compareTo(lowerBoundCalendar) > 0) {
                        // Include this result in the chart
                        chartData.add(new Entry(dateCount, result.getQuestion1()));
                        dateCount++;
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
                        // Include this result in the chart
                        chartData.add(new Entry(dateCount, result.getQuestion1()));
                        dateCount++;
                    }
                }
            }
        }

        return chartData;
    }

    private void formatGraph(List<SurveyResult> database, LineChart chart) {
        // Format the appearance of the graphs

        // Set the background colour - actually did this by setting the colour of the LinearLayout in the xml instead
        // chart.setBackgroundColor(getResources().getColor(R.color.off_white));
        chart.setDrawGridBackground(true);
        chart.setDrawBorders(true);
        chart.setBorderWidth(2f);

        // Change the width between data points on the graphs
        chart.setVisibleXRangeMaximum(3f);
        chart.setVisibleXRangeMinimum(3f);

        // Set the amount of interaction with the chart
        chart.setTouchEnabled(true);
        chart.setScaleYEnabled(false); // Users can't scale the y axis, only x
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDragDecelerationEnabled(false);

        // Set the text that appears if the user hasn't taken any surveys yet
        chart.setNoDataText("Check back here later to see your daily summary!");
        // Disable the legend
        chart.getLegend().setEnabled(false);
        // Disable the description
        chart.getDescription().setEnabled(false);
        // Give the graph a nudge to the left and right so that the x axis labels don't get cutoff
        chart.setExtraOffsets(7f, 0f, 35f, 0f);

        // Format the label of data points on the x axis
        ArrayList<String> resultDatetimes = new ArrayList<String>();
        for (SurveyResult result : database) {
            // System.out.println("GRAPH CAPTION: " + result.getSurveyResultIdAsString());
            resultDatetimes.add(result.getSurveyResultIdAsString());
        }

        ValueFormatter floatToString = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                try {
                    // System.out.println("Value: " + value);
                    return resultDatetimes.get((int) value);
                } catch (Exception e) {
                    // This usually happens when the graph only has one entry, return the first one
                    return resultDatetimes.get(0);
                }
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
        yLeft.setTextSize(12f);
        // y.setTypeface(tf); Maybe this works for other people?
        YAxis yRight = chart.getAxisRight();
        yRight.setEnabled(false);

        chart.invalidate(); // Don't think I need to update the graph, but might as well at the end of this function
    }

    private void drawLineGraph1(List<SurveyResult> surveyResults, LineChart lineChart) {
        LineDataSet lineChartData = new LineDataSet(getChartData(surveyResults), "(energy level)");
        lineChart.setGridBackgroundColor(getResources().getColor(R.color.custom_light_blue_A));
        lineChartData.setDrawValues(false);
        lineChartData.setCircleColor(getResources().getColor(R.color.custom_navy_A));
        lineChartData.setCircleSize(5f);
        lineChartData.setCircleHoleColor(getResources().getColor(R.color.white));
        lineChartData.setCircleHoleRadius(2f);
        lineChartData.setLineWidth(3f);
        lineChartData.setColor(getResources().getColor(R.color.custom_navy_A));
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<ILineDataSet>();
        iLineDataSets.add(lineChartData);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void drawLineGraph2(List<SurveyResult> surveyResults, LineChart lineChart2) {
        LineDataSet lineChartData2 = new LineDataSet(getChartData(surveyResults), "(reaction time)");
        lineChart2.setGridBackgroundColor(getResources().getColor(R.color.custom_graph_purple));
        lineChartData2.setDrawValues(false);
        lineChartData2.setCircleColor(getResources().getColor(R.color.custom_navy_A));
        lineChartData2.setCircleSize(5f);
        lineChartData2.setCircleHoleColor(getResources().getColor(R.color.white));
        lineChartData2.setCircleHoleRadius(2f);
        lineChartData2.setLineWidth(3f);
        lineChartData2.setColor(getResources().getColor(R.color.custom_navy_A));
        ArrayList<ILineDataSet> iLineDataSets2 = new ArrayList<ILineDataSet>();
        iLineDataSets2.add(lineChartData2);

        LineData lineData2 = new LineData(iLineDataSets2);
        lineChart2.setData(lineData2);
        lineChart2.invalidate();
    }

    private void drawLineGraph3(List<SurveyResult> surveyResults, LineChart lineChart3) {
        LineDataSet lineChartData3 = new LineDataSet(getChartData(surveyResults), "(fitbit data)");
        lineChart3.setGridBackgroundColor(getResources().getColor(R.color.custom_graph_green));
        lineChart3.setGridBackgroundColor(getResources().getColor(R.color.custom_graph_green));
        lineChartData3.setDrawValues(false);
        lineChartData3.setCircleColor(getResources().getColor(R.color.custom_navy_A));
        lineChartData3.setCircleSize(5f);
        lineChartData3.setCircleHoleColor(getResources().getColor(R.color.white));
        lineChartData3.setCircleHoleRadius(2f);
        lineChartData3.setLineWidth(3f);
        lineChartData3.setColor(getResources().getColor(R.color.custom_navy_A));
        ArrayList<ILineDataSet> iLineDataSets3 = new ArrayList<ILineDataSet>();
        iLineDataSets3.add(lineChartData3);

        LineData lineData3 = new LineData(iLineDataSets3);
        lineChart3.setData(lineData3);
        lineChart3.invalidate();
    }

    private void drawReportBoxes(List<SurveyResult> surveyResults, ConstraintLayout layout) {
        // Fill the fragment with a report box with the database entries from today only

        int boxCount = 0;
        // Get the current time
        LocalTime now = LocalTime.now();
        // Get the time of the daily summary notification from the settings database
        Float summaryTimeFloat = 20f; // Hardcoded until Tom shows me how to get stuff from the database
        LocalTime summaryTime = LocalTime.parse("20:00"); // parse whatever the database stores into 'summaryTime'
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, HH:mmaaa", Locale.UK);

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

                        // If there's at least one report to draw, disable the default text message
                        ConstraintLayout container = getView().findViewById(R.id.activity_left_fragment_graph_big_container);
                        TextView defaultMessage = getView().findViewById(R.id.activity_left_fragment_reports_container_message);
                        container.removeView(defaultMessage);
                        // This does get called each time, but it doesn't throw an error...
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, HH:mmaaa", Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
        calendar.setTimeInMillis(result.getSurveyResultId());
        //System.out.println("CURRENTLY: " + sdf.format(calendar.getTime()));
        String mainText = sdf.format(calendar.getTime());
        String subText = "You reported an energy level of " + result.getQuestion1().toString() + ".";

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