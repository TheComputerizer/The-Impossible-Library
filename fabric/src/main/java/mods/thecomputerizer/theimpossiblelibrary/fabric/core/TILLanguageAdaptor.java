package mods.thecomputerizer.theimpossiblelibrary.fabric.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.LanguageAdapterException;
import net.fabricmc.loader.api.ModContainer;

public class TILLanguageAdaptor implements LanguageAdapter {
    
    static {
        TILRef.logInfo("Loaded multiversionAdaptor");
    }
    
    public TILLanguageAdaptor() {
        TILRef.logInfo("Instantiated multiversionAdaptor");
    }
    
    //@Override public Consumer<ModFileScanData> getFileVisitor() {
    //    return scan -> scan.addLanguageLoader(scan.getAnnotations().stream()
    //            .filter(ad -> ad.getAnnotationType().equals(MODANNOTATION))
    //            .peek(ad -> TILRef.logDebug("Found @Mod class {} with id {}",ad.getClassType().getClassName(),ad.getAnnotationData().get("value")))
    //            .map(ad -> new TILLanguageLoader(ad.getClassType().getClassName(),(String)ad.getAnnotationData().get("value"),scan))
    //            .collect(Collectors.toMap(TILLanguageLoader::getModid,Function.identity(),(a,b)->a)));
    //}
    
    @Override public <T> T create(ModContainer mod, String value, Class<T> type) throws LanguageAdapterException {
        return null;
    }
}
