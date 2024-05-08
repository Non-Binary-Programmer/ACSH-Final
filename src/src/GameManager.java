import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameManager implements KeyListener {
    private final int width;
    private final int height;

    private int playerX;
    private int playerY;

    private double difficulty = 1.0;
    private long nextHazard = 2000000000;

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
        System.out.println(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    public void update (long deltaTime) {
        for (Tile[] row: tiles) {
            for (Tile tile: row) {
                tile.update(deltaTime);
            }
        }

        nextHazard -= deltaTime;
        if (nextHazard <= 0) {
            difficulty += 0.05;
            nextHazard += (long) (2000000000 / difficulty);

            int tilesCreated = 0;
            while (tilesCreated < difficulty) {
                int targetX = (int) (Math.random() * width);
                int targetY = (int) (Math.random() * height);

                if (tiles[targetY][targetX].getState() == Tile.State.EMPTY) {
                    tilesCreated++;
                    tiles[targetY][targetX].readyAttack(
                            (long) (1000000000 / Math.sqrt(difficulty)),
                            (long) (1000000000 / Math.sqrt(difficulty))
                    );
                }
            }
        }

        if (tiles[playerY][playerX].getState() == Tile.State.HAZARD) {
            for (Tile[] row: tiles) {
                for(Tile tile: row) {
                    tile.clear();
                }
            }

            difficulty = 1.0;
        }
    }

    public Color getColor(int x, int y) {
        return tiles[y][x].getColor();
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
