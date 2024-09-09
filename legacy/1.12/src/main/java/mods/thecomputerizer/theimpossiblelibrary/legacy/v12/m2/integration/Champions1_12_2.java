package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import c4.champions.common.capability.CapabilityChampionship;
import c4.champions.common.capability.IChampionship;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ChampionsAPI;
import net.minecraft.entity.EntityLiving;

import javax.annotation.Nullable;
import java.util.Objects;

public class Champions1_12_2 extends ChampionsAPI {

    public @Nullable IChampionship getCapability(EntityAPI<?,?> api) {
        Object entity = api.getEntity();
        return entity instanceof EntityLiving ? CapabilityChampionship.getChampionship((EntityLiving)entity) : null;
    }

    @Override public @Nullable ChampionData getChampionData(EntityAPI<?,?> entity) {
        IChampionship cap = getCapability(entity);
        return Objects.nonNull(cap) ? new ChampionData(cap.getName(),cap.getAffixes(),cap.getRank().getTier()) : null;
    }
}