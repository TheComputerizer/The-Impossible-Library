package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeLanguageProvider;
import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TILLanguageProvider implements IModLanguageProvider {
    
    final TILForgeLanguageProvider versionProvider;
    
    public TILLanguageProvider() {
        this.versionProvider = CoreAPI.getInstance().getLaunguageProvider();
        TILRef.logInfo("Successfully instantiated multiversionprovider on {}",getClass().getClassLoader());
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