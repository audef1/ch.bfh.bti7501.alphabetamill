package main.java.ch.bfh.bti7501.alphabetamill.views;

import main.java.ch.bfh.bti7501.alphabetamill.models.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Florian on 17.06.2017.
 */
public class TopPanel extends JPanel {

    private Board board;
    private JLabel status;
    private JLabel player;

    public TopPanel(Board board){
        this.board = board;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1000,100));
        this.setBackground(Color.LIGHT_GRAY);

        status =  new JLabel("Ihr Zug");
        status.setFont(new Font("Courier", Font.PLAIN, 25));
        status.setForeground(Color.GREEN);
        status.setBounds(270, 0, 600, 100);

        player = new JLabel("am Zug");
        player.setFont(new Font("Verdana", Font.PLAIN, 25));
        player.setBounds(100, 0, 600, 100);

        add(player);
        add(status);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(250,15,330,70);

        if (board.getCurrentPlayer() == 1){
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(25,25, 50, 50);
        g.setColor(Color.BLACK);
        g.drawRect(25,25, 50, 50);

    }

    public JLabel getStatus() {
        return status;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

}