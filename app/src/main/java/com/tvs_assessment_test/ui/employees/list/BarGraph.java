package com.tvs_assessment_test.ui.employees.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tvs_assessment_test.R;
import com.tvs_assessment_test.data.model.Employee;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BarGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Employee Salaries Graph");

        initViews();
    }

    private void initViews() {
        String arrayAsString = getIntent().getExtras().getString("sourceData");
        List<Employee> sourceDataArrayList = new LinkedList<>(Arrays.asList(new Gson().fromJson(arrayAsString, Employee[].class)));

        GraphView graph = findViewById(R.id.graph);

        DataPoint[] dataPoints = new DataPoint[10];

        for (int i = 0; i < 10; i++) {
            String salaryItem = sourceDataArrayList.get(i).getSalary()
                    .replace("$", "")
                    .replace(",", "");
            dataPoints[i] = new DataPoint(i, Integer.parseInt(salaryItem));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
