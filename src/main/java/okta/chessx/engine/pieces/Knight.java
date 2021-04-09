package okta.chessx.engine.pieces;

import okta.chessx.engine.Alliance;

import static okta.chessx.engine.BoardUtils.*;
import static okta.chessx.engine.board.Move.*;

import okta.chessx.engine.board.Board;
import okta.chessx.engine.board.Move;
import okta.chessx.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-7, -15, -18, -6, 6, 10, 15, 17};

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidatOffset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidatOffset;

            if (isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (
                        isFirstColumnExclusion(this.piecePosition, currentCandidatOffset) ||
                                isSecondColumnExclusion(this.piecePosition, currentCandidatOffset) ||
                                isSeventhColumnExclusion(this.piecePosition, currentCandidatOffset) ||
                                isEighthColumnExclusion(this.piecePosition, currentCandidatOffset)) {
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPos, final int candidateOffset) {
        return FIRST_COLUMN[currentPos] && (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPos, final int candidateOffset) {
        return SECOND_COLUMN[currentPos] && (candidateOffset == -10 || candidateOffset == 6);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    private static boolean isSeventhColumnExclusion(final int currentPos, final int candidateOffset) {
        return SEVENTH_COLUMN[currentPos] && (candidateOffset == -6 || candidateOffset == 10);
    }

    private static boolean isEighthColumnExclusion(final int currentPos, final int candidateOffset) {
        return EIGHTH_COLUMN[currentPos] && (candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17);
    }

}
