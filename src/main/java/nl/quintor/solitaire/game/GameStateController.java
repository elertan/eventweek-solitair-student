package nl.quintor.solitaire.game;

import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;

/**
 * Library class for GameState initiation and status checks that are called from {@link nl.quintor.solitaire.Main}.
 * The class is not instantiable, all constructors are private and all methods are static.
 */
public class GameStateController {
    private GameStateController(){}

    /**
     * Creates and initializes a new GameState object. The newly created GameState is populated with shuffled cards. The
     * stack pile and column maps are filled with headers and Deck objects. The column decks have an appropriate number
     * of invisible cards set.
     *
     * @return a new GameState object, ready to go
     */
    public static GameState init(){
        return new GameState();
    }

    /**
     * Applies a score penalty to the provided GameState object based on the amount of time passed.
     * The following formula is applied : "duration of game in seconds" / 10 * -2
     *
     * @param gameState GameState object that the score penalty is applied to
     */
    public static void applyTimePenalty(GameState gameState){
        var seconds = gameState.getEndTime().toEpochSecond(ZoneOffset.UTC) - gameState.getStartTime().toEpochSecond(ZoneOffset.UTC);

        var timePenalty = seconds / 10 * -2;
        gameState.setTimeScore(timePenalty);
    }

    /**
     * Applies a score bonus to the provided GameState object based on the amount of time passed. Assumes the game is won.
     * When the duration of the game is more than 30 seconds then apply : 700000 / "duration of game in seconds"
     *
     * @param gameState GameState object that the score penalty is applied to
     */
    public static void applyBonusScore(GameState gameState){
        // TODO: Write implementation
    }

    /**
     * Detects if the game has been won, and if so, sets the gameWon flag in the GameState object.
     * The game is considered won if there are no invisible cards left in the GameState object's columns and the stock
     * is empty.
     *
     * @param gameState GameState object of which it is determined if the game has been won
     */
    public static void detectGameWin(GameState gameState){
        // TODO: Write implementation
        boolean foundInvisibleCards = gameState.getColumns().values().stream().anyMatch(d -> (d.getInvisibleCards() > 0));

        if(!foundInvisibleCards && gameState.getStock().isEmpty()){
            gameState.setGameWon(true);
        }

    }
}
