package com.soysalmustafa.kimnerede;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class ChildView2 extends LinearLayout {

    private Button btnOnay,btnRed;
    private TextView lblAdSoyad;
    private Context context;


    public ChildView2(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
        this.context = context;
    }

    // Oluşturuduğumz View'in her elemanı için burası çağrılır.
// Oluşturulmuş olan elemanların içerikleri burada doldurulur.
    public void setItem(final Kisi car)
    {
// TextView'in yazısını burada ayarlıyoruz.
        lblAdSoyad.setText(car.ad+" "+car.soyad);

// Burada ise butonun click olayını dinliyoruz.
      btnOnay.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                String query1 = URLEncoder.encode(String.valueOf(car.getK_id()), "utf-8");// utf-8 yaparak get verisinin boşluklarını%20 olmasını ve programın hata vermesini engelledik
                String query2 = URLEncoder.encode(String.valueOf(car.getId()), "utf-8");
                request.setURI(new URI("http://soysalmustafa.com/islem.php?type=onay&k_id=" + query1 + "&id=" + query2)); //Get isteğimizi URL yoluyla belirliyoruz.
                HttpResponse response = httpclient.execute(request); //İsteğimizi gerçekleştiriyoruz.
                HttpEntity entity = response.getEntity(); //Gelen cevabı işliyoruz.
                String result = null;
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8); //utf-8 burda önemli
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString(); //Artık result stringi php tarafından ekrana print yada echo komutlarıyla yazdırılmış veriyi içeriyor.
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();


            } catch (IllegalStateException e) {


            } catch (Exception ex) {

            }



        }
    });
        btnRed.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    String query1 = URLEncoder.encode(String.valueOf(car.getK_id()), "utf-8");// utf-8 yaparak get verisinin boşluklarını%20 olmasını ve programın hata vermesini engelledik
                    String query2 = URLEncoder.encode(String.valueOf(car.getId()), "utf-8");
                    request.setURI(new URI("http://soysalmustafa.com/islem.php?type=reddet&k_id=" + query1 + "&id=" + query2)); //Get isteğimizi URL yoluyla belirliyoruz.
                    HttpResponse response = httpclient.execute(request); //İsteğimizi gerçekleştiriyoruz.
                    HttpEntity entity = response.getEntity(); //Gelen cevabı işliyoruz.
                    String result = null;
                    InputStream is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8); //utf-8 burda önemli
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString(); //Artık result stringi php tarafından ekrana print yada echo komutlarıyla yazdırılmış veriyi içeriyor.
                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();


                } catch (IllegalStateException e) {


                } catch (Exception ex) {

                }

            }
        });
    }

// onFinishInflate fonksiyonu,
//MyListAdapter sınıfındaki getView fonksiyonu tamamlandıktan sonra tetiklenir.

    @Override
    protected void onFinishInflate() {
// TODO Auto-generated method stub
        super.onFinishInflate();
        btnOnay = (Button) findViewById(R.id.btnOnay);
        btnRed = (Button) findViewById(R.id.btnRed);
        lblAdSoyad = (TextView)findViewById(R.id.lblAdSoyad);
    }
}