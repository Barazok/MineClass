package mineclass.mineclass.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerPickupItemCallback {
    Event<PlayerPickupItemCallback> EVENT = EventFactory.createArrayBacked(PlayerPickupItemCallback.class,
            (listeners) -> (player, entity) -> {
                for (PlayerPickupItemCallback event : listeners) {
                    ActionResult result = event.interact(player, entity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, ItemEntity pickupEntity);
}