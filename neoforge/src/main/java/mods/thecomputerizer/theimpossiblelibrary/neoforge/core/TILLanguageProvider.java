package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILLanguageLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader.TILLanguageLoader1_20;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforgespi.language.IModLanguageProvider;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.PLUGIN;

public class TILLanguageProvider implements IModLanguageProvider {
    
    static final Logger LOGGER = TILRef.createLogger("TIL Language Provider (Neoforge)");
    
    final CoreAPI core;
    
    public TILLanguageProvider() {
        TILRef.logInfo("Initializing multiversion language provider (NeoForge edition)");
        ClassLoader pluginLoader = NeoForgeCoreLoader.layerClassLoader(PLUGIN);
        this.core = NeoForgeCoreLoader.initCoreAPI(pluginLoader);
        LOGGER.info("Retrieved CoreAPI instance {} for multiversion language provider",this.core);
    }
    
    @Override public Consumer<ModFileScanData> getFileVisitor() {
        final Function<TILLanguageLoader1_20,String> keyMapper = TILLanguageLoader::getModid;
        final Function<TILLanguageLoader1_20,TILLanguageLoader1_20> valueMapper = Function.identity();
        final BinaryOperator<TILLanguageLoader1_20> merger = (a,b) -> a;
        final Function<ModFileScanData,Map<String,TILLanguageLoader1_20>> loaderMappper = scan ->
                scan.getAnnotations().stream()
                        .filter(ad -> ad.annotationType().equals(Type.getType(Mod.class)))
                        .map(ad -> {
                            String className = ad.clazz().getClassName();
                            String value = String.valueOf(ad.annotationData().get("value"));
                            TILRef.logInfo("Found @Mod class {} with id {}",className,value);
                            return new TILLanguageLoader1_20(this.core,className,value,scan);
                        }).collect(Collectors.toMap(keyMapper,valueMapper,merger));
        return scan -> scan.addLanguageLoader(loaderMappper.apply(scan));
    }
    
    @Override public String name() {
        return "multiversionprovider";
    }
}