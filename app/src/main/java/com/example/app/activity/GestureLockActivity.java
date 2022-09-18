package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.fragment.DialogFragmentSecurity;
import com.example.app.other.PrefrancesUtils;

import java.io.File;
import java.util.ArrayList;

public class GestureLockActivity extends AppCompatActivity implements DialogFragmentSecurity.OnCallbackReceived {
    private boolean first;
    private GestureLibrary library;
    private boolean changeLock;
    ImageView lockmethod, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefrancesUtils.getgesture() != null) {
            Intent intent = new Intent(GestureLockActivity.this, GestureUnlockActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_gesture_lock);
        lockmethod = (ImageView) findViewById(R.id.clear);
        back = (ImageView) findViewById(R.id.back);
        File gestureFile = new File(Environment.getDataDirectory() + "/data/" + getPackageName(), "gesture");
        GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
        gestureOverlayView.addOnGestureListener(new GestureProcessor());
        first = true;
        library = GestureLibraries.fromFile(gestureFile);

        back.setOnClickListener(v -> {

            Intent intent = new Intent(GestureLockActivity.this, UnlockMetodActivity.class);
            PrefrancesUtils.setQuestin(null);
            PrefrancesUtils.setgesture(null);
            PrefrancesUtils.setPatternfirst(null);
            PrefrancesUtils.setAnswer(null);
            PrefrancesUtils.setPIN(null);
            PrefrancesUtils.setPattern(null);
            PrefrancesUtils.setLockType(null);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });
    }

    @Override
    public void Update(String answer) {
        PrefrancesUtils.setgesture("gesture");

        if (answer != null && !answer.isEmpty()) {
            PrefrancesUtils.setAnswer(answer);
            Intent intent = new Intent(GestureLockActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        } else {
            Intent intent = new Intent(GestureLockActivity.this, MainActivity.class);
            startActivity(intent);
            PrefrancesUtils.setAnswer(null);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

    private class GestureProcessor implements GestureOverlayView.OnGestureListener {

        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

            findViewById(R.id.tv_gesture_desc).setVisibility(View.INVISIBLE);
        }

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            Log.e("started", "on");
        }

        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            if (first) {
                Gesture gesture = overlay.getGesture();
                library.addGesture("first", gesture);
                first = false;
                ((TextView) findViewById(R.id.tv_unlock_message)).setText(getString(R.string.confirm_gesture));
            } else {
                Gesture gestureConfirm = overlay.getGesture();
                ArrayList<Prediction> list = library.recognize(gestureConfirm);

                if (list.get(0).score < 2) {
                    //gesture do not match
                    Toast.makeText(GestureLockActivity.this, getString(R.string.gesture_dont_match), Toast.LENGTH_SHORT).show();
                    ((TextView) findViewById(R.id.tv_unlock_message)).setText(getString(R.string.gesture_dont_match_message));
                    first = true;
                } else {
                    //gesture match
                    Toast.makeText(GestureLockActivity.this, getString(R.string.gesture_set), Toast.LENGTH_LONG).show();
                    library.save();
                    showDialog();


                }
            }
        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

        }
    }

    public void showDialog() {
        FragmentManager manager = getSupportFragmentManager();
        DialogFragmentSecurity dialogFragment = new DialogFragmentSecurity();
        dialogFragment.show(manager, "Dialog");
    }

}
