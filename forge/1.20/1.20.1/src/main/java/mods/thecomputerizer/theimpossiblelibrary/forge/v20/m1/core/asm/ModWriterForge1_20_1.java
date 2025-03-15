package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.core.asm;

import cpw.mods.modlauncher.Environment;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.TypeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.asm.ModWriterForge1_20;
import org.objectweb.asm.Type;

import java.util.Objects;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static cpw.mods.modlauncher.api.IEnvironment.Keys.VERSION;

public class ModWriterForge1_20_1 extends ModWriterForge1_20 {
    
    private static final Type EVENT_BUS = TypeHelper.forge("eventbus/api/IEventBus");
    private static final boolean NEOFORGE = isActuallyNeoForge(INSTANCE.environment());
    
    private static boolean isActuallyNeoForge(Environment environment) {
        String value = environment.getProperty(VERSION.get()).orElse(null);
        return Objects.nonNull(value) && value.contains("neoforge");
    }
    
    public ModWriterForge1_20_1(CoreAPI core, MultiVersionModInfo info) {
        super(core,info);
    }
    
    @Override protected Type getOptionalContructorType() {
        return NEOFORGE ? EVENT_BUS : super.getOptionalContructorType();
    }
}
