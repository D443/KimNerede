package com.soysalmustafa.kimnerede;

import android.app.Activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;


public class Nerdeyim extends Activity implements LocationListener {
    private GoogleMap googleMap;
    String provider;
    private LocationManager locationManager;
    String adresBilgi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nerdeyim);
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        provider= LocationManager.GPS_PROVIDER;
        Location location=locationManager.getLastKnownLocation(provider);
        if(location!=null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            try {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getApplicationContext(), new GeocoderHandler());
                try {
                    // Loading map
                    initilizeMap();

                    // Changing map type
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    //  googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

                    // Showing / hiding your current location
                    googleMap.setMyLocationEnabled(true);

                    // Enable / Disable zooming controls
                    googleMap.getUiSettings().setZoomControlsEnabled(true);

                    // Enable / Disable my location button
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                    // Enable / Disable Compass icon
                    googleMap.getUiSettings().setCompassEnabled(true);

                    // Enable / Disable Rotate gesture
                    googleMap.getUiSettings().setRotateGesturesEnabled(true);

                    // Enable / Disable zooming functionality
                    googleMap.getUiSettings().setZoomGesturesEnabled(true);
                    //Zoom
                    CameraUpdate center=
                            CameraUpdateFactory.newLatLng(new LatLng(latitude,
                                    longitude));
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(11);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);

                    //Adres
                    Geocoder geo = new Geocoder(Nerdeyim.this.getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
                    if (addresses.isEmpty()) {
                        //Adres yoksa
                        // Adding a marker
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(latitude, longitude))
                                .title("Konum Adresi Alinamadi");
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        Log.e("Location", "> " + latitude + ", "
                                + longitude);
                        googleMap.addMarker(marker);
                    }
                    else {
                        if (addresses.size() > 0) {
                            // Adding a marker
                            MarkerOptions marker = new MarkerOptions().position(
                                    new LatLng(latitude, longitude))
                                    .title(addresses.get(0).getPostalCode() +", "+ addresses.get(0).getCountryName());
                            marker.icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            Log.e("Location", "> " + latitude + ", "
                                    + longitude);
                            googleMap.addMarker(marker);

                        }
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e)
            {

            }
        }
        else
        {   locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            provider= LocationManager.NETWORK_PROVIDER;
            Location locationNetwork=locationManager.getLastKnownLocation(provider);
            if(locationNetwork!=null)
            {
                double latitude = locationNetwork.getLatitude();
                double longitude = locationNetwork.getLongitude();

                try{

                    try {
                        // Loading map
                        initilizeMap();

                        // Changing map type
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        //  googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

                        // Showing / hiding your current location
                        googleMap.setMyLocationEnabled(true);

                        // Enable / Disable zooming controls
                        googleMap.getUiSettings().setZoomControlsEnabled(true);

                        // Enable / Disable my location button
                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                        // Enable / Disable Compass icon
                        googleMap.getUiSettings().setCompassEnabled(true);

                        // Enable / Disable Rotate gesture
                        googleMap.getUiSettings().setRotateGesturesEnabled(true);

                        // Enable / Disable zooming functionality
                        googleMap.getUiSettings().setZoomGesturesEnabled(true);

                        //Zoom
                        CameraUpdate center=
                                CameraUpdateFactory.newLatLng(new LatLng(latitude,
                                        longitude));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(11);

                        googleMap.moveCamera(center);
                        googleMap.animateCamera(zoom);

                   //Adres
                        Geocoder geo = new Geocoder(Nerdeyim.this.getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
                        if (addresses.isEmpty()) {
                           //Adres yoksa
                            // Adding a marker
                            MarkerOptions marker = new MarkerOptions().position(
                                    new LatLng(latitude, longitude))
                                    .title("Konum Adresi Alinamadi");
                            marker.icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            Log.e("Location", "> " + latitude + ", "
                                    + longitude);
                            googleMap.addMarker(marker);
                        }
                        else {
                            if (addresses.size() > 0) {
                                // Adding a marker
                                MarkerOptions marker = new MarkerOptions().position(
                                        new LatLng(latitude, longitude))
                                        .title(addresses.get(0).getPostalCode() +", "+ addresses.get(0).getCountryName());
                                marker.icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                Log.e("Location", "> " + latitude + ", "
                                        + longitude);
                                googleMap.addMarker(marker);

                            }
                        }





                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                catch (Exception e)
                {

                }

            }
            else
            {
                //adres.setText("Konum Bilgisi Alınamadı");
            }

        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;

            }
           adresBilgi=locationAddress;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
