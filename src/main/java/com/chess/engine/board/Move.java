package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoord;

    private Move(Board board, Piece movedPiece, int destinationCoord) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoord = destinationCoord;
    }

    public static final class MajorMove extends Move {

        public MajorMove(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }
    }

    public static final class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
            super(board, movedPiece, destinationCoord);
            this.attackedPiece = attackedPiece;
        }
    }
}
