package pl.edu.agh.bo.project.controller;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.AirPlane;
import model.Flight;
import model.SimulationStep;
import services.AirplaneService;
import services.FlightService;
import services.StatsService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by patrykks on 22/04/16.
 */
public class SimulationController {
    @FXML
    private Pane simulationPane;

    private Group group = new Group();

    private Map<Flight,Map<AirPlane,ImageView>> positions = new HashMap<Flight,Map<AirPlane,ImageView>>();
    private ExecutorService executor;
    private Image antImage = new Image("ant.png");

    private int i =0;
    @FXML
    private void initialize() {
        executor = Executors.newCachedThreadPool();
        start();
    }

    private void start() {
        Image airplaneImage = new Image("airplane.png");



        VBox vbox = new VBox(30);

        Button btn = new Button("Action");
        vbox.getChildren().add(btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            SimulationStep simulationStep = null;
                            simulationStep = StatsService.getInstance().getSimulationStep();
                            if (simulationStep != null)
                                makeSimulationStep(simulationStep);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        });

        // simple displays ImageView the image as is
        for (Flight flight : FlightService.getInstance().getAllFlights()) {

            HBox hbox = new HBox();
            positions.put(flight, new HashMap<AirPlane, ImageView>());

            for (AirPlane airPlane : AirplaneService.getInstance().getAllAirplanes()) {
                ImageView iv1 = new ImageView();
                iv1.setImage(airplaneImage);
                iv1.setFitWidth(25);
                iv1.setFitHeight(25);
                hbox.getChildren().add(iv1);
                PathTransition pathTransition = createNewPathTransition();

                positions.get(flight).put(airPlane, iv1);

            }
            vbox.getChildren().add(hbox);


        }

       // simulationScrollPane.setFitToHeight(true);
       // simulationScrollPane.setFitToWidth(true);
        simulationPane.getChildren().add(vbox);




    }

    public double getAirplaneFlightX(AirPlane airPlane, Flight flight) {
        ImageView imageView = positions.get(flight).get(airPlane);
        return  imageView.getLayoutX() -imageView.getParent().getLayoutX() + 12.5;
    }

    public double getAirplaneFlightY(AirPlane airPlane, Flight flight) {
        ImageView imageView = positions.get(flight).get(airPlane);
        return  imageView.getParent().getLayoutY() + 12.5;
    }

    private void makeSimulationStep(SimulationStep simulationStep) {
        ImageView iv1 = new ImageView();
        iv1.setImage(antImage);

        iv1.setFitWidth(15);
        iv1.setFitHeight(15);
        Path path = new Path();

        path.getElements().addAll(new MoveTo(getAirplaneFlightX(simulationStep.getAirPlaneFrom(), simulationStep.getFlightFrom()), getAirplaneFlightY(simulationStep.getAirPlaneFrom(), simulationStep.getFlightFrom())));
        path.getElements().addAll(new LineTo(getAirplaneFlightX(simulationStep.getAirPlaneTo(), simulationStep.getFlightTo()), getAirplaneFlightY(simulationStep.getAirPlaneTo(), simulationStep.getFlightTo()) ));
        path.setStrokeWidth(1);
        path.setStroke(Color.TRANSPARENT);

        PathTransition pt = new PathTransition();
        pt.setNode(iv1);
        pt.setPath(path);
        pt.setDuration(Duration.millis(16000));
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);



        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //System.out.println("Run later task " + simulationStep);
                //System.out.println(path.toString());
                simulationPane.getChildren().add(iv1);
                simulationPane.getChildren().add(path);
                simulationPane.setVisible(false);
                simulationPane.setVisible(true);
                pt.play();
                simulationPane.setVisible(false);
                simulationPane.setVisible(true);
            }
        });




    }

    public PathTransition createNewPathTransition() {
        ImageView iv1 = new ImageView();
        iv1.setImage(antImage);

        iv1.setFitWidth(15);
        iv1.setFitHeight(15);
        Path path = new Path();

        path.setStrokeWidth(1);
        path.setStroke(Color.TRANSPARENT);

        PathTransition pt = new PathTransition();
        pt.setNode(iv1);
        pt.setPath(path);
        pt.setDuration(Duration.millis(16000));
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);
        pt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                path.getElements().clear();
                iv1.setImage(null);
                System.gc();
            }
        });

        simulationPane.getChildren().add(iv1);
        simulationPane.getChildren().add(path);


        return pt;
    }

    private void makeAnimation() {
        double x = getAirplaneFlightX(AirplaneService.getInstance().getAllAirplanes().get(i%14), FlightService.getInstance().getAllFlights().get(i % 24));
        double y= getAirplaneFlightY(AirplaneService.getInstance().getAllAirplanes().get(i%14), FlightService.getInstance().getAllFlights().get(i % 24));
        i++;


        ImageView iv1 = new ImageView();
        iv1.setImage(antImage);

        iv1.setFitWidth(25);
        iv1.setFitHeight(25);
        Path path = new Path();

        path.getElements().addAll(new MoveTo(x, y));
        path.getElements().addAll(new LineTo(x + 25, y + 55));



        path.setStrokeWidth(1);
        path.setStroke(Color.TRANSPARENT);

        PathTransition pt = new PathTransition();
        pt.setNode(iv1);
        pt.setPath(path);
        pt.setDuration(Duration.millis(16000));
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);

        simulationPane.getChildren().add(iv1);
        simulationPane.getChildren().add(path);

        pt.play();


    }


}
