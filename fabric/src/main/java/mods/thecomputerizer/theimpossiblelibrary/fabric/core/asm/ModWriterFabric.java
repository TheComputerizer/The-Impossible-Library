package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;

public abstract class ModWriterFabric extends ModWriter {
    
    protected ModWriterFabric(CoreAPI core, MultiVersionModInfo info, int javaVersion) {
        super(core,info,javaVersion);
    }
}