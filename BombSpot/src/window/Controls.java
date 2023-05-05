package window;

import main.Game;
import main.Main;
import utils.Constants;
import utils.Player;

import java.awt.*;
import java.awt.event.*;

public class Controls implements KeyListener, MouseListener {

    private Game game;
    public Controls(Game game){
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.gameOver == true){
            if (e.getKeyChar() == 'r') game.restart();
        }
        else {
            if (e.getKeyChar() == 'b'){
                for (int i = 0; i < 2; i++){
                    game.board.revealRandomMine();
                }
                Player.playSound("./res/Sounds/Select.wav", -10, false);
            }
            else if (e.getKeyChar() == 'l') {
                game.lives++;
                Player.playSound("./res/Sounds/Select.wav", -10, false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (game.gameOver == false){
            if (e.getButton() == MouseEvent.BUTTON1){
                Main.game.board.selectCell(e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON3){
                Main.game.board.flagCell(e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
