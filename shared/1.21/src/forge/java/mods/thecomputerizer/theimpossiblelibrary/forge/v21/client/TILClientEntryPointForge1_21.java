package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.TILClientEntryPoint1_21;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;
import java.util.Objects;

public class TILClientEntryPointForge1_21 extends TILClientEntryPoint1_21 {
    
    private static TILClientEntryPointForge1_21 INSTANCE;
    
    public static TILClientEntryPointForge1_21 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPointForge1_21();
    }
    
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        TILRef.getClientHandles().registerKeyBindingsEvent(event);
    }
    
    public TILClientEntryPointForge1_21() {
        INSTANCE = this;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(Forge 1.21) Extra data not found! Attempting to extract from context");
            this.extraData = ModLoadingContext.get().extension();
        }
        if(this.extraData instanceof FMLJavaModLoadingContext)
            return ((FMLJavaModLoadingContext)this.extraData).getModEventBus();
        TILRef.logError("(Forge 1.21) Extra data not set to instance of FMLJavaModLoadingContext! {}",this.extraData);
        return null;
    }
    
    @Override public void onPreRegistration() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILClientEntryPointForge1_21::registerKeyMappings);
        else TILRef.logError("Failed to register keybinds!");
        super.onPreRegistration();
    }
}