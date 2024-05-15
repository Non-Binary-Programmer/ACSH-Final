import javax.swing.*;

public class EndPanel extends JPanel {
    private final LeaderboardPanel lPanel;
    private final GameDisplay display;
    private final JLabel instructions;
    private final JTextField nameField;
    private final JButton submitButton;
    private int score;

    public EndPanel(GameDisplay display, LeaderboardPanel lPanel, int score) {
        this.score = score;
        this.lPanel = lPanel;
        this.display = display;

        instructions = new JLabel("Enter your name here: ");
        add(instructions);

        nameField = new JTextField(20);
        nameField.addActionListener(e -> {
            lPanel.addScore(new Score(this.score, nameField.getText()));
            display.changePanel(GameDisplay.State.LEADERBOARD);
        });
        add(nameField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            lPanel.addScore(new Score(this.score, nameField.getText()));
            display.changePanel(GameDisplay.State.LEADERBOARD);
        });
        add(submitButton);
    }

    public void setScore (int score) {
        this.score = score;
    }

    public void focusField() {
        nameField.requestFocusInWindow();
    }
}
