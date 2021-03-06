package mineclass.mineclass;

import mineclass.mineclass.callbacks.PlayerInventorySetStackCallback;
import mineclass.mineclass.callbacks.PlayerPickupItemCallback;
import mineclass.mineclass.callbacks.ServerPlayerEvents;
import mineclass.mineclass.status.*;
import mineclass.mineclass.utils.AppliedStatus;
import mineclass.mineclass.utils.ClassStatusEffectInstance;
import mineclass.mineclass.utils.Player;
import mineclass.mineclass.utils.SmeltingEngine;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MineclassMod implements ModInitializer {

    public static final MineclassStatusEffectImpl DWARF = new DwarfStatusEffect();
    public static final MineclassStatusEffectImpl ELF = new ElfStatusEffect();
    public static final MineclassStatusEffectImpl FIRE_DWARF = new FireDwarfStatusEffect();
    public static final MineclassStatusEffectImpl NAGA = new NagaStatusEffect();

    @Override
    public void onInitialize() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "dwarf"), DWARF);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "elf"), ELF);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "firedwarf"), FIRE_DWARF);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "naga"), NAGA);

        PlayerPickupItemCallback.EVENT.register((player, pickupEntity) -> {
            ItemStack itemStack = pickupEntity.getStack();
            if (player.hasStatusEffect(DWARF)) {
                if (DWARF.getForbiddenItems().contains(itemStack.getItem())) {
                    return ActionResult.FAIL;
                }
            }
            if (player.hasStatusEffect(ELF)) {
                if (ELF.getForbiddenItems().contains(itemStack.getItem())) {
                    return ActionResult.FAIL;
                }
            }
            if (player.hasStatusEffect(FIRE_DWARF)) {
                if (FIRE_DWARF.getForbiddenItems().contains(itemStack.getItem())) {
                    return ActionResult.FAIL;
                }
                if (player.hasStatusEffect(FIRE_DWARF)) {
                    if (SmeltingEngine.isSmeltable(itemStack)) {
                        pickupEntity.setStack(SmeltingEngine.smelt(itemStack));
                    }
                }
            }
            if (player.hasStatusEffect(NAGA)) {
                if (NAGA.getForbiddenItems().contains(itemStack.getItem())) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (oldPlayer.hasStatusEffect(DWARF)) {
                applyDwarfStatus(newPlayer);
            }
            if (oldPlayer.hasStatusEffect(ELF)) {
                applyElfStatus(newPlayer);
            }
            if (oldPlayer.hasStatusEffect(FIRE_DWARF)) {
                applyFireDwarfStatus(newPlayer);
            }
            if (oldPlayer.hasStatusEffect(NAGA)) {
                applyNagaStatus(newPlayer);
            }
        });

        PlayerInventorySetStackCallback.EVENT.register((playerEntity, itemStack) -> {
            if (playerEntity.hasStatusEffect(DWARF)) {
                DWARF.dropAndRemoveForbiddenItems(playerEntity);
                if (DWARF.getClassEnchantments().containsKey(itemStack.getItem())) {
                    Player.applyEnchantmentOnStack(itemStack, DWARF.getClassEnchantments().get(itemStack.getItem()));
                    return ActionResult.PASS;
                }
            }
            if (playerEntity.hasStatusEffect(ELF)) {
                ELF.dropAndRemoveForbiddenItems(playerEntity);
                if (ELF.getClassEnchantments().containsKey(itemStack.getItem())) {
                    Player.applyEnchantmentOnStack(itemStack, ELF.getClassEnchantments().get(itemStack.getItem()));
                    return ActionResult.PASS;
                }
            }
            if (playerEntity.hasStatusEffect(FIRE_DWARF)) {
                FIRE_DWARF.dropAndRemoveForbiddenItems(playerEntity);
                if (FIRE_DWARF.getClassEnchantments().containsKey(itemStack.getItem())) {
                    Player.applyEnchantmentOnStack(itemStack, FIRE_DWARF.getClassEnchantments().get(itemStack.getItem()));
                    return ActionResult.PASS;
                }
            }
            if (playerEntity.hasStatusEffect(NAGA)) {
                NAGA.dropAndRemoveForbiddenItems(playerEntity);
                if (NAGA.getClassEnchantments().containsKey(itemStack.getItem())) {
                    Player.applyEnchantmentOnStack(itemStack, NAGA.getClassEnchantments().get(itemStack.getItem()));
                    return ActionResult.PASS;
                }
            }
            return ActionResult.PASS;
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            dispatcher.register(CommandManager.literal("class").executes(context -> {
                context.getSource().sendFeedback(new TranslatableText("command.mineclass.call_class_without_args"), false);
                return 1;
            }));

            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("clear")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                removeDwarfStatus(entity);
                                removeElfStatus(entity);
                                removeFireDwarfStatus(entity);
                                removeNagaStatus(entity);
                                return 1;
                            })
                    )
            );

            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("dwarf")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                removeElfStatus(entity);
                                removeNagaStatus(entity);
                                removeFireDwarfStatus(entity);
                                applyDwarfStatus(entity);
                                return 1;
                            })
                    )
            );

            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("elf")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                removeDwarfStatus(entity);
                                removeFireDwarfStatus(entity);
                                removeNagaStatus(entity);
                                applyElfStatus(entity);
                                return 1;
                            })
                    )
            );

            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("fire_dwarf")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                removeDwarfStatus(entity);
                                removeElfStatus(entity);
                                removeNagaStatus(entity);
                                applyFireDwarfStatus(entity);
                                return 1;
                            })
                    )
            );

            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("naga")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                removeDwarfStatus(entity);
                                removeFireDwarfStatus(entity);
                                removeElfStatus(entity);
                                applyNagaStatus(entity);
                                return 1;
                            })
                    )
            );
        });
    }

    private void applyElfStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setElf(entity, true);
        entity.addStatusEffect(ClassStatusEffectInstance.of(ELF, 0));
        ELF.applyEffects(entity);
        ELF.dropAndRemoveForbiddenItems(entity);
        ELF.applyEnchantments(entity);
    }

    private void removeElfStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setElf(entity, false);
        entity.removeStatusEffect(ELF);
        ELF.removeEffects(entity);
    }

    private void applyDwarfStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setDwarf(entity, true);
        entity.addStatusEffect(ClassStatusEffectInstance.of(DWARF, 0));
        DWARF.applyEffects(entity);
        DWARF.dropAndRemoveForbiddenItems(entity);
        DWARF.applyEnchantments(entity);
    }

    private void removeDwarfStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setDwarf(entity, false);
        entity.removeStatusEffect(DWARF);
        DWARF.removeEffects(entity);
    }

    private void applyFireDwarfStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setFireDwarf(entity, true);
        entity.addStatusEffect(ClassStatusEffectInstance.of(FIRE_DWARF, 0));
        FIRE_DWARF.applyEffects(entity);
        FIRE_DWARF.dropAndRemoveForbiddenItems(entity);
        FIRE_DWARF.applyEnchantments(entity);
    }

    private void removeFireDwarfStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setFireDwarf(entity, false);
        entity.removeStatusEffect(FIRE_DWARF);
        FIRE_DWARF.removeEffects(entity);
    }

    private void applyNagaStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setNaga(entity, true);
        entity.addStatusEffect(ClassStatusEffectInstance.of(NAGA, 0));
        NAGA.applyEffects(entity);
        NAGA.dropAndRemoveForbiddenItems(entity);
        NAGA.applyEnchantments(entity);
    }

    private void removeNagaStatus(ServerPlayerEntity entity) {
        AppliedStatus.getInstance().setNaga(entity, false);
        entity.removeStatusEffect(NAGA);
        NAGA.removeEffects(entity);
    }
}
