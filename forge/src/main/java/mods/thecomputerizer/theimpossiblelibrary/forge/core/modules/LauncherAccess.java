package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

import java.util.Objects;

import static cpw.mods.modlauncher.Launcher.INSTANCE;

/**
 * cpw.mods.modlauncher.Launcher
 */
public class LauncherAccess extends AbstractModuleSystemAccessor {
    
    LauncherAccess(Object accessorOrLogger) {
        super(INSTANCE,accessorOrLogger);
    }
    
    public ArgumentHandlerAccess argumentHandler() {
        ArgumentHandler handler = getDirect("argumentHandler");
        return Objects.nonNull(handler) ? new ArgumentHandlerAccess(handler,this) : null;
    }
    
    public EnvironmentAccess environment() {
        return ForgeModuleAccess.getEnvironment(((Launcher)this.access).environment(),this);
    }
}
