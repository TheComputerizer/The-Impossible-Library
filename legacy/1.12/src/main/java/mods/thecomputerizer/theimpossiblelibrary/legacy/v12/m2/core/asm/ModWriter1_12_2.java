package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.EMPTY_METHOD;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA8;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.PUBLIC;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModWriter1_12_2 extends ModWriter {
    
    private static final Type EVENT_HANDLER = TypeHelper.fml("common/Mod$EventHandler");
    private static final Type MOD_ANNOTATION = TypeHelper.fml("common/Mod");
    
    public ModWriter1_12_2(CoreAPI core, MultiVersionModInfo info) {
        super(core,info,JAVA8);
    }
    
    @Override protected void addClassAnnotations(ClassVisitor visitor) {
        writeClassAnnotation(visitor,MOD_ANNOTATION,mod -> {
            mod.visit("modid",this.info.getModID());
            mod.visit("name",this.info.getName());
            mod.visit("version",this.info.getVersion());
        });
    }
    
    @Override protected Type getEventMethod(String className) {
        return TypeHelper.method(VOID_TYPE,TypeHelper.fml("common/event/"+className));
    }
    
    @Override protected void mappedEntryPointMethods(Map<String,String[]> redirects, Map<String,Type> types) {
        mapEntryPointMethod(redirects,types,"<init>",EMPTY_METHOD,"onConstructed");
        mapEntryPointMethod(redirects,types,"preInit",getEventMethod("FMLPreInitializationEvent"),
                            "onPreRegistration");
        mapEntryPointMethod(redirects,types,"init",getEventMethod("FMLInitializationEvent"),
                            "onCommonSetup","checkClientSetup","checkDedicatedServerSetup");
        mapEntryPointMethod(redirects,types,"postInit",getEventMethod("FMLPostInitializationEvent"),
                            "onInterModEnqueue","onInterModProcess");
        mapEntryPointMethod(redirects,types,"loadComplete",getEventMethod("FMLLoadCompleteEvent"),
                            "onLoadComplete");
        mapEntryPointMethod(redirects,types,"serverAboutToStart",getEventMethod("FMLServerAboutToStartEvent"),
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
    
    @Override protected void writeMod(ClassWriter writer, List<Pair<String,byte[]>> classBytes) {
        super.writeMod(writer,classBytes);
        for(Entry<String,String[]> entryPoint : this.entryPointMethods.entrySet()) {
            String methodName = entryPoint.getKey();
            if(methodName.equals("<init>")) continue;
            Type eventType = this.entryPointMethodTypes.get(methodName);
            writeMethod(writer,visitor -> ASMHelper.getMethod(visitor,PUBLIC,methodName,eventType.getArgumentTypes()),
                    method -> {
                        writeMethodAnnotation(method,EVENT_HANDLER,annotation -> {});
                        addEntryHooks(method,false,methodName,false);
                    });
        }
    }
}