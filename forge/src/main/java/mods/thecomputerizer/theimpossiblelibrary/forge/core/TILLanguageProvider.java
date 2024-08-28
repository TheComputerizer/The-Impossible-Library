package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILLanguageLoader;
import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider.MODANNOTATION;

/**
 * TODO Might not be needed
 */
public class TILLanguageProvider implements IModLanguageProvider {
    
    static {
        TILRef.logInfo("Loaded multiversionprovider");
    }
    
    public TILLanguageProvider() {
        TILRef.logInfo("Instantiated multiversionprovider");
    }
    
    @Override public String name() {
        return "multiversionprovider";
    }
    
    @Override public Consumer<ModFileScanData> getFileVisitor() {
        return scan -> scan.addLanguageLoader(scan.getAnnotations().stream()
                .filter(ad -> ad.getAnnotationType().equals(MODANNOTATION))
                .peek(ad -> TILRef.logDebug("Found @Mod class {} with id {}",ad.getClassType().getClassName(),ad.getAnnotationData().get("value")))
                .map(ad -> new TILLanguageLoader(ad.getClassType().getClassName(),(String)ad.getAnnotationData().get("value"),scan))
                .collect(Collectors.toMap(TILLanguageLoader::getModid,Function.identity(),(a,b)->a)));
    }
    
    @Override public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> consumeEvent) {
    
    }
}
