package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;

public class UyeGiris extends Activity implements View.OnClickListener {
    EditText k_ad,sifre;
    TextView hata;
    Button btnGiris,btnKayit;
    int k_id;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uye_giris);
        k_ad=(EditText)findViewById(R.id.etKullanici);
        sifre=(EditText)findViewById(R.id.etSifre);
        hata=(TextView)findViewById(R.id.etHata);
        btnGiris=(Button)findViewById(R.id.btnGiris);
        btnKayit=(Button)findViewById(R.id.btnKayit);
        btnGiris.setOnClickListener(this);
        btnKayit.setOnClickListener(this);
    }

    public void girisKontrol()
    {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            String query1 = URLEncoder.encode(k_ad.getText().toString(), "utf-8");// utf-8 yaparak get verisinin boşluklarını%20 olmasını ve programın hata vermesini engelledik
            String query2 = URLEncoder.encode(sifre.getText().toString(), "utf-8");
            request.setURI(new URI("http://soysalmustafa.com/islem.php?type=enter&k_ad="+query1+"&sifre="+query2)); //Get isteğimizi URL yoluyla belirliyoruz.
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
            k_id=0;
            if(result==null || result.equals(""))
            { // Sonuc Bos donuyorsa
                Toast.makeText(getApplicationContext(),"Giris Bilgileri Hatali", Toast.LENGTH_LONG).show();//toast mesajı olarakta gösterdik

            }
            else
            {
                JSONArray jArray = new JSONArray(result);
                for(int i=0; i<jArray.length();i++){
                    JSONObject json = jArray.getJSONObject(i);
                    k_id=k_id+json.getInt("id");
                }
                Intent intent = new Intent(UyeGiris.this, UyeAnasayfa.class);
                intent.putExtra("k_id",k_id);
                startActivity(intent);
            }



        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),"Giris Bilgileri Hatali", Toast.LENGTH_LONG).show();
           ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnGiris:
                girisKontrol();
                break;
            case R.id.btnKayit:
                startActivity(new Intent("android.intent.action.uye_kayit"));
                break;
            default:
                break;
        }
    }
}
