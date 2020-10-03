package mineclass.mineclass;

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

    public HashMap<ServerPlayerEntity, Boolean> getDwarf() {
        return dwarf;
    }

    public HashMap<ServerPlayerEntity, Boolean> getElf() {
        return elf;
    }
}
