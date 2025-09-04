package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModWriterNeoForge extends ModWriter {
    
    protected static final String EVENT_SUBSCRIBER_NAME = (JAVA_21 ? "" : "Mod$")+"EventBusSubscriber";
    protected static final Type DIST = TypeHelper.neoforged("api/distmarker/Dist");
    protected static final Type EVENT_BUS = TypeHelper.neoforged("bus/api/IEventBus");
    protected static final Type EVENT_SUBSCRIBER = TypeHelper.neofml("common/"+EVENT_SUBSCRIBER_NAME);
    protected static final Type EVENT_SUBSCRIBER_BUS = TypeHelper.neofml("common/"+EVENT_SUBSCRIBER_NAME+"$Bus");
    protected static final Type MOD_ANNOTATION = TypeHelper.neofml("common/Mod");
    protected static final Type SUBSCRIBE_EVENT = TypeHelper.neoforged("bus/api/SubscribeEvent");
    
    public ModWriterNeoForge(CoreAPI core, MultiVersionModInfo info) {
        super(core,info);
    }
    
    @Override protected void addClassAnnotations(ClassVisitor visitor) {
        writeClassAnnotation(visitor,MOD_ANNOTATION,mod -> mod.visit("value",this.info.getModID()));
    }
    
    protected void addEntryHooks(MethodVisitor visitor, String method) {
        super.addEntryHooks(visitor,true,method,false);
    }
    
    protected void addEventSubscriber(InnerClassData data, ClassVisitor visitor) {
        addEventSubscriber(visitor,data.isModBus(),data.isClient(),data.isServer());
    }
    
    protected void addEventSubscriber(ClassVisitor visitor, boolean modBus, boolean client,
            boolean server) {
        writeClassAnnotation(visitor,getEventSubscriberType(),annotation -> {
            annotation.visit("modid",this.info.getModID());
            if(modBus) annotation.visitEnum("bus",getEventSubscriberBusType().getDescriptor(),"MOD");
            if((client && !server) || (!client && server))
                writeAnnotationArray(annotation,"value",array ->
                        array.visitEnum(null,DIST.getDescriptor(),client ? "CLIENT" : "DEDICATED_SERVER"));
        });
    }
    
    protected InnerClassData buildInnerClassData(ClassVisitor outerClass, String className, int flags,
            String ... entryPoints) {
        return buildInnerClassData(innerClassDataBuilder(outerClass,className,entryPoints).setFlags(flags));
    }
    
    protected InnerClassData buildInnerClassData(InnerClassDataBuilder builder) {
        return builder.constructorInit((writer,cv) -> ASMHelper.getConstructor(cv,PUBLIC))
                .constructorHandle((writer,constructor) -> {
                    writer.basicContructorHandle(constructor);
                    constructor.visitInsn(RETURN);
                    ASMHelper.finishMethod(constructor);
                })
                .entryPointHandle((visitor,entryPoint) -> {
                    final Type[] args = this.entryPointMethodTypes.get(entryPoint).getArgumentTypes();
                    writeMethod(visitor,cv -> ASMHelper.getMethod(cv,PUBLIC_STATIC,entryPoint,args),
                    method -> {
                        writeMethodAnnotation(method,SUBSCRIBE_EVENT,annotation -> {});
                        addEntryHooks(method,entryPoint);
                    });
        }).build();
    }
    
    @Override protected List<String[]> entryPointMappings() {
        return List.of(new String[]{"<init>","","onConstructed","onPreRegistration"},
                new String[]{"clientSetup","FMLClientSetupEvent","checkClientSetup"},
                new String[]{"commonSetup","FMLCommonSetupEvent","onCommonSetup"},
                new String[]{"dedicatedServerSetup","FMLDedicatedServerSetupEvent","checkDedicatedServerSetup"},
                new String[]{"interModEnqueue","InterModEnqueueEvent","onInterModEnqueue"},
                new String[]{"interModProcess","InterModProcessEvent","onInterModProcess"},
                new String[]{"loadComplete","FMLLoadCompleteEvent","onLoadComplete"},
                new String[]{"serverAboutToStart","FMLServerAboutToStartEvent","onServerAboutToStart"},
                new String[]{"serverStarting","FMLServerStartingEvent","onServerStarting"},
                new String[]{"serverStarted","FMLServerStartedEvent","onServerStarted"},
                new String[]{"serverStopping","FMLServerStoppingEvent","onServerStopping"},
                new String[]{"serverStopped","FMLServerStoppedEvent","onServerStopped"});
    }
    
    @Override protected MethodVisitor getConstructor(ClassVisitor visitor) {
        return ASMHelper.getConstructor(visitor,PUBLIC,new Type[]{EVENT_BUS});
    }
    
    @Override protected Type getEventMethod(String className) {
        if(TextHelper.isBlank(className)) return EMPTY_METHOD;
        if(className.contains("FMLServer")) {
            className = className.replace("FML","");
            return TypeHelper.method(VOID_TYPE,TypeHelper.neoforge("event/server/"+className));
        }
        return TypeHelper.method(VOID_TYPE,TypeHelper.neofml("event/lifecycle/"+className));
    }
    
    protected Type getEventSubscriberBusType() {
        return EVENT_SUBSCRIBER_BUS;
    }
    
    protected Type getEventSubscriberType() {
        return EVENT_SUBSCRIBER;
    }
    
    protected InnerClassDataBuilder innerClassDataBuilder(ClassVisitor outer, String name, String ... entryPoints) {
        return innerClassDataBuilder(outer,name,this::addEventSubscriber,entryPoints);
    }
    
    @Override protected InnerClassData[] innerClasses(ClassVisitor outerClass) {
        return new InnerClassData[]{
                buildInnerClassData(outerClass,"LoaderClient",6,"clientSetup"),
                buildInnerClassData(outerClass,"LoaderCommon",7,"commonSetup",
                                    "interModEnqueue","interModProcess","loadComplete"),
                buildInnerClassData(outerClass,"LoaderServer",5,"dedicatedServerSetup"),
                buildInnerClassData(outerClass,"ServerLifecycle",3,"serverAboutToStart",
                                    "serverStarting","serverStarted","serverStopping","serverStopped")
        };
    }
    
    /**
     * Sets the extraData field of CommonEntryPoint to the IEventBus passed into the constructor of the written class
     * so that it is internally accessible
     */
    @Override protected final void writeConstructor(ClassVisitor cv) {
        writeConstructor(cv,constructor -> {
            entryPointGetter(constructor); //Load entrypoint field
            constructor.visitVarInsn(ALOAD,1); //Load IEventBus parameter
            constructor.visitMethodInsn(INVOKEVIRTUAL,this.entryPointInternal,"setExtraData",
                    VOID_OBJECT_METHOD_DESC,false);
        });
    }
}