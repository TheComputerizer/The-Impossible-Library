package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.core.loader;

import com.electronwill.nightconfig.core.Config;
import cpw.mods.jarhandling.SecureJar;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILBetterModScan;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILFileConfigForge;
import net.minecraftforge.coremod.CoreModEngine;
import net.minecraftforge.fml.loading.moddiscovery.CoreModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minecraftforge.forgespi.locating.IModProvider;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

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
import static net.minecraftforge.forgespi.locating.IModFile.Type.LANGPROVIDER;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class TILModFileForge1_20_4 extends ModFile {
    
    static final String MOD_CLASS_VISITOR = "net.minecraftforge.fml.loading.moddiscovery.ModClassVisitor";
    static final String NIGHT_CONFIG_WRAPPER = "net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper";
    static final String SCANNER = "net.minecraftforge.fml.loading.moddiscovery.Scanner";
    static boolean fixedCoreMods;
    
    static IModFileInfo getFileInfo(IModFile file, Collection<?> infos) {
        IConfigurable config = new TILFileConfigForge(infos);
        return new ModFileInfo((ModFile)file,config,info -> {},Collections.emptyList());
    }
    
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    
    public TILModFileForge1_20_4(SecureJar file, IModLocator locator, Collection<?> infos) {
        super(file,locator,mod -> getFileInfo(mod,infos),"MOD");
        this.infos = new HashMap<>();
        for(Object info : infos) this.infos.put((MultiVersionModInfo)info,null);
        TILRef.logInfo("Created TILModFileForge1_20_4 with {} in context {}",infos,Thread.currentThread().getContextClassLoader());
    }
    
    @Override public ModFileScanData compileContent() {
        TILRef.logInfo("Starting multiversion mod scan");
        TILBetterModScan scan = new TILBetterModScan();
        scan.addModFileInfo(getModFileInfo());
        Class<?> cScanner = ClassHelper.findClass(SCANNER);
        Class<?> cModClassVisitor = ClassHelper.findClass(MOD_CLASS_VISITOR);
        ClassHelper.checkBurningWaveInit();
        scanFile(p -> scanReflectively(Constructors.newInstanceOf(cScanner,this),p,scan)); //Collects the jar paths
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
                    ClassVisitor visitor = Constructors.newInstanceOf(cModClassVisitor);
                    ClassReader reader = new ClassReader(bytes);
                    reader.accept(visitor,0);
                    Methods.invokeDirect(visitor,"buildData",scan.getClasses(),scan.getAnnotations());
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
    private void fixCoreModPackages(String ... extensions) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?> engineClass = ClassHelper.findClass(CoreModEngine.class.getName(),loader);
        Set<String> allowed = new HashSet<>(Fields.getStatic(engineClass,"ALLOWED_PACKAGES"));
        for(String extension : extensions) allowed.add(BASE_PACKAGE+"."+extension+".core");
        allowed.add(BASE_PACKAGE+".api.core");
        TILRef.logInfo("Allowed coremod packages have been expanded to {}",allowed);
        Fields.setStaticDirect(engineClass,"ALLOWED_PACKAGES",allowed);
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override public boolean identifyMods() {
        TILRef.logInfo("Identifying mods");
        boolean ret = super.identifyMods();
        if(ret) {
            List<CoreModFile> coreMods = getCoreMods();
            if(!coreMods.isEmpty() && !fixedCoreMods) {
                fixCoreModPackages("forge","forge.v20","forge.v20.m4");
                fixedCoreMods = true;
            }
        }
        TILRef.logInfo("Finished identifying mods");
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
    
    private void scanReflectively(Object scanner, Path path, ModFileScanData scan) {
        TILDev.logTrace("Attempting to scan multiversion jar path {}",path);
        try {
            Methods.invokeDirect(scanner,"fileVisitor",path,scan);
        } catch(Throwable ex) {
            TILRef.logError("Failed to scan {}!",path,ex);
        }
    }
    
    public static class TILLanguageProviderLoader extends ModFile {
        
        public static IModFileInfo getLangFileInfo(IModFile file) {
            TILRef.logInfo("Getting lang file info");
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
            IConfigurable wrapper = Constructors.newInstanceOf(ClassHelper.findClass(NIGHT_CONFIG_WRAPPER),config);
            TILRef.logInfo("Constructing lang file info");
            IModFileInfo fileInfo;
            try {
                fileInfo = new ModFileInfo((ModFile)file,wrapper,
                                           info -> Methods.invokeDirect(wrapper,"setFile",info),
                                           Collections.emptyList());
            } catch(Throwable t) {
                TILRef.logError("Failed to construct lang file info",t);
                throw t;
            }
            TILRef.logInfo("Returning lang file info");
            return fileInfo;
        }
        
        public TILLanguageProviderLoader(SecureJar file, IModProvider provider) {
            super(file,provider,TILLanguageProviderLoader::getLangFileInfo,"LANGPROVIDER");
            TILRef.logInfo("Successfully instantiated TILLanguageProviderLoader");
        }
        
        @Override public Type getType() {
            return LANGPROVIDER;
        }
    }
}