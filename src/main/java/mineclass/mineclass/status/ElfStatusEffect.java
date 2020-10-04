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

public class ElfStatusEffect extends MineclassStatusEffectImpl implements MineclassStatusEffect {

    public Set<Item> forbiddenItems = new HashSet<Item>() {{
        add(Items.DIAMOND_SWORD);
        add(Items.GOLDEN_SWORD);
        add(Items.IRON_SWORD);
        add(Items.NETHERITE_SWORD);
        add(Items.DIAMOND_PICKAXE);
        add(Items.GOLDEN_PICKAXE);
        add(Items.IRON_PICKAXE);
        add(Items.NETHERITE_PICKAXE);
        add(Items.DIAMOND_SHOVEL);
        add(Items.GOLDEN_SHOVEL);
        add(Items.IRON_SHOVEL);
        add(Items.NETHERITE_SHOVEL);
        add(Items.CROSSBOW);
    }};

    public Map<StatusEffect, Integer> classStatusEffects = Stream.of(new Object[][]{
            {StatusEffects.SPEED, 2},
            {StatusEffects.JUMP_BOOST, 1},
            {StatusEffects.STRENGTH, 1},
            {StatusEffects.LUCK, 2},
            {StatusEffects.NIGHT_VISION, 1},
    }).collect(Collectors.toMap(data -> (StatusEffect) data[0], data -> (Integer) data[1]));

    public Map<Item, List<Pair<Enchantment, Integer>>> classEnchantments = Stream.of(
            new AbstractMap.SimpleEntry<>(Items.BOW, Arrays.asList(
                    new Pair<>(Enchantments.INFINITY, 1),
                    new Pair<>(Enchantments.POWER, 5)
            ))
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public ElfStatusEffect() {
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
