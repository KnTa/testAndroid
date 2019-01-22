package edu.ntut.ken.testandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnNextToCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNextToCount = (Button) findViewById(R.id.btnNextToCount);
        btnNextToCount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchCountActivity();
                    }
                }
        );
    }

    private void launchCountActivity() {
        Intent intent = new Intent(this, CountDownActivity.class);
        startActivity(intent);
    }
}
