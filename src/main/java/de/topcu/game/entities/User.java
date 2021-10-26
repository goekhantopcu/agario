package de.topcu.game.entities;

import de.topcu.game.AgarIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User extends Pane {

    @Getter
    private static final List<User> allUsers = new ArrayList<>();

    private final Circle circle;
    private final ObservableList<Circle> splits = FXCollections.observableArrayList();

    public User(double x, double y) {
        this.circle = new Circle(x, y, 20);

        this.circle.setCenterX(x);
        this.circle.setCenterY(y);

        this.circle.setStroke(Color.WHITE);
        this.circle.setStrokeWidth(0.5);
        this.circle.setFill(Color.GREEN);

        this.getChildren().add(this.circle);
    }

    public boolean inNear(Dot dot) {
        return Shape.intersect(this.circle, dot.getCircle()).getBoundsInLocal().getWidth() != -1;
    }

    public void resize(double amount) {
        this.circle.setRadius(this.circle.getRadius() + amount);
        AgarIO.getPlayerInfo().setText("Radius: " + Math.round(this.circle.getRadius()));
    }

    // ToDo: Method to split user

}
