package okta.chessx.engine.player;

import com.google.common.collect.ImmutableList;
import okta.chessx.engine.Alliance;
import okta.chessx.engine.board.Board;
import okta.chessx.engine.board.Move;
import okta.chessx.engine.board.Move.KingSideCastleMove;
import okta.chessx.engine.board.Move.QueenSideCastleMove;
import okta.chessx.engine.board.Tile;
import okta.chessx.engine.pieces.Piece;
import okta.chessx.engine.pieces.Rook;

import java.util.*;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board, final Collection<Move> whiteStandardMoves, final Collection<Move> blackStandardMoves) {
        super(board, whiteStandardMoves, blackStandardMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePiece();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            //white king side castle
            if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() && Player.calculateAttacksOnTile(62,
                            opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }

            if (!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(56);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                            (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                }
            }
        }


        return ImmutableList.copyOf(kingCastles);
    }
}
