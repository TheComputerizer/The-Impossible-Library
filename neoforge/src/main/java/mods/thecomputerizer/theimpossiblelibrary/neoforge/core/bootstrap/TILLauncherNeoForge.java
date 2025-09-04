package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncher;
import org.objectweb.asm.Type;

import java.util.EnumSet;

/**
 * ILaunchHandlerService and ILaunchPluginService both load in the BOOT layer with ILaunchPluginService loading after
 * and serving as a decorator for ILaunchHandlerService.
 * We need to be loading in the BOOT layer for coremods to work and ILaunchPluginService is more version-agnostic.
 * Note that both this and the Forge plugin will be loaded hence the validation checks being required.
 */
public class TILLauncherNeoForge extends TILLauncher implements ILaunchPluginService {
    
    @IndirectCallers
    public TILLauncherNeoForge() {
        super("neoforge",fmlLoader("neoforged"));
    }
    
    @Override public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return none(Phase.class);
    }
}