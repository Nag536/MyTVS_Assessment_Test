package com.tvs_assessment_test.ui.employees.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tvs_assessment_test.R;
import com.tvs_assessment_test.data.model.Employee;
import com.tvs_assessment_test.ui.map.MapsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EmployeesList extends AppCompatActivity {
    private List<Employee> sourceDataArrayList;
    private List<Employee> filteredList;
    private final Activity parentActivity = EmployeesList.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Employee List");

        initViews();
    }

    private void initViews() {

        sourceDataArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();

        String arrayAsString = getIntent().getExtras().getString("sourceData");
        //LinkedList, which supports faster remove while filtering
        sourceDataArrayList = new LinkedList<>(Arrays.asList(new Gson().fromJson(arrayAsString, Employee[].class)));
        filteredList = new LinkedList<>(Arrays.asList(new Gson().fromJson(arrayAsString, Employee[].class)));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final EmployeeListAdapter mAdapter = new EmployeeListAdapter(filteredList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EmployeesList.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(EmployeesList.this, 0));
        recyclerView.setAdapter(mAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder> implements Filterable {

        private final List<Employee> dataArrayList;
        private final CustomFilter mFilter;

        @Override
        public Filter getFilter() {
            return mFilter;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView empNameTV, empDesignationTV, empCountryTV, empZipCodeTV, empDateTV, empSalaryTV;
            private final LinearLayout itemLL;

            MyViewHolder(View view) {
                super(view);

                empNameTV = view.findViewById(R.id.empNameTV);
                empDesignationTV = view.findViewById(R.id.empDesignationTV);
                empCountryTV = view.findViewById(R.id.empCountryTV);
                empZipCodeTV = view.findViewById(R.id.empZipCodeTV);
                empDateTV = view.findViewById(R.id.empDateTV);
                empSalaryTV = view.findViewById(R.id.empSalaryTV);

                itemLL = view.findViewById(R.id.itemLL);

            }
        }


        @Override
        public int getItemCount() {
            return filteredList.size();
        }

        EmployeeListAdapter(List<Employee> dataArrayList) {
            this.dataArrayList = dataArrayList;
            mFilter = new CustomFilter(EmployeeListAdapter.this);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_employee, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

            final Employee dataItem = dataArrayList.get(position);

            holder.empNameTV.setText(dataItem.getName());
            holder.empDesignationTV.setText(dataItem.getDesignation());
            holder.empCountryTV.setText(dataItem.getCountry());
            holder.empZipCodeTV.setText(dataItem.getZipCode());
            holder.empDateTV.setText(dataItem.getDate());
            holder.empSalaryTV.setText(dataItem.getSalary());

            holder.itemLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent detailsScreen = new Intent(parentActivity, EmployeeDetails.class);
                    detailsScreen.putExtra("employeeData", dataItem);
                    startActivity(detailsScreen);
                }
            });

        }

        class CustomFilter extends Filter {
            private final EmployeeListAdapter mAdapter;

            private CustomFilter(EmployeeListAdapter mAdapter) {
                super();
                this.mAdapter = mAdapter;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filteredList.clear();
                final FilterResults results = new FilterResults();
                if (constraint.length() == 0) {
                    filteredList.addAll(sourceDataArrayList);
                } else {
                    final String filterPattern = constraint.toString().toLowerCase().trim();
                    for (final Employee mWords : sourceDataArrayList) {
                        if (mWords.getName().toLowerCase().contains(filterPattern)
                                || mWords.getDesignation().toLowerCase().contains(filterPattern)
                                || mWords.getCountry().toLowerCase().contains(filterPattern)
                                || mWords.getZipCode().toLowerCase().contains(filterPattern)
                                || mWords.getSalary().toLowerCase().contains(filterPattern)) {
                            filteredList.add(mWords);
                        }
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                this.mAdapter.notifyDataSetChanged();
            }

        }

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionGraph) {

            Intent barGraphIntent = new Intent(this, BarGraph.class);
            String arrayAsString = new Gson().toJson(sourceDataArrayList);
            barGraphIntent.putExtra("sourceData", arrayAsString);
            startActivity(barGraphIntent);

        } else if (id == R.id.actionMap) {

            Intent mapsIntent = new Intent(this, MapsActivity.class);
            String arrayAsString = new Gson().toJson(sourceDataArrayList);
            mapsIntent.putExtra("sourceData", arrayAsString);
            startActivity(mapsIntent);

        } else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
