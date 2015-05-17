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

import static android.support.v4.app.ActivityCompat.startActivity;

public class ChildView3 extends LinearLayout {

    private Button btnNerede;
    private TextView lblExample;
    private Context context;


    public ChildView3(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
        this.context = context;
    }

    // Oluşturuduğumz View'in her elemanı için burası çağrılır.
// Oluşturulmuş olan elemanların içerikleri burada doldurulur.
    public void setItem(final Kisi car)
    {
// TextView'in yazısını burada ayarlıyoruz.
        lblExample.setText(car.ad+" "+car.soyad);

// Burada ise butonun click olayını dinliyoruz.
        btnNerede.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

              //

            }


        });

    }


// onFinishInflate fonksiyonu,
//MyListAdapter sınıfındaki getView fonksiyonu tamamlandıktan sonra tetiklenir.

    @Override
    protected void onFinishInflate() {
// TODO Auto-generated method stub
        super.onFinishInflate();
        btnNerede = (Button) findViewById(R.id.btnNerede);
        lblExample = (TextView)findViewById(R.id.lblExample);
    }
}