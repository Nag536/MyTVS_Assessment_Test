package com.tvs_assessment_test.ui.employees.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvs_assessment_test.R;
import com.tvs_assessment_test.data.model.Employee;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeDetails extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;
    private ImageView capturedIV;
    private TextView timeStampTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_deatils);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Employee Details");

        initViews();
    }

    private void initViews() {

        Employee dataItem = (Employee) getIntent().getSerializableExtra("employeeData");

        TextView empNameTV = findViewById(R.id.empNameTV);
        TextView empDesignationTV = findViewById(R.id.empDesignationTV);
        TextView empCountryTV = findViewById(R.id.empCountryTV);
        TextView empZipCodeTV = findViewById(R.id.empZipCodeTV);
        TextView empDateTV = findViewById(R.id.empDateTV);
        TextView empSalaryTV = findViewById(R.id.empSalaryTV);

        empNameTV.setText(dataItem.getName());
        empDesignationTV.setText(dataItem.getDesignation());
        empCountryTV.setText(dataItem.getCountry());
        empZipCodeTV.setText(dataItem.getZipCode());
        empDateTV.setText(dataItem.getDate());
        empSalaryTV.setText(dataItem.getSalary());

        capturedIV = findViewById(R.id.capturedIV);
        timeStampTV = findViewById(R.id.timeStampTV);

        findViewById(R.id.captureImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        capturedIV.setImageBitmap(thumbnail);
        timeStampTV.setText(getCurrentTimeStamp());
    }

    /**
     *
     * @return yyyy-MM-dd HH:mm:ss formate date as string
     */
    private static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
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
