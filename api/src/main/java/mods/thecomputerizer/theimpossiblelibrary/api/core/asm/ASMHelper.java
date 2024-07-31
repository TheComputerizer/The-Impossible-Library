package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;

import static java.io.File.separatorChar;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.DATA_DIRECTORY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;

public class ASMHelper {

    public static void addField(ClassVisitor visitor, int access, String name, Type type, String signature, Object value) {
        visitor.visitField(access,name,type.getDescriptor(),signature,value).visitEnd();
    }

    public static String buildSignature(Type base, Type ... innerTypes) {
        return ClassHelper.signatureDesc(base.getDescriptor(),ArrayHelper.mapTo(innerTypes,String.class,Type::getDescriptor));
    }

    public static String buildSignature(Type base, String ... innerSignatures) {
        return ClassHelper.signatureDesc(base.getDescriptor(),innerSignatures);
    }

    public static String buildSignature(Type base, String inner) {
        return ClassHelper.signatureDesc(base.getDescriptor(),inner);
    }

    public static void finishMethod(MethodVisitor visitor) {
        visitor.visitMaxs(0,0);
        visitor.visitEnd();
    }

    /**
     * Do not call ClassWriter#visitEnd before this
     */
    public static byte[] finishWriting(ClassWriter writer, Type type, boolean debugOutput) {
        writer.visitEnd();
        String name = type.getInternalName().replace('/','.');
        byte[] bytes = writer.toByteArray();
        if(debugOutput) writeDebugByteCode(name,bytes);
        return bytes;
    }

    public static AnnotationVisitor getAnnotation(ClassVisitor visitor, Class<?> clazz) {
        return getAnnotation(visitor,clazz,true);
    }

    public static AnnotationVisitor getAnnotation(MethodVisitor visitor, Class<?> clazz) {
        return getAnnotation(visitor,clazz,true);
    }

    public static AnnotationVisitor getAnnotation(ClassVisitor visitor, Type type) {
        return getAnnotation(visitor,type,true);
    }

    public static AnnotationVisitor getAnnotation(MethodVisitor visitor, Type type) {
        return getAnnotation(visitor,type,true);
    }

    public static AnnotationVisitor getAnnotation(ClassVisitor visitor, Class<?> clazz, boolean runtime) {
        if(!Annotation.class.isAssignableFrom(clazz)) {
            TILRef.logError("Class is not an annotation `{}`",clazz);
            return null;
        }
        return getAnnotation(visitor,Type.getType(clazz),runtime);
    }

    public static AnnotationVisitor getAnnotation(MethodVisitor visitor, Class<?> clazz, boolean runtime) {
        if(!Annotation.class.isAssignableFrom(clazz)) {
            TILRef.logError("Class is not an annotation `{}`",clazz);
            return null;
        }
        return getAnnotation(visitor,Type.getType(clazz),runtime);
    }

    public static AnnotationVisitor getAnnotation(ClassVisitor visitor, Type type, boolean runtime) {
        return visitor.visitAnnotation(type.getDescriptor(),runtime);
    }

    public static AnnotationVisitor getAnnotation(MethodVisitor visitor, Type type, boolean runtime) {
        return visitor.visitAnnotation(type.getDescriptor(),runtime);
    }

    public static MethodVisitor getClassConstructor(ClassVisitor visitor) {
        return getMethod(visitor,STATIC,"<clinit>",null,new String[]{},Type.VOID_TYPE);
    }

    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,new String[]{},Type.VOID_TYPE,argTypes);
    }


    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,new String[]{},returnType,argTypes);
    }

    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String signature, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",signature,new String[]{},Type.VOID_TYPE,argTypes);
    }

    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String[] exceptions, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,exceptions,Type.VOID_TYPE,argTypes);
    }

    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String signature, Type returnType,
                                               Type ... argTypes) {
        return getMethod(visitor,access,"<init>",signature,new String[]{},returnType,argTypes);
    }

    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String[] exceptions, Type returnType,
                                               Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,exceptions,returnType,argTypes);
    }

    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String signature,
                                               String[] exceptions, Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",signature,exceptions,returnType,argTypes);
    }

    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, Type ... argTypes) {
        return getMethod(visitor,access,name,null,new String[]{},Type.VOID_TYPE,argTypes);
    }

    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String[] exceptions, Type ... argTypes) {
        return getMethod(visitor,access,name,null,exceptions,Type.VOID_TYPE,argTypes);
    }

    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String signature,
                                          Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,name,signature,new String[]{},returnType,argTypes);
    }

    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String[] exceptions,
                                          Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,name,null,exceptions,returnType,argTypes);
    }

    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String signature,
                                          String[] exceptions, Type returnType, Type ... argTypes) {
        return visitor.visitMethod(access,name,Type.getMethodDescriptor(returnType,argTypes),null,exceptions);
    }

    public static ClassReader getReader(Class<?> clazz) throws IOException {
        return getReader(Type.getType(clazz));
    }

    public static ClassReader getReader(Type type) throws IOException {
        return new ClassReader(type.getClassName());
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type) {
        return getWriter(javaVer,access,type,null,OBJECT_TYPE,new String[]{});
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, String[] interfaces) {
        return getWriter(javaVer,access,type,null,OBJECT_TYPE,interfaces);
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, String signature, String[] interfaces) {
        return getWriter(javaVer,access,type,signature,OBJECT_TYPE,interfaces);
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, Type superType) {
        return getWriter(javaVer,access,type,null,superType,new String[]{});
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, String signature, Type superType) {
        return getWriter(javaVer,access,type,signature,superType,new String[]{});
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, String signature, Type superType,
                                        String[] interfaces) {
        ClassWriter writer = new ClassWriter(COMPUTE_FRAMES);
        writer.visit(javaVer,access,type.getInternalName(),signature,superType.getInternalName(),interfaces);
        return writer;
    }

    public static void writeDebugByteCode(String classpath, byte[] bytes) {
        File debugDir = new File(DATA_DIRECTORY,"asm_debug");
        String filepath = classpath.replace('.',separatorChar)+".class";
        writeByteCodeToFile(FileHelper.get(new File(debugDir,filepath),false),bytes);
    }

    public static void writeByteCodeToFile(File file, byte[] bytes) {
        try(FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(bytes);
            TILDev.logTrace("Successfully dumped class bytes to `{}`", file);
        } catch(IOException ex) {
            TILRef.logError("Failed to print class file to `{}`",file);
        }
    }
}