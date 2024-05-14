import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class LeaderboardPanel extends JPanel {
    private final TreeSet<Score> leaderboard;
    private final ArrayList<JLabel> scoreLabels;
    private final ArrayList<JLabel> nameLabels;
    private final GameDisplay display;

    public LeaderboardPanel(GameDisplay display) {
        this.display = display;
        leaderboard = new TreeSet<>();
        scoreLabels = new ArrayList<>();
        nameLabels = new ArrayList<>();

        setLayout(new GridLayout(0,2));

        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> display.changePanel(GameDisplay.State.MENU));

        JButton playButton = new JButton("Again!");
        playButton.addActionListener(e -> display.changePanel(GameDisplay.State.DISPLAY));

        add(menuButton);
        add(playButton);

        add(new JLabel("Name"));
        add(new JLabel("Score"));

        updateArrays();
        setVisible(true);
    }

    private void clearPanel() {
        for (int i = 0; i < scoreLabels.size(); i++) {
            remove(scoreLabels.get(i));
            remove(nameLabels.get(i));
        }
    }

    private void updateArrays() {
        Iterator<Score> iter = leaderboard.iterator();
        int i = 0;
        while (iter.hasNext()) {
            Score score = iter.next();

            scoreLabels.get(i).setText(Integer.toString(score.getValue()));
            nameLabels.get(i).setText(score.getName());

            add(scoreLabels.get(i));
            add(nameLabels.get(i));

            i++;
        }
    }

    public void addScore(Score score) {
        clearPanel();
        leaderboard.add(score);
        scoreLabels.add(new JLabel());
        nameLabels.add(new JLabel());
        updateArrays();
    }
}
