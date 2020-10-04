package mineclass.mineclass;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public class MineclassMod implements ModInitializer {

    public static final StatusEffect DWARF = new DwarfStatusEffect();
    public static final StatusEffect ELF = new ElfStatusEffect();

    @Override
    public void onInitialize() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "dwarf"), DWARF);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "elf"), ELF);
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (oldPlayer.hasStatusEffect(DWARF)) {
                applyDwarfEffects(newPlayer);
            }
            if (oldPlayer.hasStatusEffect(ELF)) {
                applyElfEffects(newPlayer);
            }
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("class").executes(context -> 1));
            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("dwarf")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                AppliedStatus.getInstance().setDwarf(entity, true);
                                AppliedStatus.getInstance().setElf(entity, false);
                                removeElfEffects(entity);
                                applyDwarfEffects(entity);
                                return 1;
                            })
                    )
            );
            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("elf")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                AppliedStatus.getInstance().setDwarf(entity, false);
                                AppliedStatus.getInstance().setElf(entity, true);
                                removeDwarfEffects(entity);
                                applyElfEffects(entity);
                                return 1;
                            })
                    )
            );
            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("clear")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                AppliedStatus.getInstance().setDwarf(entity, false);
                                AppliedStatus.getInstance().setElf(entity, false);
                                removeDwarfEffects(entity);
                                removeElfEffects(entity);
                                return 1;
                            })
                    )
            );
        });
    }

    private void removeElfEffects(ServerPlayerEntity entity) {
        entity.removeStatusEffect(ELF);
        entity.removeStatusEffect(StatusEffects.SPEED);
        entity.removeStatusEffect(StatusEffects.JUMP_BOOST);
        entity.removeStatusEffect(StatusEffects.STRENGTH);
        entity.removeStatusEffect(StatusEffects.LUCK);
        entity.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }

    private void removeDwarfEffects(ServerPlayerEntity entity) {
        entity.removeStatusEffect(DWARF);
        entity.removeStatusEffect(StatusEffects.HEALTH_BOOST);
        entity.removeStatusEffect(StatusEffects.RESISTANCE);
        entity.removeStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
        entity.removeStatusEffect(StatusEffects.HASTE);
        entity.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }

    private void applyDwarfEffects(ServerPlayerEntity entity) {
        entity.addStatusEffect(ClassStatusEffectInstance.of(DWARF, 0));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.HEALTH_BOOST, 1));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.RESISTANCE, 0));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.HERO_OF_THE_VILLAGE, 0));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.HASTE, 1));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.NIGHT_VISION, 0));
    }

    private void applyElfEffects(ServerPlayerEntity entity) {
        entity.addStatusEffect(ClassStatusEffectInstance.of(ELF, 0));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.SPEED, 0));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.JUMP_BOOST, 1));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.STRENGTH, 0));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.LUCK, 0));
        entity.addStatusEffect(ClassStatusEffectInstance.of(StatusEffects.NIGHT_VISION, 0));
    }
}
