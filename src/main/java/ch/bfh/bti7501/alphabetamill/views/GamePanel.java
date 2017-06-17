package main.java.ch.bfh.bti7501.alphabetamill.views;

import main.java.ch.bfh.bti7501.alphabetamill.controllers.GameController;
import main.java.ch.bfh.bti7501.alphabetamill.helpers.MoveExecutor;
import main.java.ch.bfh.bti7501.alphabetamill.models.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Florian on 14.06.2017.
 */
public class GamePanel extends JPanel {

    private Board board;
    private GameController controller;
    private BufferedImage backgroundImage;

    public GamePanel(Board board) {
        this.board = board;
        this.setBackground(new Color(225,174,108));

        try {
            String pathToImageSortBy = getClass().getClassLoader().getResource("background.jpg").getFile();
            backgroundImage = ImageIO.read(new File(pathToImageSortBy));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void prepareBoard(MoveExecutor moveExecutor) {
        controller = new GameController(this.board, this, moveExecutor);
        controller.setPositionSelected(-1);
        controller.setMillFormed(false);
        controller.setMove(null);

        addMouseListener(controller);

        repaint();
    }

    public void makeMove() {
        controller.setDoMakeMove(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw background
        g.drawImage(backgroundImage, 0, 0, this);

        // draw board
        for (int i = 0; i < 24; i++) {
            for (int j : Board.POSITION_TO_NEIGHBOURS.get(i)) {
                Point start = controller.getPositionCoords(i);
                Point end = controller.getPositionCoords(j);

                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g2.draw(new Line2D.Float(start.x, start.y, end.x, end.y));
            }
        }

        // draw edges
        for (int i = 0; i < 24; i++) {
            Point coords = controller.getPositionCoords(i);
            g.fillOval(coords.x - 10, coords.y - 10, 20, 20);
        }

        // draw stones
        for (int i = 0; i < 24; i++) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            if (controller.getMove() != null && i == controller.getMove().getFromPosition()) {
                continue;
            }

            if (board.getPositionState(i) != 0 || (controller.getMove() != null && controller.getMove().getToPosition() == i)) {
                Point coords = controller.getPositionCoords(i);

                if (controller.getPositionSelected() == i) {
                    g.setColor(new Color(216,0,0));
                    g.fillOval(coords.x - 35, coords.y - 35, 70, 70);
                    g.setColor(Color.BLACK);
                    g.drawOval(coords.x - 35, coords.y - 35, 70, 70);
                    g.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(1));
                    g.drawOval(coords.x - 24, coords.y - 24, 48, 48);
                    g.drawOval(coords.x - 12, coords.y - 12, 24, 24);
                } else if (board.getPositionState(i) == 1 || (controller.getMove() != null && controller.getMove().getToPosition() == i && board.getCurrentPlayer() == 0)) {
                    g.setColor(Color.WHITE);
                    g.fillOval(coords.x - 30, coords.y - 30, 60, 60);
                    g.setColor(Color.BLACK);
                    g.drawOval(coords.x - 30, coords.y - 30, 60, 60);
                    g2.setStroke(new BasicStroke(1));
                    g.drawOval(coords.x - 20, coords.y - 20, 40, 40);
                    g.drawOval(coords.x - 10, coords.y - 10, 20, 20);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillOval(coords.x - 30, coords.y - 30, 60, 60);
                    g.drawOval(coords.x - 30, coords.y - 30, 60, 60);
                    g.setColor(Color.LIGHT_GRAY);
                    g2.setStroke(new BasicStroke(1));
                    g.drawOval(coords.x - 20, coords.y - 20, 40, 40);
                    g.drawOval(coords.x - 10, coords.y - 10, 20, 20);
                }
            }
        }
    }
}