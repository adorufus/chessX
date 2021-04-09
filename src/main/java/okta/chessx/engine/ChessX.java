package okta.chessx.engine;

import okta.chessx.engine.board.Board;
import okta.chessx.engine.gui.Table;

import static okta.chessx.engine.Utils.Print;

public class ChessX {
    public static void main(String[] args) {
        Print("Starting chess engine\n");

        Print("Creating standard board......\n");
        Board board = Board.createStandardBoard();
        Print(board);
        Print("Creating Board Completed\n");
        Print("Creating GUI....");

        Table table = new Table();

        Print("Creating GUI Completed!");
    }
}
