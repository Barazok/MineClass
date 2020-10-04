package mineclass.mineclass.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ClassStatusEffectInstance extends StatusEffectInstance {
    public ClassStatusEffectInstance(StatusEffect statusEffect, int amplifier) {
        super(statusEffect, 99999, amplifier, false, false);
    }

    public static StatusEffectInstance of(StatusEffect effect, int amplifier) {
        return new ClassStatusEffectInstance(effect, amplifier);
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
