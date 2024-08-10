package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ClassPrinter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.*;

import javax.annotation.Nullable;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.INSTANCE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public class MultiVersionModWriter {
    
    private static final Type BOOTSTRAP = TypeHelper.getMethodType(CallSite.class,Lookup.class,String.class,
            MethodType.class,MethodType.class,MethodHandle.class,MethodType.class);
    private static final Type COMMON_ENTRY = Type.getType(CommonEntryPoint.class);
    private static final Type CONSUMER = Type.getType(Consumer.class);
    private static final Type FML_CONTAINER = Type.getType("Lnet/minecraftforge/fml/javafmlmod/FMLModContainer;");
    private static final Type FORGE_EVENT_BUS = Type.getType("Lnet/minecraftforge/eventbus/api/IEventBus;");
    private static final Type FORGE_LOADING_CONTEXT = Type.getType("Lnet/minecraftforge/fml/javafmlmod/FMLJavaModLoadingContext;");
    private static final Type FORGE_MOD = Type.getType("Lnet/minecraftforge/fml/common/Mod;");
    private static final Type FORGE_EVENT_SUBSCRIBER = Type.getType("Lnet/minecraftforge/fml/common/Mod$EventBusSubscriber;");
    private static final Type LAMBDA_META_FACTORY = Type.getType("Ljava/lang/invoke/LambdaMetafactory;");
    private static final Type LEGACY_EVENT_HANDLER = Type.getType("Lnet/minecraftforge/fml/common/Mod$EventHandler;");

    private static void addEntryHooks(MethodVisitor visitor, Type type, Type apiType, String ... apiMethods) {
        for(String method : apiMethods) {
            visitor.visitVarInsn(ALOAD,0);
            visitor.visitFieldInsn(GETFIELD,type.getInternalName(),"entryPoint",apiType.getDescriptor());
            visitor.visitMethodInsn(INVOKEVIRTUAL,apiType.getInternalName(),method,VOID_EMPTY_METHOD.getDescriptor(),false);
        }
        visitor.visitInsn(RETURN);
    }

    private static Type generateClassType(String pkgName, String entryType, String name) {
        return Type.getType("L"+pkgName.replace('.','/')+"/"+
                name.replace(" ","")+"Generated"+entryType+"Mod;");
    }

    private static String getEntryType(boolean client, boolean server) {
        return client ? (server ? "Common" : "Client") : "";
    }

    private static MethodVisitor getEventHandler(ClassVisitor visitor, String name, Type eventType) {
        return ASMHelper.getMethod(visitor,PUBLIC,name,eventType);
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

    private static Type writeClassInit(ClassVisitor visitor, Class<?> entryRef) {
        Type apiType = Type.getType(entryRef);
        ASMHelper.addField(visitor,PRIVATE_FINAL,"entryPoint",apiType,null,null);
        return apiType;
    }

    private static void writeConstructor(ClassVisitor visitor, Type type, Type apiType, Map<String,String[]> entryPoints) {
        String name = type.getInternalName();
        String apiName = apiType.getInternalName();
        MethodVisitor constructor = ASMHelper.getConstructor(visitor,PUBLIC);
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitMethodInsn(INVOKESPECIAL,OBJECT_TYPE.getInternalName(),"<init>",VOID_EMPTY_METHOD.getDescriptor(),false);
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitTypeInsn(NEW,apiName);
        constructor.visitInsn(DUP);
        constructor.visitMethodInsn(INVOKESPECIAL,apiName,"<init>",VOID_EMPTY_METHOD.getDescriptor(),false);
        constructor.visitFieldInsn(PUTFIELD,name,"entryPoint",apiType.getDescriptor());
        if(INSTANCE.getModLoader()==FORGE) {
            constructor.visitLdcInsn("Loading context string {}");
            constructor.visitMethodInsn(INVOKESTATIC,"net/minecraftforge/fml/ModLoadingContext",
                                        "get","()Lnet/minecraftforge/fml/ModLoadingContext;",false);
            log(constructor);
            //constructor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/fml/javafmlmod/FMLJavaModLoadingContext",
            //                              "getModEventBus", "()Lnet/minecraftforge/eventbus/api/IEventBus;", false);
            //constructor.visitVarInsn(ALOAD, 0);
            //constructor.visitInvokeDynamicInsn("accept",
            //                                     "("+type+")Ljava/util/function/Consumer;",
            //                                     new Handle(Opcodes.H_INVOKESTATIC,
            //                                                "java/lang/invoke/LambdaMetafactory", "metafactory",
            //                                                "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
            //                                                false), new Object[]{
            //                Type.getType("(Ljava/lang/Object;)V"), new Handle(Opcodes.H_INVOKEVIRTUAL,
            //                                                                  type.getInternalName(),
            //                                                                  "commonSetup",
            //                                                                  "(Lnet/minecraftforge/fml/event/lifecycle/FMLCommonSetupEvent;)V",
            //                                                                  false),
            //                Type.getType("(Lnet/minecraftforge/fml/event/lifecycle/FMLCommonSetupEvent;)V")});
            //constructor.visitMethodInsn(INVOKEINTERFACE, "net/minecraftforge/eventbus/api/IEventBus", "addListener",
            //                              "(Ljava/util/function/Consumer;)V", true);
            //writeForgeListeners(constructor,type.getInternalName(),entryPoints.keySet());
        }
        addEntryHooks(constructor,type,apiType,entryPoints.getOrDefault("<init>",new String[]{}));
        ASMHelper.finishMethod(constructor);
    }

    private static void writeFabricMod(ClassVisitor visitor, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapFabricEntryPoints();
        Type apiType = writeClassInit(visitor,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints);
    }
    
    private static void writeForgeListeners(MethodVisitor constructor, String name, Set<String> listeners) {
        String contextOwner = FORGE_LOADING_CONTEXT.getInternalName();
        String contextDesc = Type.getMethodDescriptor(FORGE_LOADING_CONTEXT);
        String busOwner = FORGE_EVENT_BUS.getInternalName();
        String busDesc = Type.getMethodDescriptor(FORGE_EVENT_BUS);
        String addListenerDesc = Type.getMethodDescriptor(VOID_TYPE,CONSUMER);
        Handle bootstrapHandle = new Handle(HANDLE_INVOKESTATIC,LAMBDA_META_FACTORY.getInternalName(),
                                            "metafactory",BOOTSTRAP.getDescriptor(),false);
        String bootstrapDesc = "(L"+name+";)"+CONSUMER.getDescriptor();
        for(String listener : listeners) {
            if(listener.equals("<init>")) continue;
            Type eventType = getEventType(FORGE,listener);
            Handle listenerHandle = new Handle(HANDLE_INVOKEVIRTUAL,name,listener,"("+eventType.getDescriptor()+")V",false);
            Object[] handlerArgs = new Object[]{Type.getMethodType(VOID_TYPE,OBJECT_TYPE),listenerHandle,Type.getMethodType(VOID_TYPE,eventType)};
            Label listenerLabel = new Label();
            constructor.visitLabel(listenerLabel);
            //constructor.visitMethodInsn(INVOKESTATIC,contextOwner,"get",contextDesc,false);
            //constructor.visitMethodInsn(INVOKEVIRTUAL,contextOwner,"getModEventBus",busDesc,false);
            constructor.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/fml/javafmlmod/FMLJavaModLoadingContext",
                                          "get", "()Lnet/minecraftforge/fml/javafmlmod/FMLJavaModLoadingContext;",
                                          false);
            constructor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/fml/javafmlmod/FMLJavaModLoadingContext",
                                          "getModEventBus", "()Lnet/minecraftforge/eventbus/api/IEventBus;", false);
            constructor.visitVarInsn(ALOAD,0);
            //constructor.visitInvokeDynamicInsn("accept",bootstrapDesc,bootstrapHandle,handlerArgs);
            constructor.visitInvokeDynamicInsn("accept",
                                                 "(L"+name+";)Ljava/util/function/Consumer;",
                                                 new Handle(HANDLE_INVOKESTATIC,
                                                            "java/lang/invoke/LambdaMetafactory", "metafactory",
                                                            "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                                                            false), new Object[]{
                            Type.getType("(Ljava/lang/Object;)V"), new Handle(HANDLE_INVOKEVIRTUAL,
                                                                              name,
                                                                              listener,
                                                                              "("+eventType+")V",
                                                                              false),
                            Type.getType("("+eventType+")V")});
            constructor.visitMethodInsn(INVOKEINTERFACE,busOwner,"addListener",addListenerDesc,true);
        }
    }

    private static void writeForgeMod(ClassVisitor visitor, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapForgeEntryPoints();
        AnnotationVisitor mod = ASMHelper.getAnnotation(visitor,FORGE_MOD); //add @Mod annotation
        mod.visit("value",info.getModID());
        mod.visitEnd();
        Type apiType = writeClassInit(visitor,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints);
        for(Entry<String,String[]> entryPoint : entryPoints.entrySet()) {
            String method = entryPoint.getKey();
            if(method.equals("<init>")) continue;
            Type eventType = getEventType(FORGE,method);
            MethodVisitor mv = getEventHandler(visitor,method,eventType);
            AnnotationVisitor eventHandler = ASMHelper.getAnnotation(mv,FORGE_EVENT_SUBSCRIBER);
            eventHandler.visitEnd();
            mv.visitCode();
            addEntryHooks(mv,type,apiType,entryPoint.getValue());
            ASMHelper.finishMethod(mv);
        }
    }

    private static void writeLegacyMod(ClassVisitor visitor, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapLegacyEntryPoints();
        AnnotationVisitor mod = ASMHelper.getAnnotation(visitor,FORGE_MOD); //add @Mod annotation
        mod.visit("modid",info.getModID());
        mod.visit("name",info.getName());
        mod.visit("version",info.getVersion());
        mod.visitEnd();
        Type apiType = writeClassInit(visitor,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints);
        for(Entry<String,String[]> entryPoint : entryPoints.entrySet()) {
            String method = entryPoint.getKey();
            if(method.equals("<init>")) continue;
            Type eventType = getEventType(LEGACY,method);
            MethodVisitor mv = getEventHandler(visitor,method,eventType);
            AnnotationVisitor eventHandler = ASMHelper.getAnnotation(mv,LEGACY_EVENT_HANDLER);
            eventHandler.visitEnd();
            mv.visitCode();
            addEntryHooks(mv,type,apiType,entryPoint.getValue());
            ASMHelper.finishMethod(mv);
        }
    }

    public static @Nullable Pair<String,byte[]> buildModClass(int javaVer, ModLoader modLoader, MultiVersionModInfo info) {
        String pkgName = info.getEntryClass().getPackage().getName();
        Type type = generateClassType(pkgName,getEntryType(info.isClient(),info.isServer()),info.getName());
        ClassWriter writer = ASMHelper.getWriter(javaVer,PUBLIC,type);
        switch(modLoader) {
            case FABRIC: {
                writeFabricMod(writer,type,info);
                break;
            }
            case FORGE: {
                writeForgeMod(writer,type,info);
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
            return new ImmutablePair<>(classpath,bytes);

        } catch(Throwable ex) {
            TILRef.logFatal("Failed to write class",ex);
            return null;
        }
    }

    private static void writeNeoForgeMod(ClassVisitor visitor, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapNeoForgeEntryPoints();
        Type apiType = writeClassInit(visitor,info.getEntryClass());
        writeConstructor(visitor,type,apiType,entryPoints);
    }
}