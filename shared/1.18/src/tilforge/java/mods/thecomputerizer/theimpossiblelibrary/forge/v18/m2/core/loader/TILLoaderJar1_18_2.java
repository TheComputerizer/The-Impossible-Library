package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.core.loader;

import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.impl.Jar;
import cpw.mods.niofs.union.UnionFileSystem;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.lang.module.ModuleDescriptor;
import java.net.URI;
import java.nio.file.Path;
import java.security.CodeSigner;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * In 1.18.2 we need an extension of Jar specifically rather than just an implementation of SecureJar
 */
public class TILLoaderJar1_18_2 extends Jar {
    
    @IndirectCallers
    public static Jar get(Object jarProxy, Object fileSystem, Path path, Object metadata) {
        TILRef.logInfo("Wrapping SecureJar proxy for 1.18.2 specific handling");
        return new TILLoaderJar1_18_2((SecureJar)jarProxy,(UnionFileSystem)fileSystem,path,(JarMetadata)metadata);
    }
    
    final SecureJar proxy;
    final UnionFileSystem ufs;
    final JarMetadata metadata;
    
    public TILLoaderJar1_18_2(SecureJar proxy, UnionFileSystem ufs, Path path, JarMetadata metadata) {
        super(proxy::getManifest,jar -> null,null,path);
        this.proxy = proxy;
        this.ufs = ufs;
        this.metadata = metadata;
    }
    
    @Override public ModuleDescriptor computeDescriptor() {
        return this.metadata.descriptor();
    }
    
    @Override public Optional<URI> findFile(final String name) {
        return this.proxy.findFile(name);
    }
    
    @Override public Status getFileStatus(final String name) {
        return this.proxy.getFileStatus(name);
    }
    
    @Override public Manifest getManifest() {
        return Objects.nonNull(this.proxy) ? this.proxy.getManifest() : super.getManifest();
    }
    
    @Override public CodeSigner[] getManifestSigners() {
        return this.proxy.getManifestSigners();
    }
    
    @Override public Set<String> getPackages() {
        return this.proxy.getPackages();
    }
    
    @Override public Path getPath(String first, String... rest) {
        return this.proxy.getPath(first,rest);
    }
    
    @Override public Path getPrimaryPath() {
        return this.proxy.getPrimaryPath();
    }
    
    @Override public List<Provider> getProviders() {
        return this.proxy.getProviders();
    }
    
    @Override public Path getRootPath() {
        return this.proxy.getRootPath();
    }
    
    @Override public Attributes getTrustedManifestEntries(final String name) {
        return this.proxy.getTrustedManifestEntries(name);
    }
    
    @Override public URI getURI() {
        return this.ufs.getRootDirectories().iterator().next().toUri();
    }
    
    @Override public boolean hasSecurityData() {
        return this.proxy.hasSecurityData();
    }
    
    @Override public String name() {
        return this.proxy.name();
    }
    
    @Override public String toString() {
        return "TILWrappedLoader"+super.toString();
    }
    
    @Override public Status verifyPath(final Path path) {
        return this.proxy.verifyPath(path);
    }
}