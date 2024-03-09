package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // calculation base: frames per second
    private static final int FPS = 60;

    // tile size of 16x16
    final int originalTileSize = 16;

    // scale for high density displays
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenColumn = 16;
    final int maxScreenRow = 12;

    // real resulting screen width and height
    final int screenWidth = tileSize * maxScreenColumn;
    final int screenHeight = tileSize * maxScreenRow;


    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    int playerX = 100;
    int playerY = 100;

    int playerSpeed = 4;


    long paintCount = 0;
    private long nanoSecondStarted;
    @SuppressWarnings("FieldCanBeLocal")
    private final boolean debugging = false;


    public GamePanel() {
        // depends on tile size, game field size and scaling factor
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);

        // switch on keyHandler events
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        run_alternative_game_loop_1();
    }

    // @TODO seems inefficient
    @SuppressWarnings("unused")
    public void run_alternative_game_loop_2() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta > 0) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                timer = 0;
                drawCount = 0;
            }
        }
    }


    // @TODO why calculate in nano seconds when sleep is in milli seconds?
    // @TODO suppress warning busy wait
    @SuppressWarnings("BusyWait")
    public void run_alternative_game_loop_1() {
        // game loop

        double drawInterval = (double) 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            // System.out.println("the game loop is running");

            // nanoseconds
            // long currentTime = System.nanoTime();

            // microseconds
            // long currentTime2 = System.currentTimeMillis();
            // 1. update information
            update();
            // 2. draw
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // @TODO: implement my own game loop
    @SuppressWarnings("unused")
    public void run_alternative_game_loop_3() {

    }

    // added: move diagonally
    public void update() {

        boolean up = keyHandler.upPressed;
        boolean down = keyHandler.downPressed;
        boolean left = keyHandler.leftPressed;
        boolean right = keyHandler.rightPressed;

        if (left && right) {
            left = false;
            right = false;
        }

        if (up && down) {
            up = false;
            down = false;
        }

        if (up && right) {
            playerY -= (int) (playerSpeed*0.7);
            playerX += (int) (playerSpeed*0.7);
        } else if (up && left) {
            playerY -= (int) (playerSpeed*0.7);
            playerX -= (int) (playerSpeed*0.7);
        } else if (down && right) {
            playerY += (int) (playerSpeed*0.7);
            playerX += (int) (playerSpeed*0.7);
        } else if (down && left) {
            playerY += (int) (playerSpeed*0.7);
            playerX -= (int) (playerSpeed*0.7);
        } else if (up) {
            playerY -= playerSpeed;
        } else if (down) {
            playerY += playerSpeed;
        } else if (left) {
            playerX -= playerSpeed;
        } else if (right) {
            playerX += playerSpeed;
        }

    }


    @SuppressWarnings("SingleStatementInBlock")
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (paintCount == 0) {
            this.nanoSecondStarted = System.nanoTime();
        }

        paintCount++;


        if (this.debugging) {
            System.out.println("paintCount = " + paintCount + " - " + (System.nanoTime() - nanoSecondStarted) / paintCount);
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
