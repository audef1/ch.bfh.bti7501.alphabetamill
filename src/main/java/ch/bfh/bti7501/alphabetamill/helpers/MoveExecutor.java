package main.java.ch.bfh.bti7501.alphabetamill.helpers;

import main.java.ch.bfh.bti7501.alphabetamill.models.Board;
import main.java.ch.bfh.bti7501.alphabetamill.models.Move;
import main.java.ch.bfh.bti7501.alphabetamill.views.BoardGUI;
import main.java.ch.bfh.bti7501.alphabetamill.views.GamePanel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Florian on 14.06.2017.
 */
public class MoveExecutor {

    private boolean terminate = false;
    private AlphaBetaPruningHelper solver;
    private Board currentGame;
    private ArrayList<JPanel> panels;
    private BoardGUI gui;

    public MoveExecutor(AlphaBetaPruningHelper solver, Board currentGame, ArrayList<JPanel> panels, BoardGUI gui){
        this.solver = solver;
        this.currentGame = currentGame;
        this.panels = panels;
        this.gui = gui;
    }

    public synchronized void terminate() {
        this.terminate = true;
        solver.terminateSearch();
    }

    public synchronized void makeMove(Move move) {
        if (terminate) {
            return;
        }

        currentGame.makeMove(move);

        for (JPanel panel : panels){
            panel.repaint();
        }

        if (currentGame.hasCurrentPlayerLost()) {
            if (currentGame.getCurrentPlayer() == 1) {
                gui.setStatusLabel("Gewonnen!");
            } else {
                gui.setStatusLabel("Verloren!");
            }
        } else if (currentGame.getCurrentPlayer() == 1) {
            gui.setStatusLabel("Zug ausführen...");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Move move = solver.searchForBestMove();
                    MoveExecutor.this.makeMove(move);
                }
            }).start();
        } else {
            gui.setStatusLabel("Ihr Zug");
            for (JPanel panel : panels){
                if (panel instanceof GamePanel){
                    ((GamePanel) panel).makeMove();
                }
            }
        }
    }
}