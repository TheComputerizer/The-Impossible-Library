package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ClassPrinter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public class MultiVersionModWriter {
    
    private static final Type BOOTSTRAP = TypeHelper.method(CallSite.class,Lookup.class,String.class, MethodType.class,
            MethodType.class,MethodHandle.class,MethodType.class);
    private static final Type COMMON_ENTRY = Type.getType(CommonEntryPoint.class);
    private static final Type CONSUMER = Type.getType(Consumer.class);
    private static final Type CORE_1_16_5 = TypeHelper.get("mods/thecomputerizer/theimpossiblelibrary/forge/v16/m5/core/TILCore1_16_5");
    private static final Type FML_CONTAINER = TypeHelper.fml("javafmlmod/FMLModContainer");
    private static final Type FORGE_DIST = TypeHelper.forge("api/distmarker/Dist");
    private static final Type FORGE_EVENT_BUS = TypeHelper.forge("eventbus/api/IEventBus");
    private static final Type FORGE_EVENT_SUBSCRIBER = TypeHelper.fml("common/Mod$EventBusSubscriber");
    private static final Type FORGE_EVENT_SUBSCRIBER_BUS = TypeHelper.fml("common/Mod$EventBusSubscriber$Bus");
    private static final Type FORGE_LOADING_CONTEXT = TypeHelper.fml("javafmlmod/FMLJavaModLoadingContext");
    private static final Type FORGE_MOD = TypeHelper.fml("common/Mod");
    private static final Type FORGE_SUBSCRIBE_EVENT = TypeHelper.forge("eventbus/api/SubscribeEvent");
    private static final Type LAMBDA_META_FACTORY = TypeHelper.lang("invoke/LambdaMetafactory");
    private static final Type LEGACY_EVENT_HANDLER = TypeHelper.fml("common/Mod$EventHandler");
    private static final Type MOD_LOADING_CONTEXT = TypeHelper.fml("fml/ModLoadingContext");

    private static void addEntryHooks(MethodVisitor visitor, Type type, boolean isStatic, Type apiType,
            String ... apiMethods) {
        for(String method : apiMethods) {
            if(isStatic) visitor.visitFieldInsn(GETSTATIC,type.getInternalName(),"INSTANCE",type.getDescriptor());
            else visitor.visitVarInsn(ALOAD,0);
            visitor.visitFieldInsn(GETFIELD,type.getInternalName(),"entryPoint",apiType.getDescriptor());
            visitor.visitMethodInsn(INVOKEVIRTUAL,apiType.getInternalName(),method,VOID_EMPTY_METHOD.getDescriptor(),false);
        }
        visitor.visitInsn(RETURN);
    }
    
    private static ClassVisitor addInnerClass(int javaVer, ClassVisitor outerClass, Type type, Type innerType,
            String innerName, String modid, boolean client, boolean server, boolean modBus) {
        ClassVisitor visitor = ASMHelper.getWriter(javaVer,PUBLIC,innerType);
        visitor.visitOuterClass(type.getInternalName(),null,null);
        if(CoreAPI.isForge()) {
            AnnotationVisitor eventHandler = ASMHelper.getAnnotation(visitor,FORGE_EVENT_SUBSCRIBER);
            eventHandler.visit("modid",modid);
            if(modBus) eventHandler.visitEnum("bus",FORGE_EVENT_SUBSCRIBER_BUS.getDescriptor(),"MOD");
            if(!(client && server)) {
                AnnotationVisitor dists = eventHandler.visitArray("value");
                if(client) dists.visitEnum(null,FORGE_DIST.getDescriptor(),"CLIENT");
                if(server) dists.visitEnum(null,FORGE_DIST.getDescriptor(),"DEDICATED_SERVER");
                dists.visitEnd();
            }
            eventHandler.visitEnd();
        }
        outerClass.visitInnerClass(innerType.getInternalName(),type.getInternalName(),innerName,PUBLIC_STATIC_FINAL);
        return visitor;
    }

    private static Type generateClassType(String pkgName, String entryType, String name) {
        return Type.getType("L"+pkgName.replace('.','/')+"/"+
                name.replace(" ","")+"Generated"+entryType+"Mod;");
    }

    private static String getEntryType(boolean client, boolean server) {
        return client ? (server ? "Common" : "Client") : "";
    }

    private static MethodVisitor getEventHandler(ClassVisitor visitor, int access, String name, Type eventType) {
        return ASMHelper.getMethod(visitor,access,name,eventType);
    }

    private static Type getEventType(ModLoader loader, String methodName) {
        if(loader==LEGACY) {
            switch(methodName) {
                case "preInit": return getLegacyEventType("FMLPreInitializationEvent");
                case "init": return getLegacyEventType("FMLInitializationEvent");
                case "postInit": return getLegacyEventType("FMLPostInitializationEvent");
                case "loadComplete": return getLegacyEventType("FMLLoadCompleteEvent");
                case "serverAboutToStart": return getLegacyEventType("FMLServerAboutToStartEvent");
                case "serverStarting": return getLegacyEventType("FMLServerStartingEvent");
                case "serverStarted": return getLegacyEventType("FMLServerStartedEvent");
                case "serverStopping": return getLegacyEventType("FMLServerStoppingEvent");
                case "serverStopped": return getLegacyEventType("FMLServerStoppedEvent");
                default: return VOID_EMPTY_METHOD;
            }
        }
        else if(loader==FORGE) {
            switch(methodName) {
                case "clientSetup": return getForgeEventType("FMLClientSetupEvent",false);
                case "commonSetup": return getForgeEventType("FMLCommonSetupEvent",false);
                case "dedicatedServerSetup": return getForgeEventType("FMLDedicatedServerSetupEvent",false);
                case "interModEnqueue": return getForgeEventType("InterModEnqueueEvent",false);
                case "interModProcess": return getForgeEventType("InterModProcessEvent",false);
                case "loadComplete": return getForgeEventType("FMLLoadCompleteEvent",false);
                case "serverAboutToStart": return getForgeEventType("FMLServerAboutToStartEvent",true);
                case "serverStarting": return getForgeEventType("FMLServerStartingEvent",true);
                case "serverStarted": return getForgeEventType("FMLServerStartedEvent",true);
                case "serverStopping": return getForgeEventType("FMLServerStoppingEvent",true);
                case "serverStopped": return getForgeEventType("FMLServerStoppedEvent",true);
                default: return VOID_TYPE;
            }
        }
        return VOID_TYPE;
    }

    private static Type getForgeEventType(String name, boolean server) {
        return Type.getType("Lnet/minecraftforge/fml/event/"+(server ? "server/" : "lifecycle/")+name+";");
    }

    private static Type getLegacyEventType(String name) {
        return Type.getType("Lnet/minecraftforge/fml/common/event/"+name+";");
    }
    
    private static void getModid(MethodVisitor visitor, String owner, Type api) {
        String commonEntryOwner = COMMON_ENTRY.getInternalName();
        String desc = Type.getMethodDescriptor(STRING_TYPE);
        visitor.visitVarInsn(ALOAD,0);
        visitor.visitFieldInsn(GETFIELD,owner,"entryPoint",api.getDescriptor());
        visitor.visitMethodInsn(INVOKEVIRTUAL,commonEntryOwner,"getModID",desc,false);
    }
    
    private static void log(MethodVisitor visitor) {
        Type refType = Type.getType(TILDev.class);
        String desc = Type.getMethodDescriptor(VOID_TYPE,STRING_TYPE,OBJECT_TYPE);
        visitor.visitMethodInsn(INVOKESTATIC,refType.getInternalName(),"logFromASM",desc,false);
    }

    private static Map<String,String[]> mapFabricEntryPoints() { //TODO
        return new HashMap<>();
    }

    private static Map<String,String[]> mapForgeEntryPoints() {//TODO universally handle onDedicatedServerSetup & onClientSetup?
        Map<String,String[]> map = new HashMap<>();
        map.put("<init>",new String[]{"onConstructed","onPreRegistration"});
        map.put("commonSetup",new String[]{"onCommonSetup"});
        map.put("clientSetup",new String[]{"checkClientSetup"});
        map.put("dedicatedServerSetup",new String[]{"checkDedicatedServerSetup"});
        map.put("interModEnqueue",new String[]{"onInterModEnqueue"});
        map.put("interModProcess",new String[]{"onInterModProcess"});
        map.put("loadComplete",new String[]{"onLoadComplete"});
        map.put("serverAboutToStart",new String[]{"onServerAboutToStart"});
        map.put("serverStarting",new String[]{"onServerStarting"});
        map.put("serverStarted",new String[]{"onServerStarted"});
        map.put("serverStopping",new String[]{"onServerStopping"});
        map.put("serverStopped",new String[]{"onServerStopped"});
        return map;
    }

    private static Map<String,String[]> mapLegacyEntryPoints() {
        Map<String,String[]> map = new HashMap<>();
        map.put("<init>",new String[]{"onConstructed"});
        map.put("preInit",new String[]{"onPreRegistration"});
        map.put("init",new String[]{"onCommonSetup","checkClientSetup","checkDedicatedServerSetup"});
        map.put("postInit",new String[]{"onInterModEnqueue","onInterModProcess"});
        map.put("loadComplete",new String[]{"onLoadComplete"});
        map.put("serverAboutToStart",new String[]{"onServerAboutToStart"});
        map.put("serverStarting",new String[]{"onServerStarting"});
        map.put("serverStarted",new String[]{"onServerStarted"});
        map.put("serverStopping",new String[]{"onServerStopping"});
        map.put("serverStopped",new String[]{"onServerStopped"});
        return map;
    }

    private static Map<String,String[]> mapNeoForgeEntryPoints() { //TODO
        return new HashMap<>();
    }

    private static Type writeClassInit(ClassVisitor visitor, Type type, Class<?> entryRef) {
        Type apiType = Type.getType(entryRef);
        ASMHelper.addField(visitor,PRIVATE_STATIC,"INSTANCE",type,null,null);
        ASMHelper.addField(visitor,PRIVATE_FINAL,"entryPoint",apiType,null,null);
        return apiType;
    }

    private static void writeConstructor(ClassVisitor visitor, Type type, Type apiType,
            Map<String,String[]> entryPoints, String modid) {
        String name = type.getInternalName();
        String apiName = apiType.getInternalName();
        MethodVisitor constructor = ASMHelper.getConstructor(visitor,PUBLIC);
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitMethodInsn(INVOKESPECIAL,OBJECT_TYPE.getInternalName(),"<init>",VOID_EMPTY_METHOD.getDescriptor(),false);
        if(MODID.equals(modid)) {
            if(CoreAPI.getInstance().getVersion()==V16) {
                String coreName = CORE_1_16_5.getInternalName();
                constructor.visitTypeInsn(NEW,coreName);
                constructor.visitInsn(DUP);
                constructor.visitMethodInsn(INVOKESPECIAL,coreName,"<init>",VOID_EMPTY_METHOD.getDescriptor(),false);
            }
        }
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitTypeInsn(NEW,apiName);
        constructor.visitInsn(DUP);
        constructor.visitMethodInsn(INVOKESPECIAL,apiName,"<init>",VOID_EMPTY_METHOD.getDescriptor(),false);
        constructor.visitFieldInsn(PUTFIELD,name,"entryPoint",apiType.getDescriptor());
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitFieldInsn(PUTSTATIC,name,"INSTANCE",type.getDescriptor());
        addEntryHooks(constructor,type,false,apiType,entryPoints.getOrDefault("<init>",new String[]{}));
        ASMHelper.finishMethod(constructor);
    }

    private static void writeFabricMod(ClassVisitor visitor, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapFabricEntryPoints();
        Type apiType = writeClassInit(visitor,type,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints,info.getModID());
    }
    
    private static void writeForgeLifecycleEvents(int javaVer, ClassVisitor visitor, Type type, Type apiType,
            String modid, Map<String,String[]> modEvents, Map<String,String[]> forgeEvents,
            List<Pair<String,byte[]>> classes) {
        Type commonType = TypeHelper.inner(type,"CommonLoader");
        ClassVisitor commonVisitor = addInnerClass(javaVer,visitor,type,commonType,"CommonLoader",
                                                   modid,true,true,true);
        ClassVisitor realVisitor = commonVisitor;
        Type innerType = null;
        for(Entry<String,String[]> modEvent : modEvents.entrySet()) {
            String method = modEvent.getKey();
            if(Misc.equalsAny(method,"clientSetup","dedicatedServerSetup")) {
                boolean client = method.equals("clientSetup");
                String innerName = (client ? "Client" : "Server")+"Loader";
                innerType = TypeHelper.inner(type,innerName);
                realVisitor = addInnerClass(javaVer,visitor,type,innerType,innerName,modid,client,!client,true);
            }
            Type eventType = getEventType(FORGE,method);
            MethodVisitor mv = getEventHandler(realVisitor,PUBLIC_STATIC,method,eventType);
            AnnotationVisitor eventSubscriber = ASMHelper.getAnnotation(mv,FORGE_SUBSCRIBE_EVENT);
            eventSubscriber.visitEnd();
            mv.visitCode();
            addEntryHooks(mv,type,true,apiType,modEvent.getValue());
            ASMHelper.finishMethod(mv);
            if(Objects.nonNull(innerType)) {
                byte[] bytes = ASMHelper.finishWriting((ClassWriter)realVisitor,innerType,DEV);
                classes.add(new ImmutablePair<>(innerType.getClassName(),bytes));
                realVisitor = commonVisitor;
            }
        }
        byte[] bytes = ASMHelper.finishWriting((ClassWriter)commonVisitor,commonType,DEV);
        classes.add(new ImmutablePair<>(commonType.getClassName(),bytes));
        innerType = TypeHelper.inner(type,"LifecyleEvents");
        realVisitor = addInnerClass(javaVer,visitor,type,innerType,"LifecyleEvents",modid,true,true,false);
        for(Entry<String,String[]> forgeEvent : forgeEvents.entrySet()) {
            String method = forgeEvent.getKey();
            Type eventType = getEventType(FORGE,method);
            MethodVisitor mv = getEventHandler(realVisitor,PUBLIC_STATIC,method,eventType);
            AnnotationVisitor eventSubscriber = ASMHelper.getAnnotation(mv,FORGE_SUBSCRIBE_EVENT);
            eventSubscriber.visitEnd();
            mv.visitCode();
            addEntryHooks(mv,type,true,apiType,forgeEvent.getValue());
            ASMHelper.finishMethod(mv);
        }
        bytes = ASMHelper.finishWriting((ClassWriter)realVisitor,innerType,DEV);
        classes.add(new ImmutablePair<>(innerType.getClassName(),bytes));
    }

    private static void writeForgeMod(int javaVer, ClassVisitor visitor, Type type, MultiVersionModInfo info,
            List<Pair<String,byte[]>> classes) {
        Map<String,String[]> entryPoints = mapForgeEntryPoints();
        AnnotationVisitor mod = ASMHelper.getAnnotation(visitor,FORGE_MOD); //add @Mod annotation
        mod.visit("value",info.getModID());
        mod.visitEnd();
        Type apiType = writeClassInit(visitor,type,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints,info.getModID());
        Map<String,String[]> modEvents = new HashMap<>();
        Map<String,String[]> forgeEvents = new HashMap<>();
        for(Entry<String,String[]> entryPoint : entryPoints.entrySet()) {
            String method = entryPoint.getKey();
            String[] events = entryPoint.getValue();
            switch(entryPoint.getKey()) {
                case "clientSetup": //TODO Fix the ordering so these actually work
                case "dedicatedServerSetup":
                case "<init>": continue;
                case "commonSetup":
                case "interModEnqueue":
                case "interModProcess": {
                    modEvents.put(method,events);
                    continue;
                }
                default: forgeEvents.put(method,events);
            }
        }
        writeForgeLifecycleEvents(javaVer,visitor,type,apiType,info.getModID(),modEvents,forgeEvents,classes);
    }

    private static void writeLegacyMod(ClassVisitor visitor, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapLegacyEntryPoints();
        AnnotationVisitor mod = ASMHelper.getAnnotation(visitor,FORGE_MOD); //add @Mod annotation
        mod.visit("modid",info.getModID());
        mod.visit("name",info.getName());
        mod.visit("version",info.getVersion());
        mod.visitEnd();
        Type apiType = writeClassInit(visitor,type,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints,info.getModID());
        for(Entry<String,String[]> entryPoint : entryPoints.entrySet()) {
            String method = entryPoint.getKey();
            if(method.equals("<init>")) continue;
            Type eventType = getEventType(LEGACY,method);
            MethodVisitor mv = getEventHandler(visitor,PUBLIC,method,eventType);
            AnnotationVisitor eventHandler = ASMHelper.getAnnotation(mv,LEGACY_EVENT_HANDLER);
            eventHandler.visitEnd();
            mv.visitCode();
            addEntryHooks(mv,type,false,apiType,entryPoint.getValue());
            ASMHelper.finishMethod(mv);
        }
    }

    public static List<Pair<String,byte[]>> buildModClass(int javaVer, ModLoader modLoader, MultiVersionModInfo info) {
        List<Pair<String,byte[]>> classes = new ArrayList<>();
        String pkgName = info.getEntryClass().getPackage().getName();
        Type type = generateClassType(pkgName,getEntryType(info.isClient(),info.isServer()),info.getName());
        ClassWriter writer = ASMHelper.getWriter(javaVer,PUBLIC,type);
        switch(modLoader) {
            case FABRIC: {
                writeFabricMod(writer,type,info);
                break;
            }
            case FORGE: {
                writeForgeMod(javaVer,writer,type,info,classes);
                break;
            }
            case LEGACY: {
                writeLegacyMod(writer,type,info);
                break;
            }
            case NEOFORGE: {
                writeNeoForgeMod(writer,type,info);
                break;
            }
        }
        try {
            byte[] bytes = ASMHelper.finishWriting(writer,type,DEV);
            String classpath = ClassPrinter.getClassPath(type.getInternalName());
            TILRef.logDebug("Wrote bytecode for `{}` entrypoint to `{}`",info.getModID(),classpath);
            classes.add(new ImmutablePair<>(classpath,bytes));
        } catch(Throwable ex) {
            TILRef.logFatal("Failed to write class",ex);
        }
        return classes;
    }

    private static void writeNeoForgeMod(ClassVisitor visitor, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapNeoForgeEntryPoints();
        Type apiType = writeClassInit(visitor,type,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints,info.getModID());
    }
}