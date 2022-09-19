package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.app.R;
import com.example.app.other.PrefrancesUtils;


public class UnlockMetodActivity extends AppCompatActivity {
    Button pin, gesture, pattern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unlock_metod);

        pin = findViewById(R.id.pin);
        pattern = findViewById(R.id.pattern);
        gesture = findViewById(R.id.gesture);

        getActivity();

    }

    private void getActivity(){
        if (PrefrancesUtils.getLockType(getApplicationContext()) != null) {
            if (PrefrancesUtils.getLockType(getApplicationContext()).equalsIgnoreCase("pin")) {
                Intent intent = new Intent(this, PinLockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else if (PrefrancesUtils.getLockType(getApplicationContext()).equalsIgnoreCase("pattern")) {
                Intent intent = new Intent(this, PatternSetUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else if (PrefrancesUtils.getLockType(getApplicationContext()).equalsIgnoreCase("gesture")) {
                Intent intent = new Intent(this, GestureLockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }
        else {
            pin.setOnClickListener(v -> {
                PrefrancesUtils.setLockType("pin");
                Intent intent = new Intent(UnlockMetodActivity.this, PinLockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            });

            gesture.setOnClickListener(v -> {
                PrefrancesUtils.setLockType("gesture");
                Intent intent = new Intent(UnlockMetodActivity.this, GestureLockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            });

            pattern.setOnClickListener(v -> {
                PrefrancesUtils.setLockType("pattern");
                Intent intent = new Intent(UnlockMetodActivity.this, PatternSetUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            });
        }
    }


}
