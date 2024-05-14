public class Score implements Comparable<Score>{
    private final int value;
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
