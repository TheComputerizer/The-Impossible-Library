package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.common.TILCommonEntryPoint1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.server.WrappedCommand1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.server.WrappedCommand1_20_1.INFO;
import static net.minecraft.core.registries.Registries.COMMAND_ARGUMENT_TYPE;
import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TILCommonEntryPointForge1_20_1 extends TILCommonEntryPoint1_20_1 {
    
    private static TILCommonEntryPointForge1_20_1 INSTANCE;
    
    public static TILCommonEntryPointForge1_20_1 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointForge1_20_1();
    }
    
    public static void onRegisterCreativeTabs(RegisterEvent event) {
        if(event.getRegistryKey()==CREATIVE_MODE_TAB) CreativeTabBuilder1_20.onRegister(event);
        else if(event.getRegistryKey()==COMMAND_ARGUMENT_TYPE) {
            ResourceLocation registryName = ResourceLocation.fromNamespaceAndPath(MODID,"custom_suggester");
            event.register(COMMAND_ARGUMENT_TYPE,registryName,() -> INFO);
            WrappedCommand1_20_1.registerArgType();
        }
    }
    
    public static void onSupplyCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeTabBuilder1_20.onSupply(event);
    }
    
    private TILCommonEntryPointForge1_20_1() {
        INSTANCE = this;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(Forge 1.20.1) Extra data not found! Attempting to extract from context");
            //Avoid depreaction warnings I guess
            ModLoadingContext ctx = Hacks.invokeStatic(ModLoadingContext.class,"get");
            if(Objects.nonNull(ctx)) this.extraData = ctx.extension();
        }
        if(this.extraData instanceof FMLJavaModLoadingContext)
            return ((FMLJavaModLoadingContext)this.extraData).getModEventBus();
        if(this.extraData instanceof IEventBus) return (IEventBus)this.extraData; //In the case of NeoForge
        TILRef.logError("(Forge 1.20.1) Extra data not set to instance of FMLJavaModLoadingContext or IEventBus! "+
                        "{}",this.extraData);
        return null;
    }
    
    @Override public void onPreRegistration() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointForge1_20_1::onRegisterCreativeTabs);
        super.onPreRegistration();
    }
    
    @Override public void onCommonSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointForge1_20_1::onSupplyCreativeTabs);
        super.onCommonSetup();
    }
}