package okta.chessx.engine.player;

import okta.chessx.engine.Alliance;
import okta.chessx.engine.board.Board;
import okta.chessx.engine.board.BoardUtils;
import okta.chessx.engine.board.Move;
import okta.chessx.engine.board.Move.KingSideCastleMove;
import okta.chessx.engine.board.Move.QueenSideCastleMove;
import okta.chessx.engine.pieces.Piece;
import okta.chessx.engine.pieces.Rook;

import java.util.*;

import static okta.chessx.engine.pieces.Piece.PieceType.ROOK;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegals,
                       final Collection<Move> blackStandardLegals) {
        super(board, whiteStandardLegals, blackStandardLegals);
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {

        if(!hasCastleOpportunities()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isInCheck()) {
            //whites king side castle
            if(this.board.getPiece(61) == null && this.board.getPiece(62) == null) {
                final Piece kingSideRook = this.board.getPiece(63);
                if(kingSideRook != null && kingSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                            kingSideRook.getPieceType() == ROOK) {
                        if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 61));
                        }
                    }
                }
            }
            //whites queen side castle
            if(this.board.getPiece(59) == null && this.board.getPiece(58) == null &&
                    this.board.getPiece(57) == null) {
                final Piece queenSideRook = this.board.getPiece(56);
                if(queenSideRook != null && queenSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(59, opponentLegals).isEmpty() && queenSideRook.getPieceType() == ROOK) {
                        if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 59));
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public String toString() {
        return Alliance.WHITE.toString();
    }

}
