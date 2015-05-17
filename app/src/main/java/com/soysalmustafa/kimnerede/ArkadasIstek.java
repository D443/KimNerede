package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;

/**
 * Created by Mustafa on 25.4.2015.
 */
public class ArkadasIstek extends Activity {
    ArrayList<Kisi> cars = new ArrayList<Kisi>();
    Button btnAra;
    private ListView listViewExample;
    Button btnOnay,btnRed;
    int k_id;
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arkadas_istek);
        k_id = getIntent().getIntExtra("k_id", 0);
         //ListView için elemanlar oluşturuyoruz.
        listViewExample=(ListView)findViewById(R.id.listViewExample);
        String result="";
        InputStream isr=null;
        try {
            String query1 = URLEncoder.encode(String.valueOf(k_id), "utf-8");
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost=new HttpPost("http://soysalmustafa.com/islem.php?type=requestliste&k_id="+query1);
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
            cars.clear();
            JSONArray jArray=new  JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json=jArray.getJSONObject(i);
                Kisi yeni_kisi=new Kisi();
                yeni_kisi.setAd(json.getString("adi"));
                yeni_kisi.setSoyad(json.getString("soyadi"));
                yeni_kisi.setId(json.getInt("id"));
                yeni_kisi.setK_id(k_id);
                cars.add(yeni_kisi);
            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag","Error Parsing Data"+e.toString());
        }

//Çalıştığımız Activity ve elemanları Adapter'a gönderiyoruz.
//Adapter ile, oluşturduğumuz listview'e elemanları atayacağız.
        MyListAdapter2 adapter = new MyListAdapter2(this, cars);

//Hazırlanmış olan adapter'ı, listview'in geçerli adapter'ı olarak ayarlıyoruz.
        listViewExample.setAdapter(adapter);

    }

}
