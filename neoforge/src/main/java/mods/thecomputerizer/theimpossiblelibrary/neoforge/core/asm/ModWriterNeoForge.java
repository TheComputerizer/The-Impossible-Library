package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public abstract class ModWriterNeoForge extends ModWriter {
    
    protected static final Type DIST = TypeHelper.neoforged("api/distmarker/Dist");
    protected static final Type EVENT_BUS = TypeHelper.neoforged("bus/api/IEventBus");
    protected static final Type EVENT_SUBSCRIBER = TypeHelper.neofml("common/Mod$EventBusSubscriber");
    protected static final Type EVENT_SUBSCRIBER_BUS = TypeHelper.neofml("common/Mod$EventBusSubscriber$Bus");
    protected static final Type MOD_ANNOTATION = TypeHelper.neofml("common/Mod");
    protected static final Type SUBSCRIBE_EVENT = TypeHelper.neoforged("eventbus/api/SubscribeEvent");
    
    protected ModWriterNeoForge(CoreAPI core, MultiVersionModInfo info, int javaVersion) {
        super(core,info,javaVersion);
    }
    
    @Override protected void addClassAnnotations(ClassVisitor visitor) {
        writeClassAnnotation(visitor,MOD_ANNOTATION,mod -> mod.visit("value",this.info.getModID()));
    }
    
    protected void addEntryHooks(MethodVisitor visitor, String method) {
        super.addEntryHooks(visitor,true,method,false);
    }
    
    protected void addEventSubscriber(ClassVisitor visitor, String modid, boolean modBus, boolean client,
            boolean server) {
        writeClassAnnotation(visitor,EVENT_SUBSCRIBER,annotation -> {
            annotation.visit("modid",modid);
            if(modBus) annotation.visitEnum("bus",EVENT_SUBSCRIBER_BUS.getDescriptor(),"MOD");
            if((client && !server) || (!client && server))
                writeAnnotationArray(annotation,"value",array ->
                        array.visitEnum(null,DIST.getDescriptor(),client ? "CLIENT" : "DEDICATED_SERVER"));
        });
    }
    
    protected Pair<ClassWriter,Type> addInnerEventSubscriber(ClassVisitor outerClass, String modid, boolean modBus,
            boolean client, boolean server, String innerName, String ... entryMethods) {
        return addInnerClass(outerClass,innerName,inner -> {
            addEventSubscriber(inner,modid,modBus,client,server);
            for(String methodName : entryMethods) {
                Type eventType = this.entryPointMethodTypes.get(methodName);
                writeMethod(inner,cv -> ASMHelper.getMethod(cv,PUBLIC_STATIC,methodName,eventType.getArgumentTypes()),
                        method -> {
                            writeMethodAnnotation(method,SUBSCRIBE_EVENT,annotation -> {});
                            addEntryHooks(method,methodName);
                        });
            }
        });
    }
    
    @Override protected MethodVisitor getConstructor(ClassVisitor visitor) {
        return ASMHelper.getConstructor(visitor,PUBLIC,new Type[]{EVENT_BUS});
    }
    
    @Override protected Type getEventMethod(String className) {
        className = (className.startsWith("FMLServer") ? "server" : "lifecycle")+"/"+className;
        return TypeHelper.method(VOID_TYPE,TypeHelper.neofml("event/"+className));
    }
    
    @Override protected void mappedEntryPointMethods(Map<String,String[]> redirects, Map<String,Type> types) {
        mapEntryPointMethod(redirects,types,"<init>",EMPTY_METHOD,"onConstructed","onPreRegistration");
        mapEntryPointMethod(redirects,types,"clientSetup",getEventMethod("FMLClientSetupEvent"),
                            "checkClientSetup");
        mapEntryPointMethod(redirects,types,"commonSetup",getEventMethod("FMLCommonSetupEvent"),
                            "onCommonSetup");
        mapEntryPointMethod(redirects,types,"dedicatedServerSetup",getEventMethod("FMLDedicatedServerSetupEvent"),
                            "checkDedicatedServerSetup");
        mapEntryPointMethod(redirects,types,"interModEnqueue",getEventMethod("InterModEnqueueEvent"),
                            "onInterModEnqueue");
        mapEntryPointMethod(redirects,types,"interModProcess",getEventMethod("InterModProcessEvent"),
                            "onInterModProcess");
        mapEntryPointMethod(redirects,types,"loadComplete",getEventMethod("FMLLoadCompleteEvent"),
                            "onLoadComplete");
        mapEntryPointMethod(redirects,types,"serverAboutToStart", getEventMethod("FMLServerAboutToStartEvent"),
                            "onServerAboutToStart");
        mapEntryPointMethod(redirects,types,"serverStarting",getEventMethod("FMLServerStartingEvent"),
                            "onServerStarting");
        mapEntryPointMethod(redirects,types,"serverStarted",getEventMethod("FMLServerStartedEvent"),
                            "onServerStarted");
        mapEntryPointMethod(redirects,types,"serverStopping",getEventMethod("FMLServerStoppingEvent"),
                            "onServerStopping");
        mapEntryPointMethod(redirects,types,"serverStopped",getEventMethod("FMLServerStoppedEvent"),
                            "onServerStopped");
    }
    
    /**
     * Sets the extraData field of CommonEntryPoint to the IEventBus passed into the constructor of the written class
     * so that it is internally accessible
     */
    @Override protected final void writeConstructor(ClassVisitor visitor) {
        final String extraDataDesc = TypeHelper.voidMethodDesc(OBJECT_TYPE);
        writeConstructor(visitor,constructor -> {
            constructor.visitVarInsn(ALOAD,0);
            constructor.visitFieldInsn(GETFIELD,this.modTypeInternal,"entryPoint",this.entryPointDesc);
            constructor.visitVarInsn(ALOAD,1); //Load IEventBus parameter
            constructor.visitMethodInsn(INVOKEVIRTUAL,this.entryPointInternal,"setExtraData",extraDataDesc,false);
        });
    }
}