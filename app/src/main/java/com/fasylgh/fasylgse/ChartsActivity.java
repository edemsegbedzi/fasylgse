package com.fasylgh.fasylgse;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        new FetchData().execute();


    }

    //AsyncTask to fetch data
    private class FetchData extends AsyncTask<String, String, String>{

        private ArrayList<String> var_array_name = new ArrayList<String>();
        private ArrayList<Integer> var_array_volume = new ArrayList<Integer>();
        private ArrayList<Double> var_array_change = new ArrayList<Double>();
        private ArrayList<Double> var_array_price = new ArrayList<Double>();
        private ProgressDialog var_progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //create progress dialog to tell the user to wait for update
            var_progress = new ProgressDialog(ChartsActivity.this);
            var_progress.setMessage("Fetching data\nPlease wait...");
            var_progress.setIndeterminate(false);
            var_progress.setCancelable(false);
            var_progress.show();
        }

        @Override
        protected String doInBackground(String... params) {

            //URL where data is
            String var_url_string = "https://dev.kwayisi.org/apis/gse/live?prettify";

            StringBuilder var_response = new StringBuilder();
            try {
                //Convert String to URL
                URL var_url = new URL(var_url_string);

                //HTTPConnection to load and get data
                HttpURLConnection var_httpconn = (HttpURLConnection) var_url.openConnection();

                //Check response code
                if (var_httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //Get data from response
                    BufferedReader var_br = new BufferedReader(new InputStreamReader(var_httpconn.getInputStream()), 8192);

                    //Temp String to store data
                    String var_tmp_string = null;

                    //Getting data from BufferedReader
                    while((var_tmp_string = var_br.readLine()) != null){
                        var_response.append(var_tmp_string);
                    }

                    var_br.close();

                    //Converting StringBuilder to string
                    String var_json_string = var_response.toString();

                    //Converting String to JSONObject
                    JSONArray var_json_array = new JSONArray(var_json_string);

                    //Getting the JSONObject from the JSONArray
                    for(int i=0; i< var_json_array.length(); i++){
                        //Getting data from JSONObject
                        JSONObject var_json_data = var_json_array.getJSONObject(i);

                        //Getting name from JSONObject
                        String var_name = var_json_data.getString("name");
                        var_array_name.add(var_name);

                        //Getting change from JSONObject
                        double var_change = var_json_data.getDouble("change");
                        var_array_change.add(var_change);

                        //Getting volume from JSONObject
                        int var_volume = var_json_data.getInt("volume");
                        var_array_volume.add(var_volume);

                        //Getting price from JSONObject
                        double var_price = var_json_data.getDouble("price");
                        var_array_price.add(var_price);


                    }

                } else {
                    Toast.makeText(ChartsActivity.this, "Opening connection failed.\nHTTP Code: "+var_httpconn.getResponseCode(), Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Log.e("ChartsActivity", "Failed to acquire data.\n[ERROR]:"+e.getMessage());
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            drawBarChart();
            var_progress.dismiss();

        }

        private void drawBarChart(){

            //Create XYSeries for Price
            XYSeries obj_price = new XYSeries("Price");
            //Create XYSeries for Volume
            XYSeries obj_volume = new XYSeries("Volume");

            //Adding data to XYSeries
            for(int i=0; i<var_array_price.size(); i++){
                obj_volume.add(i, var_array_volume.get(i));
                obj_price.add(i, var_array_price.get(i));
            }

            //Create XYMultipleSeriesDataset to Hold the XYSeries
            XYMultipleSeriesDataset obj_multiple = new XYMultipleSeriesDataset();
            obj_multiple.addSeries(obj_volume);
            obj_multiple.addSeries(obj_price);

            //Create XYSeriesRenderer to customize Price XYSeries
            XYSeriesRenderer obj_renderer_price = new XYSeriesRenderer();
            obj_renderer_price.setColor(Color.BLUE);
            obj_renderer_price.setFillPoints(true);
            obj_renderer_price.setLineWidth(3);
            obj_renderer_price.setDisplayChartValues(true);
            obj_renderer_price.setDisplayChartValuesDistance(15);

            //Create XYSeriesRenderer to customize Volume XYSeries
            XYSeriesRenderer obj_renderer_volume = new XYSeriesRenderer();
            obj_renderer_volume.setColor(Color.GRAY);
            obj_renderer_volume.setFillPoints(true);
            obj_renderer_volume.setLineWidth(3);
            obj_renderer_volume.setDisplayChartValues(true);
            obj_renderer_volume.setDisplayChartValuesDistance(15);

            //Create XYMultipleSeriesRenderer to customize the whole chart
            XYMultipleSeriesRenderer obj_renderer_multiple = new XYMultipleSeriesRenderer();
            obj_renderer_multiple.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
            obj_renderer_multiple.setXLabels(0);
            obj_renderer_multiple.setChartTitle("Stock Price And Volume Data");
            obj_renderer_multiple.setXTitle("Company Names");
            obj_renderer_multiple.setYTitle("Price and Volume Data");

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
            for(int i=0; i<var_array_name.size(); i++){
                obj_renderer_multiple.addXTextLabel(i, var_array_name.get(i));
            }

            //Adding price renderer to the multiple renderer
            obj_renderer_multiple.addSeriesRenderer(obj_renderer_price);
            //Adding volume renderer to the multiple renderr
            obj_renderer_multiple.addSeriesRenderer(obj_renderer_volume);

            RelativeLayout layout_main = (RelativeLayout)findViewById(R.id.activity_price_chart);
            layout_main.removeAllViews();


            GraphicalView var_factory = ChartFactory.getBarChartView(ChartsActivity.this, obj_multiple, obj_renderer_multiple, BarChart.Type.DEFAULT);

            layout_main.addView(var_factory);

        }
    }


}
