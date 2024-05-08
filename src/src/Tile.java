import java.awt.*;

public class Tile {
    public enum State {
        EMPTY,
        WARNING,
        HAZARD
    }

    private State state = State.EMPTY;
    private long warningTimer;
    private long hazardTimer;

    public Tile () {

    }

    public Color getColor () {
        switch (this.state) {
            case EMPTY, default -> {
                return Color.WHITE;
            }
            case HAZARD -> {
                return Color.RED;
            }
            case WARNING -> {
                return Color.ORANGE;
            }
        }
    }

    public void update (long deltaTime) {
        switch (this.state) {
            case WARNING -> {
                this.warningTimer -= deltaTime;
                if (warningTimer <= 0) {
                    this.state = State.HAZARD;
                }
            }
            case HAZARD -> {
                this.hazardTimer -= deltaTime;
                if (hazardTimer <= 0) {
                    this.state = State.EMPTY;
                }
            }
        }
    }

    public void readyAttack (long warningTime, long hazardTime) {
        if (this.state != State.EMPTY) {
            throw new IllegalStateException();
        }
        this.warningTimer = warningTime;
        this.hazardTimer = hazardTime;

        this.state = State.WARNING;
    }

    public State getState() {
        return state;
    }

    public void clear() {
        this.state = State.EMPTY;
    }
}
