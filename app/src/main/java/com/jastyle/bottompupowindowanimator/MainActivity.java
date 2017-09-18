package com.jastyle.bottompupowindowanimator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private LinearLayout container;
    private Button iv;
    private BottomPopuWindow bpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (LinearLayout) findViewById(R.id.container);
        iv = (Button) findViewById(R.id.iv_img);
        bpw = new BottomPopuWindow(this);
        bpw = new BottomPopuWindow(MainActivity.this);
        bpw.setFocusable(true);
        bpw.setFocusableInTouchMode(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bpw.isShowing) {
                    bpw.show();
                }
            }
        });
    }



}
