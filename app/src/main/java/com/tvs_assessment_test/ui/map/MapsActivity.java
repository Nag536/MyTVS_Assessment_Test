package com.tvs_assessment_test.ui.map;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.tvs_assessment_test.R;
import com.tvs_assessment_test.data.model.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initViews();
    }

    private void initViews() {
        new getLatLongOfCities().execute();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private class getLatLongOfCities extends AsyncTask<String, String, String> {

        final List<LatLng> listOfLatLang = new ArrayList<>(); // A list to save the coordinates if they are available
        final List<String> cityNames = new LinkedList<>();
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MapsActivity.this,
                    "",
                    "Loading...");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            if (Geocoder.isPresent()) {
                try {
                    Geocoder gc = new Geocoder(MapsActivity.this);
                    String arrayAsString = getIntent().getExtras().getString("sourceData");
                    List<Employee> sourceDataArrayList = new LinkedList<>(Arrays.asList(new Gson().fromJson(arrayAsString, Employee[].class)));

                    for (int i = 0; i < sourceDataArrayList.size(); i++) {

                        List<Address> addresses = gc.getFromLocationName(sourceDataArrayList.get(i).getCountry(),
                                5); // get the found Address Objects
                        for (Address a : addresses) {
                            if (a.hasLatitude() && a.hasLongitude()) {
                                listOfLatLang.add(new LatLng(a.getLatitude(), a.getLongitude()));
                                cityNames.add(sourceDataArrayList.get(i).getCountry());
                            }
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();

            if (listOfLatLang != null && listOfLatLang.size() > 0
                    && cityNames != null && cityNames.size() > 0) {

                madeCustomMarkers(listOfLatLang, cityNames);

            } else {
                // Add a marker in Sydney and move the camera by default
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }

            super.onPostExecute(s);
        }
    }

    private void madeCustomMarkers(List<LatLng> listOfLatLang, List<String> cityNames) {

        for (int i = 0; i < listOfLatLang.size(); i++) {

            LatLng countryLatLong = new LatLng(listOfLatLang.get(i).latitude, listOfLatLang.get(i).longitude);

            mMap.addMarker(new MarkerOptions()
                    .position(countryLatLong)
                    .title(cityNames.get(i)));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(listOfLatLang.get(0).latitude,
                listOfLatLang.get(0).longitude)));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

}
