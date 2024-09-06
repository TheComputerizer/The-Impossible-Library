package mods.thecomputerizer.theimpossiblelibrary.fabric.core.loader;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.ModContainerImpl;

import java.util.Collection;
import java.util.Objects;

import static net.fabricmc.loader.impl.FabricLoaderImpl.INSTANCE;

@Setter
public class TILModInjectorFabric implements PreLaunchEntrypoint {
    
    private Collection<ModContainerImpl> containers;
    
    @Override public void onPreLaunch() {
        if(Objects.nonNull(this.containers)) {
            INSTANCE.getModsInternal().addAll(this.containers);
            TILRef.logInfo("Injected {} mod containers",this.containers.size());
        } else TILRef.logInfo("No queued mod containers were found");
    }
}