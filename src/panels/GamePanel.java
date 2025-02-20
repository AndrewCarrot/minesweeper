package panels;

import data.Field;
import inputs.MouseInputs;
import main.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.*;
import static utilz.LoadSave.loadImage;


public class GamePanel extends JPanel {

    private final Field[] fields;
    private final BufferedImage mine;
    private final BufferedImage flag;
    private int minesCount;

    private GameWindow gameWindow;


    public GamePanel(GameWindow gameWindow){
        setPreferredSize(GAME_SIZE);
        setLayout(null);

        mine = loadImage("mine.png");
        flag = loadImage("flag.png");

        fields = new Field[TILES_IN_WIDTH*TILES_IN_HEIGHT];
        initMinesField();

        this.gameWindow = gameWindow;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGrid(g);
        drawMinesInfo(g);
    }



    public void initMinesField(){
        minesCount = 0;

        for(int j = 0; j<TILES_IN_HEIGHT;j++)
            for(int i=0; i<TILES_IN_WIDTH;i++){
                int index = j*TILES_IN_WIDTH + i;

                int random =(int) (Math.random()*6);
                if(random == 1)
                    minesCount++;

                Field f = fields[index] = new Field(
                        i*TILE_SIZE,
                        j*TILE_SIZE,
                        random == 1
                );

                f.getBtn().addMouseListener(new MouseInputs(this));

                // position and size of a button
                this.add(f.getBtn()).setBounds(
                        f.getX(),
                        f.getY(),
                        TILE_SIZE,
                        TILE_SIZE
                );

            }

        // populate contiguousMines field
        for(Field field : fields){
            int minesCount = 0;
            if(field.hasMine())
                continue;
            for(Field field1 : fields){
                if(field1.hasMine() && field1.isNextTo(field.getX(),field.getY())){
                    minesCount++;
                }
            }
            field.setContiguousMines(minesCount);
        }
    }

    // check if button has flag set, if no remove the button
    // TODO: uncover contiguous fields
    public void uncoverFields(JButton button){

        // if button is not marked with flag we delete the button and check if field has mine
        if(button.getIcon() == null) {

            int x = button.getX();
            int y = button.getY();
            Field field = null;

            // get clicked field
            for(Field f: fields){
                if (f.getY() == y && f.getX() == x){
                    field = f;
                }
            }

            // if there is a mine we uncover all the mines
            assert field != null;
            if(field.hasMine()){
                gameWindow.stopGame();
                for(Field f:fields){
                    if(f.hasMine()) {
                        JButton b = f.getBtn();
                        b.setName("Removed");
                        remove(b);
                    }
                }
            }else{ // we uncover the field and all adjacent empty fields
                button.setName("Removed");
                remove(button);
                if(field.getContiguousMines() == 0)
                    uncoverAdjacentFields(field);
            }

            repaint();
        }

    }

    public void uncoverAdjacentFields(Field field){
        for(Field f:fields){
            if(f.isNextTo(field.getX(), field.getY()) && !f.hasMine() && !f.getBtn().getName().equals("Removed")){
                JButton b = f.getBtn();
                b.setName("Removed");
                remove(b);
                if(f.getContiguousMines() == 0) {
                    uncoverAdjacentFields(f);
                }
            }
        }
    }



    /**
     * Iterates through Field array

     * if field hasMine() draws mine graphic
     * if field getContiguousMines() > 0 draws number on a tile
     */
    public void drawMinesInfo(Graphics g){
        for(Field f: fields){
            // if Button is present we don't want to draw underneath
            if(f.getBtn().getName().equals("Present"))
                continue;
            if(f.hasMine())
                g.drawImage(mine,f.getX(),f.getY(),TILE_SIZE,TILE_SIZE,null);
            else if(f.getContiguousMines() > 0){
                g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(TILE_SIZE*0.9f)));

                switch(f.getContiguousMines()){
                    case 1 -> g.setColor(new Color(0x0606E3));
                    case 2 -> g.setColor(new Color(0x38A307));
                    case 3 -> g.setColor(new Color(0xE31212));
                    case 4 -> g.setColor(new Color(0x180355));
                    case 5 -> g.setColor(new Color(0x590101));
                    case 6 -> g.setColor(new Color(0x14E1AE));
                    case 7 -> g.setColor(new Color(0x9C5504));
                    case 8 -> g.setColor(new Color(4));
                }

                g.drawString(String.valueOf(f.getContiguousMines()),f.getX()+TILE_SIZE/4,f.getY()+TILE_SIZE-TILE_SIZE/4);
            }
        }
    }

    public void drawGrid(Graphics g){
        // horizontal grid lines
        for(int i=1;i<TILES_IN_HEIGHT;i++) {
            g.setColor(Color.GRAY);
            g.drawLine(0,i* TILE_SIZE, GAME_WIDTH,i*TILE_SIZE);
        }

        // vertical grid lines
        for(int i=0;i<TILES_IN_WIDTH;i++){
            g.setColor(Color.GRAY);
            g.drawLine(i*TILE_SIZE,0,i*TILE_SIZE,GAME_HEIGHT);
        }
    }


    public BufferedImage getFlag() {
        return flag;
    }

    public BufferedImage getMine() {
        return mine;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public void setMinesCount(int minesCount) {
        this.minesCount = minesCount;
    }
}
