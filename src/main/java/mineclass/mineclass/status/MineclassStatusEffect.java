package mineclass.mineclass.status;

import mineclass.mineclass.utils.Pair;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MineclassStatusEffect {

    Set<Item> getForbiddenItems();

    Map<StatusEffect, Integer> getClassStatusEffects();

    Map<Item, List<Pair<Enchantment, Integer>>> getClassEnchantments();

    void dropAndRemoveForbiddenItems(PlayerEntity playerEntity);

    void removeEffects(ServerPlayerEntity entity);

    void applyEffects(ServerPlayerEntity entity);

    void applyEnchantments(PlayerEntity playerEntity);

    boolean canApplyUpdateEffect(int duration, int amplifier);

    void applyUpdateEffect(LivingEntity entity, int amplifier);
}
