package com.fasylgh.fasylgse;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryPrice extends AppCompatActivity {

    private int year_rangeF, year_rangeT;
    private int month_rangeF, month_rangeT;
    private int day_rangeF, day_rangeT;
    private int hour_rangeF, hour_rangeT;
    private int minute_rangeF, minute_rangeT;
    private String company_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_price);

//        company_name = getIntent().getStringExtra("CNAME");


        Button btnFrom = (Button)findViewById(R.id.btnFrom);
        Button btnTo = (Button)findViewById(R.id.btnTo);
        Button btnFetch = (Button)findViewById(R.id.btnGet);

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendar to get current information
                Calendar now = Calendar.getInstance();

                //Pick date from dialog
                DatePickerDialog dpd = new DatePickerDialog(HistoryPrice.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                      year_rangeF = year;
                        month_rangeF = month;
                        day_rangeF = dayOfMonth;
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.show();

                //Pick time from dialog
                TimePickerDialog obj_tpd = new TimePickerDialog(HistoryPrice.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour_rangeF = hourOfDay;
                        minute_rangeF = minute;
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                obj_tpd.show();
            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendar to get current information
                Calendar now = Calendar.getInstance();

                //Pick date from dialog
                        DatePickerDialog dpd = new DatePickerDialog(HistoryPrice.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                day_rangeT = dayOfMonth;
                                year_rangeT = year;
                                month_rangeT = month;
                            }
                        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.show();

                //Pick time from dialog
                TimePickerDialog obj_tpd = new TimePickerDialog(HistoryPrice.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour_rangeT = hourOfDay;
                        minute_rangeT = minute;
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                obj_tpd.show();
            }
        });

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(year_rangeF == 0 || year_rangeT == 0 || month_rangeF == 0 || month_rangeT == 0 || day_rangeF == 0 || day_rangeT == 0 || hour_rangeF == 0 || hour_rangeT == 0 || minute_rangeF == 0 || minute_rangeT == 0)
                    Toast.makeText(HistoryPrice.this, "Please select a from and to date range", Toast.LENGTH_LONG).show();

                else{
                    fetchDataFromDatabase(year_rangeF, year_rangeT, month_rangeF, month_rangeT, day_rangeF, day_rangeT, hour_rangeF, hour_rangeT, minute_rangeF, minute_rangeT);
                }
            }
        });
    }



    private void fetchDataFromDatabase(int yR, int yT, int moF, int moT, int dF, int dT, int hF, int hT, int mF, int mT){

        double[] price = {7.2, 4.2, 0, 3.9, 8.9, 1.2, 5.6, 2.3, 5.3, 2.3, 9.2, 10.2};
        String[] date = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        ArrayList<Double> price_arr = new ArrayList<Double>();
        ArrayList<String> date_arr = new ArrayList<String>();

        for(double p: price)
            price_arr.add(p);

        for(String d: date)
            date_arr.add(d);

        drawHistoryChart(price_arr, date_arr);
    }

    private void drawHistoryChart(ArrayList<Double> var_array_price, ArrayList<String> var_array_date){
        //Create XYSeries for Price
        XYSeries obj_price = new XYSeries("Date/Time");

        //Adding data to XYSeries
        for(int i=0; i<var_array_price.size(); i++){
            obj_price.add(i, var_array_price.get(i));
        }

        //Create XYMultipleSeriesDataset to Hold the XYSeries
        XYMultipleSeriesDataset obj_multiple = new XYMultipleSeriesDataset();
        obj_multiple.addSeries(obj_price);

        //Create XYSeriesRenderer to customize Price XYSeries
        XYSeriesRenderer obj_renderer_price = new XYSeriesRenderer();
        obj_renderer_price.setColor(Color.BLUE);
        obj_renderer_price.setFillPoints(true);
        obj_renderer_price.setLineWidth(3);
        obj_renderer_price.setDisplayChartValues(true);
        obj_renderer_price.setDisplayChartValuesDistance(15);


        //Create XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer obj_renderer_multiple = new XYMultipleSeriesRenderer();
        obj_renderer_multiple.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        obj_renderer_multiple.setXLabels(0);
        obj_renderer_multiple.setChartTitle("History Stock Price Data");
        obj_renderer_multiple.setXTitle("Date/Time");
        obj_renderer_multiple.setYTitle("Price Data");

        //Graph customizations

        //Text size of title
        obj_renderer_multiple.setChartTitleTextSize(12);
        //Text size of axis title
        obj_renderer_multiple.setAxisTitleTextSize(12);
        //Text size of the graph label
        obj_renderer_multiple.setLabelsTextSize(12);
        //Zoom buttons visibility
        obj_renderer_multiple.setZoomButtonsVisible(true);
        //Pan visibitly
        obj_renderer_multiple.setPanEnabled(false, false);
        //Graph Clickable
        obj_renderer_multiple.setClickEnabled(true);
        //Zoom on both axis
        obj_renderer_multiple.setZoomEnabled(true, true);
        //Show lines to display on y axis
        obj_renderer_multiple.setShowGridY(false);
        //Show lines to display on x axis
        obj_renderer_multiple.setShowGridX(false);
        //Legend to fit to screen size
        obj_renderer_multiple.setFitLegend(true);
        //External zoom
        obj_renderer_multiple.setExternalZoomEnabled(true);
        //Displaying lines on graph to be formatted
        obj_renderer_multiple.setAntialiasing(true);
        //In scroll
        obj_renderer_multiple.setInScroll(false);
        //x axis label align
        obj_renderer_multiple.setXLabelsAlign(Paint.Align.CENTER);
        //y axis label align
        obj_renderer_multiple.setYLabelsAlign(Paint.Align.LEFT);
        //Text Font
        obj_renderer_multiple.setTextTypeface("sans_serif", Typeface.NORMAL);
        //Bar spacing
        obj_renderer_multiple.setBarSpacing(1);
        //Background color ot the graph
        obj_renderer_multiple.setBackgroundColor(Color.TRANSPARENT);
        //Margin color
        obj_renderer_multiple.setMarginsColor(getResources().getColor(R.color.transparent_background));
        //Apply Background Color
        obj_renderer_multiple.setApplyBackgroundColor(true);

        //X TexLabel
        for(int i=0; i<var_array_date.size(); i++){
            obj_renderer_multiple.addXTextLabel(i, var_array_date.get(i));
        }

        //Adding price renderer to the multiple renderer
        obj_renderer_multiple.addSeriesRenderer(obj_renderer_price);

        RelativeLayout layout_main = (RelativeLayout)findViewById(R.id.rlView);
        layout_main.removeAllViews();


        GraphicalView var_factory = ChartFactory.getLineChartView(HistoryPrice.this, obj_multiple, obj_renderer_multiple);

        layout_main.addView(var_factory);
    }

}
