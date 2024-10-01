package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import org.apache.logging.log4j.core.net.UrlConnectionFactory;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.io.File.separatorChar;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.DATA_DIRECTORY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ASMHelper {

    public static void addField(ClassVisitor visitor, int access, String name, Type type, String signature, Object value) {
        visitor.visitField(access,name,type.getDescriptor(),signature,value).visitEnd();
    }
    
    public static void addSuperConstructor(MethodVisitor constructor, String name, String desc, boolean isInterface) {
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD,0);
        callInit(constructor,name,desc,isInterface);
    }
    
    public static void addNewInstance(MethodVisitor method, String name, String desc, boolean isInterface) {
        method.visitTypeInsn(NEW,name);
        method.visitInsn(DUP);
        callInit(method,name,desc,isInterface);
    }

    @IndirectCallers
    public static String buildSignature(Type base, Type ... innerTypes) {
        return ClassHelper.signatureDesc(base.getDescriptor(),ArrayHelper.mapTo(innerTypes,String.class,Type::getDescriptor));
    }
    
    @IndirectCallers
    public static String buildSignature(Type base, String ... innerSignatures) {
        return ClassHelper.signatureDesc(base.getDescriptor(),innerSignatures);
    }
    
    @IndirectCallers
    public static String buildSignature(Type base, String inner) {
        return ClassHelper.signatureDesc(base.getDescriptor(),inner);
    }
    
    @IndirectCallers
    public static void call(MethodVisitor method, int opcode, Class<?> clazz, String methodName, String desc) {
        call(method,opcode,clazz,methodName,desc,false);
    }
    
    @IndirectCallers
    public static void call(MethodVisitor method, int opcode, String className, String methodName, String desc) {
        call(method,opcode,className,methodName,desc,false);
    }
    
    public static void call(MethodVisitor method, int opcode, Class<?> clazz, String methodName, String desc,
            boolean isInterface) {
        call(method,opcode,Type.getInternalName(clazz),methodName,desc,isInterface);
    }
    
    public static void call(MethodVisitor method, int opcode, String className, String methodName, String desc,
            boolean isInterface) {
        method.visitMethodInsn(opcode,className,methodName,desc,isInterface);
    }
    
    @IndirectCallers
    public static void callEmpty(MethodVisitor method, int opcode, String name, String methodName) {
        callEmpty(method,opcode,name,methodName,false);
    }
    
    public static void callEmpty(MethodVisitor method, int opcode, String name, String methodName,
            boolean isInterface) {
        call(method,opcode,name,methodName,EMPTY_METHOD.getDescriptor(),isInterface);
    }
    
    public static void callInit(MethodVisitor method, String name, String desc, boolean isInterface) {
        call(method,INVOKESPECIAL,name,"<init>",desc,isInterface);
    }
    
    @IndirectCallers
    public static byte[] editClass(String className, Consumer<ClassWriter> consumer) throws IOException {
        return editClass(new ClassReader(className),consumer);
    }
    
    @IndirectCallers
    public static byte[] editClass(InputStream stream, Consumer<ClassWriter> consumer) throws IOException {
        return editClass(new ClassReader(stream),consumer);
    }
    
    @IndirectCallers
    public static byte[] editClass(byte[] byteCode, Consumer<ClassWriter> consumer) {
        return editClass(new ClassReader(byteCode),consumer);
    }
    
    public static byte[] editClass(ClassReader reader, Consumer<ClassWriter> consumer) {
        ClassWriter writer = getWriter(reader);
        consumer.accept(writer);
        return writer.toByteArray();
    }
    
    public static AbstractInsnNode findLabel(InsnList code, int ordinal) {
        return findNodeOrLast(code,LabelNode.class::isInstance,ordinal);
    }
    
    public static @Nullable AbstractInsnNode findNode(InsnList code, Function<AbstractInsnNode,Boolean> compare, int ordinal) {
        int count = 0;
        AbstractInsnNode matched = null;
        for(AbstractInsnNode node : code) {
            if(compare.apply(node)) {
                if(count<0 || count<=ordinal) matched = node;
                count++;
                if(ordinal>=0 && count>ordinal) break;
            }
        }
        return matched;
    }
    
    public static AbstractInsnNode findNodeOrFirst(InsnList code, Function<AbstractInsnNode,Boolean> compare, int ordinal) {
        AbstractInsnNode node = findNode(code,compare,ordinal);
        return Objects.nonNull(node) ? node : code.getFirst();
    }
    
    public static AbstractInsnNode findNodeOrLast(InsnList code, Function<AbstractInsnNode,Boolean> compare, int ordinal) {
        AbstractInsnNode node = findNode(code,compare,ordinal);
        return Objects.nonNull(node) ? node : code.getLast();
    }
    
    public static AbstractInsnNode findReturn(InsnList code) {
        return findReturn(code,-1);
    }
    
    public static AbstractInsnNode findReturn(InsnList code, int ordinal) {
        return findNodeOrLast(code,node -> node.getOpcode()==RETURN,ordinal);
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
    
    @IndirectCallers
    public static AnnotationVisitor getAnnotation(ClassVisitor visitor, Class<?> clazz) {
        return getAnnotation(visitor,clazz,true);
    }
    
    @IndirectCallers
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
    
    @IndirectCallers
    public static byte[] getBytes(URL url) throws IOException {
        return getBytes(UrlConnectionFactory.createConnection(url));
    }
    
    public static byte[] getBytes(URLConnection connection) throws IOException {
        return getBytes(connection.getInputStream());
    }
    
    public static byte[] getBytes(InputStream stream) throws IOException {
        return getBytes(new ClassReader(stream));
    }
    
    public static byte[] getBytes(ClassReader reader) {
        return getWriter(reader).toByteArray();
    }

    public static MethodVisitor getClassInit(ClassVisitor visitor) {
        return getMethod(visitor,STATIC,"<clinit>",null,new String[]{},VOID_TYPE);
    }

    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,new String[]{},VOID_TYPE,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,new String[]{},returnType,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String signature, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",signature,new String[]{},VOID_TYPE,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String[] exceptions, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,exceptions,VOID_TYPE,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String signature, Type returnType,
                                               Type ... argTypes) {
        return getMethod(visitor,access,"<init>",signature,new String[]{},returnType,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String[] exceptions, Type returnType,
                                               Type ... argTypes) {
        return getMethod(visitor,access,"<init>",null,exceptions,returnType,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getConstructor(ClassVisitor visitor, int access, String signature,
                                               String[] exceptions, Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,"<init>",signature,exceptions,returnType,argTypes);
    }

    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, Type ... argTypes) {
        return getMethod(visitor,access,name,null,new String[]{},VOID_TYPE,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String[] exceptions, Type ... argTypes) {
        return getMethod(visitor,access,name,null,exceptions,VOID_TYPE,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String signature,
                                          Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,name,signature,new String[]{},returnType,argTypes);
    }
    
    @IndirectCallers
    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String[] exceptions,
                                          Type returnType, Type ... argTypes) {
        return getMethod(visitor,access,name,null,exceptions,returnType,argTypes);
    }

    public static MethodVisitor getMethod(ClassVisitor visitor, int access, String name, String signature,
                                          String[] exceptions, Type returnType, Type ... argTypes) {
        return visitor.visitMethod(access,name,Type.getMethodDescriptor(returnType,argTypes),signature,exceptions);
    }
    
    @IndirectCallers
    public static ClassReader getReader(Class<?> clazz) throws IOException {
        return getReader(Type.getType(clazz));
    }

    public static ClassReader getReader(Type type) throws IOException {
        return new ClassReader(type.getClassName());
    }
    
    @IndirectCallers
    public static ClassWriter getWriter(int javaVer, int access, Type type) {
        return getWriter(javaVer,access,type,null,OBJECT_TYPE,new String[]{});
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, String[] interfaces) {
        return getWriter(javaVer,access,type,null,OBJECT_TYPE,interfaces);
    }
    
    @IndirectCallers
    public static ClassWriter getWriter(int javaVer, int access, Type type, String signature, String[] interfaces) {
        return getWriter(javaVer,access,type,signature,OBJECT_TYPE,interfaces);
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, Type superType) {
        return getWriter(javaVer,access,type,null,superType,new String[]{});
    }
    
    @IndirectCallers
    public static ClassWriter getWriter(int javaVer, int access, Type type, String signature, Type superType) {
        return getWriter(javaVer,access,type,signature,superType,new String[]{});
    }

    public static ClassWriter getWriter(int javaVer, int access, Type type, String signature, Type superType,
                                        String[] interfaces) {
        ClassWriter writer = new ClassWriter(COMPUTE_FRAMES);
        writer.visit(javaVer,access,type.getInternalName(),signature,superType.getInternalName(),interfaces);
        return writer;
    }
    
    public static ClassWriter getWriter(ClassReader reader) {
        return new ClassWriter(reader,COMPUTE_FRAMES);
    }
    
    public static boolean isValidReplacement(AbstractInsnNode node, @Nullable AbstractInsnNode replacement) {
        return Objects.isNull(replacement) || node!=replacement;
    }
    
    public static boolean isValidReplacement(AbstractInsnNode node, @Nullable InsnList replacement) {
        if(Objects.isNull(replacement)) return true;
        if(replacement.size()!=1) return true;
        for(AbstractInsnNode element : replacement)
            if(node==element) return false;
        return true;
    }
    
    /**
     * Set replacements to null to remove nodes
     */
    public static void replaceNode(InsnList code, Function<AbstractInsnNode,AbstractInsnNode> replacer, int min, int max) {
        Map<AbstractInsnNode,AbstractInsnNode> replacements = new HashMap<>();
        int count = 0;
        for(AbstractInsnNode node : code) {
            AbstractInsnNode replacement = replacer.apply(node);
            if(isValidReplacement(node,replacement)) {
                if(count>=min && (max<0 || count<=max)) replacements.put(node,replacement);
                count++;
                if(count>max && max>0) break;
            }
        }
        for(Entry<AbstractInsnNode,AbstractInsnNode> replacement : replacements.entrySet()) {
            AbstractInsnNode removal = replacement.getKey();
            int i = code.indexOf(removal);
            code.remove(removal);
            AbstractInsnNode replaceWith = replacement.getValue();
            if(Objects.nonNull(replaceWith)) code.insertBefore(code.get(i),replaceWith);
        }
    }
    
    /**
     * Set replacements to null or an empty lists to remove nodes
     */
    public static void replaceNodes(InsnList code, Function<AbstractInsnNode,InsnList> replacer, int min, int max) {
        Map<AbstractInsnNode,InsnList> replacements = new HashMap<>();
        int count = 0;
        for(AbstractInsnNode node : code) {
            InsnList replacement = replacer.apply(node);
            if(isValidReplacement(node,replacement)) {
                if(count>=min && (max<0 || count<=max)) replacements.put(node,replacement);
                count++;
                if(count>max && max>0) break;
            }
        }
        for(Entry<AbstractInsnNode,InsnList> replacement : replacements.entrySet()) {
            AbstractInsnNode removal = replacement.getKey();
            int i = code.indexOf(removal);
            code.remove(removal);
            InsnList replaceWith = replacement.getValue();
            if(Objects.nonNull(replaceWith)) code.insertBefore(code.get(i),replaceWith);
        }
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