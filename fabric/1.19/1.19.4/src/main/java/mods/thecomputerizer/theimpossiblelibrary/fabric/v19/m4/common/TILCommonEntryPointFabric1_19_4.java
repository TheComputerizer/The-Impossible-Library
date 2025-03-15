package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.TILCommonEntryPointFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab.CreativeTabBuilder1_19_4;
import net.minecraft.core.Registry;

import java.util.Objects;

import static net.minecraft.core.registries.BuiltInRegistries.BLOCK;
import static net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE;
import static net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE;
import static net.minecraft.core.registries.BuiltInRegistries.ITEM;
import static net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT;

public class TILCommonEntryPointFabric1_19_4 extends TILCommonEntryPointFabric1_19 {
    
    private static TILCommonEntryPointFabric1_19_4 INSTANCE;
    
    public static TILCommonEntryPointFabric1_19_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_19_4();
    }
    
    private TILCommonEntryPointFabric1_19_4() {
        INSTANCE = this;
    }
    
    @Override public void onLoadComplete() {
        CreativeTabBuilder1_19_4.onRegister(null);
        super.onLoadComplete();
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