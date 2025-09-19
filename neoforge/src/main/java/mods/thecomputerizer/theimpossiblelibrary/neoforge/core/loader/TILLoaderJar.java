package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import cpw.mods.jarhandling.JarContents;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.impl.JarSigningData;
import cpw.mods.niofs.union.UnionFileSystem;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.security.CodeSigner;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class TILLoaderJar implements SecureJar {
    
    static SecureJar get(JarContents contents, String moduleName) {
        return new TILLoaderJar(contents,moduleName);
    }
    
    final JarContents contents;
    final JarMetadata metadata;
    final JarSigningData signingData;
    final Manifest manifest;
    final ModuleDataProvider moduleDataProvider;
    final UnionFileSystem ufs;
    
    TILLoaderJar(JarContents contents, String moduleName) {
        this.contents = contents;
        this.metadata = TILLoaderJarMetadata.get(moduleName);
        this.signingData = Hacks.getFieldDirect(this.contents,"signingData");
        this.manifest = this.contents.getManifest();
        this.moduleDataProvider = TILLoaderJarModuleDataProvider.get(this.contents,this.metadata,this.signingData);
        this.ufs = Hacks.getFieldDirect(this.contents,"filesystem");
    }
    
    @Override public Status getFileStatus(String name) {
        return Hacks.invokeDirect(this.signingData,"getFileStatus",name);
    }
    
    @Override public @Nullable CodeSigner[] getManifestSigners() {
        return Hacks.invokeDirect(this.signingData,"getManifestSigners");
    }
    
    @Override public Path getPath(String first, String ... rest) {
        return this.ufs.getPath(first,rest);
    }
    
    @Override public Path getPrimaryPath() {
        return this.ufs.getPrimaryPath();
    }
    
    @Override public Path getRootPath() {
        return this.ufs.getPath("");
    }
    
    @Override public @Nullable Attributes getTrustedManifestEntries(String name) {
        return Hacks.invokeDirect(this.signingData,"getTrustedManifestEntries",this.manifest,name);
    }
    
    @Override public boolean hasSecurityData() {
        Boolean b = Hacks.invokeDirect(this.signingData,"hasSecurityData");
        return Objects.nonNull(b) && b;
    }
    
    @Override public ModuleDataProvider moduleDataProvider() {
        return this.moduleDataProvider;
    }
    
    @Override public String name() {
        return this.metadata.name();
    }
    
    @Override public String toString() {
        return "TILLoaderJar["+this.moduleDataProvider.uri()+"]";
    }
    
    @Override public Status verifyPath(Path path) {
        if(path.getFileSystem()!=this.ufs) throw new IllegalArgumentException("Wrong filesystem");
        final var pathname = path.toString();
        return Hacks.invokeDirect(this.signingData,"verifyPath",this.manifest,path,pathname);
    }
}