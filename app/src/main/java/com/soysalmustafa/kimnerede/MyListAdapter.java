package com.soysalmustafa.kimnerede;

/**
 * Created by Mustafa on 25.4.2015.
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Kisi> cars;

    public MyListAdapter(Context context,ArrayList<Kisi> cars) {
// TODO Auto-generated constructor stub
        this.context = context;
        this.cars = cars;
    }

    //Burada gönderdiğimiz ArrayList'in boyutunu alıyoruz.
    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return cars.size();
    }

    @Override
    public Object getItem(int arg0) {
// TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
// TODO Auto-generated method stub
        return 0;
    }

    //Bu fonksiyon her satır için çağrılır.
//Özel olarak oluşturduğumuz View'i burada satıra ekliyoruz.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChildView myView;
        if (null == convertView) {
//burada satır için View oluşturuluyor
            myView = (ChildView)View.inflate(context,
                    R.layout.child_item,null);
        } else {
            myView = (ChildView)convertView;
        }

// oluşturulan view'in içeriği burada ayarlanıyor.
        myView.setItem(cars.get(position));

        return myView;
    }

}