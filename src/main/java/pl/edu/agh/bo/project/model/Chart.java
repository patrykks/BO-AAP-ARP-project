package pl.edu.agh.bo.project.model;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Patryk Skalski on 27/02/16.
 */
public class Chart {
    final private LineChart chart;
    private Map<String,XYChart.Series> series;

    public Chart()
    {
        series = new HashMap<>();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);

        chart = new LineChart<Number, Number>(xAxis, yAxis) {
            @Override
            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
        };

        chart.setAnimated(true);
        chart.setId("Live line chart");
        chart.setTitle("Live Line Chart");

    }


    public LineChart getLineChart()
    {
        return chart;
    }


    public void  addSeries(String name)
    {
        LineChart.Series addedSeries = new LineChart.Series<Number, Number>();
        addedSeries.setName(name);
        chart.getData().add(addedSeries);
        series.put(name, addedSeries);
    }

    public void addDataToSeries(String name,double x, double y)
    {
        if (series.get(name) != null)
            series.get(name).getData().add(new LineChart.Data(x,y));
        else
            System.out.println("Series with name " + name + " doesnt exist");

    }

    public void clearChart()
    {
        for (String key : series.keySet()) {
            series.get(key).getData().clear();
        }
    }









}
