package mineclass.mineclass.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface PlayerInventorySetStackCallback {
    Event<PlayerInventorySetStackCallback> EVENT = EventFactory.createArrayBacked(PlayerInventorySetStackCallback.class,
            (listeners) -> (playerEntity, itemStack) -> {
                for (PlayerInventorySetStackCallback event : listeners) {
                    ActionResult result = event.interact(playerEntity, itemStack);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity playerEntity, ItemStack itemStack);
}