package okta.chessx.engine.gui;

import javax.swing.*;
import java.awt.*;

public class Table {
    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

    private final JFrame gameFrame;

    public Table() {
        this.gameFrame = new JFrame("ChessX");
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }
}
