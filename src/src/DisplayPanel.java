import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    public static final int PX_WIDTH = 800;
    public static final int PX_HEIGHT = 800;

    private int gridWidth, gridHeight, pxMargin;

    private boolean loopRunning = false;

    public DisplayPanel () {
        setBackground(Color.BLACK);

        // addInputListener(controller);

        gridWidth = 8;
        gridHeight = 8;
        pxMargin = 10;
    }

    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        int widthPerCell = (int) Math.ceil((double) PX_WIDTH / gridWidth);
        int heightPerCell = (int) Math.ceil((double) PX_HEIGHT / gridHeight);

        g.setColor(Color.WHITE);

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                g.fillRect((widthPerCell * x) + pxMargin, (heightPerCell * y) + pxMargin,
                        widthPerCell - (2 * pxMargin), heightPerCell - (2 * pxMargin));
            }
        }
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
        }
    }


}
