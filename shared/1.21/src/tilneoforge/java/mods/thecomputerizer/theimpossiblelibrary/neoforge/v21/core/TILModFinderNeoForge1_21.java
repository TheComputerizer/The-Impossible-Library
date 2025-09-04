package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.jarhandling.JarContents;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFile.Type;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.LOADERID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.neoforged.neoforgespi.locating.IModFile.Type.MOD;

public abstract class TILModFinderNeoForge1_21 {
    
    static {
        NeoForgeCoreLoader.initCoreAPI(TILSelfLocator.class.getClassLoader());
        if(!NeoForgeModLoading.setLoadingVersion(TILModFinderNeoForge1_21.class))
            throw new RuntimeException("Failed to set mod loading version for MultiVersionModReader!");
    }
   
    private final CoreAPI core;
    private final MultiVersionLoaderAPI loader;
    protected final Logger logger;
    
    protected TILModFinderNeoForge1_21() {
        this.core = CoreAPI.getInstance();
        this.loader = Objects.nonNull(this.core) ? this.core.getLoader() : null;
        this.logger = initializeLogger(getClass());
    }
    
    protected @Nullable IModFile findAndLoad(JarContents contents, Supplier<Object> attributeSupplier) {
        IModFile[] files = findAndLoad(new JarContents[]{contents},attributeSupplier,MOD,false);
        return Objects.nonNull(files) && files.length>0 ? files[0] : null;
    }
    
    @SuppressWarnings("resource")
    protected @Nullable IModFile[] findAndLoadSelf(Path[] paths, Supplier<Object> attributeSupplier) {
        JarContents[] contents = new JarContents[]{NeoForgeModLoading.buildJarContents(MODID,paths),
                NeoForgeModLoading.buildJarContents(LOADERID,paths)};
        return findAndLoad(contents,attributeSupplier,null,true);
    }
    
    protected @Nullable IModFile[] findAndLoad(JarContents[] contents, Supplier<Object> attributeSupplier, Type type,
            boolean loader) {
        MultiVersionModCandidate candidate = findCandidate(contents[0]);
        Collection<?> infos = loadCandidate(candidate);
        if(Objects.isNull(candidate) || infos.isEmpty()) return null;
        if(loader)
            return NeoForgeModLoading.createLoaderFiles(contents,attributeSupplier.get(),candidate,infos);
        IModFile file = NeoForgeModLoading.createModFile(contents[0],attributeSupplier.get(),candidate,infos,type);
        return Objects.nonNull(file) ? new IModFile[]{file} : null;
    }
    
    private @Nullable MultiVersionModCandidate findAndMergeCandidates(File file,
            Function<File,Attributes> attributesGetter) {
        MultiVersionModCandidate coreCandidate =
                MultiVersionModFinder.discoverCoreCandidate(this.loader,file,attributesGetter);
        MultiVersionModCandidate modCandidate =
                MultiVersionModFinder.discoverModCandidate(this.loader, file, attributesGetter);
        return mergeCandidates(coreCandidate,modCandidate);
    }
    
    private @Nullable MultiVersionModCandidate findCandidate(JarContents jar) {
        final Manifest manifest = getManifest(jar);
        if(Objects.nonNull(manifest) && MultiVersionModFinder.hasMods(manifest.getMainAttributes())) {
            Path path = jar.getPrimaryPath();
            if(Objects.nonNull(this.loader)) {
                this.loader.addPotentialModPath(path);
                this.logger.info("[{}]: Found mod candidate at {}",loaderName(),path);
            }
            File file = path.toFile();
            return queryFile(file) ? null : findAndMergeCandidates(file,f -> manifest.getMainAttributes());
        }
        return null;
    }
    
    protected Manifest getManifest(JarContents jar) {
        return jar.getManifest();
    }
    
    Logger initializeLogger(Class<?> c) {
        Logger logger = TILRef.createLogger(c.getSimpleName());
        if(Objects.nonNull(this.loader)) logger.info("Successfully initialized {}",c);
        else logger.error("Failed to initialize {}",c);
        return logger;
    }
    
    private Collection<?> loadCandidate(@Nullable MultiVersionModCandidate candidate) {
        if(Objects.isNull(candidate) || Objects.isNull(this.core) || Objects.isNull(this.loader))
            return Collections.emptyList();
        return this.core.loadCandidate(candidate,this.loader,getClass().getClassLoader());
    }
    
    protected String loaderName() {
        return Objects.nonNull(this.loader) ? this.loader.getName() : null;
    }
    
    private @Nullable MultiVersionModCandidate mergeCandidates(@Nullable MultiVersionModCandidate candidate1,
            @Nullable MultiVersionModCandidate candidate2) {
        if(Objects.nonNull(candidate1)) {
            if(Objects.nonNull(candidate2)) candidate1.merge(candidate2);
            return candidate1;
        }
        return candidate2;
    }
    
    private boolean queryFile(File file) {
        return Objects.nonNull(this.loader) && queryFile(loaderName(),file);
    }
    
    protected boolean queryFile(String loaderName, File file) {
        queryLoaderFile(loaderName,file,file.getName());
        return false;
    }
    
    protected void queryLoaderFile(String loaderName, File file, String fileName) {
        this.logger.info("[{}]: Checking if file {} is the loader",loaderName,fileName);
        if(Objects.isNull(MultiVersionModCandidate.getLoaderFile()) && TILDev.isLoaderName(fileName)) {
            TILDev.logInfo("[{}]: File is the loader",loaderName);
            MultiVersionModCandidate.setLoaderFile(file);
        }
    }
    
    protected URL selfLocation() {
        Class<?> c = getClass();
        ProtectionDomain pd = getClass().getProtectionDomain();
        if(Objects.nonNull(pd)) {
            CodeSource source = pd.getCodeSource();
            if(Objects.nonNull(source)) {
                URL location = source.getLocation();
                this.logger.debug("Found {} at {}",c,location);
                return location;
            }
            else this.logger.error("CodeSource instance for {} did not exist!",c);
        } else this.logger.error("ProtectionDomain instance for {} did not exist!",c);
        return null;
    }
}