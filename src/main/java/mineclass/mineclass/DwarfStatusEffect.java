package mineclass.mineclass;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class DwarfStatusEffect extends StatusEffect {

    public List<Item> forbiddenItems = Arrays.asList(
            Items.DIAMOND_AXE,
            Items.GOLDEN_AXE,
            Items.IRON_AXE,
            Items.NETHERITE_AXE,
            Items.DIAMOND_HOE,
            Items.GOLDEN_HOE,
            Items.IRON_HOE,
            Items.NETHERITE_HOE,
            Items.BOW
    );

    public DwarfStatusEffect() {
        super(StatusEffectType.BENEFICIAL, // whether beneficial or harmful for entities
                0xD98282); // color in RGB
    }

    // This method is called every tick to check weather it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = ((PlayerEntity) entity);
            forbiddenItems.forEach(item -> dropAndRemoveItem(playerEntity, item));
        }
    }

    private void dropAndRemoveItem(PlayerEntity playerEntity, Item item) {
        Player player = new Player(playerEntity);
        ItemStack stack = item.getDefaultStack();

        if (player.removeItemStack(stack)) {
            playerEntity.dropStack(stack);
        }
    }
}
