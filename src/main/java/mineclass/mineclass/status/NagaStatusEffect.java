package mineclass.mineclass.status;

import mineclass.mineclass.utils.Pair;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NagaStatusEffect extends MineclassStatusEffectImpl implements MineclassStatusEffect {

    public Set<Item> forbiddenItems = new HashSet<Item>() {{
        add(Items.DIAMOND_SWORD);
        add(Items.GOLDEN_SWORD);
        add(Items.IRON_SWORD);
        add(Items.NETHERITE_SWORD);
        add(Items.DIAMOND_AXE);
        add(Items.GOLDEN_AXE);
        add(Items.IRON_AXE);
        add(Items.NETHERITE_AXE);
        add(Items.CROSSBOW);
        add(Items.BOW);
        add(Items.FLINT_AND_STEEL);
    }};

    public Map<StatusEffect, Integer> classStatusEffects = Stream.of(new Object[][]{
            {StatusEffects.DOLPHINS_GRACE, 1},
            {StatusEffects.CONDUIT_POWER, 1},
            {StatusEffects.WATER_BREATHING, 1},
            {StatusEffects.SLOWNESS, 2},
            {StatusEffects.WEAKNESS, 1},
    }).collect(Collectors.toMap(data -> (StatusEffect) data[0], data -> (Integer) data[1]));

    public Map<Item, List<Pair<Enchantment, Integer>>> classEnchantments = Stream.of(
            new AbstractMap.SimpleEntry<>(Items.TRIDENT, Arrays.asList(
                    new Pair<>(Enchantments.LOYALTY, 3),
                    new Pair<>(Enchantments.IMPALING, 5),
                    new Pair<>(Enchantments.CHANNELING, 1)
            )),
            new AbstractMap.SimpleEntry<>(Items.NETHERITE_HOE, Collections.singletonList(
                    new Pair<>(Enchantments.SHARPNESS, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.DIAMOND_HOE, Collections.singletonList(
                    new Pair<>(Enchantments.SHARPNESS, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.IRON_HOE, Collections.singletonList(
                    new Pair<>(Enchantments.SHARPNESS, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.WOODEN_HOE, Collections.singletonList(
                    new Pair<>(Enchantments.SHARPNESS, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.GOLDEN_HOE, Collections.singletonList(
                    new Pair<>(Enchantments.SHARPNESS, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.STONE_HOE, Collections.singletonList(
                    new Pair<>(Enchantments.SHARPNESS, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.FISHING_ROD, Arrays.asList(
                    new Pair<>(Enchantments.LUCK_OF_THE_SEA, 3),
                    new Pair<>(Enchantments.LURE, 3)
            ))
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public NagaStatusEffect() {
        super(StatusEffectType.BENEFICIAL, 0x98D982);
    }

    @Override
    public Set<Item> getForbiddenItems() {
        return forbiddenItems;
    }

    @Override
    public Map<StatusEffect, Integer> getClassStatusEffects() {
        return classStatusEffects;
    }

    @Override
    public Map<Item, List<Pair<Enchantment, Integer>>> getClassEnchantments() {
        return classEnchantments;
    }
}
