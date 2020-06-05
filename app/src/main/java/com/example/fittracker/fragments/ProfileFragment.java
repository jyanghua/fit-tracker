package com.example.fittracker.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.fittracker.History;
import com.example.fittracker.R;
import com.example.fittracker.auth.AuthActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private ActionBar actionBar;
    private List<History> workoutHistoryLastWeek;
    private TextView tvUsername;
    private TextView tvNumWorkouts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvNumWorkouts = view.findViewById(R.id.tvNumWorkouts);

        tvUsername.setText(ParseUser.getCurrentUser().getUsername());

        workoutHistoryLastWeek = new ArrayList<>();
        queryNumberWorkouts();
        queryWorkoutHistoryLastWeek();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = view.findViewById(R.id.toolbarProfile);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        // Display logo and set clickable
        actionBar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.getActivity().getMenuInflater().inflate(R.menu.profile_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

                Intent i = new Intent(getContext(), AuthActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void queryNumberWorkouts() {
        ParseQuery<History> query = ParseQuery.getQuery(History.class);
        query.include(History.KEY_USER);
        query.whereEqualTo(History.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<History>() {
            @Override
            public void done(List<History> workoutHistory, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting workout history", e);
                } else {
                    Log.i(TAG, "Number of workouts: " + workoutHistory.size());
                    tvNumWorkouts.setText(String.valueOf(workoutHistory.size()));
                }
            }
        });
        ;
    }

    private void queryWorkoutHistoryLastWeek() {

        ParseQuery<History> query = ParseQuery.getQuery(History.class);
        query.include(History.KEY_USER);
        query.setLimit(25);
        query.whereEqualTo(History.KEY_USER, ParseUser.getCurrentUser());
        query.addAscendingOrder(History.KEY_DATE);
        query.findInBackground(new FindCallback<History>() {
            @Override
            public void done(List<History> workoutHistory, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting workout history", e);
                } else {
                    for (History workout: workoutHistory) {
                        Log.i(TAG, "Workout History: " + workout.getName() + ", Day: " + workout.getWorkoutDay());
                    }
                    workoutHistoryLastWeek.addAll(workoutHistory);
//                    displayWorkoutsPerWeek();
                    displayFavoriteWorkouts();
                }
            }
        });
    }

//    /**
//     * Function that displays the workout history from the past week on the Line Chart.
//     * Implemented using MPAndroidCharts.
//     */
//    private void displayWorkoutsPerWeek() {
//
//        BarChart workoutsPerWeekChart;
//        BarDataSet workoutsPerWeekDataSet;
//
//        workoutsPerWeekChart =  (BarChart) getView().findViewById(R.id.bcWorkoutsPerWeek);
//        workoutsPerWeekChart.getXAxis().setPosition(BOTTOM);
//
//        // Custom date and time format for the MPAndroidChart X Axis
//        // In this case it is only relevant to show the time since it is the last 24 hours.
//        ValueFormatter formatter = new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                Date date = new Date((long) value);
//                SimpleDateFormat fmt = new SimpleDateFormat("M-d");
//                return fmt.format(date);
//            }
//        };
//
//        workoutsPerWeekChart.getXAxis().setValueFormatter(formatter);
//
//        // List of entries that will populate the line chart
//        ArrayList<BarEntry> yValues = new ArrayList<>();
//
//        final LocalDate date = LocalDate.now();
//        LocalDate dateMinus7Days = date.minusDays(7);
//
//        //Format and display date
//        List<Date> dateX = new ArrayList<>();
//        List<Integer> workoutsY = new ArrayList<>();
//        int count = 0;
//
//        for(History h: workoutHistoryLastWeek) {
//            LocalDate current = Instant.ofEpochMilli(h.getDate().getTime())
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate();
//
//            if(dateMinus7Days.getDayOfYear() == current.getDayOfYear()) {
//                count++;
//            } else {
//                dateX.add(h.getDate());
//                workoutsY.add(count);
//                count = 0;
//                dateMinus7Days = current;
//            }
//        }
//
//        for(int i = 0; i < dateX.size(); i++) {
//            Log.i(TAG, "Date" + dateX.get(i).toInstant().getEpochSecond());
//            Log.i(TAG, "Workouts count" + workoutsY.get(i));
//            yValues.add(new BarEntry(dateX.get(i).toInstant().getEpochSecond() * 1000L, workoutsY.get(i)));
//        }
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(10000001, 4));
//        entries.add(new BarEntry(10000002, 1));
//        entries.add(new BarEntry(10000003, 2));
//        entries.add(new BarEntry(10000004, 3));
//        entries.add(new BarEntry(10000005, 4));
//        entries.add(new BarEntry(10000006, 5));
//
//        YAxis axisLeft = workoutsPerWeekChart.getAxisLeft();
//        axisLeft.setGranularity(5f);
//        axisLeft.setAxisMinimum(0);
//
//        YAxis axisRight = workoutsPerWeekChart.getAxisRight();
//        axisRight.setGranularity(5f);
//        axisRight.setAxisMinimum(0);
//
//        BarDataSet barDataSet = new BarDataSet(entries, "No Of Employee");
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(barDataSet);
//        BarData data = new BarData(dataSets);
//        data.setBarWidth(0.8f);
//
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        workoutsPerWeekChart.setData(data);
//
//        // Refreshes the chart
//        workoutsPerWeekChart.invalidate();
//    }

    private void displayFavoriteWorkouts() {
        PieChart pieChart = getView().findViewById(R.id.pcWorkouts);
        HashMap<String, Integer> map = new HashMap<>();

        for(History h: workoutHistoryLastWeek) {
            if (map.containsKey(h.getName())){
                map.replace(h.getName(), map.get(h.getName()) + 1);
            } else {
                map.put(h.getName(), 1);
            }
        }

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        PieData data = new PieData(dataSet);
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value)) + "%";
            }
        });

        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(2500, 2500);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawEntryLabels(true);
        pieChart.setDrawMarkers(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(18f);
        pieChart.setHoleRadius(32f);
        pieChart.setTransparentCircleRadius(38f);
        pieChart.getLegend().setEnabled(false);
    }

}
