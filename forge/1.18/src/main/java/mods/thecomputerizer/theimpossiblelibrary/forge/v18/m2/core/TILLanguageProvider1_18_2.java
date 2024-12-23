package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeLanguageProvider;
import mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.loader.TILLanguageLoader1_18_2;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider.MODANNOTATION;

@IndirectCallers
public class TILLanguageProvider1_18_2 implements TILForgeLanguageProvider {
    
    @Override public Consumer<ModFileScanData> getFileVisitor(IModLanguageProvider provider) {
        return scan -> scan.addLanguageLoader(scan.getAnnotations().stream()
                        .filter(ad -> ad.annotationType().equals(MODANNOTATION))
                        .peek(ad -> TILRef.logDebug("Found @Mod class {} with id {}", ad.clazz().getClassName(), ad.annotationData().get("value")))
                        .map(ad -> new TILLanguageLoader1_18_2(ad.clazz().getClassName(), (String)ad.annotationData().get("value"), scan))
                        .collect(Collectors.toMap(TILLanguageLoader1_18_2::getModid,Function.identity(),(a,b)->a)));
    }
}
