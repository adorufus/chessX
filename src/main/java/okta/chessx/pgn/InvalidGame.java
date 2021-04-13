package okta.chessx.pgn;

import java.util.Collections;
import java.util.List;

public class InvalidGame extends Game{

    final String malformedGameText;

    public InvalidGame(PGNGameTags tags, String malformedGameText, String winner) {
        super(tags, Collections.emptyList(), winner);
        this.malformedGameText = malformedGameText;
    }

    @Override
    public String toString() {
        return "Invalid Game " + this.tags;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
