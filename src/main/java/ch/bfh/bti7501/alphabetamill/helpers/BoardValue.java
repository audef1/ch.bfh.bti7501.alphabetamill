package main.java.ch.bfh.bti7501.alphabetamill.helpers;

import main.java.ch.bfh.bti7501.alphabetamill.models.Move;

/**
 * Created by Florian on 14.06.2017.
 */
public class BoardValue {

    private int value;
    private int remainingDepth;
    private Move bestMove;
    private boolean hasBeenCut;
    private boolean couldHaveBeenCutDeeper;

    public BoardValue(int value, int remainingDepth, Move bestMove, boolean hasBeenCut, boolean couldHaveBeenCutDeeper) {
        this.value = value;
        this.remainingDepth = remainingDepth;
        this.bestMove = bestMove;
        this.hasBeenCut = hasBeenCut;
        this.couldHaveBeenCutDeeper = couldHaveBeenCutDeeper;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (couldHaveBeenCutDeeper ? 1231 : 1237);
        result = prime * result + ((bestMove == null) ? 0 : bestMove.hashCode());
        result = prime * result + (hasBeenCut ? 1231 : 1237);
        result = prime * result + remainingDepth;
        result = prime * result + value;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BoardValue other = (BoardValue) obj;
        if (couldHaveBeenCutDeeper != other.couldHaveBeenCutDeeper) {
            return false;
        }
        if (bestMove == null) {
            if (other.bestMove != null) {
                return false;
            }
        } else if (!bestMove.equals(other.bestMove)) {
            return false;
        }
        if (hasBeenCut != other.hasBeenCut) {
            return false;
        }
        if (remainingDepth != other.remainingDepth) {
            return false;
        }
        if (value != other.value) {
            return false;
        }
        return true;
    }

    public int getValue() {
        return value;
    }

    public int getRemainingDepth() {
        return remainingDepth;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public boolean hasBeenCut() {
        return hasBeenCut;
    }

    public boolean couldHaveBeenCutDeeper() {
        return couldHaveBeenCutDeeper;
    }
}