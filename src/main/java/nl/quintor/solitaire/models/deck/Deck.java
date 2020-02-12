package nl.quintor.solitaire.models.deck;

import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.card.Rank;
import nl.quintor.solitaire.models.card.Suit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Model of a deck of cards. The class extends {@link ArrayList}, so it provides all of the standard methods of
 * ArrayList.
 * <p>
 * It is recommended that an appropriate Deck object is instantiated for a game using an appropriate factory
 * method (create[..]Deck()). This deck can then be shuffled and dealt out to other (empty) decks representing, for
 * example, the hand of a player.
 */
public final class Deck extends ArrayList<Card> {
    private int invisibleCards = 0;
    private DeckType deckType;

    /**
     * Constructs an empty Deck with an initial capacity of ten.
     */
    public Deck() {
        super();
    }

    /**
     * Constructs a Deck containing the cards of the specified Card collection, in the order they are returned by the
     * Card collection's iterator.
     *
     * @param cardCollection initial cards.
     */
    public Deck(Collection<Card> cardCollection) {
        super(cardCollection);
    }

    /**
     * Constructs an empty Deck with the specified initial capacity.
     *
     * @param initialCapacity initial internal array capacity.
     */
    public Deck(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty Deck of the specified DeckType.
     *
     * @param deckType type of this Deck.
     */
    public Deck(DeckType deckType){
        this.deckType = deckType;
        if (deckType == DeckType.WASTE) {
            // Shit code
            var cards = IntStream.range(0, 23).mapToObj(Card::new).collect(Collectors.toCollection(Deck::new));
            this.addAll(cards);
        } else if (deckType == DeckType.STOCK) {
            this.add(new Card(Suit.JOKER, Rank.JOKER_BLACK));
        }
    }

    /**
     * Factory method for a standard deck of cards without Jokers.
     *
     * @return a standard deck of cards without Jokers.
     */
    public static Deck createDefaultDeck(){
        return IntStream.range(0, 52).mapToObj(Card::new).collect(Collectors.toCollection(Deck::new));
    }

    /**
     * Factory method for a standard deck of cards with Jokers.
     *
     * @return a standard deck of cards with Jokers.
     */
    public static Deck createDefaultDeckWithJokers(){
        return IntStream.range(0, 54).mapToObj(Card::new).collect(Collectors.toCollection(Deck::new));
    }

    /**
     * Factory method for a piquet deck of cards (a 32-card deck without Jokers or 2-6 cards).
     *
     * @return a piquet deck of cards.
     */
    public static Deck createPiquetDeck(){
        return IntStream.range(0, 52)
            .filter(i -> i % 13 > 4)
            .mapToObj(Card::new)
            .collect(Collectors.toCollection(Deck::new));
    }

    /**
     * Getter for deckType.
     *
     * @return type of this deck
     */
    public DeckType getDeckType() {
        return deckType;
    }

    /**
     * Setter for deckType.
     *
     * @param deckType type of this deck
     */
    public void setDeckType(DeckType deckType) {
        this.deckType = deckType;
    }

    /**
     * Getter for invisibleCards.
     *
     * @return number of invisible cards in this deck
     */
    public int getInvisibleCards() {
        return invisibleCards;
    }

    /**
     * Setter for invisibleCards.
     *
     * @param invisibleCards number of invisible cards in this deck
     */
    public void setInvisibleCards(int invisibleCards) {
        this.invisibleCards = invisibleCards;
    }

    /**
     * Returns a {@link String} of the cards in this Deck, ordered by the argument
     * {@link Comparator}{@literal &lt;}{@link Card}{@literal &gt;} in ascending order, with comparison symbols (&lt; and =)
     * between the cards indicating how two cards compare. For example ♧ 2 &lt; ♧ 3 = ♧ 4. The order of the cards in this
     * Deck is NOT changed.
     *
     * @param sorter order in which the cards have to be sorted
     * @return representation of the {@link Card}s in this Deck, with comparison indicators
     */
    public String toComparativeString(Comparator<Card> sorter){
        List<Card> sortedList = new Deck(this);
        sortedList.sort(sorter);
        return sortedList.size() == 0 ? "[]" : "[" + sortedList.get(0).toShortString() +
            IntStream.range(1, sortedList.size())
                .mapToObj(i -> (sorter.compare(sortedList.get(i-1), sortedList.get(i)) < 0 ? " < " : " = ") +
                                sortedList.get(i).toShortString())
                .collect(Collectors.joining()) + "]";
    }

    /**
     * Returns a {@link String} representation of this Deck.
     *
     * @return representation of the {@link Card}s in this Deck
     */
    @Override
    public String toString() {
        return "[" + this.stream().map(Card::toShortString).collect(Collectors.joining(", ")) + "]";
    }
}
