package okta.chessx.engine.pieces;

import okta.chessx.engine.Alliance;
import okta.chessx.engine.board.Board;
import okta.chessx.engine.board.Move;
import com.google.common.collect.ImmutableList;
import okta.chessx.engine.BoardUtils;

import java.util.*;

public class Pawn extends Piece {

    private final int[] CANDIDATE_MOVE_PIECE = {8, 16, 7, 9};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_PIECE) {
            int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove() && (BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 9);

                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 && !(
                    (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] &&
                                    this.pieceAlliance.isBlack()
                            )
                    )
            )) {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            } else if (
                    currentCandidateOffset == 9 && !(
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                    (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))
            )
            {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }


        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
