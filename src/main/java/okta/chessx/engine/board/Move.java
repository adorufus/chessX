package okta.chessx.engine.board;

import okta.chessx.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoord;

    private Move(Board board, Piece movedPiece, int destinationCoord) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoord = destinationCoord;
    }

    public int getDestinationCoord() {
        return this.destinationCoord;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move {

        public MajorMove(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
            super(board, movedPiece, destinationCoord);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
