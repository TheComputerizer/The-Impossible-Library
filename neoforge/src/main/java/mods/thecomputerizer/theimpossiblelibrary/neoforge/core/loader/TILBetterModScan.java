package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
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
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.LOGGER;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Setter @Getter
public class TILBetterModScan extends ModFileScanData {
    
    private static final Map<IModInfo,String> MOD_CLASSES = new HashMap<>();
    private static final Map<String,IModFile> MOD_FILES = new HashMap<>();
    private static final Map<String,MultiVersionModInfo> MOD_INFOS = new HashMap<>();
    private static final Set<String> NUKED_PACKAGES = new HashSet<>();
    private static final Set<Path> PATHS = new HashSet<>();
    private static final Map<String,byte[]> WRITTEN_CLASSES = new HashMap<>();
    
    private CoreAPI core;
    
    public void addFilePath(Path path) {
        PATHS.add(path);
        TILRef.logInfo("Adding file path to scan (total paths = {})",PATHS);
    }
    
    public void setModClass(IModInfo mod, String className) {
        MOD_CLASSES.put(mod,className);
    }
    
    public void addWrittenClass(String className, MultiVersionModInfo info, IModFile file, byte[] bytecode) {
        MOD_INFOS.put(className,info);
        WRITTEN_CLASSES.put(className,bytecode);
        MOD_FILES.put(className.substring(0,className.lastIndexOf('.')),file);
    }
    
    /**
     * Called via reflection from TILLanguageLoader
     */
    @IndirectCallers
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
                    TILRef.logInfo("Successfully defined {} in {}",clazz,clazz.getModule());
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
        String doLast = getLastPkg(pkgs);
        try {
            Set<String> finalizedPkgs = new HashSet<>();
            for(String pkg : pkgs) finalizeModPackage(pkg,pkgToModMap.get(pkg),finalizedPkgs);
            finalizeModPackage(doLast,pkgToModMap.get(doLast),finalizedPkgs);
            for(Class<?> c : defined)
                NeoForgeCoreLoader.sanityCheckModule(c,MOD_INFOS.get(c.getName()).getModID());
            NeoForgeCoreLoader.exportAllModules();
            LOGGER.info("Theoretically fixed all the modules");
        } catch(Throwable t) {
            TILRef.logError("Failed to finalize packages for Java 9+ {}",pkgs,t);
        }
        NUKED_PACKAGES.addAll(pkgs);
    }
    
    private void finalizeModPackage(String pkg, IModInfo mod, Set<String> finalizedPkgs) {
        if(NUKED_PACKAGES.contains(pkg)) {
            TILRef.logInfo("Skipping already handled sources for {}",pkg);
            return;
        }
        NeoForgeCoreLoader.nukeAndFinalize(mod,pkg,finalizedPkgs);
    }
    
    /**
     * If the given collection of packages contains a package from this library, it needs to be handled last.
     * The package is removed from the collection if found.
     */
    protected String getLastPkg(Collection<String> pkgs) {
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
    
    public String getModClass(IModInfo info) {
        return MOD_CLASSES.get(info);
    }
    
    protected IModInfo getModFromFile(IModFile file, String modid) {
        for(IModInfo info : file.getModInfos())
            if(modid.equals(info.getModId())) return info;
        return null;
    }
}