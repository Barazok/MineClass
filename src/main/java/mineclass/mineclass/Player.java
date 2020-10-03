package mineclass.mineclass;


import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class Player {
    PlayerEntity player;

    public Player(PlayerEntity Player) {
        this.player = Player;
    }

    public boolean hasItemStack(ItemStack ItemStack) {
        for (int i = 0; i < player.inventory.main.size(); ++i) {
            if (player.inventory.main.get(i).isItemEqual(ItemStack)) {
                return true;
            }
        }
        return false;
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

    public boolean removeItemStack(ItemStack ItemStack) {
        if (this.hasItemStack(ItemStack)) {
            for (int i = 0; i < player.inventory.main.size(); ++i) {
                ItemStack slot = player.inventory.main.get(i);
                if (slot.isItemEqual(ItemStack)) {
                    if (slot.getCount() > ItemStack.getCount()) {
                        player.inventory.setStack(i,
                                new ItemStack(ItemStack.getItem(), slot.getCount() - ItemStack.getCount()));
                        return true;
                    } else if (slot.getCount() == ItemStack.getCount()) {
                        player.inventory.removeStack(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeItemStacks(List<ItemStack> ItemStacks) {
        if (this.hasItemStacks(ItemStacks)) {
            for (int i = 0; i < ItemStacks.size(); ++i) {
                ItemStack ItemStack = ItemStacks.get(i);
                for (int j = 0; j < player.inventory.main.size(); ++j) {
                    ItemStack slot = player.inventory.main.get(j);
                    if (slot.isItemEqual(ItemStack)) {
                        if (slot.getCount() > ItemStack.getCount()) {
                            player.inventory.setStack(j,
                                    new ItemStack(ItemStack.getItem(), slot.getCount() - ItemStack.getCount()));
                            if (i == ItemStacks.size() - 1) {
                                return true;
                            }
                        } else if (slot.getCount() == ItemStack.getCount()) {
                            player.inventory.removeStack(j);
                            if (i == ItemStacks.size() - 1) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean addItemStack(ItemStack ItemStack) {
        for (int i = 0; i < player.inventory.main.size(); ++i) {
            ItemStack slot = player.inventory.main.get(i);
            if (slot.isItemEqual(ItemStack)) {
                if (slot.getCount() + ItemStack.getCount() > slot.getMaxCount()) {
                    return false;
                } else if (slot.getCount() + ItemStack.getCount() <= slot.getMaxCount()) {
                    player.inventory.setStack(i,
                            new ItemStack(ItemStack.getItem(), slot.getCount() + ItemStack.getCount()));
                }
            }
        }
        return false;
    }
}