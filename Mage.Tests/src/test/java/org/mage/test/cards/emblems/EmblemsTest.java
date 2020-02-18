package org.mage.test.cards.emblems;

import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.RedGreenBeastToken;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class EmblemsTest extends CardTestPlayerBase {

    /**
     * Venser The Sojourner: -8: You get an emblem with "Whenever you cast a
     * spell, exile target permanent."
     */
    @Test
    public void testVenserTheSojournerEmblem() {
        addCard(Zone.BATTLEFIELD, playerA, "Venser, the Sojourner");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        addCounters(1, PhaseStep.UPKEEP, playerA, "Venser, the Sojourner", CounterType.LOYALTY, 5);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: You get an emblem");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 17);

        assertGraveyardCount(playerA, "Venser, the Sojourner", 1);
        assertEmblemCount(playerA, 1);
        // should be exiled using emblem ability
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Sorin, Lord of Innistrad: -2: You get an emblem with "Creatures you
     * control get +1/+0."
     */
    @Test
    public void testSorinLordOfInnistradEmblem() {
        addCard(Zone.BATTLEFIELD, playerA, "Sorin, Lord of Innistrad");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Elite Inquisitor");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        addCounters(1, PhaseStep.UPKEEP, playerA, "Sorin, Lord of Innistrad", CounterType.LOYALTY, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: You get an emblem");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: You get an emblem");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Inquisitor");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Sorin, Lord of Innistrad", 1);
        assertEmblemCount(playerA, 2);
        assertPowerToughness(playerA, "Elite Vanguard", 4, 1);
        assertPowerToughness(playerA, "Elite Inquisitor", 4, 2);
        assertPowerToughness(playerB, "Llanowar Elves", 1, 1);
    }

    /**
     * Tamiyo, the Moon Sage: -8: You get an emblem with "You have no maximum
     * hand size" and "Whenever a card is put into your graveyard from anywhere,
     * you may return it to your hand."
     *
     * Tests "You have no maximum hand size"
     */
    @Test
    public void testTamiyoTheMoonSageFirstEmblem() {
        addCard(Zone.BATTLEFIELD, playerA, "Tamiyo, the Moon Sage");
        addCard(Zone.HAND, playerA, "Mountain", 10);

        addCounters(1, PhaseStep.UPKEEP, playerA, "Tamiyo, the Moon Sage", CounterType.LOYALTY, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: You get an emblem");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertEmblemCount(playerA, 1);
        assertHandCount(playerA, 10);
    }

    /**
     * Tamiyo, the Moon Sage: -8: You get an emblem with "You have no maximum
     * hand size" and "Whenever a card is put into your graveyard from anywhere,
     * you may return it to your hand."
     *
     * Tests "Whenever a card is put into your graveyard from anywhere, you may
     * return it to your hand."
     */
    @Test
    public void testTamiyoTheMoonSageSecondEmblem() {
        addCard(Zone.BATTLEFIELD, playerA, "Tamiyo, the Moon Sage");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        addCounters(1, PhaseStep.UPKEEP, playerA, "Tamiyo, the Moon Sage", CounterType.LOYALTY, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: You get an emblem");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Elite Vanguard");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertEmblemCount(playerA, 1);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertHandCount(playerA, 1);

        boolean found = false;
        for (Card card : playerA.getHand().getCards(currentGame)) {
            if (card.getName().equals("Elite Vanguard")) {
                found = true;
            }
        }
        Assert.assertTrue("Couldn't find a card in hand: Elite Vanguard", found);
    }

    /**
     * Liliana, Defiant Necromancer: -8: You get an emblem with "Whenever a
     * creature dies, return it to the battlefield under your control at the
     * beginning of the next end step.";
     *
     * Tests "Whenever a creature dies, return it to the battlefield under your
     * control at the beginning of the next end step."
     */
    @Test
    public void testLilianaDefiantNecromancer() {
        addCard(Zone.BATTLEFIELD, playerA, "Liliana, Defiant Necromancer");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        addCounters(1, PhaseStep.UPKEEP, playerA, "Liliana, Defiant Necromancer", CounterType.LOYALTY, 6);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: You get an emblem");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertEmblemCount(playerA, 1);

        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertHandCount(playerA, 0);

    }

     /**
     * Chandra, Awakened Inferno: +2: Each opponent gets an emblem with
     * "At the beginning of your upkeep, this emblem deals 1 damage to you."
     *
     * Tests "At opponent upkeep, assert life has dropped"
     */
    @Test
    public void testChandraAwakenedInferno(){
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Awakened Inferno");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Each opponent gets an emblem");
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertLife(playerB, 19);  //check if the emblem ticked one damage on playerB
        assertEmblemCount(playerB, 1); //unique affect, actually gives the emblem to the opponent
    }

    /**
     * Dack Fayden: −6: You get an emblem with "Whenever you cast a spell that targets one or more permanents,
     * gain control of those permanents."
     *
     * Tests "Lightning Bolt the opponent's creature, without killing it, and check if it is now under your control"
     */
    @Test
    public void testDackFayden(){
        addCard(Zone.BATTLEFIELD, playerA, "Dack Fayden");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon", 1);

        addCounters(1, PhaseStep.UPKEEP, playerA, "Dack Fayden", CounterType.LOYALTY, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-6: You get an emblem");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Ancient Brontodon");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        //checks if playerA has the emblem
        assertEmblemCount(playerA, 1);
        //checks if Ancient Brontodon is now on playerA's field
        assertPermanentCount(playerA, "Ancient Brontodon", 1);
        //checks if Ancient Brontodon is no longer on playerB's field
        assertPermanentCount(playerB, "Ancient Brontodon", 0);
    }

    /**
     * Domri, Chaos Bringer: −8: You get an emblem with "At the beginning of each end step,
     * create a 4/4 red and green Beast creature token with trample."
     *
     * Tests "Check if token has been created"
     */
    @Test
    public void testDomriChaosBringer(){
        addCard(Zone.BATTLEFIELD, playerA, "Domri, Chaos Bringer");

        addCounters(1, PhaseStep.UPKEEP, playerA, "Domri, Chaos Bringer", CounterType.LOYALTY, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: You get an emblem");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        //checks if playerA has the emblem
        assertEmblemCount(playerA, 1);
        //checks if Beast Token is on the field
        assertPermanentCount(playerA, "Beast", 1);
        //check its power/toughness
        assertPowerToughness(playerA, "Beast", 4,4);
        //check its colors
        assertColor(playerA, "Beast", "RG", true);
    }
}
