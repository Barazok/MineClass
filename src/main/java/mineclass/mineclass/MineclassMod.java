package mineclass.mineclass;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MineclassMod implements ModInitializer {

    public static final StatusEffect DWARF = new DwarfStatusEffect();
    public static final StatusEffect ELF = new ElfStatusEffect();

    @Override
    public void onInitialize() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "dwarf"), DWARF);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mineclass", "elf"), ELF);
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (minecraftClient.player != null) {
                if (AppliedStatus.getInstance().isDwarf() && !minecraftClient.player.hasStatusEffect(DWARF)) {
                    minecraftClient.player.addStatusEffect(ClassStatusEffectInstance.of(DWARF));
                } else if (!AppliedStatus.getInstance().isDwarf()) {
                    minecraftClient.player.removeStatusEffect(DWARF);
                }
                if (AppliedStatus.getInstance().isElf() && !minecraftClient.player.hasStatusEffect(ELF)) {
                    minecraftClient.player.addStatusEffect(ClassStatusEffectInstance.of(ELF));
                } else if (!AppliedStatus.getInstance().isElf()) {
                    minecraftClient.player.removeStatusEffect(ELF);
                }
            }
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("class").executes(context -> 1));
            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("dwarf")
                            .executes(context -> {
                                AppliedStatus.getInstance().setElf(false);
                                AppliedStatus.getInstance().setDwarf(true);
                                return 1;
                            })
                    )
            );
            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("elf")
                            .executes(context -> {
                                AppliedStatus.getInstance().setDwarf(false);
                                AppliedStatus.getInstance().setElf(true);
                                return 1;
                            })
                    )
            );
            dispatcher.register(CommandManager.literal("class")
                    .then(CommandManager.literal("clear")
                            .executes(context -> {
                                AppliedStatus.getInstance().setDwarf(false);
                                AppliedStatus.getInstance().setElf(false);
                                return 1;
                            })
                    )
            );
        });
    }
}
