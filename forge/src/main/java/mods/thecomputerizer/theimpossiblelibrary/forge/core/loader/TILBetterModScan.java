package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class TILBetterModScan extends ModFileScanData {
    
    private final Map<String,MultiVersionModInfo> modInfos;
    private final Map<String,byte[]> writtenClasses;
    private final Set<Path> paths;
    
    public TILBetterModScan() {
        super();
        this.modInfos = new HashMap<>();
        this.writtenClasses = new HashMap<>();
        this.paths = new HashSet<>();
    }
    
    public void addFilePath(Path path) {
        this.paths.add(path);
    }
    
    public void addWrittenClass(String classpath, MultiVersionModInfo info, byte[] bytecode) {
        this.modInfos.put(classpath,info);
        this.writtenClasses.put(classpath,bytecode);
    }
    
    /**
     * Called via reflection from TILLanguageLoader
     */
    @IndirectCallers
    public void defineClasses(ClassLoader ... loaders) {
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
        for(ClassLoader loader : loaders) {
            if(loader==ClassLoader.getSystemClassLoader()) continue;
            Class<?> loaderClass = ClassHelper.findClass("net.minecraftforge.fml.ModLoader",loader);
            fixBrokenMods(ReflectionHelper.invokeStaticMethod(loaderClass,"get",new Class<?>[]{}));
            break;
        }
    }
    
    /**
     * Yeah, this is kinda necessary when trying to work with classes on the wrong class loader
     */
    public void fixBrokenMods(Object modLoader) {
        List<?> warnings = (List<?>)ReflectionHelper.getFieldInstance(modLoader,modLoader.getClass(),"loadingWarnings");
        if(Objects.isNull(warnings)) TILRef.logWarn("You win this round, Forge");
        else {
            warnings.removeIf(warning -> {
                Object msg = ReflectionHelper.invokeMethod(warning.getClass(),"formatToString",warning,new Class<?>[]{});
                String[] split = String.valueOf(msg).split(" ");
                if(split.length>1) {
                    for(Path path : this.paths) {
                        if(path.toString().endsWith(split[1])) {
                            TILRef.logWarn("{} is a perfectly valid mod file thanks",path);
                            return true;
                        }
                    }
                }
                return false;
            });
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