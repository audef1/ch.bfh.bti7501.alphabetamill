package main.java.ch.bfh.bti7501.alphabetamill.views;

import main.java.ch.bfh.bti7501.alphabetamill.helpers.AlphaBetaPruningHelper;
import main.java.ch.bfh.bti7501.alphabetamill.helpers.MoveExecutor;
import main.java.ch.bfh.bti7501.alphabetamill.models.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Florian on 14.06.2017.
 */
public class BoardGUI extends JFrame {

    private Board currentGame;
    private GamePanel boardPanel;
    private BottomPanel bottomPanel;
    private ArrayList<JPanel> panels;
    private TopPanel topPanel;
    private AlphaBetaPruningHelper solver;
    private MoveExecutor moveExecutor;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;

    public BoardGUI() {
        super("Mühle");

        BorderLayout layout = new BorderLayout(0, 0);
        this.setLayout(layout);

        currentGame = new Board();

        topPanel = new TopPanel(currentGame);
        boardPanel = new GamePanel(currentGame);
        bottomPanel = new BottomPanel(currentGame);

        panels = new ArrayList<JPanel>();
        panels.add(topPanel);
        panels.add(boardPanel);
        panels.add(bottomPanel);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        menuBar = new JMenuBar();

        menu = new JMenu("Spiel");
        menuBar.add(menu);


        /*
         * didn't work 100%
         *
        menuItem = new JMenuItem("Neu starten");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                restartGame();
            }
        });
        menu.add(menuItem);
        */

        menuItem = new JMenuItem("Beenden");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        menu.add(menuItem);

        this.setJMenuBar(menuBar);

        startNewGame();
    }

    private void startNewGame() {
        if (moveExecutor != null) {
            moveExecutor.terminate();
        }

        setStatusLabel("Ihr Zug");

        int maxDepth = 10;
        int maxTime = 5000;

        solver = new AlphaBetaPruningHelper(currentGame, maxDepth, maxTime);

        moveExecutor = new MoveExecutor(solver, currentGame, panels, this);
        boardPanel.prepareBoard(moveExecutor);

        boardPanel.makeMove();
    }

    private void restartGame(){
        currentGame = new Board();
        solver = new AlphaBetaPruningHelper(currentGame, 10, 5000);
        moveExecutor = new MoveExecutor(solver, currentGame, panels, this);
        boardPanel.prepareBoard(moveExecutor);
    }

    public void setStatusLabel(String label) {
        topPanel.getStatus().setText(label);
    }

}
