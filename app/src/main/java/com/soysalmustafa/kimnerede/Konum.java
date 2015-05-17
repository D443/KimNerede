package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
public class Konum extends Activity implements LocationListener {
    TextView adres;
    private LocationManager locationManager;
    private String provider;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konum);
        adres=(TextView)findViewById(R.id.Adres);
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
            }
                catch (Exception e)
            {

            }
        }
        else
        {   provider= LocationManager.NETWORK_PROVIDER;
            Location locationNetwork=locationManager.getLastKnownLocation(provider);
            if(locationNetwork!=null)
            {
                double latitude = locationNetwork.getLatitude();
                double longitude = locationNetwork.getLongitude();

                try{
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());

                }
                catch (Exception e)
                {

                }

            }
            else
            {
                adres.setText("Konum Bilgisi Alınamadı");
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
            adres.setText(locationAddress);
        }
    }

}

