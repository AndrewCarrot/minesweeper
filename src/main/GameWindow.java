package main;

import panels.GamePanel;
import panels.ScorePanel;

import javax.swing.*;
import java.awt.*;

public class GameWindow implements Runnable{
    private JFrame jframe;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;

    private boolean running = false;
    private boolean gameOver = false;

    public static final int TILE_SIZE = 25;
    public static final int TILES_IN_WIDTH = 30;
    public static final int TILES_IN_HEIGHT = 20;
    public static final int GAME_WIDTH = TILES_IN_WIDTH * TILE_SIZE;
    public static final int GAME_HEIGHT = TILES_IN_HEIGHT * TILE_SIZE;
    public static final Dimension GAME_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

    private int gameTime = 0;
    private Thread thread;

    public GameWindow(){

        gamePanel = new GamePanel(this);
        scorePanel = new ScorePanel(gamePanel);

        jframe = new JFrame();
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addPanels();

        thread = new Thread(this);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

    }

    public void addPanels(){
        jframe.getContentPane().add(gamePanel,BorderLayout.SOUTH);
        jframe.getContentPane().add(scorePanel,BorderLayout.NORTH);
    }

    public void resetGame(){
        gameOver = false;
        running = false;
        gamePanel.removeAll();
        gamePanel.initMinesField();
        gameTime = 0;
        scorePanel.updateMinesLabel(gamePanel.getMinesCount());
    }

    public void startGame(){
        running = true;
        gameOver = false;
        thread = new Thread(this);
        thread.start();
    }

    public void stopGame(){
        running = false;
        gameOver = true;
    }

    public boolean isRunning(){
        return running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void run(){
        long lastTimeCheck = System.currentTimeMillis();
        long currentTime;
        while(running){
            currentTime = System.currentTimeMillis();
            if(currentTime - lastTimeCheck > 1000){
                gameTime ++;
                lastTimeCheck = currentTime;
                scorePanel.updateTimeLabel(gameTime);
            }
        }
    }
}
