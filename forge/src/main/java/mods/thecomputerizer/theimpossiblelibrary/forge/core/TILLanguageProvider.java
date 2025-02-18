package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeLanguageProvider;
import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TILLanguageProvider implements IModLanguageProvider {
    
    static {
        TILDev.logError("Brooo what");
    }
    
    static CoreAPI findCoreAPI() {
        Object instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) instance = ForgeCoreLoader.initCoreAPI(CoreAPI.class.getClassLoader());
        TILDev.logInfo("Found CoreAPI? {}",instance);
        return (CoreAPI)instance;
    }
    
    final TILForgeLanguageProvider versionProvider;
    
    public TILLanguageProvider() {
        this.versionProvider = findCoreAPI().getLaunguageProvider();
        if(Objects.nonNull(this.versionProvider))
            TILRef.logInfo("Successfully initialized versioned language provider on {}",this.versionProvider.getClass().getClassLoader());
        else TILRef.logError("Initialized versioned language provider as null");
    }
    
    @Override public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> consumeEvent) {}
    
    @Override public Consumer<ModFileScanData> getFileVisitor() {
        if(Objects.nonNull(versionProvider)) return versionProvider.getFileVisitor(this);
        TILRef.logError("Version specific language provider not found! Did it fail to load?");
        return scan -> {};
    }
    
    @Override public String name() {
        return "multiversionprovider";
    }
}