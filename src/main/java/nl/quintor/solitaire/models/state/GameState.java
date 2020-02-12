package nl.quintor.solitaire.models.state;

import nl.quintor.solitaire.game.moves.RevertibleMove;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.deck.DeckType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that holds the complete state of the game, consisting of 1 stock, 7 columns and 4 stacks of {@link Deck}s, and
 * two booleans gameOver and gameLost which together indicate if the game is over.
 * <p>It can also hold a waste deck, a list of moves, the number of times the stock has been cycled, the base score, the bonus
 * time score and the start time of the game. These additional state variables can be used if additional features beyond
 * the base game are implemented.
 *
 * <p>Almost all methods are basic getters and setters, except for {@link #remember(RevertibleMove)},
 * {@link #forget(RevertibleMove)}, {@link #isGameOver()} and {@link #getScore()}.
 */
public final class GameState {
    private final Deck waste = new Deck(DeckType.WASTE);
    private final Deck stock = new Deck(DeckType.STOCK);
    private final Map<String, Deck> stackPiles = new LinkedHashMap<>(); // entries of header and deck
    private final Map<String, Deck> columns = new LinkedHashMap<>(); // entries of header and deck
    private final List<RevertibleMove> moves = new ArrayList<>();
    private int stockCycles = 0;
    private long baseScore = 0;
    private long timeScore = 0;
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime;
    private boolean gameLost = false;
    private boolean gameWon = false;

    public GameState() {
        Random r = new Random();

        var stackKeysBase = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        stackKeysBase.stream().map(x -> "S" + x).forEach(x -> {
            this.stackPiles.put(x, new Deck(DeckType.STACK));
        });

        var columnKeys = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));
        IntStream.range(0, columnKeys.size())
            .forEach(i -> {
                var columnKey = columnKeys.get(i);

                var deck = new Deck(DeckType.COLUMN);
                deck.addAll(IntStream.range(0, i + 1).mapToObj(x -> new Card(r.nextInt(54))).collect(Collectors.toCollection(Deck::new)));
                deck.setInvisibleCards(i);
                this.columns.put(columnKey, deck);
            });
    }

    /**
     * Getter for waste deck.
     *
     * @return waste deck
     */
    public Deck getWaste() {
        return waste;
    }

    /**
     * Getter for stock deck.
     *
     * @return stock deck
     */
    public Deck getStock() {
        return stock;
    }

    /**
     * Getter for stackPiles map of stack header and deck entries.
     *
     * @return stackPiles map
     */
    public Map<String, Deck> getStackPiles() {
        return stackPiles;
    }

    /**
     * Getter for columns map of column header and deck entries.
     *
     * @return columns map
     */
    public Map<String, Deck> getColumns() {
        return columns;
    }

    /**
     * Getter for moves list.
     *
     * @return moves list
     */
    public List<RevertibleMove> getMoves() {
        return moves;
    }

    /**
     * Getter for stockCycles.
     *
     * @return number of times the stock has been cycled
     */
    public int getStockCycles() {
        return stockCycles;
    }

    /**
     * Setter for stockCycles.
     *
     * @param stockCycles number of times the stock has been cycled
     */
    public void setStockCycles(int stockCycles) {
        this.stockCycles = stockCycles;
    }

    /**
     * Getter for baseScore.
     *
     * @return base score
     */
    public long getBaseScore() {
        return baseScore;
    }

    /**
     * Setter for baseScore.
     *
     * @param baseScore base score
     */
    public void setBaseScore(long baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * Getter for timeScore.
     *
     * @return time score
     */
    public long getTimeScore() {
        return timeScore;
    }

    /**
     * Setter for timeScore.
     *
     * @param timeScore time score
     */
    public void setTimeScore(long timeScore) {
        this.timeScore = timeScore;
    }

    /**
     * Getter for startTime.
     *
     * @return start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Setter for startTime.
     *
     * @param startTime start time
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for gameLost.
     *
     * @return true if the game has been lost
     */
    public boolean isGameLost() {
        return gameLost;
    }

    /**
     * Setter for gameLost.
     *
     * @param gameLost true if the game has been lost
     */
    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    /**
     * Getter for gameWon.
     *
     * @return true if the game has been won
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Setter for gameWon.
     *
     * @param gameWon true if the game has been won
     */
    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    /**
     * Combined value getter. Returns true if the game is either lost or won.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameLost || gameWon;
    }

    /**
     * Combined value getter. Returns the sum of baseScore and timeScore, or 0 if the sum is negative.
     *
     * @return positive total score or 0
     */
    public long getScore() {
        return baseScore + timeScore > 0 ? baseScore + timeScore : 0;
    }

    /**
     * Stores the provided {@link RevertibleMove} in the moves list.
     *
     * @param move move to be added to the moves list
     */
    public void remember(RevertibleMove move){
        moves.add(move);
    }

    /**
     * Removes the provided {@link RevertibleMove} from the moves list.
     *
     * @param move move to be removed from the moves list
     */
    public void forget(RevertibleMove move){
        moves.remove(move);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * String representation summarizing this GameState object, for example "12 move(s) played in 00:01:15 for 45 points".
     *
     * @return String representation of this GameState object
     */
    @Override
    public String toString(){
        LocalDateTime calculatedPlayingTime = endTime == null ? LocalDateTime.now():endTime;
        long duration = Duration.between(startTime, calculatedPlayingTime).getSeconds();
        return moves.size() + " move(s) played in " + String.format("%02d", duration / 3600) + ":" +
            String.format("%02d", duration / 60) +
            ":" + String.format("%02d", duration % 60) + " for " + getScore() + " points";
    }
}
