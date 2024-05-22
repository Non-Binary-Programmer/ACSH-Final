import java.awt.*;

public class Tile {
    public enum State {
        EMPTY,
        WARNING,
        TARGET, HAZARD
    }

    private State state = State.EMPTY;
    private long warningTimer;
    private long hazardTimer;
    private long targetTimer;
    private long initialTimer;

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
                return Color.YELLOW;
            }
            case TARGET -> {
                return Color.GREEN;
            }
        }
    }

    public void update (long deltaTime) {
        switch (this.state) {
            case WARNING -> {
                this.warningTimer -= deltaTime;
                if (warningTimer <= 0) {
                    this.state = State.HAZARD;
                    this.initialTimer = hazardTimer;
                    this.hazardTimer += warningTimer;
                }
            }
            case HAZARD -> {
                this.hazardTimer -= deltaTime;
                if (hazardTimer <= 0) {
                    this.state = State.EMPTY;
                }
            }
            case TARGET -> {
                this.targetTimer -= deltaTime;
                if (targetTimer <= 0) {
                    this.state = State.EMPTY;
                }
            }
        }
    }

    public void readyAttack (long warningTime, long hazardTime) {
        if (this.state != State.EMPTY) {
            throw new IllegalStateException();
        }
        this.initialTimer = warningTime;
        this.warningTimer = warningTime;
        this.hazardTimer = hazardTime;

        this.state = State.WARNING;
    }

    public void becomeTarget (long targetTime) {
        if (this.state != State.EMPTY) {
            throw new IllegalStateException();
        }
        this.targetTimer = targetTime;
        this.initialTimer = targetTime;

        this.state = State.TARGET;
    }

    public State getState() {
        return state;
    }

    public void clear() {
        this.state = State.EMPTY;
    }

    public double getCompletionRate() {
        switch (state) {
            case TARGET -> {
                return 1 - (double) (targetTimer) / initialTimer;
            }
            case WARNING -> {
                return 1 - (double) (warningTimer) / initialTimer;
            }
            case HAZARD -> {
                return 1 - (double) (hazardTimer) / initialTimer;
            }
            case default -> throw new IllegalStateException();
        }
    }
}
