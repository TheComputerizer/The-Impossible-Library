package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;
import static org.objectweb.asm.Type.VOID_TYPE;

public class MultiVersionModWriter {

    private static final Type COMMON_ENTRYPOINT = Type.getType(CommonEntryPoint.class);
    private static final Type FORGE_MOD = Type.getType("Lnet/minecraftforge/fml/common/Mod;");
    private static final Type LEGACY_EVENT_HANDLER = Type.getType("Lnet/minecraftforge/fml/common/Mod$EventHandler;");

    private static Type generateClassType(String pkgName, String entryType, String name) {
        return Type.getType(pkgName.replace('.','/')+"/"+
                name.replace(" ","")+"Generated"+entryType+"EntryPoint");
    }

    private static void addEntryHooks(MethodVisitor visitor, Type type, Type apiType, String ... apiMethods) {
        for(String method : apiMethods) {
            visitor.visitVarInsn(ALOAD,0);
            visitor.visitFieldInsn(GETFIELD,type.getInternalName(),"entryPoint",apiType.getDescriptor());
            visitor.visitMethodInsn(INVOKEVIRTUAL,COMMON_ENTRYPOINT.getInternalName(),method,VOID_EMPTY_METHOD.getDescriptor(),false);
        }
    }

    private static String getEntryType(boolean client, boolean server) {
        return client ? (server ? "Common" : "Client") : "";
    }

    private static MethodVisitor getEventHandler(ClassWriter writer, String name, Type eventType) {
        return ASMHelper.getMethod(writer,PUBLIC,name,eventType);
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
                default: return VOID_TYPE;
            }
        }
        else if(loader==FORGE) {
            switch(methodName) {
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

    private static Map<String,String[]> mapFabricEntryPoints() { //TODO
        return new HashMap<>();
    }

    private static Map<String,String[]> mapForgeEntryPoints() {//TODO universally handle onDedicatedServerSetup & onClientSetup?
        Map<String,String[]> map = new HashMap<>();
        map.put("<init>",new String[]{"onConstructed","onPreRegistration"});
        map.put("commonSetup",new String[]{"onCommonSetup"});
        map.put("dedicatedServerSetup",new String[]{"onDedicatedServerSetup"});
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
        map.put("init",new String[]{"onCommonSetup"});
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

    private static Type writeClassInit(ClassWriter writer, Type type, Class<?> entryRef) {
        Type apiType = Type.getType(entryRef);
        String signature = ASMHelper.buildSignature(CLASS_TYPE,apiType);
        ASMHelper.addField(writer,PRIVATE_STATIC_FINAL,"ENTRYPOINT_CLASS",CLASS_TYPE,signature,null);
        MethodVisitor clinit = ASMHelper.getClassConstructor(writer);
        clinit.visitCode();
        clinit.visitLdcInsn(apiType);
        clinit.visitFieldInsn(PUTSTATIC,type.getDescriptor(),"ENTRYPOINT_CLASS",CLASS_TYPE.getDescriptor());
        clinit.visitInsn(RETURN);
        ASMHelper.finishMethod(clinit);
        ASMHelper.addField(writer,PRIVATE_FINAL,"entryPoint",apiType,null,null);
        return apiType;
    }

    private static void writeConstructor(ClassWriter writer, Type type, Type apiType, String ... apiMethods) {
        String name = apiType.getInternalName();
        MethodVisitor constructor = ASMHelper.getConstructor(writer,PUBLIC);
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitMethodInsn(INVOKESPECIAL,OBJECT_TYPE.getInternalName(),"<init>",VOID_EMPTY_METHOD.getDescriptor(),false);
        constructor.visitVarInsn(ALOAD,0);
        constructor.visitTypeInsn(NEW,name);
        constructor.visitInsn(DUP);
        constructor.visitMethodInsn(INVOKESPECIAL,name,"<init>",VOID_EMPTY_METHOD.getDescriptor(),false);
        constructor.visitFieldInsn(PUTFIELD,type.getInternalName(),"entryPoint",apiType.getDescriptor());
        addEntryHooks(constructor,type,apiType,apiMethods);
        ASMHelper.finishMethod(constructor);
    }

    private static void writeFabricMod(ClassWriter writer, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapFabricEntryPoints();
        Type apiType = writeClassInit(writer,type,info.getModClass());
        writeConstructor(writer,type,apiType,entryPoints.getOrDefault("<init>",new String[]{}));
    }

    private static void writeForgeMod(ClassWriter writer, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapForgeEntryPoints();
        AnnotationVisitor mod = ASMHelper.getAnnotation(writer,FORGE_MOD); //add @Mod annotation
        mod.visit("value",info.getModID());
        mod.visitEnd();
        Type apiType = writeClassInit(writer,type,info.getModClass());
        writeConstructor(writer,type,apiType,entryPoints.getOrDefault("<init>",new String[]{}));
        for(Entry<String,String[]> entryPoint : entryPoints.entrySet()) {
            String method = entryPoint.getKey();
            if(method.equals("<init>")) continue;
            Type eventType = getEventType(FORGE,method);
            MethodVisitor visitor = getEventHandler(writer,method,eventType);
            visitor.visitCode();
            addEntryHooks(visitor,type,apiType,entryPoint.getValue());
            ASMHelper.finishMethod(visitor);
        }
    }

    private static void writeLegacyMod(ClassWriter writer, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapLegacyEntryPoints();
        AnnotationVisitor mod = ASMHelper.getAnnotation(writer,FORGE_MOD); //add @Mod annotation
        mod.visit("modid",info.getModID());
        mod.visit("name",info.getName());
        mod.visit("version",info.getVersion());
        mod.visitEnd();
        Type apiType = writeClassInit(writer,type,info.getModClass());
        writeConstructor(writer,type,apiType,entryPoints.getOrDefault("<init>",new String[]{}));
        for(Entry<String,String[]> entryPoint : entryPoints.entrySet()) {
            String method = entryPoint.getKey();
            if(method.equals("<init>")) continue;
            Type eventType = getEventType(LEGACY,method);
            MethodVisitor visitor = getEventHandler(writer,method,eventType);
            AnnotationVisitor eventHandler = ASMHelper.getAnnotation(visitor,LEGACY_EVENT_HANDLER);
            eventHandler.visitEnd();
            visitor.visitCode();
            addEntryHooks(visitor,type,apiType,entryPoint.getValue());
            ASMHelper.finishMethod(visitor);
        }
    }

    public static void writeMod(ClassLoader classLoader, int javaVer, ModLoader modLoader, MultiVersionModInfo info) {
        String pkgName = info.getModClass().getPackage().getName();
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
            Class<?> modClass = ASMHelper.finishWriting(classLoader,writer,type);
            TILRef.logInfo("Successfully wrote mod entrypoint for `{}` to `{}`",info.getModID(),modClass);

        } catch(Throwable ex) {
            TILRef.logFatal("Failed to write class",ex);
        }
    }

    private static void writeNeoForgeMod(ClassWriter writer, Type type, MultiVersionModInfo info) {
        Map<String,String[]> entryPoints = mapNeoForgeEntryPoints();
        Type apiType = writeClassInit(writer,type,info.getModClass());
        writeConstructor(writer,type,apiType,entryPoints.getOrDefault("<init>",new String[]{}));
    }
}