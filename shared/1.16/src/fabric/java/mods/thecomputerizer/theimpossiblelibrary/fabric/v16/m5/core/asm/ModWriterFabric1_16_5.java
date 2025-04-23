package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm.ModWriterFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import org.objectweb.asm.Type;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA8;

public class ModWriterFabric1_16_5 extends ModWriterFabric {
    
    public static final String CLIENT_MOD_INITIALIZER = Type.getInternalName(ClientModInitializer.class);
    public static final String MOD_INITIALIZER = Type.getInternalName(ModInitializer.class);
    public static final String SERVER_MOD_INITIALIZER = Type.getInternalName(DedicatedServerModInitializer.class);
    
    public ModWriterFabric1_16_5(CoreAPI core, MultiVersionModInfo info) {
        super(core,info,JAVA8);
    }
    
    @Override protected String[] modInterfaces(boolean client, boolean server) {
        return new String[]{client ? (server ? MOD_INITIALIZER : CLIENT_MOD_INITIALIZER) :
                (server ? SERVER_MOD_INITIALIZER : MOD_INITIALIZER)};
    }
}