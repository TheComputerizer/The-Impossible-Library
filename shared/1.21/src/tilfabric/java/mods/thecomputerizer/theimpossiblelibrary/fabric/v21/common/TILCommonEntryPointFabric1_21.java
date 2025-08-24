package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.tab.CreativeTabBuilder1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21.INFO;
import static net.minecraft.core.registries.BuiltInRegistries.*;

public class TILCommonEntryPointFabric1_21 extends TILCommonEntryPointFabric {
    
    private static TILCommonEntryPointFabric1_21 INSTANCE;
    
    public static TILCommonEntryPointFabric1_21 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_21();
    }
    
    private TILCommonEntryPointFabric1_21() {
        INSTANCE = this;
    }
    
    @Override public void onCommonSetup() {
        WrappedCommand1_21.registerArgType();
        Registry.register(COMMAND_ARGUMENT_TYPE,ResourceLocation.fromNamespaceAndPath(MODID,"custom_suggester"),INFO);
        super.onCommonSetup();
    }
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        CreativeTabBuilder1_21.onRegister(null);
        super.onLoadComplete();
    }
    
    @Override protected Registry<?> registryBlock() {
        return BLOCK;
    }
    
    @Override protected Registry<?> registryBlockEntity() {
        return BLOCK_ENTITY_TYPE;
    }
    
    @Override protected Registry<?> registryEntity() {
        return ENTITY_TYPE;
    }
    
    @Override protected Registry<?> registryItem() {
        return ITEM;
    }
    
    @Override protected Registry<?> registrySoundEvent() {
        return SOUND_EVENT;
    }
}
