package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class TILLanguageProvider implements IModLanguageProvider {
    
    static {
        try {
            ClassLoader plugin = TILLanguageProvider.class.getClassLoader();
            ForgeCoreLoader.resyncModules(plugin,"PLUGIN",ForgeCoreLoader.bootLoader());
        } catch(Throwable t) {
            TILRef.logError("Failed to resync modules to BOOT layer",t);
        }
    }
    
    final Object core;
    final Object versionProvider;
    
    public TILLanguageProvider() {
        TILRef.logInfo("Initializing multiversion language provider (Forge edition)");
        ClassLoader pluginLoader = ForgeCoreLoader.layerClassLoader("PLUGIN");
        this.core = ForgeCoreLoader.initCoreAPI(pluginLoader);
        this.versionProvider = Objects.nonNull(this.core) ?
                Methods.invoke(this.core,"getLaunguageProvider") : null;
        if(Objects.nonNull(this.versionProvider))
            TILRef.logInfo("Successfully initialized versioned language provider as {} on {}",this.versionProvider,
                    this.versionProvider.getClass().getClassLoader());
        else TILRef.logError("Initialized versioned language provider as null");
    }
    
    @Override public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> ignored)  {}
    
    @Override public Consumer<ModFileScanData> getFileVisitor() {
        Consumer<ModFileScanData> visitor = scan -> {};
        if(Objects.nonNull(this.versionProvider))
            visitor = Methods.invoke(this.versionProvider,"getFileVisitor",this.core,this);
        else TILRef.logError("Version specific language provider not found! Did it fail to load?");
        return visitor;
    }
    
    @Override public String name() {
        return "multiversionprovider";
    }
}