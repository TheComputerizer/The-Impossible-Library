package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeLanguageProvider;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader.TILLanguageLoader1_16_5;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@IndirectCallers
public class TILLanguageProvider1_16_5 implements TILForgeLanguageProvider { //TODO This doesn't need to be version specific
    
    @Override public Consumer<ModFileScanData> getFileVisitor(CoreAPI core, IModLanguageProvider provider) {
        return scan -> {
            Type modAnnotation = Type.getType("Lnet/minecraftforge/fml/common/Mod;");
            scan.addLanguageLoader(scan.getAnnotations().stream()
                        .filter(ad -> ad.getAnnotationType().equals(modAnnotation))
                        .peek(ad -> TILRef.logDebug("Found @Mod class {} with id {}", ad.getClassType().getClassName(),ad.getAnnotationData().get("value")))
                        .map(ad -> new TILLanguageLoader1_16_5(core,ad.getClassType().getClassName(),(String)ad.getAnnotationData().get("value"),scan))
                        .collect(Collectors.toMap(TILLanguageLoader1_16_5::getModid,Function.identity(),(a,b)->a)));
        };
    }
}
