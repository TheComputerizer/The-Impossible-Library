package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.TILCore1_12_2;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ModDiscoverer;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;

public class ModContainerWriter1_12_2 {

    private static final Logger LOGGER = TILRef.createLogger("TIL ModContainer Writer (1.12.2)");
    private static final String INJECTED_MOD_CONTAINER = "net/minecraftforge/fml/common/InjectedModContainer";
    private static final String MOD_CONTAINER = "net/minecraftforge/fml/common/ModContainer";
    private static final Type TIL_CORE = Type.getType(TILCore1_12_2.class);

    public static void cacheClass(LaunchClassLoader launchLoader, String name, Class<?> clazz) {
        Hacks.addToMapField("cachedClasses",name,clazz,s -> Hacks.getFieldDirect(launchLoader,s));
    }

    public static ASMDataTable findASMTable(Loader loader) {
        ModDiscoverer discoverer = Hacks.getFieldDirect(loader,"discoverer");
        return Objects.nonNull(discoverer) ? discoverer.getASMTable() : null;
    }

    private static void writeClinit(ClassVisitor visitor, String modid) {
        ASMHelper.addField(visitor,PRIVATE_STATIC_FINAL,"MODID",STRING_TYPE,null,modid);
    }

    private static void writeConstructor(ClassVisitor visitor, String typeName) {
        Type container = TypeHelper.get(MOD_CONTAINER);
        MethodVisitor constructor = ASMHelper.getConstructor(visitor,PUBLIC);
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitFieldInsn(GETSTATIC,typeName,"MODID",STRING_TYPE.getDescriptor());
        constructor.visitMethodInsn(INVOKESTATIC,TIL_CORE.getInternalName(),"getFMLModContainer",
                TypeHelper.methodDesc(container,STRING_TYPE),false);
        constructor.visitFieldInsn(GETSTATIC,typeName,"MODID",STRING_TYPE.getDescriptor());
        constructor.visitMethodInsn(INVOKESTATIC,TIL_CORE.getInternalName(),"getModSource",
                TypeHelper.methodDesc(FILE_TYPE,STRING_TYPE),false);
        constructor.visitMethodInsn(INVOKESPECIAL,INJECTED_MOD_CONTAINER,"<init>",
                TypeHelper.voidMethodDesc(container,FILE_TYPE),false);
        constructor.visitInsn(RETURN);
        ASMHelper.finishMethod(constructor);
    }

    public static String writeModContainer(LaunchClassLoader launchLoader, String modid, String className) {
        String internalName = className.replace('.','/');
        ClassWriter writer = ASMHelper.getWriter(CoreAPI.isJava8() ? JAVA8 : JAVA21,PUBLIC,internalName,
                                                 INJECTED_MOD_CONTAINER);
        writeClinit(writer,modid);
        writeConstructor(writer,internalName);
        byte[] bytes = ASMHelper.finishWriting(writer,internalName,true);
        LOGGER.info("Successfully wrote bytecode for `{}`",className);
        LOGGER.info("Attempting to add class to loader {}",launchLoader.getClass().getName());
        Class<?> clazz;
        //The ClassLoader cleanroom uses has a built-in method to define a class
        if(CoreAPI.isJava8()) clazz = ClassHelper.defineClass(launchLoader,className,bytes);
        else {
            ProtectionDomain pd = ModContainerWriter1_12_2.class.getProtectionDomain();
            CodeSource source = Objects.nonNull(pd) ? pd.getCodeSource() : null;
            clazz = Hacks.invokeDirect(launchLoader,"defineClass",className,bytes,source);
        }
        cacheClass(launchLoader,clazz.getName(),clazz);
        return clazz.getName();
    }
}