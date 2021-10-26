package de.topcu.game.entities;

import de.topcu.game.AgarIO;
import de.topcu.game.utils.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;

@Getter
public class Dot extends Pane {

    @Getter
    private static final ObservableList<Dot> allDots = FXCollections.observableArrayList();

    private final int size = 6;
    private final Circle circle;

    private final int x, y;

    private Dot(int x, int y) {
        this.x = x;
        this.y = y;

        this.circle = new Circle(x, y, this.size);

        this.circle.setCenterX(x);
        this.circle.setCenterY(y);

        float r = AgarIO.RANDOM.nextFloat();
        float g = AgarIO.RANDOM.nextFloat() / 2F;
        float b = AgarIO.RANDOM.nextFloat() / 2F;

        this.circle.setStroke(Color.WHITE);
        this.circle.setStrokeWidth(0.5);
        this.circle.setFill(Color.color(r, g, b));

        if (getChildren().stream().noneMatch(this::isInNear)) {
            getChildren().add(this.circle);
        }
    }

    public static void create() {
        Dot dot = new Dot(AgarIO.RANDOM.nextInt(Settings.SCENE_WIDTH), AgarIO.RANDOM.nextInt(Settings.SCENE_HEIGHT));
        allDots.add(dot);
        AgarIO.getDotField().getChildren().addAll(dot);
    }

    public static void init() {
        for (int i = 0; i < 100; i++) {
            create();
        }
    }

    private boolean isInNear(Node node) {
        if (!(node instanceof Dot)) {
            return false;
        }
        Dot dot = (Dot) node;

        double dx1 = (dot.getX() * dot.getX());
        double dx2 = getX() * getX();

        double dy1 = dot.getY() * dot.getY();
        double dy2 = getY() * getY();

        return Math.sqrt(dx1 + dx2 + dy1 + dy2) <= 10;
    }

    public void destroy() {
        allDots.remove(this);
        AgarIO.getDotField().getChildren().removeAll(this);
    }

}
