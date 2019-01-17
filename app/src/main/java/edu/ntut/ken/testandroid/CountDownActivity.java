package edu.ntut.ken.testandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CountDownActivity extends AppCompatActivity {

    Button btnCancelCount,btnNextToResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        btnCancelCount = (Button) findViewById(R.id.btnCancelCount);
        btnCancelCount.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );

        btnNextToResult = (Button) findViewById(R.id.btnNextToResult);
        btnNextToResult.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void  onClick(View view){
                        launchResult();
                        finish();
                    }
                }
        );
    }

    private void launchResult(){
        Intent intent = new Intent(this, resultActivity.class);
        startActivity(intent);
    }
}
