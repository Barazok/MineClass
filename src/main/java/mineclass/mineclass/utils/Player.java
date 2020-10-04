package mineclass.mineclass.utils;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class Player {
    PlayerEntity player;

    public Player(PlayerEntity Player) {
        this.player = Player;
    }

    public static ItemStack applyEnchantmentOnStack(ItemStack itemStack, List<Pair<Enchantment, Integer>> enchantments) {
        if (itemStack.getEnchantments().isEmpty()) {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("Unbreakable", true);
            itemStack.setTag(tag);
            enchantments.forEach(enchantmentIntegerPair -> itemStack.addEnchantment(enchantmentIntegerPair.getFirst(), enchantmentIntegerPair.getSecond()));
        }
        return itemStack;
    }

    public static ItemStack removeUnauthorizedEnchants(ItemStack itemStack) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("Unbreakable")) {
            itemStack = itemStack.getItem().getDefaultStack();
        }
        return itemStack;
    }

    public boolean hasItemStack(ItemStack itemStack) {
        for (int i = 0; i < player.inventory.main.size(); ++i) {
            if (player.inventory.main.get(i).isItemEqual(itemStack)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getItemStack(ItemStack itemStack) {
        for (int i = 0; i < player.inventory.main.size(); ++i) {
            if (player.inventory.main.get(i).isItemEqual(itemStack)) {
                return player.inventory.main.get(i);
            }
        }
        return null;
    }

    public void applyEnchantmentsOnAllStacks(ItemStack itemStack, List<Pair<Enchantment, Integer>> enchantments) {
        if (enchantments == null) {
            return;
        }
        for (int i = 0; i < player.inventory.main.size(); ++i) {
            ItemStack slot = player.inventory.main.get(i);
            if (slot.isItemEqual(itemStack)) {
                player.inventory.setStack(i, applyEnchantmentOnStack(itemStack, enchantments));
            }
        }
    }

    public void removeUnauthorizedEnchantsOnAllStack(ItemStack itemStack) {
        for (int i = 0; i < player.inventory.main.size(); ++i) {
            ItemStack slot = player.inventory.main.get(i);
            if (slot.isItemEqual(itemStack)) {
                player.inventory.setStack(i, removeUnauthorizedEnchants(itemStack));
            }
        }
    }

    public boolean hasItemStacks(List<ItemStack> ItemStacks) {
        for (int i = 0; i < ItemStacks.size(); ++i) {
            ItemStack ItemStack = ItemStacks.get(i);
            for (int j = 0; j < player.inventory.main.size(); ++j) {
                if (player.inventory.main.get(j).isItemEqual(ItemStack)) {
                    if (i == ItemStacks.size() - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeItemStack(ItemStack itemStack) {
        if (this.hasItemStack(itemStack)) {
            for (int i = 0; i < player.inventory.main.size(); ++i) {
                ItemStack slot = player.inventory.main.get(i);
                if (slot.isItemEqual(itemStack)) {
                    if (slot.getCount() > itemStack.getCount()) {
                        player.inventory.setStack(i,
                                new ItemStack(itemStack.getItem(), slot.getCount() - itemStack.getCount()));
                        return true;
                    } else if (slot.getCount() == itemStack.getCount()) {
                        player.inventory.removeStack(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeItemStacks(List<ItemStack> itemStacks) {
        if (this.hasItemStacks(itemStacks)) {
            for (int i = 0; i < itemStacks.size(); ++i) {
                ItemStack ItemStack = itemStacks.get(i);
                for (int j = 0; j < player.inventory.main.size(); ++j) {
                    ItemStack slot = player.inventory.main.get(j);
                    if (slot.isItemEqual(ItemStack)) {
                        if (slot.getCount() > ItemStack.getCount()) {
                            player.inventory.setStack(j,
                                    new ItemStack(ItemStack.getItem(), slot.getCount() - ItemStack.getCount()));
                            if (i == itemStacks.size() - 1) {
                                return true;
                            }
                        } else if (slot.getCount() == ItemStack.getCount()) {
                            player.inventory.removeStack(j);
                            if (i == itemStacks.size() - 1) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean addItemStack(ItemStack itemStack) {
        for (int i = 0; i < player.inventory.main.size(); ++i) {
            ItemStack slot = player.inventory.main.get(i);
            if (slot.isItemEqual(itemStack)) {
                if (slot.getCount() + itemStack.getCount() > slot.getMaxCount()) {
                    return false;
                } else if (slot.getCount() + itemStack.getCount() <= slot.getMaxCount()) {
                    player.inventory.setStack(i,
                            new ItemStack(itemStack.getItem(), slot.getCount() + itemStack.getCount()));
                    return true;
                }
            }
        }
        return false;
    }
}