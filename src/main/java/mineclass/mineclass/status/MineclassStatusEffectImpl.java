package mineclass.mineclass.status;

import mineclass.mineclass.utils.ClassStatusEffectInstance;
import mineclass.mineclass.utils.Player;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class MineclassStatusEffectImpl extends StatusEffect implements MineclassStatusEffect {

    public MineclassStatusEffectImpl(StatusEffectType type, int color) {
        super(type, color);
    }

    public void dropAndRemoveForbiddenItems(PlayerEntity playerEntity) {
        Player player = new Player(playerEntity);
        getForbiddenItems().forEach(item -> {
            ItemStack itemStack = item.getDefaultStack();
            ItemStack existingItemStack;
            while ((existingItemStack = player.getItemStack(itemStack)) != null) {
                player.removeItemStack(existingItemStack);
                playerEntity.dropStack(existingItemStack);
            }
        });
    }

    public void removeEffects(ServerPlayerEntity entity) {
        getClassStatusEffects().forEach((statusEffect, integer) -> entity.removeStatusEffect(statusEffect));
    }

    public void applyEffects(ServerPlayerEntity entity) {
        getClassStatusEffects().forEach((statusEffect, integer) -> entity.addStatusEffect(ClassStatusEffectInstance.of(statusEffect, integer - 1)));
    }

    public void applyEnchantments(PlayerEntity playerEntity) {
        Player player = new Player(playerEntity);
        getClassEnchantments().forEach((item, pairs) -> {
            player.applyEnchantmentsOnAllStacks(item.getDefaultStack(), pairs);
        });
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // Nothing
    }
}
