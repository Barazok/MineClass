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
        add(Items.DIAMOND_AXE);
        add(Items.GOLDEN_AXE);
        add(Items.IRON_AXE);
        add(Items.NETHERITE_AXE);
        add(Items.DIAMOND_HOE);
        add(Items.GOLDEN_HOE);
        add(Items.IRON_HOE);
        add(Items.NETHERITE_HOE);
        add(Items.BOW);
    }};

    private final Map<StatusEffect, Integer> classStatusEffects = Stream.of(new Object[][]{
            {StatusEffects.HEALTH_BOOST, 2},
            {StatusEffects.RESISTANCE, 1},
            {StatusEffects.HERO_OF_THE_VILLAGE, 1},
            {StatusEffects.HASTE, 2},
            {StatusEffects.NIGHT_VISION, 1},
    }).collect(Collectors.toMap(data -> (StatusEffect) data[0], data -> (Integer) data[1]));

    private final Map<Item, List<Pair<Enchantment, Integer>>> classEnchantments = Stream.of(
            new AbstractMap.SimpleEntry<>(Items.CROSSBOW, Arrays.asList(
                    new Pair<>(Enchantments.MULTISHOT, 4),
                    new Pair<>(Enchantments.PIERCING, 1),
                    new Pair<>(Enchantments.QUICK_CHARGE, 3)
            ))
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
