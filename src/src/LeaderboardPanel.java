import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
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
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class LeaderboardPanel extends JPanel {
    private List<Score> leaderboard;
    private final ArrayList<JLabel> scoreLabels;
    private final ArrayList<JLabel> nameLabels;
    private final GameDisplay display;
    private final JButton menuButton, playButton, clearButton, cleanButton;
    private final JTable leaderboardTable;
    private final JScrollPane scrollPane;

    public LeaderboardPanel(GameDisplay display) {
        this.display = display;
        scoreLabels = new ArrayList<>();
        nameLabels = new ArrayList<>();
        leaderboard = new ArrayList<>();
        leaderboardTable = new JTable(new TableModel() {
            @Override
            public int getRowCount() {
                return leaderboard.size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int columnIndex) {
                if (columnIndex == 0) {
                    return "score";
                } else {
                    return "name";
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                } else {
                    return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return leaderboard.get(rowIndex).getValue();
                } else {
                    return leaderboard.get(rowIndex).getName();
                }
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }
        });

        setLayout(new BorderLayout());

        menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> display.changePanel(GameDisplay.State.MENU));

        playButton = new JButton("Again!");
        playButton.addActionListener(e -> display.changePanel(GameDisplay.State.DISPLAY));

        clearButton = new JButton("Clear scores");
        clearButton.addActionListener(e -> {
            leaderboard.clear();
            leaderboardTable.repaint();
        });

        cleanButton = new JButton("Clean up scores (one per player)");
        cleanButton.addActionListener(e -> {
            updateArrays();
            ArrayList<Score> unique = new ArrayList<>();
            for (Score score : leaderboard) {
                if (unique.stream().noneMatch(s -> s.getName().equals(score.getName()))) {
                    unique.add(score);
                }
            }
            leaderboard = unique;
            leaderboardTable.repaint();
        });

        add(menuButton, BorderLayout.WEST);
        add(playButton, BorderLayout.EAST);
        add(cleanButton, BorderLayout.NORTH);
        add(clearButton, BorderLayout.SOUTH);

        scrollPane = new JScrollPane(leaderboardTable);

        add(scrollPane, BorderLayout.CENTER);

        try (FileInputStream fileStream = new FileInputStream("src/src/scores.txt");
             ObjectInputStream objectStream = new ObjectInputStream(fileStream)) {
            leaderboard = (ArrayList<Score>) objectStream.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
            leaderboard = new ArrayList<>();
        }

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
        leaderboard = leaderboard.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
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
        try (FileOutputStream fileStream = new FileOutputStream("src/src/scores.txt");
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(leaderboard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
