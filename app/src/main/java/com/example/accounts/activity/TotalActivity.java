package com.example.accounts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.accounts.R;
import com.example.accounts.base.DatabaseUtil;
import com.example.accounts.bean.Account;

import java.util.ArrayList;
import java.util.List;

public class TotalActivity extends AppCompatActivity {

    HistogramView histogramview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        histogramview = findViewById(R.id.histogramview);
        List<Account> list = DatabaseUtil.getInstance().getAllData();
        Double price1 = 0.0;
        Double price2 = 0.0;
        if (list.size()!=0){
            for (Account bean : list) {
                if (bean.getType().equals("收入")){
                    price1 += Double.valueOf(bean.getPrice());
                }else {
                    price2 += Double.valueOf(bean.getPrice());
                }
            }
        }
        List<Double> listall = new ArrayList<>();
        listall.add(price1);
        listall.add(price2);
        List<String> date = new ArrayList<>();
        date.add("收入");
        date.add("支出");
        histogramview.updateThisData(listall,date);
    }
}