package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.neoforged.neoforgespi.language.ILifecycleEvent;
import net.neoforged.neoforgespi.language.IModLanguageProvider;
import net.neoforged.neoforgespi.language.ModFileScanData;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.PLUGIN;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class TILLanguageProvider implements IModLanguageProvider {
    
    private static final String IMPL_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge.core";
    private static final String NEOFORGE_PKG = "net.neoforged.neoforgespi";
    private static final String MOD_PROVIDER_IMPL = IMPL_PKG+".TILLanguageProvider";
    private static final String MOD_PROVIDER_SERVICE = NEOFORGE_PKG+".language.IModLanguageProvider";
    
    static {
        NeoForgeCoreLoader.removeServiceFrom(MOD_PROVIDER_SERVICE,MOD_PROVIDER_IMPL,BOOT);
        try {
            ClassLoader plugin = TILLanguageProvider.class.getClassLoader();
            NeoForgeCoreLoader.resyncModules(plugin,PLUGIN,NeoForgeCoreLoader.bootLoader());
        } catch(Throwable t) {
            TILRef.logError("Failed to resync modules to BOOT layer",t);
        }
    }
    
    final Object core;
    final Object versionProvider;
    
    public TILLanguageProvider() {
        TILRef.logInfo("Initializing multiversion language provider (NeoForge edition)");
        ClassLoader pluginLoader = NeoForgeCoreLoader.layerClassLoader(PLUGIN);
        this.core = NeoForgeCoreLoader.initCoreAPI(pluginLoader);
        this.versionProvider = Objects.nonNull(this.core) ?
                Methods.invoke(this.core,"getLaunguageProvider") : null;
        if(Objects.nonNull(this.versionProvider))
            TILRef.logInfo("Successfully initialized versioned language provider on {}",this.versionProvider.getClass().getClassLoader());
        else TILRef.logError("Initialized versioned language provider as null");
    }
    
    @Override public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> ignored) {}
    
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