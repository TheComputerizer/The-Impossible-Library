package mods.thecomputerizer.theimpossiblelibrary.fabric.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import net.minecraft.core.Registry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public abstract class TILCommonEntryPointFabric extends DelegatingCommonEntryPoint {
    
    protected TILCommonEntryPointFabric() {}
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onCommonSetup() { //Stupid casting shenanigans
        //REGISTER_BLOCKS.invoker().register(registryBlock());
        //REGISTER_BLOCK_ENTITIES.invoker().register(registryBlockEntity());
        //REGISTER_ITEMS.invoker().register(registryItem());
        //REGISTER_ENTITIES.invoker().register(registryEntity());
        //REGISTER_SOUND_EVENTS.invoker().register(registrySoundEvent());
        super.onCommonSetup();
    }
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        super.onLoadComplete();
    }
    
    protected abstract Registry<?> registryBlock();
    protected abstract Registry<?> registryBlockEntity();
    protected abstract Registry<?> registryEntity();
    protected abstract Registry<?> registryItem();
    protected abstract Registry<?> registrySoundEvent();
}