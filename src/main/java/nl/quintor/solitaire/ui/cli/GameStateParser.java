package nl.quintor.solitaire.ui.cli;

import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;

import java.util.Collection;

/**
 * {@link GameState} parser for terminal printing. The class is not instantiable, all constructors are private.
 */
class GameStateParser {
    private final static int COLUMN_WIDTH = 8; // 8 columns in 64 char width (80 char width is Windows default)
    private final static int FIRST_COLUMN_WIDTH = 3;

    protected GameStateParser(){}

    /**
     * Parses {@link GameState} to a String representation for terminal printing.
     *
     * <pre>{@code
     * Example:
     *
     * 0 moves played in 00:29 for 0 points
     *
     *     O                      SA      SB      SC      SD
     *    ♤ 9                     _ _     _ _     _ _     _ _
     *
     *     A       B       C       D       E       F       G
     *  0 ♦ 6     ? ?     ? ?     ? ?     ? ?     ? ?     ? ?
     *  1         ♤ 8     ? ?     ? ?     ? ?     ? ?     ? ?
     *  2                 ♦ 7     ? ?     ? ?     ? ?     ? ?
     *  3                         ♤ 6     ? ?     ? ?     ? ?
     *  4                                 ♤ K     ? ?     ? ?
     *  5                                         ♧ 2     ? ?
     *  6                                                 ♥ 6
     *  7
     *  }</pre>
     *
     *  @param gameState a representation of the current state of the game
     *  @return a visual representation of the gameState (for monospace terminal printing)
     */
    static String parseGameState(GameState gameState){
        // TODO: Write implementation
        return "";
    }

    /**
     * Add a String representation of the requested row of all provided columns to the provided StringBuilder. If the
     * requested row did not contain any cards, return false, else true.
     * This method uses the padAndAdd @see{{@link #padNAdd(StringBuilder, String, int)}}
     * Invisible cards should be printed as "? ?"
     *
     * @param builder contains the visualization of the game state
     * @param columns the columns of which the row is printed
     * @param row the row of the columns to be printed
     * @return did the row contain any cards
     */
    protected static boolean printRow(StringBuilder builder, Collection<Deck> columns, int row){
        // TODO: Write implementation
        return true;
    }

    /**
     * Attempts to get the specified card from the deck, and returns null if the requested index is out of bounds.
     *
     * @param deck deck to get the card from
     * @param index index of the card to get
     * @return the requested card or null
     */
    protected static String getCardStringOrNull(Deck deck, int index){
        // TODO: Write implementation
        return null;
    }

    /**
     * Add a space to the left of the string if it is of length 1, then add spaces to the right until it is of size
     * totalLength. Append the result to the StringBuilder.
     *
     * @param builder StringBuilder to append the result to
     * @param string String to pad and append
     * @param totalLength The total length that the String must become
     */
    protected static void padNAdd(StringBuilder builder, String string, int totalLength){
        builder.append(string);
        if (string.length() == 1){ builder.insert(0, " "); }
        int len = totalLength - builder.length();
        for (int i = 0; i < len; i++){ builder.append(" "); }
    }
}
