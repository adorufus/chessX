package okta.chessx.engine.board;

import okta.chessx.engine.board.Board.Builder;
import okta.chessx.engine.pieces.Pawn;
import okta.chessx.engine.pieces.Piece;
import okta.chessx.engine.pieces.Rook;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoord;

    public static final Move NULL_MOVE = new NullMove();

    private Move(Board board, Piece movedPiece, int destinationCoord) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoord = destinationCoord;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;

        result = prime * result + this.destinationCoord;
        result = prime * result + this.movedPiece.hashCode();

        return result;
    }

    @Override
    public boolean equals (final Object other) {
        if(this == other) {
            return true;
        }

        if(!(other instanceof Move)) {
            return false;
        }

        final Move otherMove = (Move) other;

        return getDestinationCoord() == otherMove.getDestinationCoord() && getMovedPiece().equals(otherMove.getMovedPiece());

    }

    public int getDestinationCoord() {
        return this.destinationCoord;
    }

    public Board execute() {
        final Builder builder = new Builder();

        for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
            if(!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }

        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }

        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

        return builder.build();
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece () {
        return null;
    }

    public static final class MajorMove extends Move {

        public MajorMove(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }

    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
            super(board, movedPiece, destinationCoord);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            if(!(other instanceof AttackMove)) {
                return false;
            }

            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }

    public static final class PawnMove extends Move {

        public PawnMove(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }

    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
            super(board, movedPiece, destinationCoord, attackedPiece);
        }
    }

    public static final class PawnEnPassantMove extends PawnAttackMove {

        public PawnEnPassantMove(Board board, Piece movedPiece, int destinationCoord, Piece attackedPiece) {
            super(board, movedPiece, destinationCoord, attackedPiece);
        }
    }

    public static final class PawnJump extends Move {

        public PawnJump(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();

            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }
    }

    static abstract class CastleMove extends Move {

        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMove(Board board, Piece movedPiece, int destinationCoord, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoord);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();

            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceAlliance()));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }
    }

    public static final class KingSideCastleMove extends CastleMove{
        public KingSideCastleMove(Board board, Piece movedPiece, int destinationCoord, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoord, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove{

        public QueenSideCastleMove(Board board, Piece movedPiece, int destinationCoord, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoord, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class NullMove extends Move {

        private NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute that move!");
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("Not Instantiable!");
        }

        public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
            for(final Move move : board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoord() == destinationCoordinate) {
                    return move;
                }
            }

            return NULL_MOVE;
        }
    }

    private int getCurrentCoordinate() {

        return this.getMovedPiece().getPiecePosition();
    }
}
