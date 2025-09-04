package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;

/**
 * Write mod classes via ASM for proper multiloader/multiversion mod loading shenanigans
 */
public abstract class ModWriter implements CoreStateAccessor {
    
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
    
    protected ModWriter(CoreAPI core, MultiVersionModInfo info) {
        this.core = core;
        Entry<Map<String,String[]>,Map<String,Type>> maps = mappedEntryPointMethods(); //lol
        this.entryPointMethods = maps.getKey();
        this.entryPointMethodTypes = maps.getValue();
        this.info = info;
        this.entryPoint = Type.getType(info.getEntryClass());
        this.modType = generatedModType(info);
        this.entryPointDesc = this.entryPoint.getDescriptor();
        this.entryPointInternal = this.entryPoint.getInternalName();
        this.modTypeDesc = this.modType.getDescriptor();
        this.modTypeInternal = this.modType.getInternalName();
    }
    
    protected void addClassAnnotations(ClassVisitor visitor) {}
    
    protected void addEntryHooks(MethodVisitor method, boolean isStatic, String methodName, boolean codeVisited) {
        if(!codeVisited) method.visitCode();
        for(String entryMethod : this.entryPointMethods.get(methodName)) {
            entryPointGetter(method,isStatic);
            method.visitMethodInsn(INVOKEVIRTUAL,this.entryPointInternal,entryMethod,EMPTY_METHOD_DESC,false);
        }
    }
    
    protected void addFields(ClassVisitor visitor) {
        ASMHelper.addField(visitor,PUBLIC_STATIC,"INSTANCE",this.modType,null,null);
        ASMHelper.addField(visitor,PUBLIC_FINAL,"entryPoint",this.entryPoint,null,null);
    }
    
    protected Entry<ClassWriter,Type> addInnerClass(ClassVisitor outerClass, String innerName,
            Consumer<ClassVisitor> innerWriter) {
        return addInnerClass(outerClass,innerName,innerWriter,true,true);
    }
    
    protected Entry<ClassWriter,Type> addInnerClass(ClassVisitor outerClass, String innerName,
            Consumer<ClassVisitor> innerWriter, boolean client, boolean server) {
        Type innerType = TypeHelper.inner(this.modType,innerName);
        ClassWriter writer = ASMHelper.getWriter(JAVA_VERSION_ASM,PUBLIC_STATIC_FINAL,innerType,modInterfaces(client,server));
        writer.visitOuterClass(this.modTypeInternal,null,null);
        outerClass.visitInnerClass(innerType.getInternalName(),this.modTypeInternal,innerName,PUBLIC_STATIC_FINAL);
        innerWriter.accept(writer);
        return new SimpleImmutableEntry<>(writer,innerType);
    }
    
    public void basicContructorHandle(MethodVisitor constructor) {
        ASMHelper.addSuperConstructor(constructor,OBJECT_TYPE_NAME,EMPTY_METHOD_DESC,false);
    }
    
    public final List<Entry<String,byte[]>> buildModClass() {
        List<Entry<String,byte[]>> classBytes = new ArrayList<>();
        ClassWriter writer = ASMHelper.getWriter(JAVA_VERSION_ASM,PUBLIC,this.modType,modInterfaces(true,true));
        writeMod(writer,classBytes);
        finishWritingClass(writer,this.modType,(classpath,bytes) -> {
            TILRef.logDebug("Wrote bytecode for `{}` entrypoint to `{}`",this.info.getModID(),classpath);
            classBytes.add(new SimpleImmutableEntry<>(classpath, bytes));
        });
        return classBytes;
    }
    
    protected void classInit(MethodVisitor clinit) {}
    
    protected void constructor(MethodVisitor constructor) {
        addEntryHooks(constructor,false,"<init>",true);
    }
    
    protected void entryPointGetter(MethodVisitor visitor) {
        entryPointGetter(visitor,false);
    }
    
    protected void entryPointGetter(MethodVisitor method, boolean isStatic) {
        if(isStatic) method.visitFieldInsn(GETSTATIC,this.modTypeInternal,"INSTANCE",this.modTypeDesc);
        else method.visitVarInsn(ALOAD,0);
        method.visitFieldInsn(GETFIELD,this.modTypeInternal,"entryPoint",this.entryPointDesc);
    }
    
    protected abstract List<String[]> entryPointMappings();
    
    protected void finishWritingClass(ClassWriter writer, Type type,
            BiConsumer<String,byte[]> byteCodeAcceptor) {
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
    
    protected final InnerClassDataBuilder innerClassDataBuilder(ClassVisitor outer, String name,
            BiConsumer<InnerClassData,ClassVisitor> classInitHandle, String ... entryPoints) {
        return new InnerClassDataBuilder(outer,name,classInitHandle,entryPoints);
    }
    
    protected InnerClassData[] innerClasses(ClassVisitor outerClass) {
        return new InnerClassData[]{};
    }
    
    protected boolean isClient() {
        return this.core.getSide().isClient() && this.info.isClient();
    }
    
    protected boolean isServer() {
        return this.core.getSide().isServer() && this.info.isServer();
    }
    
    /**
     * lol
     */
    protected Entry<Map<String,String[]>,Map<String,Type>> mappedEntryPointMethods() {
        Map<String,String[]> redirects = new HashMap<>();
        Map<String,Type> types = new HashMap<>();
        for(String[] mappings : entryPointMappings()) {
            String name = mappings[0];
            redirects.put(name,Arrays.copyOfRange(mappings,2,mappings.length));
            types.put(name,getEventMethod(mappings[1]));
        }
        return new SimpleImmutableEntry<>(Collections.unmodifiableMap(redirects),Collections.unmodifiableMap(types));
    }
    
    protected String[] modInterfaces(boolean client, boolean server) {
        return new String[]{};
    }
    
    @SuppressWarnings("SameParameterValue")
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
    
    protected void writeConstructor(ClassVisitor visitor) {
        writeConstructor(visitor,constructor -> {});
    }
    
    protected final void writeConstructor(ClassVisitor visitor, Consumer<MethodVisitor> extraDataHandler) {
        writeMethod(visitor,this::getConstructor,constructor -> {
            ASMHelper.addSuperConstructor(constructor,OBJECT_TYPE_NAME,EMPTY_METHOD_DESC,false);
            constructor.visitVarInsn(ALOAD,0);
            constructor.visitFieldInsn(PUTSTATIC,this.modTypeInternal,"INSTANCE",this.modTypeDesc);
            constructor.visitVarInsn(ALOAD,0);
            ASMHelper.addNewInstance(constructor,this.entryPointInternal,EMPTY_METHOD_DESC,false);
            constructor.visitFieldInsn(PUTFIELD,this.modTypeInternal,"entryPoint",this.entryPointDesc);
            extraDataHandler.accept(constructor);
            constructor(constructor);
        });
    }
    
    protected void writeInnerClass(Entry<ClassWriter,Type> writerPair, List<Entry<String,byte[]>> classBytes) {
        if(Objects.nonNull(writerPair)) {
            writeInnerClass(writerPair,(classpath,bytes) -> {
                TILRef.logDebug("Finished writing inner class {}",classpath);
                classBytes.add(new SimpleImmutableEntry<>(classpath,bytes));
            });
        } else TILRef.logDebug("Not writing inner class for null writerPair");
    }
    
    protected void writeInnerClass(Entry<ClassWriter,Type> writerPair, BiConsumer<String,byte[]> byteCodeAcceptor) {
        finishWritingClass(writerPair.getKey(),writerPair.getValue(),byteCodeAcceptor);
    }
    
    protected void writeInnerConstructor(ClassVisitor visitor) {
        writeMethod(visitor,this::getConstructor,constructor -> {
            ASMHelper.addSuperConstructor(constructor,OBJECT_TYPE_NAME,EMPTY_METHOD_DESC,false);
            constructor.visitVarInsn(ALOAD,0);
            constructor.visitFieldInsn(PUTSTATIC,this.modTypeInternal,"INSTANCE",this.modTypeDesc);
        });
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
    
    protected void writeMod(ClassWriter writer, List<Entry<String,byte[]>> classBytes) {
        writeClassInit(writer);
        writeConstructor(writer);
        for(InnerClassData data : innerClasses(writer)) writeInnerClass(data.write(this),classBytes);
    }
    
    public static final class InnerClassData {
        
        final ClassVisitor outerClass;
        @Getter final boolean client;
        @Getter final boolean modBus;
        @Getter final boolean server;
        final String className;
        final String[] entryMethods;
        final BiConsumer<InnerClassData,ClassVisitor> classInitHandle;
        final BiConsumer<ModWriter,MethodVisitor> constructorHandle;
        final BiFunction<ModWriter,ClassVisitor,MethodVisitor> constructorInit;
        final BiConsumer<ClassVisitor,String> entryPointHandle;
        
        InnerClassData(ClassVisitor outerClass, String className, boolean modBus, boolean client, boolean server,
                String[] entryMethods, BiConsumer<InnerClassData,ClassVisitor> classInitHandle,
                BiFunction<ModWriter,ClassVisitor,MethodVisitor> constructorInit,
                BiConsumer<ModWriter,MethodVisitor> constructorHandle,
                BiConsumer<ClassVisitor,String> entryPointHandle) {
            this.outerClass = outerClass;
            this.className = className;
            this.client = client;
            this.modBus = modBus;
            this.server = server;
            this.entryMethods = entryMethods;
            this.classInitHandle = classInitHandle;
            this.constructorInit = constructorInit;
            this.constructorHandle = constructorHandle;
            this.entryPointHandle = entryPointHandle;
        }
        
        void addConstructor(ModWriter writer, ClassVisitor visitor) {
            this.constructorHandle.accept(writer,this.constructorInit.apply(writer,visitor));
        }
        
        void init(ModWriter writer, ClassVisitor innerVisitor) {
            this.classInitHandle.accept(this,innerVisitor);
            addConstructor(writer,innerVisitor);
        }
        
        public Entry<ClassWriter,Type> write(ModWriter writer) {
            return writer.addInnerClass(this.outerClass,this.className,
                    innerVisitor -> write(writer,innerVisitor),this.client,this.server);
        }
        
        void write(ModWriter writer, ClassVisitor innerVisitor) {
            init(writer,innerVisitor);
            for(String entryPoint : this.entryMethods) this.entryPointHandle.accept(innerVisitor,entryPoint);
        }
    }
    
    protected static final class InnerClassDataBuilder {
        
        final ClassVisitor outerClass;
        final String className;
        final BiConsumer<InnerClassData,ClassVisitor> classInitHandle;
        final String[] entryMethods;
        boolean client;
        boolean modBus;
        boolean server;
        BiConsumer<ModWriter,MethodVisitor> constructorHandle;
        BiFunction<ModWriter,ClassVisitor,MethodVisitor> constructorInit;
        BiConsumer<ClassVisitor,String> entryPointHandle;
        
        protected InnerClassDataBuilder(ClassVisitor outerClass, String className,
                BiConsumer<InnerClassData,ClassVisitor> classInitHandle, String ... entryMethods) {
            this.outerClass = outerClass;
            this.className = className;
            this.classInitHandle = classInitHandle;
            this.entryMethods = entryMethods;
        }
        
        public InnerClassDataBuilder allFlags() {
            return bothSides().modBus();
        }
        
        public InnerClassDataBuilder bothSides() {
            return client().server();
        }
        
        public InnerClassData build() {
            if(Objects.isNull(this.constructorInit)) this.constructorInit = ModWriter::getConstructor;
            if(Objects.isNull(this.constructorHandle)) this.constructorHandle = ModWriter::basicContructorHandle;
            return new InnerClassData(this.outerClass,this.className,this.modBus,this.client,this.server,
                    this.entryMethods,this.classInitHandle,this.constructorInit,this.constructorHandle,
                    this.entryPointHandle);
        }
        
        public InnerClassDataBuilder client() {
            this.client = true;
            return this;
        }
        
        public InnerClassDataBuilder constructorHandle(BiConsumer<ModWriter,MethodVisitor> handle) {
            this.constructorHandle = handle;
            return this;
        }
        
        public InnerClassDataBuilder constructorInit(BiFunction<ModWriter,ClassVisitor,MethodVisitor> init) {
            this.constructorInit = init;
            return this;
        }
        
        public InnerClassDataBuilder entryPointHandle(BiConsumer<ClassVisitor,String> handle) {
            this.entryPointHandle = handle;
            return this;
        }
        
        public InnerClassDataBuilder modBus() {
            this.modBus = true;
            return this;
        }
        
        public InnerClassDataBuilder modBusClient() {
            return modBus().client();
        }
        
        public InnerClassDataBuilder modBusServer() {
            return modBus().server();
        }
        
        public InnerClassDataBuilder server() {
            this.server = true;
            return this;
        }
        
        public InnerClassDataBuilder setFlags(int flags) {
            flags = MathHelper.clamp(flags,0,7);
            this.modBus = (flags>>2)==1; //111 -> 001
            this.client = ((flags>>1)&~2)==1; //111 -> (011 & ~010) -> (011 & 101) -> 001
            this.server = (flags&~6)==1; //111 -> (111 & ~110) -> (111 & 001) -> 001
            return this;
        }
    }
}