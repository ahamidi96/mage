package org.mage.test.cards.abilities.keywords;

import mage.constants.Zone;
import org.mage.test.serverside.base.CardTestPlayerBase;
import org.junit.Test;

public class SimpleKeywordTest extends CardTestPlayerBase {

    @Test
    public void simpleKeyword(){
        addCard(Zone.BATTLEFIELD, playerA, "Zetalpa, Primal Dawn", 1);

        execute();

        assertKeywords(playerA, "Zetalpa, Primal Dawn", "flying");
        assertKeywords(playerA, "Zetalpa, Primal Dawn", "indestructible");
        assertKeywords(playerA, "Zetalpa, Primal Dawn", "double strike");
        assertKeywords(playerA, "Zetalpa, Primal Dawn", "vigilance");
        assertKeywords(playerA, "Zetalpa, Primal Dawn", "trample");

    }
}
