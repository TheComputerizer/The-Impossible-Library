package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.loader;

import cpw.mods.jarhandling.SecureJar;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILBetterModScan;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILFileConfigNeoForge;
import net.neoforged.coremod.CoreModScriptingEngine;
import net.neoforged.fml.loading.moddiscovery.CoreModFile;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
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
import net.neoforged.neoforgespi.locating.IModFileReader;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassReader;

import java.io.File;
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
import static net.neoforged.neoforgespi.locating.IModFile.Type.MOD;
import static net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes.DEFAULT;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@SuppressWarnings("UnstableApiUsage")
public class TILModFileNeoForge1_21 extends ModFile {
    
    static boolean fixedCoreMods;
    
    static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        IConfigurable config = new TILFileConfigNeoForge(infos,"multiversionloader");
        return new ModFileInfo((ModFile)file,config,info -> {},Collections.emptyList());
    }
    
    static SecureJar jarFor(MultiVersionModCandidate candidate) {
        return SecureJar.from(candidate.getFile().toPath());
    }
    
    private final MultiVersionModCandidate candidate;
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    
    public TILModFileNeoForge1_21(IModFileCandidateLocator locator, MultiVersionModCandidate candidate,
            Collection<?> infos) {
        this(DEFAULT.withLocator(locator),candidate,infos);
    }
    
    public TILModFileNeoForge1_21(IModFileReader reader, MultiVersionModCandidate candidate, Collection<?> infos) {
        this(DEFAULT.withReader(reader),candidate,infos);
    }
    
    public TILModFileNeoForge1_21(ModFileDiscoveryAttributes attributes, MultiVersionModCandidate candidate,
            Collection<?> infos) {
        super(jarFor(candidate),mod -> getFileInfo(mod,infos),MOD,attributes);
        this.candidate = candidate;
        this.infos = new HashMap<>();
        for(Object info : infos) this.infos.put((MultiVersionModInfo)info,null);
        TILRef.logInfo("Created TILModFileNeoForge1_21 with {} in context {}",infos,Thread.currentThread().getContextClassLoader());
    }
    
    @Override public ModFileScanData compileContent() {
        populateMultiversionData();
        TILRef.logInfo("Starting multiversion mod scan");
        TILBetterModScan scan = new TILBetterModScan();
        scan.setCore(this.candidate.getCore());
        scan.addModFileInfo(getModFileInfo());
        scanFile(p -> scanReflectively(new Scanner(this),p,scan)); //Collects the jar paths
        TILRef.logDebug("Injecting @Mod annotations from multiversion mod info");
        Set<AnnotationData> annotations = scan.getAnnotations();
        if(Objects.nonNull(annotations)) {
            TILRef.logTrace("Annotation data is present");
            for(Entry<MultiVersionModInfo,MultiVersionModData> entry : this.infos.entrySet()) {
                MultiVersionModInfo info = entry.getKey();
                MultiVersionModData data = entry.getValue();
                if(Objects.isNull(data)) {
                    TILRef.logWarn("Skipping mod injection for {} since no data exists",info.getModID());
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
                IModInfo mod = null;
                for(IModInfo potentialMod : getModInfos()) {
                    if(potentialMod.getModId().equals(info.getModID())) {
                        mod = potentialMod;
                        break;
                    }
                }
                if(Objects.nonNull(mod)) scan.setModClass(mod,info.getModClasspath());
                else TILRef.logError("Failed to set mod class for scan of {}!",info.getModID());
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
                fixCoreModPackages("api","neoforge","neoforge.v21","neoforge.v20.m6","neoforge.v21.m1");
                fixedCoreMods = true;
            }
        }
        return ret;
    }
    
    public void populateMultiversionData() {
        CoreAPI core = this.candidate.getCore();
        File file = this.candidate.getFile();
        for(MultiVersionModInfo info : this.infos.keySet()) {
            MultiVersionModData data = core.getModData(file,this.candidate,info);
            if(Objects.nonNull(data)) {
                this.infos.put(info,data);
                TILRef.logInfo("Populated data for {}",info);
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
}