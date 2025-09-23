package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.locating.IModFile;

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

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V21_1;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class TILBetterModScan extends ModFileScanData {
    
    private static final String MODLOADER = "net.minecraftforge.fml.ModLoader";
    private static final Set<String> NUKED_PACKAGES = new HashSet<>();
    private static final Map<String,IModFile> MOD_FILES = new HashMap<>();
    private static final Map<String,MultiVersionModInfo> MOD_INFOS = new HashMap<>();
    private static final Map<String,byte[]> WRITTEN_CLASSES = new HashMap<>();
    private static final Set<Path> PATHS = new HashSet<>();
    
    public void addFilePath(Path path) {
        PATHS.add(path);
        TILRef.logInfo("Adding file path to scan (total paths = {})",PATHS);
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
        boolean java8 = ForgeCoreLoader.isJava8();
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
        GameVersion version = CoreAPI.getInstance().getVersion();
        boolean newFormat = version==V20_4 || version==V20_6 || version==V21_1;
        WRITTEN_CLASSES.clear();
        if(pkgs.isEmpty()) {
            TILRef.logWarn("No classes were defined so no sources will be added");
            return;
        }
        if(newFormat) fixBrokenModsNew(ClassHelper.findClass(MODLOADER,target));
        if(java8) {
            try {
                fixBrokenMods(ClassHelper.findClass(MODLOADER,target));
                ForgeCoreLoader.nukeAndFinalizeJava8(sourceStack(outerClasses),target,NUKED_PACKAGES.isEmpty());
                NUKED_PACKAGES.addAll(pkgs);
            } catch(Throwable t) {
                TILRef.logError("Failed to finalize packages for Java 8 {}",pkgs,t);
            }
        } else {
            try {
                ForgeCoreLoader.addLibraryToGameLayer(getLoaderPkg(pkgs),MODID);
            } catch(Throwable t) {
                TILRef.logError("Failed to finalize packages for Java 9+ {}",pkgs,t);
            }
        }
    }
    
    public void fixBrokenMods(Class<?> loaderClass) {
        fixBrokenMods(loaderClass,"loadingWarnings");
    }
    
    /**
     * Yeah, this is kinda necessary when trying to work with classes on the wrong class loader
     */
    private void fixBrokenMods(Class<?> loaderClass, String fieldName) {
        List<?> warningsOrExceptions = Hacks.getField(Hacks.invokeStatic(loaderClass,"get"),fieldName);
        if(Objects.isNull(warningsOrExceptions)) TILRef.logWarn("You win this round, Forge");
        else {
            warningsOrExceptions.removeIf(warningOrException -> {
                String formatted = Hacks.invoke(warningOrException,"formatToString");
                String[] split = Objects.nonNull(formatted) ? formatted.split(" ") : new String[]{};
                if(split.length>1) {
                    for(Path path : PATHS) {
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
    
    public void fixBrokenModsNew(Class<?> loaderClass) {
        fixBrokenMods(loaderClass,"loadingExceptions");
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
    
    protected IModInfo getModFromFile(IModFile file, String modid) {
        for(IModInfo info : file.getModInfos())
            if(modid.equals(info.getModId())) return info;
        return null;
    }
    
    /**
     * The set of all classes used as reference when adding sources to the target ClassLoader.
     * Makes multi-project dev environments possible
     */
    protected Set<Class<?>> sourceStack(Collection<Class<?>> generated) {
        Set<Class<?>> sourcesFrom = new HashSet<>(generated);
        for(MultiVersionModInfo info : MOD_INFOS.values()) sourcesFrom.add(info.getEntryClass());
        return sourcesFrom;
    }
}