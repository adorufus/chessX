package okta.chessx.engine.board;

import okta.chessx.engine.Alliance;
import com.google.common.collect.ImmutableList;
import okta.chessx.engine.pieces.*;

import java.util.*;

import static okta.chessx.engine.BoardUtils.NUM_TILES;
import static okta.chessx.engine.BoardUtils.NUM_TILES_PER_ROW;

public class Board {

    private final List<Tile> gameBoard;

    private final Collection<Piece> whitePiece;
    private final Collection<Piece> blackPiece;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;

    public Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePiece = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPiece = calculateActivePieces(this.gameBoard, Alliance.BLACK);

        final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePiece);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPiece);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < NUM_TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % NUM_TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {
        final List<Piece> activePiece = new ArrayList<>();

        for(final Tile tile : gameBoard) {
            if(tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if(piece.getPieceAlliance() == alliance) {
                    activePiece.add(piece);
                }
            }
         }

        return ImmutableList.copyOf(activePiece);
    }

    private List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[NUM_TILES];

        for (int i = 0; i < NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }

        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();

        //black
        builder.setPiece(new Rook(0, Alliance.BLACK));
        builder.setPiece(new Knight(1, Alliance.BLACK));
        builder.setPiece(new Bishop(2, Alliance.BLACK));
        builder.setPiece(new Queen(3, Alliance.BLACK));
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece(new Bishop(5, Alliance.BLACK));
        builder.setPiece(new Knight(6, Alliance.BLACK));
        builder.setPiece(new Rook(7, Alliance.BLACK));
        builder.setPiece(new Pawn(8, Alliance.BLACK));
        builder.setPiece(new Pawn(9, Alliance.BLACK));
        builder.setPiece(new Pawn(10, Alliance.BLACK));
        builder.setPiece(new Pawn(11, Alliance.BLACK));
        builder.setPiece(new Pawn(12, Alliance.BLACK));
        builder.setPiece(new Pawn(13, Alliance.BLACK));
        builder.setPiece(new Pawn(14, Alliance.BLACK));
        builder.setPiece(new Pawn(15, Alliance.BLACK));

        //white
        builder.setPiece(new Pawn(48, Alliance.WHITE));
        builder.setPiece(new Pawn(49, Alliance.WHITE));
        builder.setPiece(new Pawn(50, Alliance.WHITE));
        builder.setPiece(new Pawn(51, Alliance.WHITE));
        builder.setPiece(new Pawn(52, Alliance.WHITE));
        builder.setPiece(new Pawn(53, Alliance.WHITE));
        builder.setPiece(new Pawn(54, Alliance.WHITE));
        builder.setPiece(new Pawn(55, Alliance.WHITE));
        builder.setPiece(new Rook(56, Alliance.WHITE));
        builder.setPiece(new Knight(57, Alliance.WHITE));
        builder.setPiece(new Bishop(58, Alliance.WHITE));
        builder.setPiece(new Queen(59, Alliance.WHITE));
        builder.setPiece(new King(60, Alliance.WHITE));
        builder.setPiece(new Bishop(61, Alliance.WHITE));
        builder.setPiece(new Knight(62, Alliance.WHITE));
        builder.setPiece(new Rook(63, Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);


        return builder.build();
    }

    public Tile getTile(int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance alliance) {
            this.nextMoveMaker = nextMoveMaker;

            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }
}
