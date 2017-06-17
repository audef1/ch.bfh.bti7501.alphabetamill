package main.java.ch.bfh.bti7501.alphabetamill.helpers;

import main.java.ch.bfh.bti7501.alphabetamill.models.Move;
import main.java.ch.bfh.bti7501.alphabetamill.models.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Florian on 14.06.2017.
 */
public class AlphaBetaPruningHelper {

    private static final int INFINITY = 1001;
    private static final int WIN_VALUE = 1000;
    private static final int END_SEARCH = 10000;

    private Board Board;
    private int maxDepth;
    private int maxTime;
    private long startTime;
    private Map<Long, BoardValue> transpositionTable;
    private Move currentBestMove;
    private int currentBestMoveValue;
    private MoveEvaluator moveEvaluator;
    private Board currentBoard;
    private boolean doTerminateMove;

    public AlphaBetaPruningHelper(Board Board, int maxDepth, int maxTime) {
        this.Board = Board;
        this.maxDepth = maxDepth;
        this.maxTime = maxTime;
        this.moveEvaluator = new MoveEvaluator();
        this.transpositionTable = new HashMap<Long, BoardValue>();
        this.doTerminateMove = false;

        this.currentBoard = null;
        this.startTime = 0;
        this.currentBestMove = null;
        currentBestMoveValue = -INFINITY;
    }

    private int getNumberOfAdjacentMoves(int player) {
        int result = 0;

        for (int i = 0; i < Board.NUMBER_OF_POSITIONS; i++) {
            if (currentBoard.getPositionState(i) == player + 1) {
                for (int j : Board.POSITION_TO_NEIGHBOURS.get(i)) {
                    if (currentBoard.getPositionState(j) == 0) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    private int getNumberOfMills(int player) {
        int result = 0;

        millCordsLoop:
        for (List<Integer> millCords : Board.POSSIBLE_MILLS) {
            for (int i : millCords) {
                if (currentBoard.getPositionState(i) != player + 1) {
                    continue millCordsLoop;
                }
            }

            result++;
        }

        return result;
    }

    private int evaluateCurrentBoard() {
        int result = 0;

        result += 10 * (currentBoard.getRemainingPiecesOfCurrentPlayer() - currentBoard.getRemainingPiecesOfOtherPlayer());
        result += 2 * (getNumberOfAdjacentMoves(currentBoard.getCurrentPlayer()) - getNumberOfAdjacentMoves(currentBoard.getOtherPlayer()));
        result += 8 * (getNumberOfMills(currentBoard.getCurrentPlayer()) - getNumberOfMills(currentBoard.getOtherPlayer()));

        return result;
    }

    private int alphaBetaPruningSearch(int alpha, int beta, int currentDepth, int remainingDepth) {
        if ((System.currentTimeMillis() - startTime > maxTime && currentBestMove != null) || doTerminateMove) {
            doTerminateMove = false;
            return END_SEARCH;
        }

        BoardValue boardComputedValue = transpositionTable.get(currentBoard.getBoardID());
        if (boardComputedValue != null && boardComputedValue.getRemainingDepth() >= remainingDepth) {

            if (boardComputedValue.hasBeenCut()) {
                alpha = Math.max(alpha, boardComputedValue.getValue());
            }

            if (boardComputedValue.couldHaveBeenCutDeeper()) {
                beta = Math.min(beta, boardComputedValue.getValue());
            }

            if (!(boardComputedValue.couldHaveBeenCutDeeper() || boardComputedValue.hasBeenCut())
                    || alpha >= beta) {
                if (currentDepth == 0) {
                    currentBestMove = boardComputedValue.getBestMove();
                    currentBestMoveValue = boardComputedValue.getValue();
                }

                return boardComputedValue.getValue();
            }
        }

        List<Move> validMoves = currentBoard.getValidMoves(moveEvaluator);
        if (currentBoard.getRemainingPiecesOfCurrentPlayer() < 3 || validMoves.isEmpty()) {
            return - WIN_VALUE;
        }

        if (remainingDepth == 0) {
            return evaluateCurrentBoard();
        } else {
            Move nodeBestMove = null;
            int nodeBestValue = -INFINITY;

            for (Move move : validMoves) {
                currentBoard.makeMove(move);

                int value = -alphaBetaPruningSearch(-beta, -alpha, currentDepth + 1, remainingDepth - 1);

                currentBoard.undoMove(move);

                if (Math.abs(value) == END_SEARCH) {
                    return END_SEARCH;
                }

                if (value > nodeBestValue) {
                    nodeBestValue = value;
                    nodeBestMove = move;
                }

                if (value > alpha) {
                    alpha = value;

                    if (currentDepth == 0) {
                        currentBestMove = move;
                        currentBestMoveValue = alpha;
                    }
                }

                if (alpha >= beta) {
                    break;
                }
            }

            transpositionTable.put(currentBoard.getBoardID(), new BoardValue(nodeBestValue, remainingDepth, nodeBestMove, alpha >= beta, nodeBestValue < alpha));

            return nodeBestValue;
        }
    }

    public Move searchForBestMove() {
        currentBestMove = null;
        startTime = System.currentTimeMillis();
        currentBoard = new Board(Board);
        currentBestMoveValue = -INFINITY;
        Move prevBestMove = currentBestMove;
        int prevBestMoveValue = currentBestMoveValue;

        for (int depth = Math.min(2, maxDepth); depth <= maxDepth; depth += 2) {
            int value = alphaBetaPruningSearch(-INFINITY, INFINITY, 0, depth);

            if (Math.abs(value) == END_SEARCH) {
                if (currentBestMoveValue <= prevBestMoveValue) {
                    currentBestMove = prevBestMove;
                    currentBestMoveValue = prevBestMoveValue;
                }

                break;
            }

            prevBestMove = currentBestMove;
            prevBestMoveValue = value;
        }

        return currentBestMove;
    }

    public synchronized void terminateSearch() {
        doTerminateMove = true;
    }

    public void setBoard(Board Board) {
        this.currentBoard = Board;
    }

    public Board getBoard() {
        return currentBoard;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxTime() {
        return maxTime;
    }
}