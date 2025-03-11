package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.DelegatingClientEntryPoint;

import java.io.File;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public final class TILClientEntryPoint1_12_2 extends DelegatingClientEntryPoint {
    
    private static TILClientEntryPoint1_12_2 INSTANCE;
    
    public static TILClientEntryPoint1_12_2 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPoint1_12_2();
    }
    
    private TILClientEntryPoint1_12_2() {
        Minecraft1_12_2.getInstance().addResourcePackFolder(new File("TILResources"));
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onClientSetup() {}
}
