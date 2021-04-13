package okta.chessx.engine.player.ai;

import okta.chessx.engine.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
