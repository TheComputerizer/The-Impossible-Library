package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import net.minecraft.core.Registry;

import java.util.Objects;

import static net.minecraft.core.Registry.*;

public class TILCommonEntryPointFabric1_18_2 extends TILCommonEntryPointFabric {
    
    private static TILCommonEntryPointFabric1_18_2 INSTANCE;
    
    public static TILCommonEntryPointFabric1_18_2 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_18_2();
    }
    
    private TILCommonEntryPointFabric1_18_2() {
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
