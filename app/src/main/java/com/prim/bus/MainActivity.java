package com.prim.bus;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.primbus.PrimBus;
import com.primbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void mainT(View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PrimBus.getDefault().post("testAsync", "mainT", 1);
            }
        });
    }

    public void async(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrimBus.getDefault().post("testMain", "async");
            }
        }).start();
    }

    public void not(View view) {
        PrimBus.getDefault().post("notParams");
    }

    public void more(View view) {
        PrimBus.getDefault().post("moreParams", "Parmas", true, new Main2Activity.More("jake", 100));
    }

    public void more2(View view) {
        PrimBus.getDefault().post("moreParams2", "Parmas2", true, new Main2Activity.More("jake2", 1002));

    }
}
