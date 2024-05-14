import javax.swing.*;

public class GameDisplay {
    public static final String GAME_NAME = "Dodge!";

    public void toLeaderboardWithScore(int score) {
        changePanel(State.END);
    }

    public enum State {
        DISPLAY,
        MENU,
        END, LEADERBOARD
    }
    private State state;

    private JFrame frame;
    private final DisplayPanel dPanel;
    private final MenuPanel mPanel;
    private final LeaderboardPanel lPanel;

    public GameDisplay() {
        frame = new JFrame(GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dPanel = new DisplayPanel(this);
        mPanel = new MenuPanel(this);
        lPanel = new LeaderboardPanel(this);
        frame.add(mPanel);

        this.state = State.MENU;

        frame.pack();
        frame.setVisible(true);
    }

    public void changePanel(State target) {
        switch (state) {
            case MENU -> frame.remove(mPanel);
            case DISPLAY -> {
                frame.remove(dPanel);
                dPanel.unloop();
            }
            case LEADERBOARD -> frame.remove(lPanel);
        }
        this.state = target;
        switch (target) {
            case LEADERBOARD -> {
                frame.add(lPanel);
                frame.pack();
            }
            case DISPLAY -> {
                frame.add(dPanel);
                frame.pack();

                dPanel.requestFocusInWindow();
                Thread loopThread = new Thread(dPanel::loop);
                loopThread.start();
            }
            case MENU -> {
                frame.add(mPanel);
                frame.pack();
            }
        }
    }
}
