package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by Mustafa on 15.4.2015.
 */
public class Yakinimdakiler extends Activity implements LocationListener {
    // Google Map
    private GoogleMap googleMap;
    Bundle bdn;
    String latitude;
    String longitude;
    String alinmis;
    TextView latitudee,longitudee,veri;
    int k_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yakinimdakiler);
        k_id = getIntent().getIntExtra("k_id", 0);

        try {
            String result="";
            InputStream isr=null;
            try {
                String query1= URLEncoder.encode(String.valueOf(k_id), "utf-8");
                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost=new HttpPost("http://soysalmustafa.com/islem.php?type=yakinimdakiler&k_id="+query1);
                HttpResponse response=httpclient.execute(httppost);
                HttpEntity entity=response.getEntity();
                isr=entity.getContent();
            }
            catch (Exception e) {
                Log.e("log_tag", "Error in Http connection" + e.toString());

            }
            //response to string
            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(isr,"utf-8"),8);
                StringBuilder sb=new StringBuilder();
                String line=null;
                while((line=reader.readLine()) != null)
                {
                    sb.append(line+"\n");
                }
                isr.close();
                result=sb.toString();

            }
            catch (Exception e) {
                Log.e("log_tag","Error converting result"+e.toString());
            }
            //parse json data
            try {
                    String s="";
                JSONArray jArray=new  JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject json=jArray.getJSONObject(i);
                    latitude=json.getString("latitude");
                    longitude=json.getString("longitude");
                    s=json.getString("adi")+json.getString("soyadi");;
                    Double latitu=Double.parseDouble(latitude);
                    Double longitu=Double.parseDouble(longitude);


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
                                CameraUpdateFactory.newLatLng(new LatLng(latitu,
                                        longitu));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(11);

                        googleMap.moveCamera(center);
                        googleMap.animateCamera(zoom);


                    }
                    catch (Exception e) {

                    }
                    if(i==1) {
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(latitu, longitu))
                                .title(s);
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        Log.e("Location", "> " + latitude + ", "
                                + longitude);
                        googleMap.addMarker(marker);
                    }
                    else if(i==2)
                    {
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(latitu, longitu))
                                .title(s);
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        Log.e("Location", "> " + latitude + ", "
                                + longitude);
                        googleMap.addMarker(marker);
                    }
                    else
                    {
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(latitu, longitu))
                                .title(s);
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        Log.e("Location", "> " + latitude + ", "
                                + longitude);
                        googleMap.addMarker(marker);
                    }
                }


            } catch (Exception e) {
                Log.e("log_tag","Error Parsing Data"+e.toString());
            }


        }
        catch (Exception e) {

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
