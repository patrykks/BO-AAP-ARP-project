package pl.edu.agh.bo.project.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.FlightTableItem;
import services.FlightTableService;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by patrykks on 13/04/16.
 */
public class TableViewController {
    private Set<FlightTableItem> flightSet;
    private ExecutorService executor;

    @FXML
    private TableView<FlightTableItem> tableView;

    @FXML
    private TableColumn<FlightTableItem, String> flightIdColumn;

    @FXML
    private TableColumn<FlightTableItem, String> startAirportColumn;

    @FXML
    private TableColumn<FlightTableItem, String> endAirportColumn;

    @FXML
    private TableColumn<FlightTableItem, String> plannedDepTimeColumn;

    @FXML
    private TableColumn<FlightTableItem, String> realdDepTimeColumn;

    @FXML
    private TableColumn<FlightTableItem, String> numbuerOfPassngerColumn;

    @FXML
    private TableColumn<FlightTableItem, String> flightdelayPenealtyColumn;

    @FXML
    private TableColumn<FlightTableItem, String> cancelledFlightPenaltyColumn;

    @FXML
    private TableColumn<FlightTableItem, String> airplaneIdColumn;



    @FXML
    private void initialize() {
        executor = Executors.newCachedThreadPool();;
        ObservableList<FlightTableItem> data = FXCollections.observableArrayList();
        tableView.setItems(data);
        flightIdColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getId()));
            }
        });
        startAirportColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(c.getValue().getStartAirport().toString());
            }
        });
        endAirportColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(c.getValue().getEndAirport().toString());
            }
        });

        plannedDepTimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(c.getValue().getPlannedDepTIme().toString());
            }
        });

        realdDepTimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                Date realDepTime = c.getValue().getRealdDepTIme();
                String date = "";
                if (realDepTime != null) {
                    date = realDepTime.toString();
                }
                return new SimpleStringProperty(date);
            }
        });

        numbuerOfPassngerColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getNumbuerOfPassnger()));
            }
        });

        flightdelayPenealtyColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getFlightdelayPenealty()));
            }
        });

        cancelledFlightPenaltyColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getCancelledFlightPenalty()));
            }
        });

        airplaneIdColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FlightTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FlightTableItem, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().getAirplaneId()));
            }
        });
        ;
        data.addAll(FlightTableService.getInstance().getAll());

        tableView.setRowFactory(new Callback<TableView<FlightTableItem>, TableRow<FlightTableItem>>() {
            @Override
            public TableRow<FlightTableItem> call(TableView<FlightTableItem> paramP) {
                return new TableRow<FlightTableItem>() {
                    @Override
                    protected void updateItem(FlightTableItem oldFlightTableItem, boolean paramBoolean) {
                        String red = "-fx-background-color: palevioletred";
                        String green = "-fx-background-color: palegreen";
                        if (oldFlightTableItem != null) {
                            FlightTableItem updatedItem = FlightTableService.getInstance().getByID(oldFlightTableItem.getId());
                            setStyle(red);
                            if (!updatedItem.isCancelled()) {
                                setStyle(green);
                            }
                            tableView.getColumns().get(5).setVisible(false);
                            tableView.getColumns().get(5).setVisible(true);
                            super.updateItem(updatedItem, paramBoolean);
                        }

                    }
                };
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    data.clear();
                    data.addAll(FlightTableService.getInstance().getAll());
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
