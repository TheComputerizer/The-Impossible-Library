package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.BiFunction;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.*;

public abstract class InfernalMobsAPI implements ModAPI {

    public static final String MODID = "infernalmobs";
    public static final String NAME = "Infernal Mobs";

    protected InfernalMobsAPI() {}

    @Override
    public String getID() {
        return MODID;
    }

    public abstract @Nullable InfernalData<?> getInfernalData(EntityAPI<?,?> entity);

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        switch(version) {
            case V12: return loader==LEGACY;
            case V18:
            case V19: return loader==FORGE;
            case V20: return loader==FORGE || loader==NEOFORGE;
            default: return false;
        }
    }

    public boolean isInfernal(EntityAPI<?,?> entity) {
        return Objects.nonNull(getInfernalData(entity));
    }

    public static class InfernalData<I> {

        private final I instance;
        @Getter private final String name;
        @Getter private final Collection<String> displayNames;
        @Getter private final int size;
        private final BiFunction<I,Class<?>,Boolean> modClassChecker;

        public InfernalData(I instance, String name, String[] displayNames, int size,
                            BiFunction<I,Class<?>,Boolean> modClassChecker) {
            this.instance = instance;
            this.name = name;
            this.displayNames = new HashSet<>(Arrays.asList(displayNames));
            this.size = size;
            this.modClassChecker = modClassChecker;
        }

        public boolean hasModifierClass(Class<?> clazz) {
            return this.modClassChecker.apply(this.instance,clazz);
        }
    }
}