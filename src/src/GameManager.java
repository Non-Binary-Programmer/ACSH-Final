import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameManager implements KeyListener {
    private final int width;
    private final int height;

    private int playerX;
    private int playerY;

    private final Tile[][] tiles;

    public GameManager (int width, int height) {
        this.width = width;
        this.height = height;
        this.playerX = width / 2;
        this.playerY = height / 2;

        this.tiles = new Tile[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col] = new Tile();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
