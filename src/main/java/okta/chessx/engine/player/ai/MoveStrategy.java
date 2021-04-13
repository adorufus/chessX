package okta.chessx.engine.player.ai;

import okta.chessx.engine.board.Board;
import okta.chessx.engine.board.Move;

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    Move execute(Board board);

}
