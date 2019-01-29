package edu.ntut.ken.testandroid;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.RequiresApi;

public class resultActivity extends AppCompatActivity {

    Button btnTop;
    LineChart  chart;
    LineDataSet dataSet1, dataSet2;
    LineData data;
    CheckBox chkDistance;
    List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

    private final float maxYValOfData1 =120f;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        List<Entry> poitList1= new ArrayList<Entry>();
        for (int i= 0;i<6;i++){
            float mult = 30 / 2f;
            float val = (float) (Math.random() * mult) + 70;

            poitList1.add(new Entry(i,val)); }
        dataSet1= new LineDataSet(poitList1, "data 1");
        dataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet1.setDrawValues(true);//在点上显示数值 默认true
        dataSet1.setValueTextSize(12f);//数值字体大小，同样可以设置字体颜色、自定义字体等

        List<Entry> poitList2= new ArrayList<Entry>();
        float r_val = 0;
        for (int i= 0;i<6;i++){
            float mult = 10.0f;
            poitList2.add(new Entry(i,r_val));
            r_val = r_val + (float) (Math.random() * mult) + 20;  }
        dataSet2 = new LineDataSet(poitList2, "data 2");
        dataSet2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSet2.setColor(Color.GREEN); // 设置点击某个点时，横竖两条线的颜色
        dataSet2.setDrawValues(true);//在点上显示数值 默认true
        dataSet2.setValueTextSize(12f);//数值字体大小，同样可以设置字体颜色、自定义字体等

        dataSets.add(dataSet1);
        dataSets.add(dataSet2);

        data = new LineData(dataSets);

        chart = (LineChart) findViewById(R.id.chart);
        chart.setData(data);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);

        Entry maxVal = Collections.max(poitList1, Comparator.comparing(s -> s.getY()));
        setDataSet1YAxisMaxVal(maxVal, leftAxis);

        chart.setHighlightPerDragEnabled(false);
        chart.setHighlightPerTapEnabled(false);
        chart.invalidate();

        btnTop = (Button) findViewById(R.id.btnTop);
        btnTop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
        chkDistance=(CheckBox) findViewById(R.id.chbDistance);
        chkDistance.setOnCheckedChangeListener(changeListener);
        chkDistance.setChecked(true);
    }
    private CheckBox.OnCheckedChangeListener changeListener= new CheckBox.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
            if (chkDistance.isChecked()){
                if(!dataSets.contains(dataSet2)){
                    dataSets.add(dataSet2);
                }
                chart.invalidate();
            }else{
                dataSets.remove(dataSet2);
                chart.invalidate();
            }
        }
    };

    private void setDataSet1YAxisMaxVal(Entry p_entry, YAxis yAxiss){
        if(p_entry.getY()>maxYValOfData1){
            yAxiss.setAxisMaximum(p_entry.getY());
        }else
        {
            yAxiss.setAxisMaximum(maxYValOfData1);
        }
    }
}
