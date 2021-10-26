package de.topcu.game;

import de.topcu.game.entities.Dot;
import de.topcu.game.entities.User;
import de.topcu.game.utils.Settings;
import de.topcu.game.utils.Vector2D;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;

import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AgarIO extends Application {

    public static final Random RANDOM = new Random();

    @Getter
    private static final Pane playField = new Pane(), dotField = new Pane();

    @Getter
    private static final Label playerInfo = new Label();

    private final Vector2D mouse = new Vector2D(0, 0);

    private User user;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        StackPane layerPane = new StackPane();

        layerPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        playerInfo.setFont(new Font("Verdana", 25));
        playerInfo.setTextFill(Color.WHITE);

        layerPane.getChildren().addAll(dotField, playField);

        root.setCenter(layerPane);

        Scene scene = new Scene(root, Settings.SCENE_WIDTH / 2, Settings.SCENE_HEIGHT / 2);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.setResizable(false);

        Dot.init();

        user = new User(scene.getWidth() / 2, scene.getHeight() / 2);
        playField.getChildren().addAll(user, playerInfo);

        // ToDo: Add movement!
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    TranslateTransition tt = new TranslateTransition(Duration.millis(250), dotField);
                    tt.setFromX(dotField.getTranslateX());
                    tt.setFromY(dotField.getTranslateY());

                    double speed = 50;
                    double dx = (Settings.SCENE_WIDTH / 4 < mouse.x ? -1 : 1) * speed;
                    double dy = (Settings.SCENE_HEIGHT / 4 < mouse.y ? -1 : 1) * speed;

                    tt.setToX(tt.getFromX() + dx);
                    tt.setToY(tt.getFromY() + dy);

                    tt.play();

//                    Dot.getAllDots().forEach(dot -> {
//                        if (outOfWindow(dot.getCircle())) {
//                            dotField.getChildren().removeAll(dot);
//                        } else {
//
//                        }
//                    });
                });
            }
        };

        timer.schedule(timerTask, 0L, 100L);

        scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            mouse.set(event.getX(), event.getY());
            this.foodCheck(user);
        });

        stage.show();
    }

    private boolean outOfWindow(Node node) {
        return !node.getBoundsInParent().intersects(node.getBoundsInParent().getWidth(), node.getBoundsInParent().getHeight(),
                playField.getWidth() - node.getBoundsInParent().getWidth() * 2,
                playField.getHeight() - node.getBoundsInParent().getHeight() * 2);
    }

    private void foodCheck(User user) {
        Optional<Dot> optional = dotField.getChildren().stream()
                .filter(node -> node instanceof Dot).map(node -> ((Dot) node)).filter(user::inNear).findFirst();

        if (optional.isPresent()) {
            optional.get().destroy();
            Dot.create();
            user.resize(0.5);
        }
    }

}