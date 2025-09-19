package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap;

import cpw.mods.jarhandling.SecureJar.ModuleDataProvider;
import cpw.mods.niofs.union.UnionFileSystem;
import cpw.mods.util.LambdaExceptionUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleReference;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSigner;
import java.util.Optional;
import java.util.jar.Manifest;

public class TILBootJar implements ModuleDataProvider {
    
    @IndirectCallers
    static void updateReference(Logger logger, ModuleReference reference, URI uri) {
        Hacks.setFieldDirect(reference,"jar",new TILBootJar(Hacks.getFieldDirect(reference,"jar"),uri));
        logger.info("Updated ModuleDataProvider for JarModuleReference");
    }
    
    final ModuleDataProvider provider;
    final URI uri;
    final UnionFileSystem ufs;
    
    private TILBootJar(ModuleDataProvider provider, URI uri) {
        this.provider = provider;
        this.uri = uri;
        this.ufs = (UnionFileSystem)Paths.get(uri).getFileSystem();
    }
    
    @Override public ModuleDescriptor descriptor() {
        return this.provider.descriptor();
    }
    
    @Override public Manifest getManifest() {
        return this.provider.getManifest();
    }
    
    @Override public Optional<URI> findFile(String name) {
        Path path = this.ufs.getPath(name);
        return Optional.of(this.ufs.getRoot().resolve(path)).filter(Files::exists).map(Path::toUri);
    }
    
    @Override public String name() {
        return this.provider.name();
    }
    
    @Override public Optional<InputStream> open(String name) {
        return findFile(name).map(Paths::get).map(LambdaExceptionUtils.rethrowFunction(Files::newInputStream));
    }
    
    @Override public String toString() {
        return this.provider.toString();
    }
    
    @Override public @Nullable URI uri() {
        return this.uri;
    }
    
    @Override public @Nullable CodeSigner[] verifyAndGetSigners(String cname, byte[] bytes) {
        return this.provider.verifyAndGetSigners(cname,bytes);
    }
}