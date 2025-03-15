package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.TILCommonEntryPointFabric1_19;
import net.minecraft.core.Registry;

import java.util.Objects;

import static net.minecraft.core.Registry.*;

public class TILCommonEntryPointFabric1_19_2 extends TILCommonEntryPointFabric1_19 {
    
    private static TILCommonEntryPointFabric1_19_2 INSTANCE;
    
    public static TILCommonEntryPointFabric1_19_2 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_19_2();
    }
    
    private TILCommonEntryPointFabric1_19_2() {
        INSTANCE = this;
    }
    
    @Override protected Registry<?> registryBlock() {
        return BLOCK;
    }
    
    @Override protected Registry<?> registryBlockEntity() {
        return BLOCK_ENTITY_TYPE;
    }
    
    @Override protected Registry<?> registryEntity() {
        return ENTITY_TYPE;
    }
    
    @Override protected Registry<?> registryItem() {
        return ITEM;
    }
    
    @Override protected Registry<?> registrySoundEvent() {
        return SOUND_EVENT;
    }
}
