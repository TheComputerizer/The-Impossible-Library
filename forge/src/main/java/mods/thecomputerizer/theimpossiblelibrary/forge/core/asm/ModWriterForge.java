package mods.thecomputerizer.theimpossiblelibrary.forge.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Arrays;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.EMPTY_METHOD;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.PUBLIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.PUBLIC_STATIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.RETURN;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModWriterForge extends ModWriter {
    
    protected static final Type DIST = TypeHelper.forge("api/distmarker/Dist");
    protected static final Type EVENT_SUBSCRIBER = TypeHelper.fml("common/Mod$EventBusSubscriber");
    protected static final Type EVENT_SUBSCRIBER_BUS = TypeHelper.fml("common/Mod$EventBusSubscriber$Bus");
    protected static final Type JAVA_LOADING_CONTEXT = TypeHelper.fml("javafmlmod/FMLJavaModLoadingContext");
    protected static final Type MOD_ANNOTATION = TypeHelper.fml("common/Mod");
    protected static final Type SUBSCRIBE_EVENT = TypeHelper.forge("eventbus/api/SubscribeEvent");
    
    public ModWriterForge(CoreAPI core, MultiVersionModInfo info) {
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
        writeClassAnnotation(visitor,EVENT_SUBSCRIBER,annotation -> {
            annotation.visit("modid",this.info.getModID());
            if(modBus) annotation.visitEnum("bus",EVENT_SUBSCRIBER_BUS.getDescriptor(),"MOD");
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
        return Arrays.asList(new String[]{"<init>","","onConstructed","onPreRegistration"},
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
    
    @Override protected Type getEventMethod(String className) {
        if(TextHelper.isBlank(className)) return EMPTY_METHOD;
        if(V18_OR_LATER && className.contains("FMLServer")) {
            className = className.replace("FML","");
            return TypeHelper.method(VOID_TYPE,TypeHelper.forge("event/server/"+className));
        }
        className = (className.startsWith("FMLServer") ? "server" : "lifecycle")+"/"+className;
        return TypeHelper.method(VOID_TYPE,TypeHelper.fml("event/"+className));
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
}