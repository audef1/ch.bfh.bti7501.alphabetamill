package main.java.ch.bfh.bti7501.alphabetamill.models;

/**
 * Created by Florian on 14.06.2017.
 */
public class Move {

    private int fromPosition;
    private int toPosition;
    private int positionOfTakenPiece;
    private int hash = 0;

    public Move(int toPosition) {
        if (toPosition < 0 || toPosition >= Board.NUMBER_OF_POSITIONS) {
            throw new IllegalArgumentException();
        }

        this.fromPosition = -1;
        this.toPosition = toPosition;
        this.positionOfTakenPiece = -1;
    }

    public Move(int fromPosition, int toPosition) {
        if (toPosition < 0 || toPosition >= Board.NUMBER_OF_POSITIONS
                || fromPosition < -1 || fromPosition >= Board.NUMBER_OF_POSITIONS) {
            throw new IllegalArgumentException();
        }

        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.positionOfTakenPiece = -1;
    }

    public Move(int fromPosition, int toPosition, int positionOfTakenPiece) {
        if (toPosition < 0 || toPosition >= Board.NUMBER_OF_POSITIONS
                || fromPosition < -1 || fromPosition >= Board.NUMBER_OF_POSITIONS
                || positionOfTakenPiece < 0 || positionOfTakenPiece >= Board.NUMBER_OF_POSITIONS) {
            throw new IllegalArgumentException();
        }

        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.positionOfTakenPiece = positionOfTakenPiece;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            final int prime = 31;
            int result = 1;
            result = prime * result + fromPosition;
            result = prime * result + positionOfTakenPiece;
            result = prime * result + toPosition;
            hash = result;
        }

        return hash;
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
        Move other = (Move) obj;
        if (fromPosition != other.fromPosition) {
            return false;
        }
        if (positionOfTakenPiece != other.positionOfTakenPiece) {
            return false;
        }
        if (toPosition != other.toPosition) {
            return false;
        }
        return true;
    }



    public int getFromPosition() {
        return fromPosition;
    }

    public int getToPosition() {
        return toPosition;
    }

    public int getPositionOfTakenPiece() {
        return positionOfTakenPiece;
    }
}