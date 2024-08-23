package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class ChampionsAPI implements ModAPI {

    public static final String MODID = "champions";
    public static final String NAME = "Champions";

    protected ChampionsAPI() {}

    public abstract @Nullable ChampionData getChampionData(EntityAPI<?,?> entity);

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public boolean isChampion(EntityAPI<?,?> entity) {
        return Objects.nonNull(getChampionData(entity));
    }

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        return loader.isLegacyForge() ? version.isV12() :
                (loader.isModernForge() && (version.isV16() || version.isV18()));
    }

    @Getter
    public static class ChampionData {

        private final String name;
        private final Collection<String> affixes;
        private final int tier;

        public ChampionData(String name, Collection<String> affixes, int tier) {
            this.name = name;
            this.affixes = affixes;
            this.tier = tier;
        }
    }
}