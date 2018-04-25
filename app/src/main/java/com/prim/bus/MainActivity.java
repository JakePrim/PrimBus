package com.prim.bus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.primbus.PrimBus;
import com.primbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrimBus.getDefault().register(this);

        PrimBus.getDefault().post("test", "test");
    }

    @Subscribe("test")
    public void Test(String msg, Integer args) {
        Log.e(TAG, "Test: msg;" + msg + " args:" + args);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
