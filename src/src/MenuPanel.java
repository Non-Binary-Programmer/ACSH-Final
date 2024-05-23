import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final GameDisplay display;

    private final JPanel sizePanel;

    private final JLabel titleLabel, sizeLabel;
    private int size = 7;

    private final JButton playButton, leaderButton, plusButton, minusButton;

    public MenuPanel (GameDisplay display) {
        this.display = display;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.titleLabel = new JLabel("Dodge!");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        this.sizePanel = new JPanel();
        sizePanel.setLayout(new BorderLayout());

        this.sizeLabel = new JLabel("Size: " + size);
        sizePanel.add(sizeLabel, BorderLayout.CENTER);

        this.plusButton = new JButton("+");
        plusButton.addActionListener(e -> {
            size++;
            size = Math.min(size, 15);
            sizeLabel.setText("Size: " + size);
            display.changeDimensions(size, size);
        });
        sizePanel.add(plusButton, BorderLayout.EAST);

        this.minusButton = new JButton("-");
        minusButton.addActionListener(e -> {
            size--;
            size = Math.max(size, 4);
            sizeLabel.setText("Size: " + size);
            display.changeDimensions(size, size);
        });
        sizePanel.add(minusButton, BorderLayout.WEST);

        add(sizePanel);

        this.playButton = new JButton("Play!");
        playButton.addActionListener(e -> display.changePanel(GameDisplay.State.GAME));
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        add(playButton);

        this.leaderButton = new JButton("Leaderboard");
        leaderButton.addActionListener(e -> display.changePanel(GameDisplay.State.LEADERBOARD));
        leaderButton.setAlignmentX(CENTER_ALIGNMENT);
        add(leaderButton);

        setPreferredSize(new Dimension(200, 100));
    }

    public void setSize(int size) {
        this.size = size;
        sizeLabel.setText("Size: " + size);
    }
}
