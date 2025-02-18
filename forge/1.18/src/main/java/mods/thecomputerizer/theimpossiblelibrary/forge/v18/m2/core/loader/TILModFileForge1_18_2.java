package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.loader;

import com.electronwill.nightconfig.core.Config;
import cpw.mods.jarhandling.SecureJar;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILBetterModScan;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILFileConfigForge;
import net.minecraftforge.coremod.CoreModEngine;
import net.minecraftforge.fml.loading.moddiscovery.CoreModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModClassVisitor;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper;
import net.minecraftforge.fml.loading.moddiscovery.Scanner;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassReader;

import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static net.minecraftforge.forgespi.locating.IModFile.Type.LANGPROVIDER;

public class TILModFileForge1_18_2 extends ModFile {
    
    static boolean fixedCoreMods;
    
    static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        IConfigurable config = new TILFileConfigForge(infos);
        return new ModFileInfo((ModFile)file,config,Collections.emptyList());
    }
    
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    
    public TILModFileForge1_18_2(SecureJar file, IModLocator locator, Collection<?> infos) {
        super(file,locator,mod -> getFileInfo(mod,infos),"MOD");
        this.infos = new HashMap<>();
        for(Object info : infos) this.infos.put((MultiVersionModInfo)info,null);
        TILRef.logInfo("Created TILModFileForge1_18_2 in context {}",Thread.currentThread().getContextClassLoader());
    }
    
    @Override public ModFileScanData compileContent() {
        TILRef.logDebug("Starting multiversion mod scan");
        TILBetterModScan scan = new TILBetterModScan();
        scan.addModFileInfo(getModFileInfo());
        final MethodHandle handle = ReflectionHelper.findMethodHandle(Scanner.class,"fileVisitor",Path.class,
                                                                      ModFileScanData.class);
        scanFile(p -> scanReflectively(new Scanner(this),p,scan,handle)); //Collects the jar paths
        TILRef.logDebug("Injecting @Mod annotations from multiversion mod info");
        Set<AnnotationData> annotations = scan.getAnnotations();
        if(Objects.nonNull(annotations)) {
            TILRef.logTrace("Annotation data is present");
            for(Entry<MultiVersionModInfo,MultiVersionModData> entry : this.infos.entrySet()) {
                MultiVersionModData data = entry.getValue();
                if(Objects.isNull(data)) {
                    TILRef.logWarn("Skipping mod injection for {} since no data exists",entry.getKey().getModID());
                    continue;
                }
                for(Pair<String,byte[]> classBytes : data.writeModClass()) {
                    String classpath = classBytes.getLeft();
                    byte[] bytes = classBytes.getRight();
                    scan.addWrittenClass(classpath,data.getInfo(),bytes);
                    ModClassVisitor visitor = new ModClassVisitor();
                    ClassReader reader = new ClassReader(bytes);
                    reader.accept(visitor,0);
                    visitor.buildData(scan.getClasses(),scan.getAnnotations());
                    TILRef.logInfo("Successfully loaded & scanned mod class {}!",classpath);
                }
            }
        } else TILRef.logError("@Mod scan annotation set for multiversion mod is null?");
        List<IModLanguageProvider> loaders = getLoaders();
        if(loaders.isEmpty()) TILRef.logError("Why are there no language loaders??");
        else {
            for(IModLanguageProvider loader : loaders) {
                TILRef.logDebug("Injecting scan data into the language loader");
                loader.getFileVisitor().accept(scan);
            }
        }
        TILRef.logInfo("Finishing multiversion mod scan",Thread.currentThread());
        scan.addFilePath(getFilePath());
        return scan;
    }
    
    /**
     * No easy way for generic core mods? Fine, I'll do it myself
     */
    @SuppressWarnings("unchecked")
    private void fixCoreModPackages(String ... extensions) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        TILRef.logInfo("But the real ICoreModProvider loader is {}",loader);
        Class<?> engineClass = ClassHelper.findClass(CoreModEngine.class.getName(),loader);
        Object allowed = ReflectionHelper.getFieldInstance(engineClass,"ALLOWED_PACKAGES");
        if(allowed instanceof Set<?>) fixCoreModPackages((Set<String>)allowed,extensions);
        else TILRef.logError("Failed to fix coremods (allowed packages = {})",allowed);
    }
    
    private void fixCoreModPackages(Set<String> allowed, String ... extensions) {
        for(String extension : extensions) allowed.add(BASE_PACKAGE+"."+extension+".core");
        TILDev.logDebug("Allowed coremod packages have been expanded to {}",allowed);
    }
    
    @Override public List<IModInfo> getModInfos() {
        return getModFileInfo().getMods();
    }
    
    @Override public boolean identifyMods() {
        boolean ret = super.identifyMods();
        if(ret) {
            List<CoreModFile> coreMods = getCoreMods();
            if(!coreMods.isEmpty() && !fixedCoreMods) {
                fixCoreModPackages("api","forge","legacy","forge.v18.m2");
                fixedCoreMods = true;
            }
        }
        return ret;
    }
    
    public void populateMultiversionData(Map<String,MultiVersionModData> dataMap) {
        for(MultiVersionModData data : dataMap.values()) {
            MultiVersionModInfo info = data.getInfo();
            if(this.infos.containsKey(info)) {
                TILRef.logDebug("Populated data for {}",info);
                this.infos.put(info,data);
            }
        }
    }
    
    private void scanReflectively(Scanner scanner, Path path, ModFileScanData scan, MethodHandle handle) {
        TILDev.logTrace("Attempting to scan multiversion jar path {}",path);
        try {
            handle.invoke(scanner,path,scan);
        } catch(Throwable ex) {
            TILRef.logError("Failed to scan {}!",path,ex);
        }
    }
    
    public static class TILLanguageProviderLoader extends ModFile {
        
        public static IModFileInfo getLangFileInfo(IModFile file) {
            Config config = Config.inMemory();
            config.set("modLoader","minecraft");
            config.set("loaderVersion","1");
            Config mod = Config.inMemory();
            mod.set("modId","multiversionprovider");
            mod.set("version",VERSION);
            mod.set("displayName","Multiversion Language Provider");
            mod.set("logoFile", "logo.png");
            mod.set("authors", "The_Computerizer");
            mod.set("description", "Multiversion language loader for "+NAME);
            config.set("mods",Collections.singletonList(mod));
            IConfigurable wrapper = new NightConfigWrapper(config);
            return new ModFileInfo((ModFile)file,wrapper,Collections.emptyList());
        }
        
        public TILLanguageProviderLoader(SecureJar file, IModLocator locator) {
            super(file,locator,TILLanguageProviderLoader::getLangFileInfo,"LANGPROVIDER");
        }
        
        @Override public Type getType() {
            return LANGPROVIDER;
        }
    }
}