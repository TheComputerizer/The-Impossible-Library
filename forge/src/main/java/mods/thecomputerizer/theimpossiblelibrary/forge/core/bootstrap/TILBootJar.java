package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class TILBootJar {
    
    static final String MODULE_DATA_PROVIDER = "cpw.mods.jarhandling.SecureJar$ModuleDataProvider";
    
    static Optional<URI> findFile(FileSystem ufs, Object name) {
        Path path = ufs.getPath((String)name);
        Path root = Hacks.invoke(ufs,"getRoot");
        if(Objects.isNull(root)) return Optional.empty();
        return Optional.of(root.resolve(path)).filter(Files::exists).map(Path::toUri);
    }
    
    @IndirectCallers
    static void updateReference(Logger logger, Object reference, URI uri) {
        Object provider = new TILBootJar(Hacks.getFieldDirect(reference,"jar"),uri).providerProxy;
        if(Objects.nonNull(provider)) {
            Hacks.setFieldDirect(reference,"jar",provider);
            logger.info("Updated ModuleDataProvider for JarModuleReference");
        } else logger.error("Failed to create proxy for {}",MODULE_DATA_PROVIDER);
    }
    
    final Object providerProxy;
    
    private TILBootJar(Object provider, URI uri) {
        this.providerProxy = createProxy(getClass().getClassLoader(),provider,uri);
    }
    
    Object createProxy(ClassLoader loader, final Object provider, final URI uri) {
        final FileSystem ufs = Paths.get(uri).getFileSystem();
        return ClassHelper.newGenericProxy(loader,MODULE_DATA_PROVIDER,(methodName,args) -> {
            switch(methodName) {
                case "descriptor": return Hacks.invoke(provider,"descriptor");
                case "findFile": return findFile(ufs,args[0]);
                case "getManifest": return Hacks.invoke(provider,"getManifest");
                case "name": return Hacks.invoke(provider,"name");
                case "open": return findFile(ufs,args[0]).map(Paths::get).map(LamdbaExceptionUtils.rethrowFunction(Files::newInputStream));
                case "uri": return uri;
                case "verifyAndGetSigners": return Hacks.invoke(provider,"verifyAndGetSigners",args[0],args[1]);
                default: return null;
            }
        });
    }
}