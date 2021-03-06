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

public class DwarfStatusEffect extends MineclassStatusEffectImpl {

    private final Set<Item> forbiddenItems = new HashSet<Item>() {{
        add(Items.DIAMOND_AXE);
        add(Items.GOLDEN_AXE);
        add(Items.IRON_AXE);
        add(Items.NETHERITE_AXE);
        add(Items.DIAMOND_HOE);
        add(Items.GOLDEN_HOE);
        add(Items.IRON_HOE);
        add(Items.NETHERITE_HOE);
        add(Items.BOW);
        add(Items.TRIDENT);
    }};

    private final Map<StatusEffect, Integer> classStatusEffects = Stream.of(new Object[][]{
            {StatusEffects.HEALTH_BOOST, 2},
            {StatusEffects.RESISTANCE, 1},
            {StatusEffects.HERO_OF_THE_VILLAGE, 1},
            {StatusEffects.HASTE, 1},
            {StatusEffects.NIGHT_VISION, 1},
    }).collect(Collectors.toMap(data -> (StatusEffect) data[0], data -> (Integer) data[1]));

    private final Map<Item, List<Pair<Enchantment, Integer>>> classEnchantments = Stream.of(
            new AbstractMap.SimpleEntry<>(Items.NETHERITE_PICKAXE, Arrays.asList(
                    new Pair<>(Enchantments.EFFICIENCY, 8),
                    new Pair<>(Enchantments.FORTUNE, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.DIAMOND_PICKAXE, Arrays.asList(
                    new Pair<>(Enchantments.EFFICIENCY, 8),
                    new Pair<>(Enchantments.FORTUNE, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.IRON_PICKAXE, Arrays.asList(
                    new Pair<>(Enchantments.EFFICIENCY, 8),
                    new Pair<>(Enchantments.FORTUNE, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.GOLDEN_PICKAXE, Arrays.asList(
                    new Pair<>(Enchantments.EFFICIENCY, 8),
                    new Pair<>(Enchantments.FORTUNE, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.STONE_PICKAXE, Arrays.asList(
                    new Pair<>(Enchantments.EFFICIENCY, 8),
                    new Pair<>(Enchantments.FORTUNE, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.WOODEN_PICKAXE, Arrays.asList(
                    new Pair<>(Enchantments.EFFICIENCY, 8),
                    new Pair<>(Enchantments.FORTUNE, 2)
            ))
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public DwarfStatusEffect() {
        super(StatusEffectType.BENEFICIAL, 0xD98282);
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
