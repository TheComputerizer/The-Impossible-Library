package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.DelegatingClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILClientEntryPointForge1_19 extends DelegatingClientEntryPoint {
    
    private static TILClientEntryPointForge1_19 INSTANCE;
    
    public static TILClientEntryPointForge1_19 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPointForge1_19();
    }
    
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        TILRef.getClientHandles().registerKeyBindingsEvent(event);
    }
    
    public TILClientEntryPointForge1_19() {
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(Forge 1.19) Extra data not found! Attempting to extract from context");
            this.extraData = ModLoadingContext.get().extension();
        }
        if(this.extraData instanceof FMLJavaModLoadingContext)
            return ((FMLJavaModLoadingContext)this.extraData).getModEventBus();
        TILRef.logError("(Forge 1.19) Extra data not set to instance of FMLJavaModLoadingContext! {}",this.extraData);
        return null;
    }
    
    @Override public void onClientSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILClientEntryPointForge1_19::registerKeyMappings);
        else TILRef.logError("Failed to register keybinds!");
    }
}