package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionCoreMod;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

/**
 * For internal use only
 */
@MultiVersionCoreMod(modid = MODID, modName = NAME, modVersion = VERSION)
public final class TILCoreEntryPoint extends CoreEntryPoint {
    
    private static TILCoreEntryPoint INSTANCE;
    
    private static void devTrace(String msg, Object ... args) {
        TILRef.logInfo("[TILCoreEntryPoint Trace]: "+msg, args);
    }
    
    public static TILCoreEntryPoint getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new TILCoreEntryPoint();
        return INSTANCE;
    }
    
    private final CoreEntryPoint versionHandler;
    
    public TILCoreEntryPoint() {
        devTrace("constructor");
        this.versionHandler = CoreAPI.getInstance().getCoreVersionHandler();
    }
    
    @Override public List<String> classTargets() {
        return Objects.nonNull(this.versionHandler) ? this.versionHandler.classTargets() : Collections.emptyList();
    }
    
    @Override public ClassNode editClass(ClassNode classNode) {
        TILRef.logInfo("editClass TIL thing");
        return Objects.nonNull(this.versionHandler) ? this.versionHandler.editClass(classNode) : classNode;
    }
    
    @Override public String getCoreID() {
        return MODID+"_core";
    }
    
    @Override public String getCoreName() {
        return NAME+" Core";
    }
}