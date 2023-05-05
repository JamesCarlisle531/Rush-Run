package main;

import utils.BufferedImages;
import utils.Constants;
import utils.Player;
import window.GamePanel;
import window.GameWindow;

import java.awt.*;
import java.util.ArrayList;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private int width;
    private int height;

    public Board board;

    public int lives;
    public boolean gameOver;
    public boolean victory;

    public Game(){
        width = Constants.WIDTH;
        height = Constants.HEIGHT;
        restart();
    }

    public void restart(){
        board = new Board(0, 100, width, height - 100, 60, 20);
        lives = 1;
        gameOver = false;
        victory = false;
    }

    public void initializeGame(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gameThread = new Thread(this);
        gameThread.start();
        Player.playSound("./res/Sounds/BGMusic.wav", -10f, true);
    }

    public void display(Graphics g){
        g.drawImage(BufferedImages.backgroundImg, 0, 0, width, height, null);

        board.display(g);

        drawUI(g);
    }

    public void drawUI(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(20, 20, 120, 60);
        g.setColor(Color.black);
        g.drawRect(20, 20, 120, 60);
        Constants.displayText(g, String.valueOf(board.flagCount), 80, 68, 48, 2, true);

        g.setColor(Color.gray);
        g.fillRect(width/2 - 140, 20, 280, 60);
        g.setColor(Color.black);
        g.drawRect(width/2 - 140, 20, 280, 60);
        Constants.displayText(g, "Bomb Spot", width/2, 67, 48, 2, true);

        if (gameOver == true){
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, width, height);
            Constants.displayText(g, victory ? "Victory" : "Game Over", width/2, height/2 + 20, 120, 4, true);
            Constants.displayText(g, "Press [R] to restart", width/2, height - 20, 24, 2, true);
        }
    }

    @Override
    public void run() {
        long previousTime = System.currentTimeMillis();
        while(true){
            long currentTime = System.currentTimeMillis();
            if(currentTime - previousTime >= Constants.FRAME_DURATION){
                previousTime = currentTime;

                Main.gamePanel.repaint();
            }
        }
    }
}
