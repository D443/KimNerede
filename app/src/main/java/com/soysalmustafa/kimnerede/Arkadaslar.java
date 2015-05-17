package com.soysalmustafa.kimnerede;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class Arkadaslar extends Activity {
    ArrayList<String> cars = new ArrayList<String>();
    ListView lv1;
    int k_id;
    Bundle bdn;
    public int getCount() {
        return cars.size();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arkadaslar);
        lv1=(ListView)findViewById(R.id.listViewExample);
        k_id = getIntent().getIntExtra("k_id", 0);
        bdn=new Bundle();
      // Arkadaşların Listelenmesi
        String result="";
        InputStream isr=null;
        try {
            String query1 = URLEncoder.encode(String.valueOf(k_id), "utf-8");
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost=new HttpPost("http://soysalmustafa.com/islem.php?type=arkadasgoruntuleme&k_id="+query1);
            HttpResponse response=httpclient.execute(httppost);
            HttpEntity entity=response.getEntity();
            isr=entity.getContent();
        }
        catch (Exception e) {
            // TODO: handle exception
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
            // TODO: handle exception
            Log.e("log_tag","Error converting result"+e.toString());
        }
        //parse json data
        try {
            String s="";
            JSONArray jArray=new  JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json=jArray.getJSONObject(i);
                int dakika,saat,gun,ay,yil;
                dakika=Integer.parseInt(json.getString("tarih"));
                saat=dakika/60;
                gun=dakika/(24*60);
                ay=dakika/(30*24*60);
                yil=dakika/(12*30*24*60);
                if(dakika<60){
                    s=json.getString("kullanici_adi")+"  -  ("+json.getString("tarih")+")  dakika önce aktifti.";

                }
                else if(saat<24){
                    s=json.getString("kullanici_adi")+"  -  ("+saat+")  saat önce aktifti.";

                }

                else if(gun<30){
                    s=json.getString("kullanici_adi")+"  -  ("+gun+")  gün önce aktifti.";

                }
                else if(ay<12){
                    s=json.getString("kullanici_adi")+"  -  ("+ay+")  ay önce aktifti.";

                }

                else{
                    s=json.getString("kullanici_adi")+"  -  ("+yil+")  yıl önce aktifti.";

                }

                cars.add(s);

            }


        } catch (Exception e) {

            Log.e("log_tag","Error Parsing Data"+e.toString());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,android.R.id.text1,cars);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final Intent i=new Intent(getApplicationContext(),ArkadasHarita.class);
                String ad=cars.get((int) id);
                String aktarilacak=ad;
                bdn.putString("veri",aktarilacak);
                i.putExtras(bdn);
                startActivity(i);

            }
        });
    }
    }

