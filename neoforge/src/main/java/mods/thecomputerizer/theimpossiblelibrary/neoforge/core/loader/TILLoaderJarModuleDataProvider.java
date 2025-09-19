package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import cpw.mods.jarhandling.JarContents;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar.ModuleDataProvider;
import cpw.mods.jarhandling.impl.JarSigningData;
import cpw.mods.util.LambdaExceptionUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.NeoforgeModuleAccess;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSigner;
import java.util.Objects;
import java.util.Optional;
import java.util.jar.Manifest;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;

public class TILLoaderJarModuleDataProvider implements ModuleDataProvider {
    
    private static URI computeLoaderURI() {
        ResolvedModuleAccess module = NeoforgeModuleAccess.findResolvedModuleIn(BOOT_ID,BOOT);
        if(Objects.isNull(module)) TILRef.logError("Failed to find ResolvedModule {} in BOOT layer!",BOOT_ID);
        URI uri = Objects.nonNull(module) ? module.reference().location() : null;
        TILRef.logInfo("Called computeLoaderURI on TILLoaderJarModuleDataProvider and returning URI is {}",uri);
        return uri;
    }
    
    public static ModuleDataProvider get(JarContents contents, JarMetadata metadata,
            JarSigningData signingData) {
        return new TILLoaderJarModuleDataProvider(contents,metadata,signingData);
    }
    
    final JarContents contents;
    final JarMetadata metadata;
    final JarSigningData signingData;
    URI uri;
    
    TILLoaderJarModuleDataProvider(JarContents contents, JarMetadata metadata, JarSigningData signingData) {
        this.contents = contents;
        this.metadata = metadata;
        this.signingData = signingData;
    }
    
    @Override public ModuleDescriptor descriptor() {
        return this.metadata.descriptor();
    }
    
    @Override public Optional<URI> findFile(String name) {
        return this.contents.findFile(name);
    }
    
    @Override public Manifest getManifest() {
        return this.contents.getManifest();
    }
    
    @Override public String name() {
        return this.metadata.name();
    }
    
    @Override public Optional<InputStream> open(String name) {
        return findFile(name).map(Paths::get).map(LambdaExceptionUtils.rethrowFunction(Files::newInputStream));
    }
    
    @Override public @Nullable URI uri() {
        if(Objects.isNull(this.uri)) this.uri = computeLoaderURI();
        return this.uri;
    }
    
    @Override public @Nullable CodeSigner[] verifyAndGetSigners(String className, byte[] bytes) {
        return Hacks.invokeDirect(this.signingData,"verifyAndGetSigners",getManifest(),className,bytes);
    }
}