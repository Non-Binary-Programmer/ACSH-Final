import java.io.Serial;
import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private final int value;
    @Serial
    private static final long serialVersionUID = 3934788245289324L;
    private final String name;

    public Score(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Score o) {
        return value - o.getValue();
    }
}
