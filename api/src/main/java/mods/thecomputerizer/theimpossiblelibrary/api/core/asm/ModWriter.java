package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.OBJECT_TYPE;

public abstract class ModWriter {
    
    protected static final String EMPTY_METHOD_DESC = EMPTY_METHOD.getDescriptor();
    
    protected final CoreAPI core;
    protected final Map<String,Type> entryPointMethodTypes;
    protected final Map<String,String[]> entryPointMethods;
    @Getter protected final MultiVersionModInfo info;
    protected final String entryPointDesc;
    protected final String entryPointInternal;
    protected final String modTypeDesc;
    protected final String modTypeInternal;
    protected final Type entryPoint;
    protected final Type modType;
    protected final int javaVersion;
    
    protected ModWriter(CoreAPI core, MultiVersionModInfo info, int javaVersion) {
        this.core = core;
        Map<String,String[]> entryPointMethods = new HashMap<>();
        Map<String,Type> entryPointMethodTypes = new HashMap<>();
        mappedEntryPointMethods(entryPointMethods,entryPointMethodTypes);
        this.entryPointMethods = Collections.unmodifiableMap(entryPointMethods);
        this.entryPointMethodTypes = Collections.unmodifiableMap(entryPointMethodTypes);
        this.info = info;
        this.entryPoint = Type.getType(info.getEntryClass());
        this.modType = generatedModType(info);
        this.entryPointDesc = this.entryPoint.getDescriptor();
        this.entryPointInternal = this.entryPoint.getInternalName();
        this.modTypeDesc = this.modType.getDescriptor();
        this.modTypeInternal = this.modType.getInternalName();
        this.javaVersion = javaVersion;
    }
    
    protected void addClassAnnotations(ClassVisitor visitor) {}
    
    protected void addEntryHooks(MethodVisitor visitor, boolean isStatic, String method, boolean codeVisited) {
        if(!codeVisited) visitor.visitCode();
        for(String entryMethod : this.entryPointMethods.get(method)) {
            if(isStatic) visitor.visitFieldInsn(GETSTATIC,this.modTypeInternal,"INSTANCE",this.modTypeDesc);
            else visitor.visitVarInsn(ALOAD,0);
            visitor.visitFieldInsn(GETFIELD,this.modTypeInternal,"entryPoint",this.entryPointDesc);
            visitor.visitMethodInsn(INVOKEVIRTUAL, this.entryPointInternal,entryMethod, EMPTY_METHOD_DESC,false);
        }
    }
    
    protected void addFields(ClassVisitor visitor) {
        ASMHelper.addField(visitor,PRIVATE_STATIC,"INSTANCE",this.modType,null,null);
        ASMHelper.addField(visitor,PRIVATE_FINAL,"entryPoint",this.entryPoint,null,null);
    }
    
    protected Pair<ClassWriter,Type> addInnerClass(ClassVisitor outerClass, String innerName,
            Consumer<ClassVisitor> innerWriter) {
        Type innerType = TypeHelper.inner(this.modType,innerName);
        ClassWriter writer = ASMHelper.getWriter(this.javaVersion,PUBLIC_STATIC_FINAL,innerType);
        writer.visitOuterClass(this.modTypeInternal,null,null);
        outerClass.visitInnerClass(innerType.getInternalName(),this.modTypeInternal,innerName,PUBLIC_STATIC_FINAL);
        innerWriter.accept(writer);
        return new ImmutablePair<>(writer,innerType);
    }
    
    public final List<Pair<String,byte[]>> buildModClass() {
        List<Pair<String,byte[]>> classBytes = new ArrayList<>();
        ClassWriter writer = ASMHelper.getWriter(this.javaVersion,PUBLIC,this.modType);
        writeMod(writer,classBytes);
        finishWritingClass(writer,this.modType,(classpath,bytes) -> {
            TILRef.logDebug("Wrote bytecode for `{}` entrypoint to `{}`",this.info.getModID(),classpath);
            classBytes.add(new ImmutablePair<>(classpath, bytes));
        });
        return classBytes;
    }
    
    protected void classInit(MethodVisitor clinit) {}
    
    protected void constructor(MethodVisitor constructor) {
        addEntryHooks(constructor,false,"<init>",true);
    }
    
    protected void finishWritingClass(ClassWriter writer, Type type, BiConsumer<String,byte[]> byteCodeAcceptor) {
        String classpath = ClassPrinter.getClassPath(type.getInternalName());
        try {
            byte[] bytes = ASMHelper.finishWriting(writer,type,DEV);
            byteCodeAcceptor.accept(classpath,bytes);
        } catch(Throwable ex) {
            TILRef.logFatal("Failed to write bytecode for classpath {}",classpath,ex);
        }
    }
    
    protected MethodVisitor getConstructor(ClassVisitor visitor) {
        return ASMHelper.getConstructor(visitor,PUBLIC);
    }
    
    private Type generatedModType(MultiVersionModInfo info) {
        String pkgName = info.getEntryClass().getPackage().getName();
        String modName = info.getName().replace(" ","");
        return generatedModType(pkgName,modName,info.isClient(),info.isServer());
    }
    
    protected Type generatedModType(String pkgName, String modName, boolean client, boolean server) {
        String extension = "Generated"+(client ? (server ? "Common" : "Client") : (server ? "Server" : ""))+"Mod";
        return TypeHelper.fromBinary(pkgName+"."+modName+extension);
    }
    
    protected abstract Type getEventMethod(String className);
    
    protected abstract void mappedEntryPointMethods(Map<String,String[]> redirects, Map<String,Type> types);
    
    protected void mapEntryPointMethod(Map<String,String[]> redirects, Map<String,Type> types, String name, Type type,
            String ... methods) {
        redirects.put(name,methods);
        types.put(name,type);
    }
    
    protected final void writeAnnotationArray(AnnotationVisitor annotation, String name,
            Consumer<AnnotationVisitor> arrayWriter) {
        AnnotationVisitor array = annotation.visitArray(name);
        arrayWriter.accept(array);
        array.visitEnd();
    }
    
    protected final void writeClassAnnotation(ClassVisitor visitor, Type type,
            Consumer<AnnotationVisitor> annotationWriter) {
        AnnotationVisitor annotation = ASMHelper.getAnnotation(visitor,type);
        annotationWriter.accept(annotation);
        annotation.visitEnd();
    }
    
    protected final void writeClassInit(ClassVisitor visitor) {
        addClassAnnotations(visitor);
        addFields(visitor);
        writeMethod(visitor,ASMHelper::getClassInit,this::classInit);
    }
    
    protected final void writeConstructor(ClassVisitor visitor) {
        writeMethod(visitor,this::getConstructor,constructor -> {
            ASMHelper.addSuperConstructor(constructor,OBJECT_TYPE.getInternalName(),EMPTY_METHOD_DESC,false);
            constructor.visitVarInsn(ALOAD,0);
            ASMHelper.addNewInstance(constructor,this.entryPointInternal,EMPTY_METHOD_DESC,false);
            constructor.visitFieldInsn(PUTFIELD,this.modTypeInternal,"entryPoint",this.entryPointDesc);
            constructor.visitVarInsn(ALOAD,0);
            constructor.visitFieldInsn(PUTSTATIC,this.modTypeInternal,"INSTANCE",this.modTypeDesc);
            constructor(constructor);
        });
    }
    
    protected void writeInnerClass(Pair<ClassWriter,Type> writerPair, List<Pair<String,byte[]>> classBytes) {
        writeInnerClass(writerPair,(classpath,bytes) -> {
            TILRef.logDebug("Finished writing inner class {}",classpath);
            classBytes.add(new ImmutablePair<>(classpath,bytes));
        });
    }
    
    protected void writeInnerClass(Pair<ClassWriter,Type> writerPair, BiConsumer<String,byte[]> byteCodeAcceptor) {
        finishWritingClass(writerPair.getLeft(),writerPair.getRight(),byteCodeAcceptor);
    }
    
    protected final void writeMethod(ClassVisitor visitor, Function<ClassVisitor,MethodVisitor> methodGetter,
            Consumer<MethodVisitor> methodWriter) {
        MethodVisitor method = methodGetter.apply(visitor);
        methodWriter.accept(method);
        method.visitInsn(RETURN);
        ASMHelper.finishMethod(method);
    }
    
    protected final void writeMethodAnnotation(MethodVisitor method, Type type,
            Consumer<AnnotationVisitor> annotationWriter) {
        AnnotationVisitor annotation = ASMHelper.getAnnotation(method,type);
        annotationWriter.accept(annotation);
        annotation.visitEnd();
    }
    
    protected void writeMod(ClassWriter writer, List<Pair<String,byte[]>> classBytes) {
        writeClassInit(writer);
        writeConstructor(writer);
    }
}