import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private final GameDisplay display;

    private final JLabel titleLabel;

    private final JButton playButton, leaderButton;

    public MenuPanel (GameDisplay display) {
        this.display = display;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.titleLabel = new JLabel("Dodge!");
        add(titleLabel);

        this.playButton = new JButton("Play!");
        playButton.addActionListener(e -> display.changePanel(GameDisplay.State.DISPLAY));
        add(playButton);

        this.leaderButton = new JButton("Leaderboard");
        leaderButton.addActionListener(e -> display.changePanel(GameDisplay.State.LEADERBOARD));
        add(leaderButton);
    }
}
