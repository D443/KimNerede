package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UyeKayit extends Activity {

    private EditText adi;
    private EditText soyadi;
    private EditText kullanici_adi;
    private EditText sifre;
    private TextView hata;
    private Button btnKaydol;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uye_kayit);
        adi=(EditText)findViewById(R.id.etAd);
        soyadi=(EditText)findViewById(R.id.etSoyad);
        kullanici_adi=(EditText)findViewById(R.id.etKullanici);
        sifre=(EditText)findViewById(R.id.etSifre);
        hata=(TextView)findViewById(R.id.txtview);
        btnKaydol=(Button)findViewById(R.id.btnKayitOl);

        btnKaydol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ekle();
            }
        });
    }
    public  void ekle()
    {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            String query1 = URLEncoder.encode(adi.getText().toString(), "utf-8");// utf-8 yaparak get verisinin boşluklarını%20 olmasını ve programın hata vermesini engelledik
            String query2 = URLEncoder.encode(soyadi.getText().toString(), "utf-8");
            String query3 = URLEncoder.encode(kullanici_adi.getText().toString(), "utf-8");
            String query4 = URLEncoder.encode(sifre.getText().toString(), "utf-8");
            String query5=URLEncoder.encode(imei,"utf-8");
            request.setURI(new URI("http://soysalmustafa.com/islem.php?type=add&ad="+query1+"&soyad="+query2+"&k_adi="+query3+"&sifre="+query4+"&imei="+query5)); //Get isteğimizi URL yoluyla belirliyoruz.
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
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();//toast mesajı olarakta gösterdik

        }
        catch(IllegalStateException e)
        {

            hata.setText("Hata: "+e.toString());
        }
        catch(Exception ex){
            hata.setText("Hata: "+ex.toString());
        }
    }

}
