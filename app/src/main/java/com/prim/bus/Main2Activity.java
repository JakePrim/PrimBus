package com.prim.bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.primbus.PrimBus;
import com.primbus.Subscribe;
import com.primbus.ThreadMethod;

public class Main2Activity extends AppCompatActivity {

    private TextView textView;

    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView);
        PrimBus.getDefault().register(this);
    }

    @Subscribe(tag = "testMain", thread = ThreadMethod.MAIN)
    public void runMain(String test) {
        textView.setText("我是TextView,我一直运行在主线程中..." + test + " --> " + Thread.currentThread().getName());
    }

    @Subscribe(tag = "testAsync", thread = ThreadMethod.BACKGROUND)
    public void runAsync(String test, Integer i) {
        Log.e(TAG, "runAsync: 我一直运行在子线程中. " + " test:" + test + " i: " + i + " --> "
                + Thread.currentThread().getName());
    }

    @Subscribe(tag = "notParams")
    public void notParams() {
        textView.setText("我是不需要参数的,通知我即可");
    }

    @Subscribe(tag = {"moreParams", "moreParams2"})
    public void moreParams(String s, Boolean b, More more) {
        textView.setText("我可以接收到多个参数: s -> " + s + " b-> " + b + " more" + more.toString());
    }

    public static class More {
        String name;
        int i;

        public More(String name, int i) {
            this.name = name;
            this.i = i;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "More{" +
                    "name='" + name + '\'' +
                    ", i=" + i +
                    '}';
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PrimBus.getDefault().unregister(this);
    }

    public void change(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
