import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    public static final int PX_WIDTH = 800;
    public static final int PX_HEIGHT = 800;

    private int gridWidth, gridHeight, pxMargin;

    private boolean loopRunning = false;

    private GameManager manager;

    public DisplayPanel () {
        setBackground(Color.BLACK);

        gridWidth = 8;
        gridHeight = 8;
        pxMargin = 10;

        manager = new GameManager(gridWidth, gridHeight);

        addKeyListener(manager);

        loopRunning = true;
    }

    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        int widthPerCell = (int) Math.ceil((double) PX_WIDTH / gridWidth);
        int heightPerCell = (int) Math.ceil((double) PX_HEIGHT / gridHeight);

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                g.setColor(manager.getColor(x, y));
                g.fillRect((widthPerCell * x) + pxMargin, (heightPerCell * y) + pxMargin,
                        widthPerCell - (2 * pxMargin), heightPerCell - (2 * pxMargin));
            }
        }

        g.setColor(Color.BLUE);

        g.fillOval((widthPerCell * manager.getPlayerX()) + pxMargin,
                (heightPerCell * manager.getPlayerY()) + pxMargin,
                widthPerCell - (2 * pxMargin), heightPerCell - (2 * pxMargin));

        g.setColor(Color.WHITE);
        g.drawString("Life: " + manager.getLife(), 10, PX_HEIGHT);
    }

    public Dimension getPreferredSize() {
        return new Dimension(PX_WIDTH, PX_HEIGHT);
    }

    public void loop() {
        long lastTime = System.nanoTime();

        while (loopRunning) {
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            //do things
            repaint();
            manager.update(deltaTime);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
    }


}
