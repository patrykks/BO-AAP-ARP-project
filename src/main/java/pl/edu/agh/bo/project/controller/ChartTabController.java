package pl.edu.agh.bo.project.controller;

import alg.model.Aco;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import pl.edu.agh.bo.project.model.Chart;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 27/02/16.
 */
public class ChartTabController {

    private Chart chart;
    private ExecutorService executor;
    private boolean shouldBreak;


    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    Pane chartPane;

    @FXML
    public void initialize() {
        shouldBreak = true;
        chart = new Chart();
        chart.addSeries("min-cost");
        chartPane.getChildren().add(chart.getLineChart());

        chartPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                chart.getLineChart().resize(chartPane.getWidth(), chartPane.getHeight());
                chart.getLineChart().setPrefSize(chartPane.getWidth(), chartPane.getHeight());
            }
        });
        chartPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                chart.getLineChart().resize(chartPane.getWidth(), chartPane.getHeight());
                chart.getLineChart().setPrefSize(chartPane.getWidth(), chartPane.getHeight());
            }
        });

    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        if (shouldBreak) {
            chart.clearChart();
            shouldBreak = false;
            executor = Executors.newCachedThreadPool();
            executor.execute(new UpdateQueue());
        }
    }

    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        shouldBreak = true;
        if (executor != null) executor.shutdown();
    }


    private class UpdateQueue implements Runnable {

        public void run() {
            int counter = 0;
            Aco aco = new Aco(100);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    aco.solve();
                }
            });
            while (!shouldBreak) {
                double minCost = aco.getMinCost();
                if (Double.isFinite(minCost))
                    chart.addDataToSeries("min-cost",counter, aco.getMinCost() );
                counter++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
