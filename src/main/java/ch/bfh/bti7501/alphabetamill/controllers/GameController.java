package main.java.ch.bfh.bti7501.alphabetamill.controllers;

import main.java.ch.bfh.bti7501.alphabetamill.helpers.MoveExecutor;
import main.java.ch.bfh.bti7501.alphabetamill.models.Board;
import main.java.ch.bfh.bti7501.alphabetamill.models.Move;
import main.java.ch.bfh.bti7501.alphabetamill.views.GamePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Florian on 15.06.2017.
 */
public class GameController extends MouseAdapter {

    private Board board;
    private GamePanel panel;
    private MoveExecutor moveExecutor;
    private Move move;
    private boolean doMakeMove;
    private boolean millFormed;
    private int positionSelected;

    public GameController(Board board, GamePanel panel, MoveExecutor moveExecutor){
        this.board = board;
        this.panel = panel;
        this.doMakeMove = false;
        this.moveExecutor = moveExecutor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!doMakeMove || board.getCurrentPlayer() != 0 || board.hasCurrentPlayerLost()) {
            return;
        }

        int x = e.getX();
        int y = e.getY();

        for (int i = 0; i < 24; i++) {
            Point coords = getPositionCoords(i);

            if (coords.x - 20 <= x && x <= coords.x + 20
                    && coords.y - 20 <= y && y <= coords.y + 20) {
                if (millFormed) {
                    if (board.getPositionState(i) == board.getOtherPlayer() + 1) {
                        boolean areAllOtherPlayerPiecesFromMill = board.areAllPiecesFromMill(board.getOtherPlayer());

                        if (areAllOtherPlayerPiecesFromMill || !board.doesPieceCompleteMill(-1, i, board.getOtherPlayer())) {
                            move = new Move(move.getFromPosition(), move.getToPosition(), i);
                            if (board.isMoveValid(move)) {
                                moveExecutor.makeMove(move);
                                move = null;
                                millFormed = false;
                                doMakeMove = false;
                            }
                        }
                    }
                } else {
                    if (board.getPositionState(i) == 0) {
                        if (positionSelected == -1) {
                            move = new Move(i);
                        } else {
                            move = new Move(positionSelected, i);
                        }
                    } else if (board.getPositionState(i) == board.getCurrentPlayer() + 1) {
                        if (positionSelected == -1) {
                            positionSelected = i;
                        } else if (positionSelected == i) {
                            positionSelected = -1;
                        } else {
                            positionSelected = i;
                        }
                    }

                    if (move != null) {
                        if (board.isMoveValid(move)) {
                            positionSelected = -1;
                            if (board.doesPieceCompleteMill(move.getFromPosition(), move.getToPosition(), board.getCurrentPlayer())) {
                                millFormed = true;
                            } else {
                                moveExecutor.makeMove(move);
                                move = null;
                                doMakeMove = false;
                            }
                        } else {
                            move = null;
                        }
                    }
                }

                panel.repaint();

                break;
            }
        }
    }

    public Point getPositionCoords(int position) {
        Point result = new Point();

        int margin = 50;
        int width = panel.getSize().width - 2 * margin;
        int height = panel.getSize().height - 2 * margin;
        // used when dynamic window size
        //int metric = Math.min(width, height);
        int metric = 495;
        int positionSpace = metric / 6;

        int row = position / 3;
        if (row < 3) {
            result.x = row * positionSpace + (position % 3) * (metric - 2 * row * positionSpace) / 2;
            result.y = row * positionSpace;
        } else if (row == 3) {
            result.x = (position % 3) * positionSpace;
            result.y = row * positionSpace;
        } else {
            Point point = getPositionCoords(23 - position);
            point.x -= margin;
            point.y -= margin;
            result.x = metric - point.x;
            result.y = metric - point.y;
        }

        result.x += margin;
        result.y += margin;


        return result;
    }

    public void setDoMakeMove(boolean doMakeMove) {
        this.doMakeMove = doMakeMove;
    }

    public int getPositionSelected() {
        return positionSelected;
    }

    public void setPositionSelected(int positionSelected) {
        this.positionSelected = positionSelected;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public boolean isMillFormed() {
        return millFormed;
    }

    public void setMillFormed(boolean millFormed) {
        this.millFormed = millFormed;
    }

}