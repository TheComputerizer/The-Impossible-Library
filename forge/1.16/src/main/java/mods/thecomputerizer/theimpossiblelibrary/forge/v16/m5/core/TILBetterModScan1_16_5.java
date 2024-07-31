package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider.IModLanguageLoader;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.annotation.ElementType.TYPE;
import static net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider.MODANNOTATION;

public class TILBetterModScan1_16_5 extends ModFileScanData {
    
    private final Collection<MultiVersionModInfo> infos;
    private Map<String,? extends IModLanguageLoader> targets;
    
    public TILBetterModScan1_16_5(Collection<MultiVersionModInfo> infos) {
        super();
        this.infos = infos;
        for(MultiVersionModInfo info : this.infos) {
            Class<?> modClass = ClassHelper.findClass(info.getModClasspath());
            if(Objects.nonNull(modClass)) {
                Map<String,Object> data = new HashMap<>();
                data.put("value",info.getModID());
                getAnnotations().add(new AnnotationData(MODANNOTATION,TYPE,Type.getType(modClass),"",data));
            }
        }
    }
}
