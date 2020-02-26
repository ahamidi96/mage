package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DoubleStrikeTest extends CardTestPlayerBase {


    @Test
    public void doubleStrikeAttacker_AttackerLives_DefenderDies_Combat1(){
        addCard(Zone.BATTLEFIELD, playerA, "Boros Swiftblade", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Alabaster Mage", 1);

        attack(1, playerA, "Boros Swiftblade");
        block(1, playerB, "Alabaster Mage", "Boros Swiftblade");

        setStopAt(1, PhaseStep.FIRST_COMBAT_DAMAGE);
        execute();

        assertGraveyardCount(playerB, "Alabaster Mage", 1);
        assertGraveyardCount(playerA, "Boros Swiftblade", 0);
    }

    @Test
    public void doubleStrikeAttacker_AttackerLives_DefenderDies_Combat2(){
        addCard(Zone.BATTLEFIELD, playerA, "Boros Swiftblade", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Devoted Druid", 1);

        attack(1, playerA, "Boros Swiftblade");
        block(1, playerB, "Devoted Druid", "Boros Swiftblade");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Devoted Druid", 1);
        assertGraveyardCount(playerA, "Boros Swiftblade", 0);
    }


}
