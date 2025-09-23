package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ForgeModuleAccess;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;

public class TILLoaderJarModuleDataProvider {
    
    static final String MODULE_DATA_PROVIDER = "cpw.mods.jarhandling.SecureJar$ModuleDataProvider";
    
    private static URI computeLoaderURI() {
        ResolvedModuleAccess module = ForgeModuleAccess.findResolvedModuleIn(BOOT_ID,"BOOT");
        if(Objects.isNull(module)) TILRef.logError("Failed to find ResolvedModule {} in BOOT layer!",BOOT_ID);
        URI uri = Objects.nonNull(module) ? module.reference().location() : null;
        TILRef.logInfo("Called computeLoaderURI on TILLoaderJarModuleDataProvider and returning URI is {}",uri);
        return uri;
    }
    
    static Optional<URI> findFile(FileSystem ufs, Object name) {
        Path path = ufs.getPath((String)name);
        Path root = Hacks.invoke(ufs,"getRoot");
        if(Objects.isNull(root)) return Optional.empty();
        return Optional.of(root.resolve(path)).filter(Files::exists).map(Path::toUri);
    }
    
    public static TILLoaderJarModuleDataProvider get(Object jar, Object metadata, Manifest manifest) {
        final URI uri = computeLoaderURI();
        return Objects.nonNull(uri) ? new TILLoaderJarModuleDataProvider(jar,metadata,manifest,uri) : null;
    }
    
    final Object providerProxy;
    final FileSystem ufs;
    final URI uri;
    
    TILLoaderJarModuleDataProvider(final Object jar, final Object metadata, final Manifest manifest, final URI uri) {
        this.uri = uri;
        this.ufs = Paths.get(uri).getFileSystem();
        this.providerProxy = createProxy(getClass().getClassLoader(),jar,metadata,manifest,uri,this.ufs);
    }
    
    Object createProxy(ClassLoader loader, final Object jar, final Object metadata, final Manifest manifest,
            final URI uri, final FileSystem fs) {
        return ClassHelper.newGenericProxy(loader,MODULE_DATA_PROVIDER,(methodName,args) -> {
            switch(methodName) {
                case "descriptor": return Hacks.invoke(metadata,"descriptor");
                case "findFile": return findFile(fs,args[0]);
                case "getManifest": return manifest;
                case "name": return Hacks.invoke(metadata,"name");
                case "open": return findFile(fs,args[0]).map(Paths::get).map(LamdbaExceptionUtils.rethrowFunction(Files::newInputStream));
                case "uri": return uri;
                case "verifyAndGetSigners": return Hacks.invoke(jar,"verifyAndGetSigners",args[0],args[1]);
                default: return null;
            }
        });
    }
}