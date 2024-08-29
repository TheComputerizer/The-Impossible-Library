package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.asm.ModWriterForge;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.ClassWriter;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA8;

public class ModWriterForge1_16_5 extends ModWriterForge {
    
    public ModWriterForge1_16_5(CoreAPI core, MultiVersionModInfo info) {
        super(core,info,JAVA8);
    }
    
    @Override protected void writeMod(ClassWriter writer, List<Pair<String,byte[]>> classBytes) {
        super.writeMod(writer,classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),true,true,
                false,"LoaderClient","clientSetup"),classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),true,true,true,
                                "LoaderCommon","commonSetup","interModEnqueue",
                                "interModProcess","loadComplete"),classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),true,false,true,
                                "LoaderServer","dedicatedServerSetup"),classBytes);
        writeInnerClass(addInnerEventSubscriber(writer,this.info.getModID(),false,true,true,
                                "ServerLifecycle","serverAboutToStart","serverStarting",
                                "serverStarted","serverStopping","serverStopped"),classBytes);
    }
}