package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.loader;

import com.electronwill.nightconfig.core.Config;
import cpw.mods.jarhandling.SecureJar;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILBetterModScan;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILFileConfigNeoForge;
import net.neoforged.coremod.CoreModScriptingEngine;
import net.neoforged.fml.loading.moddiscovery.CoreModFile;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.fml.loading.moddiscovery.NightConfigWrapper;
import net.neoforged.fml.loading.modscan.ModClassVisitor;
import net.neoforged.fml.loading.modscan.Scanner;
import net.neoforged.neoforgespi.language.IConfigurable;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassReader;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static net.neoforged.neoforgespi.locating.IModFile.Type.LIBRARY;
import static net.neoforged.neoforgespi.locating.IModFile.Type.MOD;
import static net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes.DEFAULT;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@SuppressWarnings("UnstableApiUsage")
public class TILModFileNeoForge1_21 extends ModFile {
    
    static boolean fixedCoreMods;
    
    static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        IConfigurable config = new TILFileConfigNeoForge(infos);
        return new ModFileInfo((ModFile)file,config,info -> {},Collections.emptyList());
    }
    
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    
    public TILModFileNeoForge1_21(SecureJar file, IModFileCandidateLocator locator, Collection<?> infos) {
        super(file,mod -> getFileInfo(mod,infos),MOD,DEFAULT.withLocator(locator));
        this.infos = new HashMap<>();
        for(Object info : infos) this.infos.put((MultiVersionModInfo)info,null);
        TILRef.logInfo("Created TILModFileForge1_20_4 with {} in context {}",infos,Thread.currentThread().getContextClassLoader());
    }
    
    //TODO IModLanguageLoader#loadMod requires an IModInfo instance so some load oredering will need to be reworked
    @Override public ModFileScanData compileContent() {
        TILRef.logInfo("Starting multiversion mod scan");
        TILBetterModScan scan = new TILBetterModScan();
        scan.addModFileInfo(getModFileInfo());
        scanFile(p -> scanReflectively(new Scanner(this),p,scan)); //Collects the jar paths
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
                    scan.addWrittenClass(classpath,data.getInfo(),this,bytes);
                    ModClassVisitor visitor = new ModClassVisitor();
                    ClassReader reader = new ClassReader(bytes);
                    reader.accept(visitor,0);
                    visitor.buildData(scan.getClasses(),scan.getAnnotations());
                    TILRef.logInfo("Successfully loaded & scanned mod class {}!",classpath);
                }
            }
        } else TILRef.logError("@Mod scan annotation set for multiversion mod is null?");
        List<IModLanguageLoader> loaders = getLoaders();
        if(loaders.isEmpty()) TILRef.logError("Why are there no language loaders??");
        else {
            TILRef.logDebug("Injecting scan data into language loader");
            //realLoader.loadMod();
            
        }
        TILRef.logInfo("Finishing multiversion mod scan",Thread.currentThread());
        scan.addFilePath(getFilePath());
        return scan;
    }
    
    /**
     * No easy way for generic core mods? Fine, I'll do it myself
     */
    private void fixCoreModPackages(String ... extensions) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        TILRef.logInfo("But the real ICoreModProvider loader is {}",loader);
        Class<?> engineClass = ClassHelper.findClass(CoreModScriptingEngine.class.getName(), loader);
        Set<String> allowed = new HashSet<>(Fields.getStatic(engineClass,"ALLOWED_PACKAGES"));
        for(String extension : extensions) allowed.add(BASE_PACKAGE+"."+extension+".core");
        TILDev.logDebug("Allowed coremod packages have been expanded to {}",allowed);
        Fields.setStaticDirect(engineClass,"ALLOWED_PACKAGES",allowed);
    }
    
    @Override public List<IModInfo> getModInfos() {
        return getModFileInfo().getMods();
    }
    
    @Override public boolean identifyMods() {
        boolean ret = super.identifyMods();
        if(ret) {
            List<CoreModFile> coreMods = getCoreMods();
            if(!coreMods.isEmpty() && !fixedCoreMods) {
                fixCoreModPackages("api","neoforge","neoforge.v21","neoforge.v21.m1");
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
    
    private void scanReflectively(Scanner scanner, Path path, ModFileScanData scan) {
        TILDev.logTrace("Attempting to scan multiversion jar path {}",path);
        try {
            Methods.invokeDirect(scanner,"fileVisitor",path,scan);
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
            mod.set("logoFile","logo.png");
            mod.set("authors","The_Computerizer");
            mod.set("description","Multiversion language loader for "+NAME);
            config.set("mods",Collections.singletonList(mod));
            IConfigurable wrapper = new NightConfigWrapper(config);
            return new ModFileInfo((ModFile)file,wrapper,info -> {},Collections.emptyList());
        }
        
        public TILLanguageProviderLoader(SecureJar file, IModFileCandidateLocator locator) {
            super(file,TILLanguageProviderLoader::getLangFileInfo,LIBRARY,DEFAULT.withLocator(locator));
        }
        
        @Override public Type getType() {
            return LIBRARY;
        }
    }
}