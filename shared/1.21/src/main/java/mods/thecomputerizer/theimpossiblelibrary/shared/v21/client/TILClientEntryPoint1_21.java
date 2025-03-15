package mods.thecomputerizer.theimpossiblelibrary.shared.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import org.jetbrains.annotations.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILClientEntryPoint1_21 extends ClientEntryPoint {
    
    @Override public @Nullable ClientEntryPoint setDelegatedClientHandle() {
        return null;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onClientSetup() {}
}