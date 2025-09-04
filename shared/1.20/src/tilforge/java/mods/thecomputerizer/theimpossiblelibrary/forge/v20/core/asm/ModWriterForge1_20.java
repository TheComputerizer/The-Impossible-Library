package mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.asm.ModWriterForge;
import org.objectweb.asm.Type;

public class ModWriterForge1_20 extends ModWriterForge {
    
    public ModWriterForge1_20(CoreAPI core, MultiVersionModInfo info) {
        super(core,info);
    }
    
    protected Type getOptionalContructorType() {
        return JAVA_LOADING_CONTEXT;
    }
}