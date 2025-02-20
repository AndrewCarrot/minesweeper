package inputs;

import panels.GamePanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static main.GameWindow.TILE_SIZE;

public class MouseInputs implements MouseListener{

    private final GamePanel panel;

    public MouseInputs(GamePanel panel){
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton) e.getSource();


        // left click means we uncover field i.e. remove button
        if(panel.getGameWindow().isRunning()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                panel.uncoverFields(button);
            }
            // if right mouse button is clicked the flag will be drawn or removed
            else if (e.getButton() == MouseEvent.BUTTON3) {
                if (button.getIcon() == null) {
                    button.setIcon(new ImageIcon(panel.getFlag().getScaledInstance(TILE_SIZE, TILE_SIZE, 1)));
                    panel.setMinesCount(panel.getMinesCount()-1);
                }
                else {
                    button.setIcon(null);
                    panel.setMinesCount(panel.getMinesCount()+1);
                }
            }
        }else if(!panel.getGameWindow().isGameOver()){
            panel.getGameWindow().startGame();
            if (e.getButton() == MouseEvent.BUTTON1) {
                panel.uncoverFields(button);
            }else if (e.getButton() == MouseEvent.BUTTON3) {
                if (button.getIcon() == null) {
                    button.setIcon(new ImageIcon(panel.getFlag().getScaledInstance(TILE_SIZE, TILE_SIZE, 1)));
                    panel.setMinesCount(panel.getMinesCount()-1);
                }
                else {
                    button.setIcon(null);
                    panel.setMinesCount(panel.getMinesCount()+1);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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