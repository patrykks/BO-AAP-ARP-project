package pl.edu.agh.bo.project.controller;

/**
 * Created by patrykks on 22/04/16.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.StatsItem;
import services.StatsService;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/*** Created by patrykks on 13/04/16.
 */
public class StatsController {
    private Set<StatsItem> statSet;
    private ExecutorService executor;

    @FXML
    private TableView<StatsItem> statsTableView;

    @FXML
    private TableColumn<StatsItem, String> statsIdColumn;

    @FXML
    private TableColumn<StatsItem, String> minCostColumn;

    @FXML
    private TableColumn<StatsItem, String> timeColumn;

    @FXML
    private TableColumn<StatsItem, String> iterationColumn;

    @FXML
    private TableColumn<StatsItem, String> precentageImprovmentColumn;


    @FXML
    private void initialize() {
        executor = Executors.newCachedThreadPool();;
        ObservableList<StatsItem> data = FXCollections.observableArrayList();
        statsTableView.setItems(data);

        statsIdColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StatsItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<StatsItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getId()));
            }
        });
        minCostColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StatsItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<StatsItem, String> c) {
                return new SimpleStringProperty(String.format("%.2f",(c.getValue().getMincost())));
            }
        });
        timeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StatsItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<StatsItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getTime()));
            }
        });

        iterationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StatsItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<StatsItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getIteration()));
            }
        });

        precentageImprovmentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StatsItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<StatsItem, String> c) {
                return new SimpleStringProperty(String.format("%.2f",(c.getValue().getProcentageImprovment())));
            }
        });

        data.addAll(StatsService.getInstance().getAll());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    data.clear();
                    data.addAll(StatsService.getInstance().getAll());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }




}
