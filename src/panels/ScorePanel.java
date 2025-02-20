package panels;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.GAME_WIDTH;
import static utilz.LoadSave.loadImage;

public class ScorePanel extends JPanel {

    private GamePanel panel;
    private BufferedImage smiley;
    private JButton btn;
    private JLabel timeLabel;
    private JLabel minesLabel;


    public ScorePanel(GamePanel panel){
        setPreferredSize(new Dimension(GAME_WIDTH,60));
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(null);
        this.panel = panel;
        initScorePanel();
    }

    public void initScorePanel(){
        smiley = loadImage("smiley.png");

        btn = new JButton(new ImageIcon(smiley.getScaledInstance(40,40,1)));
        btn.setBorder(BorderFactory.createBevelBorder(
                1,
                new Color(0x525255),
                new Color(0xFFFFFF)
        ));
        add(btn).setBounds(GAME_WIDTH/2 - 20, this.getHeight()/2 + 10,45,45);
        btn.addActionListener(e -> {
            panel.getGameWindow().resetGame();
        });

        timeLabel = new JLabel("000");
        timeLabel.setFont(new Font("TimesNewRoman", Font.PLAIN,25));
        add(timeLabel).setBounds(GAME_WIDTH-100,getHeight()/4,100,60);

        minesLabel = new JLabel(String.format("%03d",panel.getMinesCount()));
        minesLabel.setFont(new Font("TimesNewRoman",Font.PLAIN,25));
        add(minesLabel).setBounds(100,getHeight()/4,100,60);
    }

    public void updateMinesLabel( int mines){
        minesLabel.setText(String.format("%03d",mines));
    }

    public void updateTimeLabel(int time){
        timeLabel.setText(String.format("%03d",time));
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }
}
