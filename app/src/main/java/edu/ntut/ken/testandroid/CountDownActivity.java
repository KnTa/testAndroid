package edu.ntut.ken.testandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;

public class CountDownActivity extends AppCompatActivity {

    Button btnNextToResult, btnStartCount;
    TextView txtCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        btnStartCount = (Button) findViewById(R.id.btnStart);
        btnStartCount.setTag(1);
        btnStartCount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if((Integer) v.getTag()==0){
                            finish();
                        }else{
                            btnStartCount.setTag(0);
                            btnStartCount.setText(R.string.cancel);
                            countdownTimer();
                        }
                    }
                }
        );

        btnNextToResult = (Button) findViewById(R.id.btnNextToResult);
        btnNextToResult.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchResult();
                        finish();
                    }
                }
        );

        txtCounter = (TextView)findViewById(R.id.txtCounter);
    }

    private void launchResult() {
        Intent intent = new Intent(this, resultActivity.class);
        startActivity(intent);
    }

    private void countdownTimer(){
        new CountDownTimer(360000, 1000) {

            public void onTick(long millisUntilFinished) {

                txtCounter.setText(millisUntilFinished / 60000 + ":" + String.format("%02d",(millisUntilFinished % 60000) / 1000));
            }

            public void onFinish() {
                txtCounter.setText("0:00");
            }
        }.start();

    }
}
