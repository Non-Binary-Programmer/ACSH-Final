import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class LeaderboardPanel extends JPanel {
    private final ArrayList<Score> leaderboard;
    private final ArrayList<JLabel> scoreLabels;
    private final ArrayList<JLabel> nameLabels;
    private final GameDisplay display;
    private final JButton menuButton, playButton;

    public LeaderboardPanel(GameDisplay display) {
        this.display = display;
        leaderboard = new ArrayList<>();
        scoreLabels = new ArrayList<>();
        nameLabels = new ArrayList<>();

        setLayout(new GridLayout(0,2));

        menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> display.changePanel(GameDisplay.State.MENU));

        playButton = new JButton("Again!");
        playButton.addActionListener(e -> display.changePanel(GameDisplay.State.DISPLAY));

        add(menuButton);
        add(playButton);

        add(new JLabel("Score"));
        add(new JLabel("Name"));

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
        Iterator<Score> iter = leaderboard.stream().sorted(Comparator.reverseOrder()).iterator();
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

    public void focusButton() {
        playButton.requestFocusInWindow();
    }

    public void saveScores() {
        try (FileOutputStream fileStream = new FileOutputStream("src/src/scores.tmp");
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(leaderboard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
