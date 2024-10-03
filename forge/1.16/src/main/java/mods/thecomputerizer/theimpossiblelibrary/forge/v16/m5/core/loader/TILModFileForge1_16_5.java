package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILBetterModScan;
import net.minecraftforge.coremod.CoreModEngine;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LanguageLoadingProvider;
import net.minecraftforge.fml.loading.moddiscovery.CoreModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModClassVisitor;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.fml.loading.moddiscovery.ModFileParser;
import net.minecraftforge.fml.loading.moddiscovery.Scanner;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import net.minecraftforge.forgespi.locating.IModLocator;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassReader;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static net.minecraftforge.forgespi.locating.IModFile.Type.LANGPROVIDER;

public class TILModFileForge1_16_5 extends ModFile {
    
    static boolean fixedCoreMods;
    static boolean loadedProvider;
    
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    protected IModFileInfo fileInfo;
    protected IModLanguageProvider loader;
    protected Path accessTransformer;
    protected List<CoreModFile> coreMods;
    
    public TILModFileForge1_16_5(Path path, IModLocator locator, Collection<?> infos) {
        super(path,locator,null);
        this.infos = new HashMap<>();
        for(Object info : infos) this.infos.put((MultiVersionModInfo)info,null);
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
        IModLanguageProvider loader = getLoader();
        if(Objects.nonNull(loader)) {
            TILRef.logDebug("Injecting scan data into the language loader");
            loader.getFileVisitor().accept(scan);
        } else TILRef.logError("Why is the language loader missing??");
        TILRef.logInfo("Finishing multiversion mod scan",Thread.currentThread());
        scan.addFilePath(getFilePath());
        return scan;
    }
    
    @SuppressWarnings("unchecked")
    private List<CoreModFile> findCoreMods() {
        Object list = ReflectionHelper.invokeMethod(ModFileParser.class,"getCoreMods",null,new Class<?>[]{
                ModFile.class},this);
        return list instanceof List<?> ? (List<CoreModFile>)list : Collections.emptyList();
    }
    
    /**
     * No easy way for generic core mods? Fine, I'll do it myself
     */
    @SuppressWarnings("unchecked")
    private void fixCoreModPackages(String ... extensions) {
        Object allowed = ReflectionHelper.getFieldInstance(CoreModEngine.class,"ALLOWED_PACKAGES");
        if(allowed instanceof Set<?>) fixCoreModPackages((Set<String>)allowed,extensions);
        else TILRef.logError("Failed to fix coremods (allowed packages = {})",allowed);
    }
    
    private void fixCoreModPackages(Set<String> allowed, String ... extensions) {
        for(String extension : extensions) allowed.add(BASE_PACKAGE+"."+extension+".core");
        TILDev.logDebug("Allowed coremod packages have been expanded to {}",allowed);
    }
    
    @Override public Optional<Path> getAccessTransformer() {
        return Optional.ofNullable(Files.exists(this.accessTransformer) ? this.accessTransformer : null);
    }
    
    @Override public List<CoreModFile> getCoreMods() {
        if(Objects.isNull(this.coreMods)) {
            this.coreMods = findCoreMods();
            if(!this.coreMods.isEmpty() && !fixedCoreMods) {
                fixCoreModPackages("api","fabric","forge","legacy","fabric.v16.m5","forge.v16.m5");
                fixedCoreMods = true;
            }
        }
        TILRef.logInfo("Found coremods {}",this.coreMods);
        return this.coreMods;
    }
    
    @Override public IModLanguageProvider getLoader() {
        if(Objects.isNull(this.loader)) identifyLanguage();
        return this.loader;
    }
    
    @Override public List<IModInfo> getModInfos() {
        return getModFileInfo().getMods();
    }
    
    @Override public IModFileInfo getModFileInfo() {
        if(Objects.isNull(this.fileInfo)) {
            if(!loadedProvider) {
                TILRef.logWarn("Invalid? We'll see about that, Forge");
                TILRef.logInfo("Loading multiversion language provider");
                LanguageLoadingProvider provider =  FMLLoader.getLanguageLoadingProvider();
                provider.addAdditionalLanguages(Collections.singletonList(new TILLanguageProviderLoader(getFilePath(),getLocator())));
                loadedProvider = true;
            }
            TILRef.logInfo("Building IModFileInfo");
            Constructor<?> infoConstructor = ReflectionHelper.findConstructor(ModFileInfo.class,ModFile.class,IConfigurable.class);
            if(Objects.nonNull(infoConstructor)) {
                if(!infoConstructor.isAccessible()) infoConstructor.setAccessible(true);
                try {
                    this.fileInfo = (ModFileInfo)infoConstructor.newInstance(this,new TILFileConfigForge1_16_5(this.infos.keySet()));
                    TILRef.logDebug("Successfully captured multiversion mods for {}",getFileName());
                } catch(ReflectiveOperationException ex) {
                    TILRef.logFatal("Failed to capture mod file for {}! A crash may be imminent",getFileName(),ex);
                }
            } else TILRef.logFatal("Failed to find ModFileInfo constructor???");
        }
        return this.fileInfo;
    }
    
    @Override public void identifyLanguage() {
        IModFileInfo info = getModFileInfo();
        LanguageLoadingProvider provider =  FMLLoader.getLanguageLoadingProvider();
        this.loader = provider.findLanguage(this,info.getModLoader(),info.getModLoaderVersion());
    }
    
    @Override public boolean identifyMods() {
        TILRef.logInfo("Loading mod file {} with language {}",getFilePath(),getModFileInfo().getModLoader());
        //this.coreMods = ModFileParser.getCoreMods(this); //TODO Coremod support
        this.accessTransformer = getLocator().findPath(this,"META-INF","accesstransformer.cfg");
        return true;
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
        
        public TILLanguageProviderLoader(Path file, IModLocator locator) {
            super(file,locator,null);
        }
        
        @Override public Type getType() {
            return LANGPROVIDER;
        }
    }
}