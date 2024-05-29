import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderboardPanel extends JPanel {
    private ArrayList<List<Score>> leaderboards;
    private final ArrayList<JLabel> scoreLabels;
    private final ArrayList<JLabel> nameLabels;
    private final GameDisplay display;
    private final JButton menuButton, playButton, clearButton, cleanButton, plusButton, minusButton;
    private final JTable leaderboardTable;
    private final JScrollPane scrollPane;
    private final JPanel sizePanel, northPanel;
    private final JLabel sizeLabel;

    private int size = 7;

    @SuppressWarnings("unchecked")
    public LeaderboardPanel(GameDisplay display) {
        this.display = display;
        scoreLabels = new ArrayList<>();
        nameLabels = new ArrayList<>();
        leaderboards = new ArrayList<>();
        leaderboardTable = new JTable(new TableModel() {
            @Override
            public int getRowCount() {
                return leaderboards.get(size - 5).size();
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
                    return leaderboards.get(size - 5).get(rowIndex).value();
                } else {
                    return leaderboards.get(size - 5).get(rowIndex).name();
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
        playButton.addActionListener(e -> display.changePanel(GameDisplay.State.GAME));

        clearButton = new JButton("Clear scores");
        clearButton.addActionListener(e -> {
            leaderboards.get(size - 5).clear();
            leaderboardTable.repaint();
        });

        cleanButton = new JButton("Clean up scores (one per player)");
        cleanButton.addActionListener(e -> {
            updateArrays();
            ArrayList<Score> unique = new ArrayList<>();
            for (Score score : leaderboards.get(size - 5)) {
                if (unique.stream().noneMatch(s -> s.name().equals(score.name()) || s.name().equals(""))) {
                    unique.add(score);
                }
            }
            leaderboards.set(size - 5, unique);
            leaderboardTable.repaint();
        });
        cleanButton.setAlignmentX(CENTER_ALIGNMENT);
        cleanButton.setPreferredSize(new Dimension(this.getWidth(), cleanButton.getPreferredSize().height));

        this.sizePanel = new JPanel();
        sizePanel.setLayout(new BorderLayout());

        this.sizeLabel = new JLabel("Size: " + size);
        sizeLabel.setAlignmentX(CENTER_ALIGNMENT);
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sizePanel.add(sizeLabel, BorderLayout.CENTER);

        this.plusButton = new JButton("+");
        plusButton.addActionListener(e -> {
            size++;
            size = Math.min(size, 15);
            sizeLabel.setText("Size: " + size);
            display.changeDimensions(size, size);
            leaderboardTable.repaint();
        });
        sizePanel.add(plusButton, BorderLayout.EAST);

        this.minusButton = new JButton("-");
        minusButton.addActionListener(e -> {
            size--;
            size = Math.max(size, 5);
            sizeLabel.setText("Size: " + size);
            display.changeDimensions(size, size);
            leaderboardTable.repaint();
        });
        sizePanel.add(minusButton, BorderLayout.WEST);

        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(cleanButton);
        northPanel.add(sizePanel);

        add(menuButton, BorderLayout.WEST);
        add(playButton, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);
        add(clearButton, BorderLayout.SOUTH);

        scrollPane = new JScrollPane(leaderboardTable);

        add(scrollPane, BorderLayout.CENTER);

        try (FileInputStream fileStream = new FileInputStream("src/src/scores.txt");
             ObjectInputStream objectStream = new ObjectInputStream(fileStream)) {
            leaderboards = (ArrayList<List<Score>>) objectStream.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
            leaderboards = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                ArrayList<Score> starter = new ArrayList<>();
                int score = 100000;
                for (int j = 0; j < 10; j++) {
                    starter.add(new Score(score, ""));
                    score -= 10000;
                }
                leaderboards.add(starter);
            }
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
        System.out.println(leaderboards);
        leaderboards.set(size - 5, leaderboards.get(size - 5).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    public void addScore(Score score) {
        clearPanel();
        leaderboards.get(size - 5).add(score);
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
            objectStream.writeObject(leaderboards);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSize (int size) {
        this.size = size;
        sizeLabel.setText("Size: " + size);
    }
}
