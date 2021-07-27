package vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DelayEffect extends AbstractGameEffect {
    AbstractGameEffect effect;

    public DelayEffect(AbstractGameEffect effect, float duration) {
        this.effect = effect;
        this.duration = duration;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            AbstractDungeon.topLevelEffects.add(effect);
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
