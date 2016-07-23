package com.example.wagh.drishti;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button button;
    EditText editText;
    String address;
    double lat,lon,userlat,userlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button=(Button)findViewById(R.id.b1);
        editText=(EditText)findViewById(R.id.et1);



        final Intent i = new Intent(getBaseContext(),history.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address=editText.getText().toString();

                Geocoder geocoder=new Geocoder(MapsActivity.this);
                List<android.location.Address> addresses;
                LatLng geoPoint=null;

                try
                {
                    addresses=geocoder.getFromLocationName(address,5);
                    android.location.Address location =addresses.get(0);
                    lon=location.getLongitude();
                    lat=location.getLatitude();
                    geoPoint= new LatLng(location.getLatitude(),location.getLongitude());
                    Toast.makeText(MapsActivity.this,"This is "+geoPoint+"your answer",Toast.LENGTH_SHORT).show();

                    //updating the cursor
                    LatLng user =new LatLng(lat,lon);
                    mMap.addMarker(new MarkerOptions().position(user).title("This is "+address+"!"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(user));

                    Toast.makeText(getApplicationContext(),"LONG PRESS FOR NEXT ACTIVITY TO OPEN",Toast.LENGTH_LONG).show();


                    /** GPS

                    LocationManager locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
                    LocationListener locationListener11=new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            userlat=location.getLatitude();
                            userlong=location.getLongitude();

                        }
                    };
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, (android.location.LocationListener) locationListener11);

                    LatLng userposition=new LatLng(userlat,userlong);
                    mMap.addMarker(new MarkerOptions().position(userposition).title("I m Here"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(userposition));**/



                }
                catch (IOException e)
                {
                    Log.e("ERROR","error geopoint",e);
                }

                catch (SecurityException se)
                {
                    Log.e("ERROR","error GPS",se);
                }

            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // New Intent Saving Data
                i.putExtra("Lat",lat);
                i.putExtra("Long",lon);
                i.putExtra("Address",address);
                startActivity(i);

                return false;
            }
        });

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

        // Add a marker in Sydney and move the camera
        /**LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));**/

        //adding new marker


    }
}
