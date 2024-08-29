package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class TILBetterModScan extends ModFileScanData {
    
    private final Map<String,MultiVersionModInfo> modInfos;
    private final Map<String,byte[]> writtenClasses;
    
    public TILBetterModScan() {
        super();
        this.modInfos = new HashMap<>();
        this.writtenClasses = new HashMap<>();
    }
    
    public void addWrittenClass(String classpath, MultiVersionModInfo info, byte[] bytecode) {
        this.modInfos.put(classpath,info);
        this.writtenClasses.put(classpath,bytecode);
    }
    
    /**
     * Called via reflection from TILLanguageLoader
     */
    @SuppressWarnings("unused") public void defineClasses(ClassLoader ... loaders) {
        TILRef.logInfo("This is being called from {} and being synced to {}",getClass().getClassLoader(),loaders);
        loadSources(loaders);
        for(Entry<String,byte[]> entry : this.writtenClasses.entrySet()) {
            String classpath = entry.getKey();
            Class<?> entryClass = this.modInfos.get(classpath).getEntryClass();
            ClassLoader entryLoader = entryClass.getClassLoader();
            for(ClassLoader loader : loaders) {
                if(loader!=entryLoader) {
                    //Ensures that all extensions of CommonEntryPoint referenced by the written mod class
                    //will be loaded in the context of the mod class loader
                    ClassHelper.syncSourcesAndLoadClass(entryLoader,loader,entryClass.getName());
                }
                ClassHelper.resolveClass(loader,ClassHelper.defineClass(loader,classpath,entry.getValue()));
                TILRef.logDebug("Successfully defined and resolved class {} for {}",classpath,loader);
            }
        }
    }
    
    private void loadSources(ClassLoader ... loaders) {
        CoreAPI.addClassLoadingURLS(loaders);
        for(ClassLoader loader : loaders) {
            Class<?> clazz = ClassHelper.findClass(CoreAPI.findLoadingClass(),loader);
            if(Objects.nonNull(clazz)) {
                TILRef.logInfo("Successfully loaded CoreAPI instance {} to {}",clazz,clazz.getClassLoader());
                CoreAPI.setInstance(clazz);
                return;
            } else TILRef.logError("Failed to load CoreAPI class");
        }
    }
}