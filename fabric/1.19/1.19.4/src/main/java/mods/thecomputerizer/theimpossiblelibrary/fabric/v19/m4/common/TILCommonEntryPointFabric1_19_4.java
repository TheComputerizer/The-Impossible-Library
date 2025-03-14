package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.TILCommonEntryPointFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab.CreativeTabBuilder1_19_4;

import java.util.Objects;

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
}