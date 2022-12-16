package com.example.brainfatigueapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardPopupFrag extends DialogFragment {

    public DashboardPopupFrag() {
        // Required empty public constructor
    }
    public DashboardPopupFrag(String title) {
        // Required empty public constructor
        this.title = title;
    }
    String title = "";
    /*
    public static DashboardPopupFrag newInstance(String param1, String param2) {
        DashboardPopupFrag fragment = new DashboardPopupFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
     */

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
    }
}