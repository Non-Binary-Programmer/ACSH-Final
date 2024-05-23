import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;

public class GamePanel extends JPanel {
    public int pxWidth = 800;
    public int pxHeight = 800;

    private int gridWidth, gridHeight, pxMargin;

    private boolean loopRunning = false;

    private GameManager manager;

    public GamePanel(GameDisplay display) {
        setBackground(Color.BLACK);

        gridWidth = 7;
        gridHeight = 7;
        pxMargin = 15;

        manager = new GameManager(gridWidth, gridHeight, display);

        addKeyListener(manager);
        addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
            @Override
            public void ancestorResized(HierarchyEvent e) {
                super.ancestorResized(e);
                pxWidth = getWidth();
                pxHeight = getHeight();
            }
        });

        loopRunning = true;
    }

    protected void paintComponent (Graphics g) {
        System.out.println("painting");
        super.paintComponent(g);

        int widthPerCell = (int) Math.ceil((double) pxWidth / gridWidth);
        int heightPerCell = (int) Math.ceil((double) pxHeight / gridHeight);

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                g.setColor(manager.getColor(x, y));
                g.fillRect((widthPerCell * x) + pxMargin, (heightPerCell * y) + pxMargin,
                        widthPerCell - (2 * pxMargin), heightPerCell - (2 * pxMargin));
                if (manager.getColor(x, y).equals(Color.YELLOW)) {
                    g.setColor(Color.ORANGE);
                    int modifiedWidth = (int) Math.ceil((widthPerCell - (2 * pxMargin)) *
                            manager.getCompletionRate(x, y));
                    int modifiedHeight = (int) Math.ceil((heightPerCell - (2 * pxMargin)) *
                            manager.getCompletionRate(x, y));

                    g.fillRect((widthPerCell * x) + (widthPerCell / 2) - (modifiedWidth / 2),
                            (heightPerCell * y) + (heightPerCell / 2) - (modifiedHeight / 2),
                            modifiedWidth, modifiedHeight);
                }
            }
        }

        g.setColor(Color.BLUE);

        g.fillOval((widthPerCell * manager.getPlayerX()) + pxMargin,
                (heightPerCell * manager.getPlayerY()) + pxMargin,
                widthPerCell - (2 * pxMargin), heightPerCell - (2 * pxMargin));

        g.setColor(Color.WHITE);
        g.drawString("Life: " + manager.getLife(), 10, pxHeight);
        g.drawString("Score: " + manager.getScore(), pxWidth - 300, pxHeight);
        g.drawString("Session High Score: " + manager.getHighScore(), pxWidth - 200, pxHeight);
    }

    public Dimension getPreferredSize() {
        return new Dimension(pxWidth, pxHeight);
    }

    public void loop() {
        loopRunning = true;
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

    public void unloop() {
        loopRunning = false;
    }

    public void changeDimensions(int width, int height) {
        this.gridWidth = width;
        this.gridHeight = height;
        manager.changeDimensions(width, height);
    }
}
