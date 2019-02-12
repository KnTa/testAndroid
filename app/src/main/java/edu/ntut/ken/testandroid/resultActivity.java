package edu.ntut.ken.testandroid;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.RequiresApi;

class MyRightYAxisValueFormatter implements IAxisValueFormatter {

    public MyRightYAxisValueFormatter() {

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return String.format("%.1f M",value);
    }
}

class MyXAxisValueFormatter implements IAxisValueFormatter {

    public MyXAxisValueFormatter() {

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return String.format("%02d:%02d",(int)(value/60),(int)(value%60));
    }
}


public class resultActivity extends AppCompatActivity {
    public final float startWalkingTime=60f;
    public final float finishWalkingTime=420f;
    public final float baseDistance = 1.2f;

    Button btnTop;
    LineChart  chart;
    LineDataSet dataSetHeart, dataSetMet, dataSetDistance;
    TextView txvTotalDistance;
    LineData data;
    CheckBox chbDistance, chbMET;
    List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
    List<String[]> csvDatas;

    private final float maxYValOfData1 =120f;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ReadCSV reader = new ReadCSV(getApplicationContext(), R.raw.file_312905);
        csvDatas = reader.getCSVData();
        csvDatas.remove(0);

        List<Entry> heartList= new ArrayList<Entry>();
        List<Entry> metList= new ArrayList<Entry>();

        String[] first = csvDatas.get(0);
        float startNumber = Float.parseFloat(first[0]);
        for (String[] data:csvDatas)
        {
            float xTime = Float.parseFloat(data[0]) - startNumber + 1;

            if(Float.parseFloat(data[1])>0)heartList.add(new Entry(xTime, Float.parseFloat(data[1])));
            if(Float.parseFloat(data[2])>0)metList.add(new Entry(xTime, Float.parseFloat(data[2])));
        }

        dataSetHeart= new LineDataSet(heartList, "Heart");
        dataSetHeart.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSetHeart.setDrawValues(true);//在点上显示数值 默认true
        dataSetHeart.setValueTextSize(12f);//数值字体大小，同样可以设置字体颜色、自定义字体等

        dataSetMet = new LineDataSet(metList, "MET");
        dataSetMet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSetMet.setColor(Color.GREEN); // 设置点击某个点时，横竖两条线的颜色
        dataSetMet.setDrawValues(true);//在点上显示数值 默认true
        dataSetMet.setValueTextSize(12f);//数值字体大小，同样可以设置字体颜色、自定义字体等

        List<Entry> distanceList= new ArrayList<Entry>();
        float distance = 0;
        for (float i= startWalkingTime+1.0f;i<finishWalkingTime;i++){
            float stay = (float)(Math.pow(Math.random(),2)*1.2f);
            distance = distance+baseDistance-stay;
            distanceList.add(new Entry(i,distance)); }
        dataSetDistance = new LineDataSet(distanceList, "Distance");
        dataSetDistance.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSetDistance.setColor(Color.BLUE);
        dataSetDistance.setDrawCircles(false);
        dataSetDistance.setDrawValues(false);

        dataSets.add(dataSetHeart);
        dataSets.add(dataSetMet);
        dataSets.add(dataSetDistance);

        data = new LineData(dataSets);

        chart = (LineChart) findViewById(R.id.chart);
        chart.setData(data);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(30f);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new MyXAxisValueFormatter());
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setAxisMinimum(0f);
        rightAxis.setValueFormatter(new MyRightYAxisValueFormatter());

        Entry maxVal = Collections.max(heartList, Comparator.comparing(s -> s.getY()));
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
        chbDistance=(CheckBox) findViewById(R.id.chbDistance);
        chbDistance.setOnCheckedChangeListener(changeListener);
        chbDistance.setChecked(true);

        chbMET=(CheckBox) findViewById(R.id.chbMET);
        chbMET.setOnCheckedChangeListener(changeListener);
        chbMET.setChecked(true);

        txvTotalDistance=(TextView) findViewById(R.id.txvTotalDistance);
        Entry finalDistance = distanceList.get(distanceList.size()-1);
        float d = finalDistance.getY();
        txvTotalDistance.setText(getResources().getString(R.string.totalDistance) + String.format("%.1f M",d));
    }

    private void chkChartCheck(CheckBox chk,LineDataSet set){
        if (chk.isChecked()){
            if(!dataSets.contains(set)){
                dataSets.add(set);
            }
            chart.invalidate();
        }else{
            dataSets.remove(set);
            chart.invalidate();
        }
    }

    private CheckBox.OnCheckedChangeListener changeListener= new CheckBox.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
            if(chbDistance!=null) chkChartCheck(chbDistance, dataSetDistance);
            if(chbMET!=null) chkChartCheck(chbMET, dataSetMet);
        }
    };

    private void setDataSet1YAxisMaxVal(Entry p_entry, YAxis yAxis){
        if(p_entry.getY()>maxYValOfData1){
            yAxis.setAxisMaximum(p_entry.getY());
        }else
        {
            yAxis.setAxisMaximum(maxYValOfData1);
        }
    }
}
