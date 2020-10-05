package mineclass.mineclass.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmeltingEngine {
    private static final Map<Item, Item> smeltingCorrespondance = Stream.of(new Item[][]{
            {Items.COAL_ORE, Items.COAL},
            {Items.DIAMOND_ORE, Items.DIAMOND},
            {Items.EMERALD_ORE, Items.EMERALD},
            {Items.GOLD_ORE, Items.GOLD_INGOT},
            {Items.IRON_ORE, Items.IRON_INGOT},
            {Items.LAPIS_ORE, Items.LAPIS_LAZULI},
            {Items.NETHER_QUARTZ_ORE, Items.QUARTZ},
            {Items.NETHER_GOLD_ORE, Items.GOLD_NUGGET},
            {Items.REDSTONE_ORE, Items.REDSTONE},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static boolean isSmeltable(ItemStack itemStack) {
        return smeltingCorrespondance.containsKey(itemStack.getItem());
    }

    public static ItemStack smelt(ItemStack itemStack) {
        return new ItemStack(smeltingCorrespondance.get(itemStack.getItem()), itemStack.getCount());
    }
}
