package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.EMPTY_METHOD;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.PUBLIC;
import static org.objectweb.asm.Type.VOID_TYPE;

public class ModWriter1_12_2 extends ModWriter {
    
    private static final Type EVENT_HANDLER = TypeHelper.fml("common/Mod$EventHandler");
    private static final Type MOD_ANNOTATION = TypeHelper.fml("common/Mod");
    
    public ModWriter1_12_2(CoreAPI core, MultiVersionModInfo info) {
        super(core,info);
    }
    
    @Override protected void addClassAnnotations(ClassVisitor visitor) {
        writeClassAnnotation(visitor,MOD_ANNOTATION,mod -> {
            mod.visit("modid",this.info.getModID());
            mod.visit("name",this.info.getName());
            mod.visit("version",this.info.getVersion());
        });
    }
    
    @Override protected List<String[]> entryPointMappings() {
        return Arrays.asList(
                new String[]{"<init>","","onConstructed"},
                new String[]{"preInit","FMLPreInitializationEvent","onPreRegistration"},
                new String[]{"init","FMLInitializationEvent","onCommonSetup","checkClientSetup","checkDedicatedServerSetup"},
                new String[]{"postInit","FMLPostInitializationEvent","onInterModEnqueue","onInterModProcess"},
                new String[]{"loadComplete","FMLLoadCompleteEvent","onLoadComplete"},
                new String[]{"serverAboutToStart","FMLServerAboutToStartEvent","onServerAboutToStart"},
                new String[]{"serverStarting","FMLServerStartingEvent","onServerStarting"},
                new String[]{"serverStarted","FMLServerStartedEvent","onServerStarted"},
                new String[]{"serverStopping","FMLServerStoppingEvent","onServerStopping"},
                new String[]{"serverStopped","FMLServerStoppedEvent","onServerStopped"});
    }
    
    @Override protected Type getEventMethod(String className) {
        if(TextHelper.isBlank(className)) return EMPTY_METHOD;
        return TypeHelper.method(VOID_TYPE,TypeHelper.fml("common/event/"+className));
    }
    
    @Override protected void writeMod(ClassWriter writer, List<Entry<String,byte[]>> classBytes) {
        super.writeMod(writer,classBytes);
        for(Entry<String,String[]> entryPoint : this.entryPointMethods.entrySet()) {
            final String methodName = entryPoint.getKey();
            if("<init>".equals(methodName)) continue;
            final Type[] eventArgs = this.entryPointMethodTypes.get(methodName).getArgumentTypes();
            final Consumer<MethodVisitor> methodWriter = method -> {
                writeMethodAnnotation(method,EVENT_HANDLER,annotation -> {});
                addEntryHooks(method,false,methodName,false);
            };
            writeMethod(writer,cv -> ASMHelper.getMethod(cv,PUBLIC,methodName,eventArgs),methodWriter);
        }
    }
}