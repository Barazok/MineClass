package mineclass.mineclass;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.effect.StatusEffect;
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
            System.out.println("Respawn event : " + oldPlayer + " ; " + newPlayer);
            if (oldPlayer.hasStatusEffect(DWARF)) {
                System.out.println(oldPlayer + " had dwarf effect !");
                newPlayer.addStatusEffect(ClassStatusEffectInstance.of(DWARF));
            }
            if (oldPlayer.hasStatusEffect(ELF)) {
                System.out.println(oldPlayer + " had elf effect !");
                newPlayer.addStatusEffect(ClassStatusEffectInstance.of(ELF));
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
                                entity.addStatusEffect(ClassStatusEffectInstance.of(DWARF));
                                entity.removeStatusEffect(ELF);
                                return 1;
                            })
                    )
            );
            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("elf")
                            .executes(context -> {
                                ServerPlayerEntity entity = context.getSource().getPlayer();
                                AppliedStatus.getInstance().setElf(entity, true);
                                AppliedStatus.getInstance().setDwarf(entity, false);
                                entity.addStatusEffect(ClassStatusEffectInstance.of(ELF));
                                entity.removeStatusEffect(DWARF);
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
                                entity.removeStatusEffect(DWARF);
                                entity.removeStatusEffect(ELF);
                                return 1;
                            })
                    )
            );
        });
    }
}
