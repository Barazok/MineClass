package mineclass.mineclass;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ClassStatusEffectInstance extends StatusEffectInstance {
    public ClassStatusEffectInstance(StatusEffect statusEffect) {
        super(statusEffect, 99999, 0, false, false);
        setPermanent(true);
    }

    public static StatusEffectInstance of(StatusEffect effect) {
        return new ClassStatusEffectInstance(effect);
    }

    @Override
    public boolean update(LivingEntity entity, Runnable overwriteCallback) {
        if (getEffectType().canApplyUpdateEffect(getDuration(), getAmplifier())) {
            this.applyUpdateEffect(entity);
        }
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity) {
        getEffectType().applyUpdateEffect(entity, getAmplifier());
    }
}
