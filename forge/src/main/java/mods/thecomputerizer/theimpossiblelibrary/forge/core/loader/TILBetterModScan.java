package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class TILBetterModScan extends ModFileScanData {
    
    static boolean alreadyNuked;
    
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
    
    public void addWrittenClass(String className, MultiVersionModInfo info, byte[] bytecode) {
        this.modInfos.put(className,info);
        this.writtenClasses.put(className,bytecode);
    }
    
    /**
     * Called via reflection from TILLanguageLoader
     */
    @IndirectCallers
    public void defineClasses(ClassLoader target) {
        boolean java8 = ForgeCoreLoader.isJava8();
        String pkg = null;
        for(Entry<String,byte[]> entry : this.writtenClasses.entrySet()) {
            String className = entry.getKey();
            if(Objects.isNull(pkg)) pkg = className.substring(0,className.lastIndexOf('.'));
            Class<?> clazz = ClassHelper.resolveClass(target,ClassHelper.defineClass(target,className,entry.getValue()));
            if(Objects.nonNull(clazz))
                TILRef.logDebug("Successfully defined and resolved class {} for {}",className,target);
            else TILRef.logError("Class was defined as null?? {}",className);
        }
        if(!alreadyNuked) {
            if(java8) {
                Class<?> loaderClass = ClassHelper.findClass("net.minecraftforge.fml.ModLoader",target);
                fixBrokenMods(ReflectionHelper.invokeStaticMethod(loaderClass,"get",new Class<?>[]{}));
                alreadyNuked = true;
            } else if(Objects.nonNull(pkg)) {
                ForgeCoreLoader.nukeAndFinalize(pkg);
                alreadyNuked = true;
            }
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
}