package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import powers.TriumphPower;
import util.TextureLoader;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RelicCrucible extends CustomRelic implements OnRemoveCardFromMasterDeckRelic {
    public static final String ID = "reliquary:Crucible";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/crucible.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/crucible.png");

    public RelicCrucible() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard removed) {
        if (!removed.upgraded) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        List<AbstractCard> upgradeableCards = p.masterDeck.group.stream().filter(c -> c.canUpgrade()).collect(Collectors.toList());
        if (upgradeableCards.isEmpty()) {
            return;
        }
        flash();
        if (upgradeableCards.size() > 1) {
            Collections.shuffle(upgradeableCards);
        }
        for (int i = 0; i < 2 && i < upgradeableCards.size(); i++) {
            AbstractCard card = upgradeableCards.get(i);
            card.upgrade();
            p.bottledCardUpgradeCheck(upgradeableCards.get(0));
            float xOffset = upgradeableCards.size() == 1 ? 0 :  -AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale;
            if (i == 1) {
                xOffset *= -1;
            }
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + xOffset, Settings.HEIGHT / 2.0F));
        }
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicCrucible();
    }
}
