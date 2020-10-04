package mineclass.mineclass.utils;

import net.minecraft.server.network.ServerPlayerEntity;

import java.io.Serializable;
import java.util.HashMap;

public class AppliedStatus implements Serializable {
    /**
     * Instance unique pré-initialisée
     */
    private static final AppliedStatus INSTANCE = new AppliedStatus();
    private final HashMap<ServerPlayerEntity, Boolean> dwarf = new HashMap<>();
    private final HashMap<ServerPlayerEntity, Boolean> elf = new HashMap<>();
    private final HashMap<ServerPlayerEntity, Boolean> fireDwarf = new HashMap<>();
    private final HashMap<ServerPlayerEntity, Boolean> naga = new HashMap<>();

    /**
     * Constructeur privé
     */
    private AppliedStatus() {
    }

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static AppliedStatus getInstance() {
        return INSTANCE;
    }

    public void setDwarf(ServerPlayerEntity entity, boolean dwarf) {
        this.dwarf.put(entity, dwarf);
    }

    public void setElf(ServerPlayerEntity entity, boolean elf) {
        this.elf.put(entity, elf);
    }

    public void setFireDwarf(ServerPlayerEntity entity, boolean fireDwarf) {
        this.elf.put(entity, fireDwarf);
    }

    public void setNaga(ServerPlayerEntity entity, boolean naga) {
        this.elf.put(entity, naga);
    }

    public HashMap<ServerPlayerEntity, Boolean> getDwarf() {
        return dwarf;
    }

    public HashMap<ServerPlayerEntity, Boolean> getElf() {
        return elf;
    }

    public HashMap<ServerPlayerEntity, Boolean> getFireDwarf() {
        return fireDwarf;
    }

    public HashMap<ServerPlayerEntity, Boolean> getNaga() {
        return naga;
    }
}
