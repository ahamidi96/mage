package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PheresBandBrawler extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public PheresBandBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Pheres-Band Brawler enters the battlefield, it fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()
                .setText("it fights up to one target creature you don't control"));
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private PheresBandBrawler(final PheresBandBrawler card) {
        super(card);
    }

    @Override
    public PheresBandBrawler copy() {
        return new PheresBandBrawler(this);
    }
}
