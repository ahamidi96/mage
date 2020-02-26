package mage.cards.decks;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.game.GameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeckTest {

    private Card mockedCard;
    private CardInfo mockedCardInfo;
    private Deck testDeck;
    private DeckCardLists mockedCardList;


    @Spy
    private List<DeckCardInfo> mockedInfoList = new ArrayList<>();

    @Spy
    private Set<String> expansionSetCodes =  new LinkedHashSet<>();

    @Spy
    private  Set<Card> cards = new LinkedHashSet<>();

    /*
        Set up the class object to be mocked.
        In this case it's the Card class
     */

    @Before
    public void setup() throws GameException {
        mockedCard = mock(Card.class);
        mockedCardInfo = mock(CardInfo.class);
        mockedCardList = mock(DeckCardLists.class);

        //create a new deck
        testDeck = new Deck();


        mockedInfoList.add(new DeckCardInfo("Silver Knight", "8", "LEA"));
        mockedInfoList.add(new DeckCardInfo("Grizzly Bears", "268", "LEA"));

        //expansionSetCodes.add("ABC");
    }

    @Test
    public void testExpansionSetCodes() {
        when(mockedCardList.getCards()).thenReturn(mockedInfoList);
        when(mockedCard.getExpansionSetCode()).thenReturn("ABC");

        Assert.assertEquals(testDeck.getExpansionSetCodes(), expansionSetCodes);
    }

}
