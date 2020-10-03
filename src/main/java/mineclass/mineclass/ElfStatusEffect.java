package mineclass.mineclass;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class ElfStatusEffect extends StatusEffect {

    public List<Item> forbiddenItems = Arrays.asList(
            Items.DIAMOND_SWORD,
            Items.GOLDEN_SWORD,
            Items.IRON_SWORD,
            Items.NETHERITE_SWORD,
            Items.DIAMOND_PICKAXE,
            Items.GOLDEN_PICKAXE,
            Items.IRON_PICKAXE,
            Items.NETHERITE_PICKAXE,
            Items.DIAMOND_SHOVEL,
            Items.GOLDEN_SHOVEL,
            Items.IRON_SHOVEL,
            Items.NETHERITE_SHOVEL
    );

    public ElfStatusEffect() {
        super(StatusEffectType.BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = ((PlayerEntity) entity);
            forbiddenItems.forEach(item -> dropAndRemoveItem(playerEntity, item));
        }
    }

    private void dropAndRemoveItem(PlayerEntity playerEntity, Item item) {
        PlayerInventory playerInventory = playerEntity.inventory;
        ItemStack stack = item.getDefaultStack();

        if (playerInventory != null && playerInventory.contains(stack)) {
            int slot = playerInventory.getSlotWithStack(stack);
            playerEntity.dropStack(stack);
            playerInventory.removeStack(slot);
        }
    }
}
