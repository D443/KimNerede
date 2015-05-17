package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;

public class UyeAnasayfa extends Activity implements LocationListener {
    int k_id;
    private Handler mHandler = new Handler();
    TextView goster;
    private LocationManager locationManager;
    private String provider;
    String enlemDegeri;
    String boylamDegeri;


    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder(+

        ).permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uye_anasayfa);
        k_id = getIntent().getIntExtra("k_id", 0);
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            gpsErisilemiyorUyarisi();
        }
        provider=LocationManager.GPS_PROVIDER;
        Location location=locationManager.getLastKnownLocation(provider);
        if(location!=null)
        {   provider=LocationManager.NETWORK_PROVIDER;
            Location locationNetwork=locationManager.getLastKnownLocation(provider);
            enlemDegeri=(String.valueOf(location.getLatitude()));
            boylamDegeri=(String.valueOf(location.getLongitude()));
            startTime();
        }
        else
        {

        }
        /**
         * Creating all buttons instances
         * */
        // Dashboard News feed button
        Button btn_konum = (Button) findViewById(R.id.btn_konum);

        // Dashboard Friends button
        Button btn_nerdeyim = (Button) findViewById(R.id.btn_nerdeyim);

        // Dashboard Messages button
        Button btn_arkadaslar = (Button) findViewById(R.id.btn_arkadaslar);

        // Dashboard Places button
        Button btn_arkadas_istek = (Button) findViewById(R.id.btn_arkadas_istek);

        // Dashboard Events button
        Button btn_arkadas_ara = (Button) findViewById(R.id.btn_arkadas_ara);

        // Dashboard Photos button
        Button btn_yakinimdakiler = (Button) findViewById(R.id.btn_yakinimdakiler);

        /**
         * Handling all button click events
         * */

        // Listening to News Feed button click
        btn_konum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), Konum.class);
                startActivity(i);
            }
        });

        // Listening Friends button click
        btn_nerdeyim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), Nerdeyim.class);
                startActivity(i);
            }
        });

        // Listening Messages button click
        btn_arkadaslar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent intent = new Intent(UyeAnasayfa.this, Arkadaslar.class);
                intent.putExtra("k_id",k_id);
                startActivity(intent);
            }
        });

        // Listening to Places button click
        btn_arkadas_istek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent intent = new Intent(UyeAnasayfa.this, ArkadasIstek.class);
                intent.putExtra("k_id",k_id);
                startActivity(intent);
            }
        });

        // Listening to Events button click
        btn_arkadas_ara.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent intent = new Intent(UyeAnasayfa.this, ArkadasAra.class);
                intent.putExtra("k_id",k_id);
                startActivity(intent);
            }
        });

        // Listening to Photos button click
        btn_yakinimdakiler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent intent = new Intent(UyeAnasayfa.this, Yakinimdakiler.class);
                intent.putExtra("k_id",k_id);
                startActivity(intent);
            }
        });
    }
    private void startTime() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 45000);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // buraya ne yapmak istiyorsan o kodu yaz.. Kodun sonlandıktan sonra 1 saniye sonra tekrar çalışacak şekilde handler tekrar çalışacak.
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                String query1 = URLEncoder.encode(enlemDegeri, "utf-8");// utf-8 yaparak get verisinin boşluklarını%20 olmasını ve programın hata vermesini engelledik
                String query2 = URLEncoder.encode(boylamDegeri, "utf-8");
                String query3=URLEncoder.encode(imei,"utf-8");
                String query4 = URLEncoder.encode(String.valueOf(k_id), "utf-8");
                request.setURI(new URI("http://soysalmustafa.com/islem.php?type=addkordinaat&latitude="+query1+"&longitude="+query2+"&imei="+query3+"&k_id="+query4)); //Get isteğimizi URL yoluyla belirliyoruz.
                HttpResponse response = httpclient.execute(request); //İsteğimizi gerçekleştiriyoruz.
                HttpEntity entity = response.getEntity(); //Gelen cevabı işliyoruz.
                String result = null;
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8); //utf-8 burda önemli
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result=sb.toString(); //Artık result stringi php tarafından ekrana print yada echo komutlarıyla yazdırılmış veriyi içeriyor.
               //  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();//toast mesajı olarakta gösterdik
                //Harita İşlemleri

            }
            catch(IllegalStateException e)
            {

            }
            catch(Exception ex){

            }

            mHandler.postDelayed(this, 45000);
        }
    };
    private void gpsErisilemiyorUyarisi()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Gps Kapali Açmak İster misiniz ?")
                .setCancelable(false)
                .setPositiveButton("GPS aktifleştir", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO Auto-generated method stub
                        gpsSecenekleriGoster();
                    }
                });
        builder.setNegativeButton("Hayır etkinleştirme", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private void gpsSecenekleriGoster()
    {
        Intent gpsOptionsIntent=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        locationManager.requestLocationUpdates(provider,400,1,this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        locationManager.removeUpdates(this);
    }



    @Override
    public void onLocationChanged(Location location) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        enlemDegeri=(String.valueOf(location.getLatitude()));
        boylamDegeri=(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Aktif konum bilgisi kaynağı:"+provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Pasif konum bilgisi kaynağı:"+provider, Toast.LENGTH_SHORT).show();
    }
}
