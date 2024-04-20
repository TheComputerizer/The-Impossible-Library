package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.TILCore1_12_2;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ModDiscoverer;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModContainerWriter1_12_2 {

    private static final Type INJECTEDMODCONTAINER = Type.getType("net/minecraftforge/fml/common/InjectedModContainer");
    private static final Type MODCONTAINER = Type.getType("net/minecraftforge/fml/common/ModContainer");
    private static final Type TIL_CORE = Type.getType(TILCore1_12_2.class);

    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    public static void cacheClass(LaunchClassLoader launchLoader, String name, Class<?> clazz) {
        ((Map<String,Class<?>>)ReflectionHelper.getFieldInstance(launchLoader,LaunchClassLoader.class,"cachedClasses")).put(name,clazz);
    }

    public static ASMDataTable findASMTable(Loader loader) {
        Object discoverer = ReflectionHelper.getFieldInstance(loader,Loader.class,"discoverer");
        return discoverer instanceof ModDiscoverer ? ((ModDiscoverer)discoverer).getASMTable() : null;
    }

    private static void writeClinit(ClassVisitor visitor, String modid) {
        ASMHelper.addField(visitor,PRIVATE_STATIC_FINAL,"MODID",STRING_TYPE,null,modid);
    }

    private static void writeConstructor(ClassVisitor visitor, Type type) {
        MethodVisitor constructor = ASMHelper.getConstructor(visitor,PUBLIC);
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitFieldInsn(GETSTATIC,type.getInternalName(),"MODID",STRING_TYPE.getDescriptor());
        constructor.visitMethodInsn(INVOKESTATIC,TIL_CORE.getInternalName(),"getFMLModContainer",
                Type.getMethodDescriptor(Type.getType("L"+MODCONTAINER+";"),STRING_TYPE),false);
        constructor.visitFieldInsn(GETSTATIC,type.getInternalName(),"MODID",STRING_TYPE.getDescriptor());
        constructor.visitMethodInsn(INVOKESTATIC,TIL_CORE.getInternalName(),"getModSource",
                Type.getMethodDescriptor(FILE_TYPE,STRING_TYPE),false);
        constructor.visitMethodInsn(INVOKESPECIAL,INJECTEDMODCONTAINER.getInternalName(),"<init>",
                Type.getMethodDescriptor(VOID_TYPE,Type.getType("L"+MODCONTAINER+";"),FILE_TYPE),false);
        constructor.visitInsn(RETURN);
        ASMHelper.finishMethod(constructor);
    }

    public static String writeModContainer(LaunchClassLoader launchLoader, String modid, String classpath) {
        Type type = Type.getType(classpath.replace('.','/'));
        ClassWriter writer = ASMHelper.getWriter(JAVA8,PUBLIC,type,INJECTEDMODCONTAINER);
        writeClinit(writer,modid);
        writeConstructor(writer,type);
        byte[] bytes = ASMHelper.finishWriting(writer,type,true);
        TILRef.logInfo("Successfully wrote bytecode for `{}`",classpath);
        Class<?> clazz = ClassHelper.defineClass(launchLoader,classpath,bytes);
        cacheClass(launchLoader,clazz.getName(),clazz);
        return clazz.getName();
    }
}