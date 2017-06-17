package main.java.ch.bfh.bti7501.alphabetamill.helpers;

import main.java.ch.bfh.bti7501.alphabetamill.models.Move;

/**
 * Created by Florian on 15.06.2017.
 */
public class MoveValue implements Comparable<MoveValue> {

    private Move move;
    private int value;

    public MoveValue(Move move, int value) {
        this.move = move;
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((move == null) ? 0 : move.hashCode());
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
        MoveValue other = (MoveValue) obj;
        if (move == null) {
            if (other.move != null) {
                return false;
            }
        } else if (!move.equals(other.move)) {
            return false;
        }
        if (value != other.value) {
            return false;
        }
        return true;
    }

    public Move getMove() {
        return move;
    }

    @SuppressWarnings("unused")
    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(MoveValue o) {
        if (value > o.value) {
            return -1;
        } else if (value < o.value) {
            return 1;
        } else if (move.hashCode() < o.move.hashCode()) {
            return -1;
        } else if (move.hashCode() > o.move.hashCode()) {
            return 1;
        } else if (move.getFromPosition() - o.move.getFromPosition() != 0) {
            return move.getFromPosition() - o.move.getFromPosition();
        } else if (move.getToPosition() - o.move.getToPosition() != 0) {
            return move.getToPosition() - o.move.getToPosition();
        } else if (move.getPositionOfTakenPiece() - o.move.getPositionOfTakenPiece() != 0) {
            return move.getPositionOfTakenPiece() - o.move.getPositionOfTakenPiece();
        } else {
            return 0;
        }
    }
}