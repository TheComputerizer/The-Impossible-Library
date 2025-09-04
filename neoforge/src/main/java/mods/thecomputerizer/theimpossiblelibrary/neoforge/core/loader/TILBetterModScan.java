package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.locating.IModFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Setter @Getter
public class TILBetterModScan extends ModFileScanData {
    
    private static final Map<String,IModFile> MOD_FILES = new HashMap<>(); //pkg -> file
    private static final Map<String,MultiVersionModInfo> MOD_INFOS = new HashMap<>(); //class name -> info
    private static final Set<Path> PATHS = new HashSet<>();
    private static final Map<String,byte[]> WRITTEN_CLASSES = new HashMap<>();
    
    private CoreAPI core;
    
    public void addFilePath(Path path) {
        PATHS.add(path);
        TILRef.logInfo("Adding file path to scan (total paths = {})",PATHS);
    }
    
    public void addWrittenClass(String className, MultiVersionModInfo info, IModFile file, byte[] bytecode) {
        MOD_INFOS.putIfAbsent(className,info);
        WRITTEN_CLASSES.put(className,bytecode);
        MOD_FILES.putIfAbsent(className.substring(0,className.lastIndexOf('.')),file);
    }
    
    public void defineClasses(ClassLoader target) {
        if(MOD_INFOS.isEmpty() || WRITTEN_CLASSES.isEmpty() ) {
            TILRef.logInfo("No classes left to define for TILBetterModScan");
            return;
        }
        for(String className : MOD_INFOS.keySet()) TILRef.logInfo(className);
        Set<String> pkgs = new HashSet<>();
        List<Class<?>> defined = new ArrayList<>();
        Set<Class<?>> outerClasses = new HashSet<>();
        Map<String,IModInfo> pkgToModMap = new HashMap<>();
        for(Entry<String,byte[]> entry : WRITTEN_CLASSES.entrySet()) {
            String className = entry.getKey();
            byte[] bytes = entry.getValue();
            try {
                Class<?> clazz = ClassHelper.resolveClass(target,ClassHelper.defineClass(target,className,bytes));
                if(Objects.nonNull(clazz)) {
                    defined.add(clazz);
                    String pkg = className.substring(0,className.lastIndexOf('.'));
                    if(!className.contains("$") && !pkgs.contains(pkg)) outerClasses.add(clazz);
                    pkgs.add(pkg);
                    pkgToModMap.putIfAbsent(pkg,getModFromFile(MOD_FILES.get(pkg),MOD_INFOS.get(className).getModID()));
                } else TILRef.logError("Class was defined as null?? {}",className);
            } catch(Throwable t) {
                throw new RuntimeException("Failed to define class "+className,t);
            }
        }
        WRITTEN_CLASSES.clear();
        if(pkgs.isEmpty()) {
            TILRef.logWarn("No classes were defined so no sources will be added");
            return;
        }
        try {
            NeoForgeCoreLoader.handleDevPackages(getLoaderPkg(pkgs),MODID);
        } catch(Throwable t) {
            TILRef.logError("Failed to finalize packages for Java 9+ {}",pkgs,t);
        }
    }
    
    /**
     * If the given collection of packages contains a package from this library, it needs to be specially handled
     */
    protected String getLoaderPkg(Collection<String> pkgs) {
        String last = null;
        for(String pkg : pkgs) {
            if(pkg.contains(BASE_PACKAGE)) {
                last = pkg;
                break;
            }
        }
        if(Objects.nonNull(last)) pkgs.remove(last);
        return last;
    }
    
    public String getModClass(String modid) {
        for(Entry<String,MultiVersionModInfo> classToInfoEntry : MOD_INFOS.entrySet())
            if(modid.equals(classToInfoEntry.getValue().getModID())) return classToInfoEntry.getKey();
        return null;
    }
    
    protected IModInfo getModFromFile(IModFile file, String modid) {
        for(IModInfo info : file.getModInfos())
            if(modid.equals(info.getModId())) return info;
        return null;
    }
}