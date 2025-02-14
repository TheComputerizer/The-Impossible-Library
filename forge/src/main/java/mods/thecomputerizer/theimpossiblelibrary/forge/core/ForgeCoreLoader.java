package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.Launcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.burningwave.core.classes.JavaClass;
import org.burningwave.core.io.FileSystemItem;
import org.burningwave.core.io.FileSystemItem.Criteria;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Resources;

public class ForgeCoreLoader {
    
    private static final String API_PACKAGE = "mods.thecomputerizer.theimpossiblelibrary.api";
    private static final String FORGE_PACKAGE = "mods.thecomputerizer.theimpossiblelibrary.forge";
    private static final String APICORE = API_PACKAGE+".core.CoreAPI";
    private static final Logger LOGGER = LogManager.getLogger("TIL ForgeCoreLoader");
    private static MethodHandle defineClassHandle;
    
    //The module system forced me to find a very powerful alternative, but at least I don't need to do Unsafe hacking
    static {
        if(isJava8()) LOGGER.info("I see you are running Java 8. Good choice, but I'll be using burningwave anyways");
        else LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
    }
    
    /**
     * Should be the ClassLoader for the BOOT layer or the system ClassLoader if Java 8
     */
    static ClassLoader bootLoader() {
        ClassLoader loader = Launcher.class.getClassLoader();
        return Objects.nonNull(loader) ? loader : ClassLoader.getSystemClassLoader();
    }
    
    static byte[] bytesFrom(@Nullable InputStream stream) {
        if(Objects.nonNull(stream)) {
            try(ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int read;
                while((read = stream.read(buffer))!=-1) bytes.write(buffer,0,read);
                return bytes.toByteArray();
            } catch(IOException ex) {
                LOGGER.error("Failed to get byte[] from InputStream",ex);
            }
        }
        return new byte[]{};
    }
    
    static Class<?> defineClass(String binaryName, byte[] classBytes) {
        LOGGER.debug("Attempting to inject bootstrap class {}",binaryName);
        if(Objects.isNull(classBytes)) {
            LOGGER.error("Tried to define class with null byte array! {}",binaryName);
            return null;
        }
        return defineClass(bootLoader(),binaryName,ByteBuffer.wrap(classBytes));
    }
    
    static Class<?> defineClass(ClassLoader loader, String binaryName, ByteBuffer bytes) {
        if(Objects.isNull(defineClassHandle)) defineClassHandle = ClassLoaders.getDefineClassMethod(bootLoader());
        try {
            return (Class<?>)defineClassHandle.invoke(loader,binaryName,bytes,(ProtectionDomain)null);
        } catch(Throwable t) {
            throw new RuntimeException("Failed to define "+binaryName+" for "+loader,t);
        }
    }
    
    static Class<?> defineClassFrom(ClassLoader loader, String binaryName) {
        try(InputStream stream = getClassStream(loader,binaryName)) {
            return defineClass(binaryName,bytesFrom(stream));
        } catch(IOException ex) {
            LOGGER.error("Failed to define {}",binaryName);
        }
        return null;
    }
    
    /**
     * Returns a list of the names for all classes needed to define a CoreAPI instance.
     * This is necessary since there doesn't seem to be a straightforward way of adding classpaths during runtime to
     * the ModuleClassLoader that Forge uses in 1.18.2+ even via burningwave.
     * CoreAPI needs to be loaded in the BOOT layer for CoreMod stuff to work properly.
     */
    static List<String> getAPIStack(String version) {
        List<String> stack = new ArrayList<>();
        getEntirePackage(stack,API_PACKAGE+".core");
        getEntirePackage(stack,API_PACKAGE+".util");
        getEntirePackage(stack,API_PACKAGE+".io");
        getEntirePackage(stack,FORGE_PACKAGE+".core");
        getEntirePackage(stack,versionPackage(version)+".core");
        stack.add(API_PACKAGE+".common.CommonAPI");
        stack.add(API_PACKAGE+".common.CommonEntryPoint");
        stack.add(API_PACKAGE+".client.ClientAPI");
        stack.add(API_PACKAGE+".client.ClientEntryPoint");
        stack.add(versionClassName("common.Common",version));
        stack.add(versionClassName("client.Client",version));
        stack.add(versionClassName("common.CommonForge",version));
        stack.add(versionClassName("client.ClientForge",version));
        LOGGER.info("Defining {} CoreAPI related classes",stack.size());
        return stack;
    }
    
    /**
     * Get the command line argument handler in case we need to check stuff very early in the loading process
     */
    static ArgumentHandler getArgumentHandler() {
        LOGGER.info("Atempting to get ArgumentHandler for loader {}",Thread.currentThread().getContextClassLoader());
        Object args = getField(INSTANCE.getClass(),"argumentHandler",INSTANCE);
        if(!(args instanceof ArgumentHandler)) {
            LOGGER.error("Failed to find argument handler!");
            return null;
        }
        return (ArgumentHandler)args;
    }
    
    static @Nullable Object getBootLoadedCoreAPI() {
        return getCoreAPIReflectively(bootLoader());
    }
    
    static InputStream getClassStream(ClassLoader loader, String classpath) {
        return loader.getResourceAsStream(classpath.replace('.','/')+".class");
    }
    
    static Object getCoreAPIReflectively(ClassLoader loader) {
        try {
            return getField(Class.forName(APICORE,false,loader),"INSTANCE",null);
        } catch(ClassNotFoundException ex) {
            LOGGER.debug("CoreAPI not found on {}",loader);
        }
        return null;
    }
    
    /**
     * Gets the names of all classes in the input package and adds them to the input collection
     */
    static void getEntirePackage(Collection<String> names, String pkgName) {
        FileSystemItem source = getSelfSource();
        Criteria fileFilter = Criteria.forAllFileThat(item -> {
            JavaClass javaClass = item.toJavaClass();
            if(Objects.isNull(javaClass)) return false;
            String pkg = javaClass.getPackageName();
            return Objects.nonNull(pkg) && pkg.contains(pkgName);
        });
        Collection<FileSystemItem> children = source.findInAllChildren(fileFilter);
        LOGGER.info("Found {} childern in package {}",children.size(),pkgName);
        for(FileSystemItem child : children) names.add(child.toJavaClass().getName());
    }
    
    static @Nullable Object getField(Class<?> cls, String name, @Nullable Object instance) {
        try {
            Field field = cls.getDeclaredField(name);
            if(!field.isAccessible()) field.setAccessible(true);
            return field.get(instance);
        } catch(Exception ex) {
            LOGGER.error("Failed to get field {} from {} on instance {}",name,cls,instance,ex);
        }
        return null;
    }
    
    static FileSystemItem getSelfSource() {
        return Resources.getClassPath(ForgeCoreLoader.class).getParentContainer();
    }
    
    static String getVersionStr() {
        ArgumentHandler handler = getArgumentHandler();
        if(Objects.isNull(handler)) return null;
        String[] rawArgs = (String[])getField(handler.getClass(),"args",handler);
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find version using handler {}",handler);
            return null;
        }
        int versionIndex = -1;
        for(int i=0;i<rawArgs.length;i++) {
            if(rawArgs[i].equals("--fml.mcVersion")) {
                versionIndex = i+1;
                break;
            }
        }
        if(versionIndex>=0) {
            LOGGER.info("Found fml.mcVersion arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        LOGGER.error("Failed to find version from {}",Arrays.toString(rawArgs));
        return null;
    }
    
    /**
     * Returns a CoreAPI instance on the input ClassLoader. Initializes the source if necessary
     */
    static @Nullable Object initCoreAPI(ClassLoader loader) {
        LOGGER.debug("Starting CoreAPI init");
        Object bootInstance = getBootLoadedCoreAPI();
        if(Objects.nonNull(bootInstance)) {
            LOGGER.info("Returning existing CoreAPI instance found in the BOOT layer");
            return bootInstance;
        }
        String version = getVersionStr();
        Class<?> coreClass = loadAPIStack(loader,version);
        try {
            return coreClass.newInstance();
        } catch(InstantiationException | IllegalAccessException ex) {
            LOGGER.fatal("Caught reflection exception while trying to get CoreAPI instance as {}",coreClass,ex);
        } catch(Exception ex) {
            LOGGER.fatal("Unknown error while trying to get CoreAPI instance as {}",coreClass,ex);
        }
        LOGGER.fatal("Failed to initialize CoreAPI [Forge-{}] using {}",version,loader);
        return null;
    }
    
    static boolean isJava8() {
        return System.getProperty("java.version").startsWith("1.");
    }
    
    /**
     * Define necessary classes for the versioned CoreAPI instance
     * Returns the instance class
     */
    static Class<?> loadAPIStack(ClassLoader loader, String version) {
        Class<?> clazz = null;
        List<String> stack = getAPIStack(version);
        Map<String,Throwable> errors = new HashMap<>();
        String versionName = versionClassName("core.TILCoreForge",version);
        Class<?> maybe = loadStack(loader,stack,errors,versionName);
        if(Objects.nonNull(maybe)) clazz = maybe;
        int maxErrors = 3;
        while(!errors.isEmpty()) {
            stack.clear();
            stack.addAll(errors.keySet());
            errors.clear();
            maybe = loadStack(loader,stack,errors,versionName);
            if(Objects.nonNull(maybe) && Objects.isNull(clazz)) clazz = maybe;
            maxErrors--;
            if(maxErrors<=0) break;
        }
        for(Entry<String,Throwable> error : errors.entrySet())
            LOGGER.error("Failed to define class {} after 3 attempts",error.getKey(),error.getValue());
        if(Objects.isNull(clazz)) throw new RuntimeException("Failed to load CoreAPI instance [Forge-"+version+"]");
        LOGGER.info("Successfully loaded CoreAPI instance {}",clazz);
        return clazz;
    }
    
    /**
     * Defines a list of classes from names while storing the names of any that fail to be defined.
     * Returns a Class object whose name matches the input
     */
    static Class<?> loadStack(ClassLoader ref, List<String> stack, Map<String,Throwable> errors, String returnThis) {
        Class<?> clazz = null;
        for(String name : stack) {
            try {
                Class<?> maybe = defineClassFrom(ref,name);
                if(returnThis.equals(name)) {
                    clazz = maybe;
                    LOGGER.info("Got class matching {}",returnThis);
                }
            } catch(Throwable t) {
                LOGGER.info("Failed to load {} once",name);
                errors.put(name,t); //A parent class may not be loaded yet
            }
        }
        return clazz;
    }
    
    /**
     * Include any packages after the base. Should include Forge in name if necessary
     */
    static String versionClassName(String name, String version) {
        return versionPackage(version)+"."+versionQuantify(name,version);
    }
    
    /**
     * ModLoader will always be Forge so we can cheat this a bit more than the CoreAPI implementation
     */
    static String versionPackage(String version) {
        String[] split = version.split("\\.");
        if(split.length<3) throw new RuntimeException("Can't parse package for unknown version "+version);
        return FORGE_PACKAGE+".v"+split[1]+".m"+split[2];
    }
    
    static String versionQuantify(String name, String version) {
        return name+version.replace('.','_');
    }
}
