package com.soysalmustafa.kimnerede;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URLEncoder;
import java.util.ArrayList;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;


public class ArkadasAra extends Activity {
    ArrayList<Kisi> cars = new ArrayList<Kisi>();
    private ListView listView;
    private TextView Kullanici_Adi;
    Button btnAra;
    int k_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arkadas_ara);
        k_id = getIntent().getIntExtra("k_id", 0);
        Kullanici_Adi=(TextView)findViewById(R.id.etKullanici);
        //ListView'e erişebilmek için findViewById komutu ile handle alıyoruz.
        listView = (ListView) findViewById(R.id.listViewExample);
        //ListView için elemanlar oluşturuyoruz.
        btnAra=(Button)findViewById(R.id.btnAra);
        btnAra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             getData();
            }
        });

    }
    public void getData()
    {
        String result="";
        InputStream isr=null;
        try {
            String query1 = URLEncoder.encode(Kullanici_Adi.getText().toString(), "utf-8");
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost=new HttpPost("http://soysalmustafa.com/islem.php?type=liste&k_adi="+query1);
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
            String ad="";
            String soyad="";
            String s="";
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
        MyListAdapter adapter = new MyListAdapter(this, cars);

//Hazırlanmış olan adapter'ı, listview'in geçerli adapter'ı olarak ayarlıyoruz.
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}