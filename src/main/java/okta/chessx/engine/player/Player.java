package okta.chessx.engine.player;

import com.google.common.collect.ImmutableList;
import okta.chessx.engine.Alliance;
import okta.chessx.engine.board.Board;
import okta.chessx.engine.board.Move;
import okta.chessx.engine.pieces.King;
import okta.chessx.engine.pieces.Piece;

import java.util.*;
import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves){
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    public static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves) {
        final List<Move> attackMove = new ArrayList<>();

        for(final Move move : opponentMoves) {
            if(piecePosition == move.getDestinationCoord()) {
                attackMove.add(move);
            }
        }

        return ImmutableList.copyOf(attackMove);
    }

    private King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }

        throw new RuntimeException("Should Not Reach here! not a valid board");

    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !isHasEscapeMoves();
    }

    private boolean isHasEscapeMoves() {
        for(final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);

            if(transition.getMoveStatus().isDone()) {
                return true;
            }
        }

        return false;
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !isHasEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(this.board, move, MoveStatus.DONE);
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    private Piece getPlayerKing() {
        return this.playerKing;
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    public abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);

}
