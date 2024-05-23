import java.io.Serial;
import java.io.Serializable;

public record Score(int value, String name) implements Comparable<Score>, Serializable {
    @Serial
    private static final long serialVersionUID = 3934788245289324L;

    @Override
    public int compareTo(Score o) {
        return value - o.value();
    }
}
