package main.java.ch.bfh.bti7501.alphabetamill.views;

import main.java.ch.bfh.bti7501.alphabetamill.models.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Florian on 17.06.2017.
 */
public class BottomPanel extends JPanel {

    private Board board;


    public BottomPanel(Board board){
        this.board = board;
        this.setPreferredSize(new Dimension(1000,100));
        this.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw opponents stones
        for (int i = 0; i < board.getUnputPiecesOfPlayer(1); i++) {
            g.setColor(Color.BLACK);
            g.fillOval(i*35 + 30, 15, 30, 30);
        }

        // draw players stones
        for (int i = 0; i < board.getUnputPiecesOfPlayer(0); i++) {
            g.setColor(Color.WHITE);
            g.fillOval(this.getWidth() - 30 - i*35 - 30, 55, 30, 30);
            g.setColor(Color.BLACK);
            g.drawOval(this.getWidth() - 30 - i*35 - 30, 55, 30, 30);
        }

        // draw opponents won stones
        for (int i = 0; i < 9 - board.getRemainingPiecesOfPlayer(0); i++) {
            g.setColor(Color.WHITE);
            g.fillOval(i*35 + 30, 55, 30, 30);
            g.setColor(Color.BLACK);
            g.drawOval(i*35 + 30, 55, 30, 30);
        }

        // draw players won stones
        for (int i = 0; i < 9 - board.getRemainingPiecesOfPlayer(1); i++) {
            g.setColor(Color.BLACK);
            g.fillOval(this.getWidth() - 30 - i*35 - 30, 15, 30, 30);
        }


    }

}
