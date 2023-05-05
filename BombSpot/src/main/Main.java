package main;

import utils.BufferedImages;
import window.GamePanel;
import window.GameWindow;

public class Main {

    public static Game game;
    public static GamePanel gamePanel;
    private static GameWindow gameWindow;

    public static void main(String[] args) {
        BufferedImages.loadImages();

        game = new Game();
        gamePanel = new GamePanel(game);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        game.initializeGame(gamePanel);
    }
}
