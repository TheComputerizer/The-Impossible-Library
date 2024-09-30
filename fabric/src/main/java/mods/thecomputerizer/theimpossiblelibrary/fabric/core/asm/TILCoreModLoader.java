package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import com.chocohead.mm.api.ClassTinkerers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.Set;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class TILCoreModLoader implements Runnable {
    
    void log(BiConsumer<String,Object[]> logger, String msg, Object ... args) {
        logger.accept("[Multiversion Transformer (Fabric)]: "+msg,args);
    }
    
    @Override public void run() {
        Set<CoreEntryPoint> entryPoints = CoreAPI.getInstance().getCoreInstances();
        log(TILRef::logInfo,"Initializing {} coremod(s)",entryPoints.size());
        for(CoreEntryPoint entryPoint : entryPoints) {
            String name = entryPoint.getCoreName();
            log(TILRef::logDebug,"Finding targets for {}",name);
            for(String target : entryPoint.classTargets()) {
                log(TILDev::logDebug,"[{}]: Adding class target {}",name,target);
                ClassTinkerers.addTransformation(target,entryPoint::editClass);
            }
        }
    }
}