package com.example.nghia.countdowntimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    private static final String REMANING_TIME = "remaining_time";
    private Button buttonStart;
    private Button buttonStop;
    private EditText etMins;
    private EditText etSecs;
    private TextView tvTime;
    private CountDownTimer timer;

    private TextView tvNotification;

    private int totalSecs;
    private int remainingSecs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getReferences();
        loadReferences(savedInstanceState);
        addListeners();
        setupUI();

    }

    private void setupUI() {
        etSecs.clearFocus();
        etMins.clearFocus();
    }

    private void loadReferences(Bundle savedBundle) {
        if (savedBundle != null) {
            remainingSecs = savedBundle.getInt(REMANING_TIME);
        } else {
            remainingSecs = 0;
        }
        totalSecs = 0;

    }

    private void addListeners() {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (etMins.getText().toString().isEmpty()){
                        totalSecs = 0;
                    }
                    else{
                        totalSecs = Integer.parseInt(etMins.getText().toString());
                    }
                    totalSecs *= 60;
                    if(!etSecs.getText().toString().isEmpty()){
                        totalSecs += Integer.parseInt(etSecs.getText().toString());
                    }
                    remainingSecs = totalSecs;
                    tvNotification.setVisibility(View.INVISIBLE);
                    startCountDown();

                }catch (Exception e){
                    tvNotification.setVisibility(View.VISIBLE);
                }


            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }
            }
        });
    }
    private void stopCountDown(){
        if (timer != null) {
            timer.cancel();
        }
    }
    private void startCountDown() {
       stopCountDown();

        timer = new CountDownTimer((remainingSecs + 1) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingSecs--;
                if (remainingSecs < 0) remainingSecs = 0;
                Log.d(TAG, String.format("onTick: milis: %d, remainning: %d", millisUntilFinished, remainingSecs));
                tvTime.setText(String.format("%02d:%02d", remainingSecs / 60, remainingSecs % 60));
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                timer.cancel();
//                tvTime.setText(String.format("%02d:%02d", 0,0));
            }
        };
        timer.start();
    }

    private void getReferences() {
        buttonStart = (Button) findViewById(R.id.btn_start);
        buttonStop = (Button) findViewById(R.id.btn_stop);

        etMins = (EditText) findViewById(R.id.et_mins);
        etSecs = (EditText) findViewById(R.id.et_secs);

        tvTime = (TextView) findViewById(R.id.tv_time);

        tvNotification = (TextView) findViewById(R.id.tv_notification );
    }

    @Override
    protected void onStart() {
        super.onStart();
        startCountDown();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REMANING_TIME, remainingSecs);
        stopCountDown();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCountDown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
