package utils;

import main.Main;
import window.GameWindow;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Constants {
    //Windows
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;
    public static final int FPS = 60;
    public static final float FRAME_DURATION = 1000 / Constants.FPS;

    //Board
    public static final int HIDDEN_EMPTY = 0;
    public static final int FOUND_EMPTY = 1;
    public static final int HIDDEN_MINE = -1;
    public static final int FOUND_MINE = -2;
    public static final int FLAGGED = -3;

    public static void displayText(Graphics g, String text, float x, float y, int textSize, int shadow, boolean isCentered){
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textSize));

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int width = metrics.stringWidth(text);

        int nx = isCentered ? (int)x - width/2 : (int)x;

        g.setColor(Color.black);
        g.drawString(text, nx + shadow , (int)y + shadow);
        g.setColor(Color.white);
        g.drawString(text, nx, (int)y);
    }
}
