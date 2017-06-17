package main.java.ch.bfh.bti7501.alphabetamill.helpers;

import main.java.ch.bfh.bti7501.alphabetamill.models.Board;
import main.java.ch.bfh.bti7501.alphabetamill.models.Move;

/**
 * Created by Florian on 14.06.2017.
 */
public class MoveEvaluator {

    private Board board;
    private Move move;

    public MoveEvaluator(){

    }

    public int evaluate(Board board, Move move) {
        if (board != null){
            this.board = board;
        }

        if (move != null){
            this.move = move;
        }

        if (board.doesPieceCompleteMill(move.getFromPosition(), move.getToPosition(), board.getCurrentPlayer())) {
            return 9;
        }

        if (board.doesPieceCompleteMill(move.getFromPosition(), move.getToPosition(), board.getOtherPlayer())) {
            for (int neighbour : board.POSITION_TO_NEIGHBOURS.get(move.getToPosition())) {
                if (board.getPositionState(neighbour) == board.getOtherPlayer() + 1) {
                    return 8;
                }
            }

            return 4;
        }

        if (board.doesPieceCompleteMill(-1, move.getFromPosition(), board.getOtherPlayer())) {
            for (int neighbour : board.POSITION_TO_NEIGHBOURS.get(move.getFromPosition())) {
                if (board.getPositionState(neighbour) == board.getOtherPlayer() + 1) {
                    return -2;
                }
            }

            return -1;
        }

        return 0;
    }
}