package main;

import utils.BufferedImages;
import utils.Constants;
import utils.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Struct;

public class Board {
    public int boardX, boardY;
    public int boardWidth, boardHeight;

    private int cellSize;
    private int mineCount;

    private int[][] grid;
    private int[][] saveGrid;

    public int flagCount;

    public Board(int boardX, int boardY, int boardWidth, int boardHeight, int cellSize, int mineCount) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.cellSize = cellSize;
        this.mineCount = mineCount;
        flagCount = mineCount;
        createGrid();
    }

    public void display(Graphics g){
        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid[y].length; x++){
                drawCell(g, x, y);
            }
        }
    }

    public void selectCell(int x, int y){
        if (x < boardX || x >= boardX + boardWidth || y < boardY || y >= boardY + boardHeight) return;
        int bx = (x-boardX) / cellSize;
        int by = (y-boardY) / cellSize;

        int value = grid[by][bx];
        if (value == Constants.HIDDEN_EMPTY){
            grid[by][bx] = Constants.FOUND_EMPTY + countMineNeighbours(bx, by);
            exploreEmptyCells(bx, by);
            Player.playSound("./res/Sounds/Select.wav", -10f, false);
        }
        else if (value == Constants.HIDDEN_MINE){
            Main.game.lives--;
            if (Main.game.lives <= 0) {
                Main.game.gameOver = true;
                revealMines();
            }
            else {
                grid[by][bx] = Constants.FLAGGED;
            }
            Player.playSound("./res/Sounds/Select.wav", -10f, false);
        }
    }

    public void revealMines(){
        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid[0].length; x++){
                if (grid[y][x] == Constants.HIDDEN_MINE){
                    grid[y][x] = Constants.FOUND_MINE;
                }
            }
        }
    }

    public void revealRandomMine(){
        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid[0].length; x++){
                if (grid[y][x] == Constants.HIDDEN_MINE){
                    grid[y][x] = Constants.FLAGGED;
                    flagCount--;
                    if (checkVictory()){
                        Main.game.gameOver = true;
                        Main.game.victory = true;
                    }
                    return;
                }
            }
        }
    }

    public void flagCell(int x, int y){
        if (x < boardX || x >= boardX + boardWidth || y < boardY || y >= boardY + boardHeight) return;
        int bx = (x-boardX) / cellSize;
        int by = (y-boardY) / cellSize;

        int value = grid[by][bx];
        if (value == Constants.HIDDEN_EMPTY || value == Constants.HIDDEN_MINE){
            grid[by][bx] = Constants.FLAGGED;
            flagCount--;
            Player.playSound("./res/Sounds/Select.wav", -10f, false);
        } else if (value == Constants.FLAGGED){
            grid[by][bx] = saveGrid[by][bx];
            flagCount++;
            Player.playSound("./res/Sounds/Select.wav", -10f, false);
        }
        if (checkVictory()){
            Main.game.gameOver = true;
            Main.game.victory = true;
        }
    }

    public boolean checkVictory(){
        if (flagCount != 0) return false;
        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid[0].length; x++){
                if (saveGrid[y][x] == Constants.HIDDEN_MINE && grid[y][x] != Constants.FLAGGED){
                    return false;
                }
            }
        }
        return true;
    }

    private void drawCell(Graphics g, int x, int y){
        int xCord = boardX + x * cellSize;
        int yCord = boardY + y * cellSize;
        int value = grid[y][x];

        BufferedImage img = null;
        if (value == Constants.HIDDEN_EMPTY || value == Constants.HIDDEN_MINE) img = BufferedImages.hiddenImg;
        else if (value >= Constants.FOUND_EMPTY) img = BufferedImages.foundImg;
        else if (value == Constants.FOUND_MINE) img = BufferedImages.mineImg;
        else if (value == Constants.FLAGGED) img = BufferedImages.flagImg;

        g.drawImage(img, xCord, yCord, cellSize, cellSize, null);

        if (value > Constants.FOUND_EMPTY){
            String text = String.valueOf(value-1);
            Constants.displayText(g, text, xCord + cellSize/2, yCord + cellSize*0.8f, cellSize/5*4, 1, true);
        }
        g.setColor(Color.black);
        g.drawRect(xCord, yCord, cellSize, cellSize);
    }

    private void exploreEmptyCells(int x, int y){
        int[] dx = { 1, 0, -1, 0 };
        int[] dy = { 0, 1, 0, -1 };

        for (int i = 0; i < 4; i++){
            if (isValidCell(x + dx[i], y + dy[i])){
                if (grid[y + dy[i]][x + dx[i]] == Constants.HIDDEN_EMPTY || (grid[y + dy[i]][x + dx[i]] == Constants.FLAGGED && saveGrid[y + dy[i]][x + dx[i]] == Constants.HIDDEN_EMPTY)){
                    if (grid[y + dy[i]][x + dx[i]] == Constants.FLAGGED) flagCount++;
                    int neighbours = Constants.FOUND_EMPTY + countMineNeighbours(x + dx[i], y  + dy[i]);

                    grid[y + dy[i]][x + dx[i]] = neighbours;
                    if (neighbours == Constants.FOUND_EMPTY){
                        exploreEmptyCells(x + dx[i], y  + dy[i]);
                    }
                }
            }
        }
    }

    private boolean isValidCell(int x, int y){
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }

    private int countMineNeighbours(int x, int y){
        int neighbours = 0;
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i != 0 || j != 0){
                    int cx = x + j;
                    int cy = y + i;
                    if (isValidCell(cx, cy)){
                        if (saveGrid[cy][cx] == Constants.HIDDEN_MINE){
                            neighbours++;
                        }
                    }
                }
            }
        }
        return neighbours;
    }

    private void createGrid(){
        int gw = boardWidth / cellSize;
        int gh = boardHeight / cellSize;
        grid = new int[gh][gw];
        saveGrid = new int[gh][gw];
        while (mineCount > 0){
            int mx = (int) (Math.random() * gw);
            int my = (int) (Math.random() * gh);
            if (grid[my][mx] == 0){
                grid[my][mx] = -1;
                saveGrid[my][mx] = -1;
                mineCount--;
            }
        }
    }
}
