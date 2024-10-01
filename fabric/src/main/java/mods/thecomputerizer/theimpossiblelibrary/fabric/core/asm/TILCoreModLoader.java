package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import com.chocohead.mm.api.ClassTinkerers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

import java.util.Set;
import java.util.function.BiConsumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

@SuppressWarnings("unused")
public class TILCoreModLoader implements Runnable {
    
    String findMappedClass(String name) {
        MappingResolver mapper = FabricLoader.getInstance().getMappingResolver();
        return mapper.mapClassName("intermediary",name);
    }
    
    void log(BiConsumer<String,Object[]> logger, String msg, Object ... args) {
        logger.accept("[Multiversion Transformer (Fabric)]: "+msg,args);
    }
    
    @Override public void run() {
        Set<CoreEntryPoint> entryPoints = CoreAPI.getInstance().getCoreInstances();
        log(TILRef::logInfo,"Initializing {} coremod(s)",entryPoints.size());
        for(CoreEntryPoint entryPoint : entryPoints) {
            String name = entryPoint.getCoreName();
            log(TILRef::logInfo,"Finding targets for {}",name);
            for(String target : entryPoint.classTargets()) {
                String mapped = DEV ? target : findMappedClass(target);
                log(TILRef::logInfo,"[{}]: Adding class target {} (mapped {})",name,target,mapped);
                ClassTinkerers.addTransformation(mapped,entryPoint::editClass);
            }
        }
    }
}