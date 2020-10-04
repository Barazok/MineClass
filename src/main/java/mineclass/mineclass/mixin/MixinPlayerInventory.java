package mineclass.mineclass.mixin;

import mineclass.mineclass.callbacks.PlayerInventorySetStackCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class MixinPlayerInventory {

    @Shadow
    @Final
    public PlayerEntity player;

    @Inject(method = "setStack", at = @At("TAIL"), cancellable = true)
    private void setStack(final int slot, final ItemStack stack, final CallbackInfo info) {
        ActionResult result = PlayerInventorySetStackCallback.EVENT.invoker().interact(player, stack);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}