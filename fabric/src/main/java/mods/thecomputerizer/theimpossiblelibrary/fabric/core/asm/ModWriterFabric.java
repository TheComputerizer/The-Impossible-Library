package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;

public class ModWriterFabric extends ModWriter {
    
    protected static final String FABRIC_HELPER_INTERNAL = Type.getType(FabricHelper.class).getInternalName();
    protected static final String FINALIZER_DESC = TypeHelper.voidMethod(CommonEntryPoint.class).getDescriptor();
    
    public ModWriterFabric(CoreAPI core, MultiVersionModInfo info) {
        this(core,info,ClientModInitializer.class,ModInitializer.class,DedicatedServerModInitializer.class);
    }
    
    protected ModWriterFabric(CoreAPI core, MultiVersionModInfo info, Class<?> client, Class<?> common,
            Class<?> server) {
        this(core,info,Type.getInternalName(client),Type.getInternalName(common),Type.getInternalName(server));
    }
    
    final String clientInit;
    final String commonInit;
    final String serverInit;
    
    protected ModWriterFabric(CoreAPI core, MultiVersionModInfo info, String client, String common, String server) {
        super(core,info);
        this.clientInit = client;
        this.commonInit = common;
        this.serverInit = server;
    }
    
    protected void addEntryHooks(MethodVisitor visitor, boolean isStatic, String method) {
        addEntryHooks(visitor,isStatic,method,false);
    }
    
    protected Entry<ClassWriter,Type> addInnerEntryPoint(ClassVisitor outerClass, boolean client) {
        if(client ? !isClient() : !isServer()) return null;
        return addInnerClass(outerClass,"Loader"+(client ? "Client" : "Server"),inner -> {
            writeMethod(inner,cv -> ASMHelper.getConstructor(cv,PUBLIC),constructor ->
                    ASMHelper.addSuperConstructor(constructor,OBJECT_TYPE_NAME,EMPTY_METHOD_DESC,false));
            String init = client ? "onInitializeClient" : "onInitializeServer";
            writeMethod(inner,cv -> ASMHelper.getMethod(cv,PUBLIC,init),
                        method -> addEntryHooks(method,true,init));
        },client,!client);
    }
    
    @Override protected Type getEventMethod(String className) {
        return EMPTY_METHOD;
    }
    
    @Override protected List<String[]> entryPointMappings() {
        return Arrays.asList(
                new String[]{"<init>","","onConstructed","onPreRegistration"},
                new String[]{"onInitialize","","onCommonSetup"},
                new String[]{"onInitializeClient","","checkClientSetup","onInterModEnqueue"},
                new String[]{"onInitializeServer","","checkDedicatedServerSetup","onInterModEnqueue"});
    }
    
    @Override protected String[] modInterfaces(boolean client, boolean server) {
        return new String[]{client && server ? this.commonInit : (client ? this.clientInit : this.serverInit)};
    }
    
    @Override protected void writeMod(ClassWriter writer, List<Entry<String,byte[]>> classBytes) {
        super.writeMod(writer,classBytes);
        final String init = "onInitialize";
        writeMethod(writer,cv -> ASMHelper.getMethod(cv,PUBLIC,init),method -> {
            addEntryHooks(method,false,init);
            entryPointGetter(method);
            method.visitMethodInsn(INVOKESTATIC,FABRIC_HELPER_INTERNAL,"finalizeEntrypoints",FINALIZER_DESC,
                                   false);
        });
        for(boolean b : BOOLEAN_VALUES) writeInnerClass(addInnerEntryPoint(writer,b),classBytes);
    }
}