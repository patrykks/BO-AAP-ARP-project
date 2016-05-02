package pl.edu.agh.bo.project.controller;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.AirPlane;
import model.Flight;
import services.AirplaneService;
import services.FlightService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by patrykks on 22/04/16.
 */
public class SimulationController {
    @FXML
    private ScrollPane simulationScrollPane;

    private Map<Flight,Map<AirPlane,ImageView>> positions = new HashMap<Flight,Map<AirPlane,ImageView>>();
    private ExecutorService executor;
    private Image antImage = new Image("ant.png");
    private StackPane holder = new StackPane();
    private int i =0;
    @FXML
    private void initialize() {
        executor = Executors.newCachedThreadPool();
        start();
    }

    private void start() {
        Image airplaneImage = new Image("airplane.png");



        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);

        Button btn = new Button("Action");
        vbox.getChildren().add(btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                makeAnimation();
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

                positions.get(flight).put(airPlane, iv1);

            }
            hbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(hbox);


        }

        simulationScrollPane.setFitToHeight(true);
        simulationScrollPane.setFitToWidth(true);
        holder.getChildren().add(vbox);
        simulationScrollPane.setContent(holder);



    }

    private void makeAnimation() {
        System.out.println(holder.getLayoutBounds());

        i++;
        ImageView imageView = positions.get(FlightService.getInstance().getAllFlights().get(i % 25)).get(AirplaneService.getInstance().getAllAirplanes().get(0));
        double x = imageView.getX() - imageView.getBoundsInParent().getMinX();
        double y= imageView.getY();
        System.out.println(imageView.getX());
        System.out.println(imageView.getY());
        System.out.println(imageView.getLayoutX());
        System.out.println(imageView.getLayoutY());
        System.out.println(imageView.getBoundsInLocal());
        System.out.println(imageView.getBoundsInParent());
        System.out.println(imageView.getLayoutBounds());

        ImageView iv1 = new ImageView();
        iv1.setImage(antImage);

        iv1.setFitWidth(25);
        iv1.setFitHeight(25);
        Path path = new Path();

        path.getElements().addAll(new MoveTo(x, y));
        path.getElements().addAll(new LineTo(x + 50, y + 50));


        path.setStrokeWidth(1);
        path.setStroke(Color.TRANSPARENT);

        PathTransition pt = new PathTransition();
        pt.setNode(iv1);
        pt.setPath(path);
        pt.setDuration(Duration.millis(16000));
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);

        holder.getChildren().add(iv1);
        holder.getChildren().add(path);

        pt.play();
    }


}
