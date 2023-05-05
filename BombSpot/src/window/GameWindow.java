package window;

import javax.swing.*;

public class GameWindow extends JFrame {

    private JFrame gameWindow;

    public GameWindow(GamePanel gamePanel){
        gameWindow = new JFrame("Bomb Spot");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        gameWindow.add(gamePanel);

        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}