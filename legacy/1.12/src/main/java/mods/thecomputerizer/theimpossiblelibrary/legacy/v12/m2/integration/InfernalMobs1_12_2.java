package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.InfernalMobsAPI;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nullable;
import java.util.Objects;

public class InfernalMobs1_12_2 extends InfernalMobsAPI {

    @Override public @Nullable InfernalData<MobModifier> getInfernalData(EntityAPI<?,?> entity) {
        MobModifier mod = getModifier(entity);
        return Objects.nonNull(mod) ? new InfernalData<>(mod,mod.getModName(),mod.getDisplayNames(),mod.getModSize(),
                MobModifier::containsModifierClass) : null;
    }

    public @Nullable MobModifier getModifier(EntityAPI<?,?> api) {
        Object entity = api.getEntity();
        return entity instanceof EntityLivingBase ? InfernalMobsCore.getMobModifiers((EntityLivingBase)entity) : null;
    }
}