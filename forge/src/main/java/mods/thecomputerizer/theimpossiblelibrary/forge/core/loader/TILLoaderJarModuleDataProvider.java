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
import java.util.function.Function;
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
    
    static Optional<URI> findFile(FileSystem fs, String name, Function<FileSystem,Path> rootGetter) {
        Path root = rootGetter.apply(fs);
        if(Objects.isNull(root)) return Optional.empty();
        return Optional.of(root.resolve(fs.getPath(name))).filter(Files::exists).map(Path::toUri);
    }
    
    public static TILLoaderJarModuleDataProvider get(Object jar, Object metadata, Manifest manifest,
            boolean useProxy) {
        final URI uri = computeLoaderURI();
        return Objects.nonNull(uri) ? new TILLoaderJarModuleDataProvider(jar,metadata,manifest,uri,useProxy) : null;
    }
    
    final Function<FileSystem,Path> rootGetter;
    final Object providerProxy;
    final FileSystem ufs;
    final URI uri;
    final Manifest manifest;
    
    TILLoaderJarModuleDataProvider(final Object jar, final Object metadata, final Manifest manifest, final URI uri,
            final boolean useProxy) {
        this.rootGetter = ForgeModLoading.isSecureLoadingFormat() ?
                fs -> fs.getRootDirectories().iterator().next() :
                fs -> Hacks.invoke(fs,"getRoot");
        this.uri = uri;
        this.ufs = Paths.get(uri).getFileSystem();
        this.manifest = manifest;
        this.providerProxy = useProxy ? createProxy(getClass().getClassLoader(),jar,metadata,uri) : null;
    }
    
    Object createProxy(ClassLoader loader, final Object jar, final Object metadata, final URI uri) {
        return ClassHelper.newGenericProxy(loader,MODULE_DATA_PROVIDER,(methodName,args) -> {
            switch(methodName) {
                case "descriptor": return Hacks.invoke(metadata,"descriptor");
                case "findFile": return findFile(this.ufs,(String)args[0],this.rootGetter);
                case "getManifest": return this.manifest;
                case "name": return Hacks.invoke(metadata,"name");
                case "open": return findFile(this.ufs,(String)args[0],this.rootGetter).map(Paths::get)
                        .map(LamdbaExceptionUtils.rethrowFunction(Files::newInputStream));
                case "uri": return uri;
                case "verifyAndGetSigners": return Hacks.invoke(jar,"verifyAndGetSigners",args[0],args[1]);
                default: return null;
            }
        });
    }
    
    Optional<URI> findFile(final String name) {
        return findFile(this.ufs,name,this.rootGetter);
    }
}