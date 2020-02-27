package org.mage.test.cards;

import mage.MageInt;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Zone;
import mage.game.GameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
import org.mockito.Mockito;
import org.mockito.Spy;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeckTest extends CardTestPlayerBase {

    private Card mockedCard;

    /*
        Set up the class object to be mocked.
        In this case it's the CardImpl class
     */

    @Before
    public void setup() throws GameException {
        mockedCard = Mockito.mock(CardImpl.class);
    }

    @Test
    public void testMockedCard() {
        Mockito.when(mockedCard.getName()).thenReturn("Armin's Card");
        Mockito.when(mockedCard.getExpansionSetCode()).thenReturn("ABC");
        Mockito.when(mockedCard.isCreature()).thenReturn(true);
        Mockito.when(mockedCard.isInstant()).thenReturn(false);
        Mockito.when(mockedCard.isArtifact()).thenReturn(false);
        Mockito.when(mockedCard.getRules()).thenReturn(null);
        Mockito.when(mockedCard.getPower()).thenReturn(new MageInt(1000));
        Mockito.when(mockedCard.getToughness()).thenReturn(new MageInt(1000));

        Assert.assertEquals(mockedCard.getExpansionSetCode(), "ABC");
    }

}
