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

public class FireDwarfStatusEffect extends MineclassStatusEffectImpl {

    private final Set<Item> forbiddenItems = new HashSet<Item>() {{
        add(Items.DIAMOND_SWORD);
        add(Items.GOLDEN_SWORD);
        add(Items.IRON_SWORD);
        add(Items.NETHERITE_SWORD);
        add(Items.DIAMOND_HOE);
        add(Items.GOLDEN_HOE);
        add(Items.IRON_HOE);
        add(Items.NETHERITE_HOE);
        add(Items.DIAMOND_SHOVEL);
        add(Items.GOLDEN_SHOVEL);
        add(Items.IRON_SHOVEL);
        add(Items.NETHERITE_SHOVEL);
        add(Items.BOW);
        add(Items.ARROW);
        add(Items.TRIDENT);
    }};

    private final Map<StatusEffect, Integer> classStatusEffects = Stream.of(new Object[][]{
            {StatusEffects.FIRE_RESISTANCE, 1},
            {StatusEffects.HASTE, 1},
            {StatusEffects.JUMP_BOOST, 2},
            {StatusEffects.NIGHT_VISION, 1},
            {StatusEffects.HEALTH_BOOST, 2},
    }).collect(Collectors.toMap(data -> (StatusEffect) data[0], data -> (Integer) data[1]));

    private final Map<Item, List<Pair<Enchantment, Integer>>> classEnchantments = Stream.of(
            new AbstractMap.SimpleEntry<>(Items.NETHERITE_AXE, Collections.singletonList(
                    new Pair<>(Enchantments.FIRE_ASPECT, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.DIAMOND_AXE, Collections.singletonList(
                    new Pair<>(Enchantments.FIRE_ASPECT, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.IRON_AXE, Collections.singletonList(
                    new Pair<>(Enchantments.FIRE_ASPECT, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.GOLDEN_AXE, Collections.singletonList(
                    new Pair<>(Enchantments.FIRE_ASPECT, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.STONE_AXE, Collections.singletonList(
                    new Pair<>(Enchantments.FIRE_ASPECT, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.WOODEN_AXE, Collections.singletonList(
                    new Pair<>(Enchantments.FIRE_ASPECT, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.NETHERITE_PICKAXE, Collections.singletonList(
                    new Pair<>(Enchantments.EFFICIENCY, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.DIAMOND_PICKAXE, Collections.singletonList(
                    new Pair<>(Enchantments.EFFICIENCY, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.IRON_PICKAXE, Collections.singletonList(
                    new Pair<>(Enchantments.EFFICIENCY, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.GOLDEN_PICKAXE, Collections.singletonList(
                    new Pair<>(Enchantments.EFFICIENCY, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.STONE_PICKAXE, Collections.singletonList(
                    new Pair<>(Enchantments.EFFICIENCY, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.WOODEN_PICKAXE, Collections.singletonList(
                    new Pair<>(Enchantments.EFFICIENCY, 5)
            )),
            new AbstractMap.SimpleEntry<>(Items.FLINT_AND_STEEL, new ArrayList<Pair<Enchantment, Integer>>())
            // See to make infinity working on crossbow
            // Inventory auto smelt
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public FireDwarfStatusEffect() {
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
