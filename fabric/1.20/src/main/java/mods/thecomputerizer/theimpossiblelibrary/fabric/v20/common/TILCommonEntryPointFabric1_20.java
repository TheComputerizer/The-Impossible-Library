package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.BLOCK;
import static net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE;
import static net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE;
import static net.minecraft.core.registries.BuiltInRegistries.ITEM;
import static net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT;

public class TILCommonEntryPointFabric1_20 extends TILCommonEntryPointFabric {
    
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
