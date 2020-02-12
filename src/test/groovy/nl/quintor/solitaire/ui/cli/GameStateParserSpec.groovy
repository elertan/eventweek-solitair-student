package nl.quintor.solitaire.ui.cli

import nl.quintor.solitaire.TestUtil
import nl.quintor.solitaire.models.card.Card
import nl.quintor.solitaire.models.card.Rank
import nl.quintor.solitaire.models.card.Suit
import nl.quintor.solitaire.models.deck.Deck
import spock.lang.Specification
import spock.lang.Unroll


class GameStateParserSpec extends Specification {

    @Unroll
    def "PadNAdd should add padding with testcase : #testCase" () {
        given:
            def builder = new StringBuilder()
            def lineLength = 20
        expect:
            GameStateParser.padNAdd(builder, stringToPad, lineLength)
            builder.toString() == expectedResult
        where:
            stringToPad | expectedResult        || testCase
            ''          | '                    '|| "pad string of lenght of 0"
            'a'         | ' a                  '|| "pad string of lenght of 1"
            'abc'       | 'abc                 '|| "pad string of lenght greater then 1"
    }

    @Unroll
    def "cardStringOrNull should return #expectedResult when testing : #testCase" () {
        expect:
            def result  = GameStateParser.getCardStringOrNull(inputDeck, index)
            result == expectedResult
        where:
            inputDeck | index |  expectedResult || testCase
            new Deck()|0|null|| 'Should return null when input deck is empty'
            new Deck()|99|null|| 'Should return null when index is invalid'
            TestUtil.createTestDeckWithCards([new Card(Suit.HEARTS, Rank.KING), new Card(Suit.DIAMONDS, Rank.THREE)])|1|'♦ 3'|'Should return ♦ 3 whith valid index pointing to card'
    }

    @Unroll
    def "printRow should return #expectedResult when testing: #testCase" () {
        given:
            def builder = new StringBuilder()
        expect:
            def result = GameStateParser.printRow(builder, inputDecks, rowIndex)
            result == expectedResult
            builder.toString() == expectedBuilderContent
        where:
            inputDecks                                                              | rowIndex || expectedResult | expectedBuilderContent                                     | testCase
            TestUtil.createTestColumnsCollections(0)                                | 0        || false          | '                                                        ' | 'printing empty decks'
            TestUtil.createTestColumnsCollections(1)                                | 0        || true           | '♤ A     ♤ K     ♤ Q     ♤ J     ♤ 10    ♤ 9     ♤ 8   ' | 'printing decks containing only visible cards and one row'
            TestUtil.createTestColumnsCollections(2)                                | 1        || true           | '♤ 7     ♤ 6     ♤ 5     ♤ 4     ♤ 3     ♤ 2     ♥ A   ' | 'printing decks containing only visible cards and multiple rows'
            TestUtil.createTestColumnsCollections(2)                                | 9        || false          | '                                                        ' | 'printing decks containing only visible cards and multiple rows with non existing  index'
            TestUtil.setInvisiblecards(TestUtil.createTestColumnsCollections(1), 1) | 0        || true           | '? ?     ? ?     ? ?     ? ?     ? ?     ? ?     ? ?     ' | 'printing decks containing only invisible cards and one row'
    }

    def "parseGameState should return a string representation of the game state for a new game" () {
        given:
            def gameState = TestUtil.createFixedNewGame()
        when:
            def result = GameStateParser.parseGameState(gameState)
        then:
            result ==
"""0 move(s) played in 00:00:00 for 0 points

   O (2)                   SA      SB      SC      SD      
   ♧ 8                     ♧ A     _ _     _ _     _ _     

    A       B       C       D       E       F       G      
 0 ♥ K     ? ?     ? ?     ? ?     ? ?     ? ?     ? ?     
 1         ♧ Q     ? ?     ? ?     ? ?     ? ?     ? ?     
 2                 ♧ 2     ? ?     ? ?     ? ?     ? ?     
 3                         ♤ A     ? ?     ? ?     ? ?     
 4                                 ♧ 10    ? ?     ? ?     
 5                                         ♥ 9     ? ?     
 6                                                 ♧ J     
 7                                                         
"""
    }

}