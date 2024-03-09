package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        // application stops when window is closed
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setResizable(false);
        window.setTitle("2D Adventure");

        // add a GamePanel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // size window according to sub components
        window.pack();

        // center window
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
