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

    private int score = 0;
    private int life = 3;

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
        System.out.println(e.getKeyCode());
        switch (e.getKeyCode()) {
            case 87: // W
            case 38: // Up arrow
            case 104: // Numpad 8
                if (playerY != 0) {
                    playerY--;
                }
                break;
            case 65: // A
            case 37: // Left arrow
            case 100: // Numpad 4
                if (playerX != 0) {
                    playerX--;
                }
                break;
            case 83: // S
            case 40: // Down arrow
            case 98: // Numpad 2
                if (playerY != height - 1) {
                    playerY++;
                }
                break;
            case 68: // D
            case 39: // Right arrow
            case 102: // Numpad 6
                if (playerX != width - 1) {
                    playerX++;
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
            while (tilesCreated < difficulty * 3) {
                int targetX = (int) (Math.random() * width);
                int targetY = (int) (Math.random() * height);

                if (tiles[targetY][targetX].getState() == Tile.State.EMPTY) {
                    tilesCreated++;
                    tiles[targetY][targetX].readyAttack(
                            (long) (1000000000 / Math.sqrt(difficulty)),
                            (long) (1000000000 / Math.sqrt(difficulty))
                    );
                    score += 100 * difficulty;
                }
            }
        }

        if (tiles[playerY][playerX].getState() == Tile.State.HAZARD) {
            if (--life == 0) {
                for (Tile[] row : tiles) {
                    for (Tile tile : row) {
                        tile.clear();
                    }
                }

                difficulty = 1.0;
                score = 0;
                life = 3;
            } else {
                tiles[playerY][playerX].clear();
            }
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

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }
}
