package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILNeoForgeLanguageProvider;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader.TILLanguageLoader1_20;
import net.neoforged.neoforgespi.language.IModLanguageProvider;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@IndirectCallers
public class TILLanguageProvider1_20_4 implements TILNeoForgeLanguageProvider<IModLanguageProvider> {
    
    @Override public Consumer<ModFileScanData> getFileVisitor(CoreAPI core, IModLanguageProvider provider) {
        return scan -> {
            final String className = "net.neoforged.fml.javafmlmod.FMLJavaModLanguageProvider";
            final ClassLoader pluginLoader = NeoForgeCoreLoader.layerClassLoader("PLUGIN");
            Hacks.checkBurningWaveInit();
            Type modAnnotation = Hacks.getFieldStatic(Hacks.findClass(className,pluginLoader),"MODANNOTATION");
            scan.addLanguageLoader(scan.getAnnotations().stream()
                        .filter(ad -> ad.annotationType().equals(modAnnotation))
                        .peek(ad -> TILRef.logDebug("Found @Mod class {} with id {}",ad.clazz().getClassName(),ad.annotationData().get("value")))
                        .map(ad -> new TILLanguageLoader1_20(core,ad.clazz().getClassName(),(String)ad.annotationData().get("value"),scan))
                        .collect(Collectors.toMap(TILLanguageLoader1_20::getModid,Function.identity(),(a,b)->a)));
        };
    }
}