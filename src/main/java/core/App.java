package core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    private static Player player = new Player(400, 300); // Player starts at the center
    private static Camera camera = new Camera(player);

    private static final Image BACKGROUND_IMAGE = new Image("background.jpg");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);

        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Resize listener to adjust canvas size when window is resized
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            canvas.setWidth(newWidth.doubleValue());
        });

        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            canvas.setHeight(newHeight.doubleValue());
        });

        AnimationTimer animationLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                gc.drawImage(BACKGROUND_IMAGE, -camera.getX(), -camera.getY());

                // Update camera position based on the player's movement
                camera.update();

                // Draw entities using the camera offset
                gc.setFill(Color.BLUE);
                gc.fillRect(1000 - camera.getX(), 100 - camera.getY(), 100, 100); // Static entity

                gc.setFill(Color.RED);
                gc.fillRect(player.getX() - camera.getX(), player.getY() - camera.getY(), 100, 100); // Player

                // Move player for testing
                player.move(0, 0);


            }
        };
        animationLoop.start();

        scene.setOnKeyPressed((e) -> {
            if(e.getCode() == KeyCode.A){
                player.move(-10, 0);
            }

            if(e.getCode() == KeyCode.D){
                player.move(10, 0);
            }

            if(e.getCode() == KeyCode.S){
                player.move(0, 10);
            }

            if(e.getCode() == KeyCode.W){
                player.move(0, -10);
            }
        });
    }
}
