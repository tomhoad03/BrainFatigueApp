package com.example.brainfatigueapp;

import android.os.Bundle;
import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
        ConstraintLayout layout = (ConstraintLayout) getView().findViewById(R.id.activity_right_fragment_reports_container);

        // Format the button
        Button newButton = new Button(getActivity());
        newButton.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        newButton.setText("Button");
        // newButton.setId(111);

        //add button to the layout
        layout.addView(newButton);
    }
}