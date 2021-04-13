package okta.chessx.example;

import okta.chessx.engine.board.Board;
import okta.chessx.example.Table;

import static okta.chessx.engine.utils.Utils.Print;

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
