package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.app.R;
import com.example.app.other.PrefrancesUtils;

public class SpalashActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_spalash);
//
//        Thread mythread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(1500);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (PrefrancesUtils.getLockType(getApplicationContext()) != null) {
//                                if (PrefrancesUtils.getLockType(getApplicationContext()).equalsIgnoreCase("pin")) {
//                                    Intent intent = new Intent(SpalashActivity.this, PinLockActivity.class);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                    finish();
//                                } else if (PrefrancesUtils.getLockType(getApplicationContext()).equalsIgnoreCase("pattern")) {
//                                    Intent intent = new Intent(SpalashActivity.this, PatternSetUpActivity.class);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                    finish();
//                                } else if (PrefrancesUtils.getLockType(getApplicationContext()).equalsIgnoreCase("gesture")) {
//                                    Intent intent = new Intent(SpalashActivity.this, GestureLockActivity.class);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                    finish();
//                                }
//                            } else {
//                                Intent intent = new Intent(SpalashActivity.this, UnlockMetodActivity.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                finish();
//                            }
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        mythread.start();
//    }
}
