import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameDisplay {
    public static final String GAME_NAME = "Dodge!";

    public void toLeaderboardWithScore(int score) {
        ePanel.setScore(score);
        changePanel(State.END);
    }

    public enum State {
        DISPLAY,
        MENU,
        END, LEADERBOARD
    }
    private State state;

    private JFrame frame;
    private final GamePanel gPanel;
    private final MenuPanel mPanel;
    private final LeaderboardPanel lPanel;
    private final EndPanel ePanel;

    public GameDisplay() {
        frame = new JFrame(GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gPanel = new GamePanel(this);
        mPanel = new MenuPanel(this);
        lPanel = new LeaderboardPanel(this);
        ePanel = new EndPanel(this, lPanel, 0);
        frame.add(mPanel);

        this.state = State.MENU;

        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                lPanel.saveScores();
            }
        });
        frame.setVisible(true);
    }

    public void changePanel(State target) {
        switch (state) {
            case MENU -> frame.remove(mPanel);
            case DISPLAY -> {
                frame.remove(gPanel);
                gPanel.unloop();
            }
            case LEADERBOARD -> frame.remove(lPanel);
            case END -> frame.remove(ePanel);
        }
        this.state = target;
        switch (target) {
            case LEADERBOARD -> {
                frame.add(lPanel);
                frame.pack();
                lPanel.focusButton();
            }
            case DISPLAY -> {
                frame.add(gPanel);
                frame.pack();

                gPanel.requestFocusInWindow();
                Thread loopThread = new Thread(gPanel::loop);
                loopThread.start();
            }
            case MENU -> {
                frame.add(mPanel);
                frame.pack();
            }
            case END -> {
                frame.add(ePanel);
                frame.pack();
                ePanel.focusField();
            }
        }
    }
}
