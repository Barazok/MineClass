package mineclass.mineclass;

import java.io.Serializable;

public class AppliedStatus implements Serializable {
    /**
     * Instance unique pré-initialisée
     */
    private static final AppliedStatus INSTANCE = new AppliedStatus();
    private boolean dwarf = false;
    private boolean elf = false;

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

    public boolean isDwarf() {
        return dwarf;
    }

    public void setDwarf(boolean dwarf) {
        this.dwarf = dwarf;
    }

    public boolean isElf() {
        return elf;
    }

    public void setElf(boolean elf) {
        this.elf = elf;
    }
}
