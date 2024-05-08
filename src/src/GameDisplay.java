import javax.swing.*;

public class GameDisplay {
    public static final String GAME_NAME = "Dodge!";

    private JFrame frame;
    private DisplayPanel dPanel;

    public GameDisplay() {
        frame = new JFrame(GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dPanel = new DisplayPanel();
        frame.add(dPanel);

        frame.pack();
        frame.setVisible(true);

        dPanel.loop();
    }
}
