package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ForgeModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ModuleClassLoaderAccess;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSigner;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static java.util.jar.JarFile.MANIFEST_NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.BASE_PACKAGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;

public class TILLoaderJar {
    
    static final Set<String> BOOT_PACKAGES = new HashSet<>();
    static final CodeSigner[] EMPTY_CODESIGNERS = new CodeSigner[]{};
    static final String MANIFEST_VERIFIER = "cpw.mods.jarhandling.impl.ManifestVerifier";
    static final String SECURE_JAR = "cpw.mods.jarhandling.SecureJar";
    
    static Set<String> bootPackages() {
        if(BOOT_PACKAGES.isEmpty()) {
            ModuleClassLoaderAccess loader = ForgeModuleAccess.getModuleClassLoader("BOOT");
            ResolvedModuleAccess moudle = loader.configuration().getModule(BOOT_ID);
            if(Objects.nonNull(moudle)) BOOT_PACKAGES.addAll(moudle.packages(false));
        }
        return BOOT_PACKAGES;
    }
    
    static Object get(Supplier<Manifest> manifestSupplier, String moduleName, Path path, boolean jarModuleData) {
        return new TILLoaderJar(manifestSupplier.get(),moduleName,path,jarModuleData).jarProxy;
    }
    
    private static CodeSigner[] getSignersOrEmpty(Map<String,StatusData> dataMap, String name) {
        return getSigners(dataMap,name,EMPTY_CODESIGNERS);
    }
    
    @SuppressWarnings("SameParameterValue")
    private static CodeSigner[] getSigners(Map<String,StatusData> dataMap, String name) {
        return getSigners(dataMap,name,null);
    }
    
    private static CodeSigner[] getSigners(Map<String,StatusData> dataMap, String name,
            CodeSigner[] defaultSigners) {
        return dataMap.containsKey(name) ? dataMap.get(name).signers : defaultSigners;
    }
    
    private static <E extends Enum<E>> E getStatus(Map<String,StatusData> dataMap, String name,
            boolean hasSecurityData) {
        Class<E> enumClass = StatusData.enumClass();
        if(Objects.isNull(enumClass)) return null;
        if(!hasSecurityData) return Enum.valueOf(enumClass,"UNVERIFIED");
        return dataMap.containsKey(name) ? GenericUtils.cast(dataMap.get(name).status) :
                Enum.valueOf(enumClass,"NONE");
    }
    
    private static boolean hasSecurityData(Object instance) {
        return Hacks.invokeDefault(false,instance,"hasSecurityData");
    }
    
    /**
     * If this is 1.18.2, wrap the proxy so that it extends Jar instead of implementing SecureJar.
     * Otherwise, return the proxy as is.
     */
    private static Object maybeWrapProxy(Object jarProxy, TILLoaderJarModuleDataProvider provider, Path path,
            Object metadata) {
        return Objects.nonNull(provider.providerProxy) ? jarProxy :
                wrapProxyForOldHandler(jarProxy,provider.ufs,path,metadata);
    }
    
    /**
     * Specific 1.18.2 handle due to the old securejarhandler version
     */
    private static Object wrapProxyForOldHandler(Object jarProxy, Object fileSystem, Path path, Object metadata) {
        final String jarClassName = BASE_PACKAGE+".forge.v18.m2.core.loader.TILLoaderJar1_18_2";
        final Class<?> jarClass = Hacks.findClass(jarClassName,TILLoaderJar.class.getClassLoader());
        return Hacks.invokeStatic(jarClass,"get",jarProxy,fileSystem,path,metadata);
    }
    
    final Object jarProxy;
    
    TILLoaderJar(Manifest manifest, String moduleName, Path path, boolean jarModuleData) {
        Object metadata = TILLoaderJarMetadata.get(moduleName);
        TILLoaderJarModuleDataProvider provider = TILLoaderJarModuleDataProvider.get(this,metadata,manifest,jarModuleData);
        this.jarProxy = createAndMaybeWrapProxy(getClass().getClassLoader(),manifest,metadata,provider,moduleName,path);
    }
    
    Object createAndMaybeWrapProxy(ClassLoader loader, final Manifest manifest, final Object metadata,
            final TILLoaderJarModuleDataProvider provider, final String moduleName, final Path path) {
        return maybeWrapProxy(createProxy(loader,manifest,provider,moduleName,path),provider,path,metadata);
    }
    
    Object createProxy(ClassLoader loader, final Manifest manifest, final TILLoaderJarModuleDataProvider provider,
            final String moduleName, final Path primaryPath) {
        final Object verifier = Hacks.construct(MANIFEST_VERIFIER);
        final Map<String,StatusData> statusData = new HashMap<>();
        final Hashtable<String,CodeSigner[]> pendingSigners = new Hashtable<>();
        final Hashtable<String,CodeSigner[]> verifiedSigners = new Hashtable<>();
        final Supplier<Path> primaryPathSupplier = ForgeModLoading.isSecureLoadingFormat() ?
                () -> primaryPath : () -> Hacks.invoke(provider.ufs,"getPrimaryPath");
        InvocationHandler proxyHandler = (instance,method,args) -> {
            switch(method.getName()) {
                case "equals": return Objects.nonNull(args[0]) && args[0].hashCode()==instance.hashCode();
                case "findFile": return provider.findFile((String)args[0]);
                case "getFileStatus": {
                    final String name = (String)args[0];
                    final boolean hasSecurityData = Hacks.invokeDefault(false,instance,"hasSecurityData");
                    return getStatus(statusData,name,hasSecurityData);
                }
                case "getManifest": return provider.manifest;
                case "getManifestSigners": return statusData.containsKey(MANIFEST_NAME) ?
                        statusData.get(MANIFEST_NAME).signers : null;
                case "getPackages": return bootPackages();
                case "getPath": return provider.ufs.getPath((String)args[0],(String[])args[1]);
                case "getPrimaryPath": return primaryPathSupplier.get();
                case "getProviders": return Collections.emptyList();
                case "getRootPath": return provider.ufs.getPath("");
                case "getTrustedManifestEntries": {
                    final String name = (String)args[0];
                    Attributes attributes = manifest.getAttributes(name);
                    CodeSigner[] manifestSigners = getSigners(statusData,MANIFEST_NAME);
                    CodeSigner[] objectSigners = getSignersOrEmpty(statusData,name);
                    return Objects.isNull(manifestSigners) || (manifestSigners.length==objectSigners.length) ?
                            attributes : null;
                }
                case "hasSecurityData": return !pendingSigners.isEmpty() || !verifiedSigners.isEmpty();
                case "hashCode": return instance.hashCode();
                case "moduleDataProvider": return provider.providerProxy;
                case "name": return moduleName;
                case "toString": return "Jar["+provider.uri+"]";
                case "verifyAndGetSigners": {
                    if(!hasSecurityData(instance)) return null;
                    final String name = (String)args[0];
                    if(statusData.containsKey(name)) return statusData.get(name).signers;
                    CodeSigner[] signers = Hacks.invokeDefault("invokeDirect",null,verifier,
                            "verify",manifest,pendingSigners,verifiedSigners,name,args[2]);
                    if(Objects.isNull(signers)) {
                        StatusData.add(statusData,"INVALID",null);
                        return null;
                    }
                    StatusData.add(statusData,"VERIFIED",signers);
                    return signers;
                }
                case "verifyPath": {
                    final Path path = GenericUtils.cast(args[0]);
                    if(Objects.isNull(path)) throw new IllegalArgumentException("Null path??");
                    if(path.getFileSystem()!=provider.ufs) throw new IllegalArgumentException("Wrong filesystem");
                    final String pathName = path.toString();
                    if(statusData.containsKey(pathName))
                        return getStatus(statusData,pathName,hasSecurityData(instance));
                    try {
                        byte[] bytes = Files.readAllBytes(path);
                        Hacks.invoke(instance,"verifyAndGetSigners",pathName,bytes);
                        return Hacks.invoke(instance,"getFileStatus",pathName);
                    } catch(IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                }
                default: return null;
            }
        };
        return ClassHelper.newProxy(loader,proxyHandler,SECURE_JAR,JarData.class);
    }
    
    interface JarData {
        @IndirectCallers CodeSigner[] verifyAndGetSigners(final String name, final byte[] bytes);
    }
    
    static final class StatusData {
        
        static final String SECURE_JAR_STATUS = "cpw.mods.jarhandling.SecureJar$Status";
        static Class<Enum<?>> statusClass;
        
        static void add(final Map<String,StatusData> statusData, final String name,
                @Nullable final CodeSigner[] signers) {
            StatusData data = get(name,signers);
            if(Objects.nonNull(data)) statusData.put(name,data);
        }
        
        static <E extends Enum<E>> Class<E> enumClass() {
            if(Objects.isNull(statusClass)) statusClass = GenericUtils.cast(Hacks.findClass(SECURE_JAR_STATUS));
            return GenericUtils.cast(statusClass);
        }
        
        static <E extends Enum<E>> StatusData get(final String name, @Nullable final CodeSigner[] signers) {
            Class<E> enumClass = enumClass();
            if(Objects.isNull(enumClass)) {
                TILRef.logError("Enum class {} not found?? Cannot instantiate StatusData",SECURE_JAR_STATUS);
                return null;
            }
            return new StatusData(Enum.valueOf(enumClass,name),signers);
        }
        
        final Enum<?> status;
        final CodeSigner[] signers;
        
        StatusData(final Enum<?> status, final CodeSigner[] signers) {
            this.status = status;
            this.signers = signers;
        }
    }
}