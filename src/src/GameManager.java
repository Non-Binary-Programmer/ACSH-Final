import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameManager implements KeyListener {
    private final int width;
    private final int height;

    private final GameDisplay display;

    private int playerX;
    private int playerY;

    private double difficulty = 1.0;
    private long nextHazard = 2000000000;

    private int score = 0;

    private int highScore = 0;
    private int life = 3;

    private final Tile[][] tiles;

    public GameManager (int width, int height, GameDisplay display) {
        this.display = display;
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

    public void reset () {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.clear();
            }
        }

        difficulty = 1.0;
        score = 0;
        life = 3;
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
            double random = Math.random();

            if (random < 1.0 / 8) {
                nextHazard = (long) (1900000000 / Math.sqrt(difficulty));
                for (int x = 0; x < tiles[0].length; x++) {
                    for (int y = 0; y < tiles.length; y++) {
                        if ((x + y) % 2 == (playerX + playerY) % 2) {
                            tiles[y][x].clear();
                            tiles[y][x].readyAttack(
                                    (long) (1000000000 / Math.sqrt(difficulty)),
                                    (long) (900000000 / Math.sqrt(difficulty))
                            );
                        } else {
                            if (Math.abs(x - playerX) + Math.abs(y - playerY) == 1) {
                                if (Math.random() < 0.5 && tiles[y][x].getState() == Tile.State.EMPTY) {
                                    tiles[y][x].becomeTarget((long) (1900000000 / Math.sqrt(difficulty)));
                                }
                            }
                        }
                    }
                }
                score += 100 * Math.pow(difficulty, 2);
                highScore = Math.max(score, highScore);
            } else if (random < 2.0 / 8) {
                nextHazard = (long) (1900000000 / Math.sqrt(difficulty));
                for (int x = 0; x < tiles[0].length; x++) {
                    for (int y = 0; y < tiles.length; y++) {
                        if (y % 2 == playerY % 2) {
                            tiles[y][x].clear();
                            tiles[y][x].readyAttack(
                                    (long) (1000000000 / Math.sqrt(difficulty)),
                                    (long) (900000000 / Math.sqrt(difficulty))
                            );
                        } else {
                            if (Math.abs(x - playerX) + Math.abs(y - playerY) == 3 && y % 2 != playerY % 2) {
                                if (Math.random() < 0.5 && tiles[y][x].getState() == Tile.State.EMPTY) {
                                    tiles[y][x].becomeTarget((long) (1900000000 / Math.sqrt(difficulty)));
                                }
                            }
                        }
                    }
                }
                score += 200 * Math.pow(difficulty, 2);
                highScore = Math.max(score, highScore);
            } else if (random < 3.0 / 8) {
                nextHazard = (long) (1900000000 / Math.sqrt(difficulty));
                for (int x = 0; x < tiles[0].length; x++) {
                    for (int y = 0; y < tiles.length; y++) {
                        if (x % 2 == playerX % 2) {
                            tiles[y][x].clear();
                            tiles[y][x].readyAttack(
                                    (long) (1000000000 / Math.sqrt(difficulty)),
                                    (long) (900000000 / Math.sqrt(difficulty))
                            );
                        } else {
                            if (Math.abs(x - playerX) + Math.abs(y - playerY) == 3 && x % 2 != playerY % 2) {
                                if (Math.random() < 0.5 && tiles[y][x].getState() == Tile.State.EMPTY) {
                                    tiles[y][x].becomeTarget((long) (900000000 / Math.sqrt(difficulty)));
                                }
                            }
                        }
                    }
                }
                score += 200 * Math.pow(difficulty, 2);
                highScore = Math.max(score, highScore);
            } else if (random < 4.0 / 8) {
                nextHazard = (long) (1900000000 / Math.sqrt(difficulty));
                for (int x = 0; x < tiles[0].length; x++) {
                    for (int y = 0; y < tiles.length; y++) {
                        tiles[y][x].clear();
                        if (x != playerX || y != playerY) {
                            tiles[y][x].readyAttack(
                                    (long) (1000000000 / Math.sqrt(difficulty)),
                                    (long) (900000000 / Math.sqrt(difficulty))
                            );
                        } else {
                            if (tiles[y][x].getState() == Tile.State.EMPTY) {
                                tiles[y][x].becomeTarget((long) (1900000000 / Math.sqrt(difficulty)));
                            }
                        }
                    }
                }
                highScore = Math.max(score, highScore);
            } else {
                nextHazard += (long) (2000000000 / difficulty);
                int tilesCreated = 0;
                while (tilesCreated < difficulty * 3) {
                    int targetX = (int) (Math.random() * width);
                    int targetY = (int) (Math.random() * height);

                    if (tiles[targetY][targetX].getState() == Tile.State.EMPTY) {
                        tilesCreated++;
                        if (Math.random() < 0.95) {
                            tiles[targetY][targetX].readyAttack(
                                    (long) (1000000000 / Math.sqrt(difficulty)),
                                    (long) (1000000000 / Math.sqrt(difficulty))
                            );
                            score += 100 * difficulty;
                        } else {
                            tiles[targetY][targetX].becomeTarget((long) (3000000000L / Math.sqrt(difficulty)));
                        }
                    }
                    highScore = Math.max(score, highScore);
                }
            }
        }

        if (tiles[playerY][playerX].getState() == Tile.State.HAZARD) {
            if (--life == 0) {
                int temp = score;
                reset();
                display.toLeaderboardWithScore(temp);
            } else {
                for (Tile[] row: tiles) {
                    for (Tile tile: row) {
                        if (tile.getState() != Tile.State.TARGET) {
                            tile.clear();
                        }
                    }
                }
            }
        }

        if (tiles[playerY][playerX].getState() == Tile.State.TARGET) {
            double rand = Math.random();
            if (rand < 0.5) {
                score += 500 * difficulty;
            } else if (rand < 0.9) {
                score += 200 * difficulty;
                difficulty -= 0.15;
            } else {
                score += 100 * difficulty;
                life++;
            }
            highScore = Math.max(score, highScore);
            tiles[playerY][playerX].clear();
        }
    }

    public Color getColor(int x, int y) {
        return tiles[y][x].getColor();
    }

    public double getCompletionRate(int x, int y) {
        return tiles[y][x].getCompletionRate();
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

    public int getHighScore() {
        return highScore;
    }

    public int getLife() {
        return life;
    }
}
