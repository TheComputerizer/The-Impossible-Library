package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.List;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public abstract class ModWriterFabric extends ModWriter {
    
    protected static final String FABRIC_HELPER_INTERNAL = Type.getType(FabricHelper.class).getInternalName();
    protected static final String FINALIZER_DESC = TypeHelper.method(VOID_TYPE,CommonEntryPoint.class).getDescriptor();
    
    protected ModWriterFabric(CoreAPI core, MultiVersionModInfo info, int javaVersion) {
        super(core,info,javaVersion);
    }
    
    protected void addEntryHooks(MethodVisitor visitor, boolean isStatic, String method) {
        addEntryHooks(visitor,isStatic,method,false);
    }
    
    protected Pair<ClassWriter,Type> addInnerEntryPoint(ClassVisitor outerClass, boolean client, String innerName) {
        return addInnerClass(outerClass,innerName,inner -> {
            writeMethod(inner,cv -> ASMHelper.getConstructor(cv,PUBLIC),constructor ->
                    ASMHelper.addSuperConstructor(constructor,OBJECT_TYPE.getInternalName(),EMPTY_METHOD_DESC,false));
            String init = client ? "onInitializeClient" : "onInitializeServer";
            writeMethod(inner,cv -> ASMHelper.getMethod(cv,PUBLIC,init),method -> addEntryHooks(method,true,init));
        },client,!client);
    }
    
    @Override protected Type getEventMethod(String className) {
        return EMPTY_METHOD;
    }
    
    @Override protected void mappedEntryPointMethods(Map<String,String[]> redirects, Map<String,Type> types) {
        mapEntryPointMethod(redirects,types,"<init>",EMPTY_METHOD,"onConstructed","onPreRegistration");
        mapEntryPointMethod(redirects,types,"onInitialize",EMPTY_METHOD,"onCommonSetup");
        mapEntryPointMethod(redirects,types,"onInitializeClient",EMPTY_METHOD,"checkClientSetup",
                            "onInterModEnqueue");
        mapEntryPointMethod(redirects,types,"onInitializeServer",EMPTY_METHOD,
                            "checkDedicatedServerSetup","onInterModEnqueue");
    }
    
    @Override protected void writeMod(ClassWriter writer, List<Pair<String,byte[]>> classBytes) {
        super.writeMod(writer,classBytes);
        final String init = "onInitialize";
        writeMethod(writer,cv -> ASMHelper.getMethod(cv,PUBLIC,init),method -> {
            addEntryHooks(method,false,init);
            method.visitVarInsn(ALOAD,0);
            method.visitFieldInsn(GETFIELD,this.modTypeInternal,"entryPoint",this.entryPointDesc);
            method.visitMethodInsn(INVOKESTATIC,FABRIC_HELPER_INTERNAL,"finalizeEntrypoints",FINALIZER_DESC,false);
        });
        if(this.info.isClient() && this.core.getSide().isClient())
            writeInnerClass(addInnerEntryPoint(writer,true,"LoaderClient"),classBytes);
        if(this.info.isServer() && this.core.getSide().isServer())
            writeInnerClass(addInnerEntryPoint(writer,false,"LoaderServer"),classBytes);
    }
}