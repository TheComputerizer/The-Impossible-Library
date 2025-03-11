package mods.thecomputerizer.theimpossiblelibrary.fabric.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILCommonEntryPointFabric extends DelegatingCommonEntryPoint {
    
    private static TILCommonEntryPointFabric INSTANCE;
    
    public static TILCommonEntryPointFabric getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric();
    }
    
    protected TILCommonEntryPointFabric() {
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        super.onLoadComplete();
    }
}