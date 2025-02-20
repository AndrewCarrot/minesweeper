package data;

import javax.swing.*;
import java.awt.*;

import static main.GameWindow.TILE_SIZE;

public class Field {
    private final int x;
    private final int y;
    private JButton btn;
    private boolean hasMine;
    private int contiguousMines;

    public Field(int x, int y, boolean hasMine) {
        this.x = x;
        this.y = y;
        this.hasMine = hasMine;
        initButton();
    }

    private void initButton(){
        btn = new JButton();
        btn.setName("Present");
        btn.setBackground(new Color(198, 196, 196));
        btn.setBorder(BorderFactory.createBevelBorder(
                1,
                new Color(0x525255),
                new Color(0xFFFFFF)
        ));
    }

    public boolean isNextTo(int x, int y){
        if(this.x == x && this.y == y)
            return false;
        return this.x <= x+TILE_SIZE &&
                this.x >= x-TILE_SIZE &&
                this.y <= y+TILE_SIZE &&
                this.y >= y-TILE_SIZE;
    }

    public JButton getBtn(){
        return btn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasMine() {
        return hasMine;
    }

    public void setHasMine(boolean hasMine){
        this.hasMine = hasMine;
    }

    public int getContiguousMines() {
        return contiguousMines;
    }

    public void setContiguousMines(int x){
        this.contiguousMines = x;
    }

    public String toString(){
        return btn.getBounds() + "\n";
    }

    public boolean equals(Field field){
        return this.x == field.x && this.y == field.y;
    }
}
