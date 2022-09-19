package com.example.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.example.app.R;
import com.example.app.fragment.DialogFragmentSecurity;
import com.example.app.other.PrefrancesUtils;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.List;

public class PatternSetUpActivity extends AppCompatActivity implements DialogFragmentSecurity.OnCallbackReceived {
    private PatternLockView mPatternLockView;

    String[] descriptionData = {"Unlock", "Confirm", "Save"};
    TextView textView;
//    StateProgressBar stateProgressBar;
    Button reset, conform;
    ImageView lockmethod, back;
    Button skip, save;
    EditText nickname;
    String conformpattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_set_up);

        if (PrefrancesUtils.getPattern() != null) {
            Intent intent = new Intent(PatternSetUpActivity.this, PatternUnlockActivity.class);
            startActivity(intent);
            finish();
        }

//        stateProgressBar = (StateProgressBar) findViewById(R.id.state_progress_bar);
        textView = findViewById(R.id.spinner);
        reset = findViewById(R.id.reset);
        conform = findViewById(R.id.conform);
//        stateProgressBar.setStateDescriptionData(descriptionData);
        mPatternLockView = findViewById(R.id.patter_lock_view);
        lockmethod = findViewById(R.id.clear);
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> {

            Intent intent = new Intent(PatternSetUpActivity.this, UnlockMetodActivity.class);
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

        reset.setOnClickListener(v -> {

            PrefrancesUtils.setPatternfirst(null);
            textView.setText("Draw an unlock pattern");
//            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
//            stateProgressBar.enableAnimationToCurrentState(true);
            conform.setVisibility(View.GONE);
            mPatternLockView.clearPattern();
        });

        conform.setOnClickListener(v -> {

            FragmentManager manager = getSupportFragmentManager();
            DialogFragmentSecurity myDialogFragment = new DialogFragmentSecurity();
            myDialogFragment.show(manager, "Dialog");
        });

        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, com.andrognito.patternlockview.R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, com.andrognito.patternlockview.R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, com.andrognito.patternlockview.R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);

        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Log.e("start", "Pattern drawing started");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                Log.e("progress", "Pattern progress: " +
                        PatternLockUtils.patternToString(mPatternLockView, progressPattern));
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Log.e("complete", "Pattern complete: " +
                        PatternLockUtils.patternToString(mPatternLockView, pattern));

                if (PrefrancesUtils.getPatternfirst() != null) {
                    reset.setVisibility(View.VISIBLE);
                    Log.e("pattern", PrefrancesUtils.getPatternfirst());
                    conformpattern = PatternLockUtils.patternToString(mPatternLockView, pattern);

                    if (conformpattern.equalsIgnoreCase(PrefrancesUtils.getPatternfirst()) && !conformpattern.isEmpty()) {
                        conform.setVisibility(View.VISIBLE);
                        textView.setText(" Pattern  Matched");
//                        stateProgressBar.enableAnimationToCurrentState(true);
//                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                    } else {
                        textView.setText(" Pattern Not Matched");
                        mPatternLockView.clearPattern();
                        conform.setVisibility(View.INVISIBLE);
//                        stateProgressBar.enableAnimationToCurrentState(true);
//                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        //Toast.makeText(PatternActivity.this, " Pattern Not Matched", Toast.LENGTH_SHORT).show();
                    }
                } else if (pattern.size() >= 3) {
                    String firstdraw = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    PrefrancesUtils.setPatternfirst(firstdraw);
//                    stateProgressBar.enableAnimationToCurrentState(true);
//                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                    textView.setText("Draw Pattern to confirm");
                    reset.setVisibility(View.VISIBLE);
                    mPatternLockView.clearPattern();
                } else {
                    textView.setText("Draw atleast 3 dot");
                    mPatternLockView.clearPattern();
                    //Toast.makeText(PatternActivity.this, "Draw atleast 3 dot", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCleared() {
                Log.e("cleared", "Pattern has been cleared");
            }
        });
    }

    @Override
    public void Update(String answer) {
        PrefrancesUtils.setPattern(conformpattern);

        if (answer != null && !answer.isEmpty()) {
            PrefrancesUtils.setAnswer(answer);
            Intent intent = new Intent(PatternSetUpActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        } else {
            Intent intent = new Intent(PatternSetUpActivity.this, MainActivity.class);
            startActivity(intent);
            PrefrancesUtils.setAnswer(null);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        PrefrancesUtils.setPatternfirst(null);
        super.onBackPressed();
    }
}
