package okta.chessx.pgn;

import okta.chessx.engine.board.Board;
import okta.chessx.engine.board.Move;
import okta.chessx.engine.player.Player;

public interface PGNPersistence {
    void persistGame(Game game);
    Move getNextBestMove(Board board, Player player, String gameText);
}
