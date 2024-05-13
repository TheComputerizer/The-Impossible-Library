package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ToolType;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.sound.SoundBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;

@SuppressWarnings({"unchecked","unused"})
public class RegistryHelper {

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getBiomeRegistry() {
        return (RegistryAPI<R>)getHandler().getBiomeRegistry();
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getBlockRegistry() {
        return (RegistryAPI<R>)getHandler().getBlockRegistry();
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getBlockEntityRegistry() {
        return (RegistryAPI<R>)getHandler().getBlockEntityRegistry();
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getEffectRegistry() {
        return (RegistryAPI<R>)getHandler().getEffectRegistry();
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getEntityRegistry() {
        return (RegistryAPI<R>)getHandler().getEntityRegistry();
    }

    public static <E extends RegistryEntryAPI<?>> E getEntryIfPresent(
            ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        return getHandler().getEntryIfPresent(registryKey,entryKey);
    }

    public static RegistryHandlerAPI getHandler() {
        return TILRef.getCommonSubAPI(CommonAPI::getRegistryHandler);
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getItemRegistry() {
        return (RegistryAPI<R>)getHandler().getItemRegistry();
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getPotionRegistry() {
        return (RegistryAPI<R>)getHandler().getPotionRegistry();
    }

    public static RegistryAPI<?> getRegistry(ResourceLocationAPI<?> key) {
        return getHandler().getRegistry(key);
    }

    public static RegistryAPI<?> getRegistry(Class<?> type) {
        return getHandler().getRegistry(type);
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getSoundRegistry() {
        return (RegistryAPI<R>)getHandler().getSoundRegistry();
    }
    
    public static BlockBuilderAPI makeBlockBuilder() {
        return makeBlockBuilder(null);
    }
    
    public static BlockBuilderAPI makeBlockBuilder(@Nullable BlockBuilderAPI parent) {
        return getHandler().makeBlockBuilder(parent);
    }
    
    public static BlockEntityBuilderAPI makeBlockEntityBuilder() {
        return makeBlockEntityBuilder(null);
    }
    
    public static BlockEntityBuilderAPI makeBlockEntityBuilder(@Nullable BlockEntityBuilderAPI parent) {
        return getHandler().makeBlockEntityBuilder(parent);
    }
    
    public static CreativeTabBuilderAPI makeCreativeTabBuilder() {
        return getHandler().makeCreativeTabBuilder();
    }
    
    public static DiscBuilderAPI makeDiscBuilder() {
        return makeDiscBuilder(null);
    }
    
    public static DiscBuilderAPI makeDiscBuilder(@Nullable ItemBuilderAPI parent) {
        return getHandler().makeDiscBuilder(parent);
    }
    
    public static EntityBuilderAPI makeEntityBuilder() {
        return makeEntityBuilder(null);
    }
    
    public static EntityBuilderAPI makeEntityBuilder(@Nullable EntityBuilderAPI parent) {
        return getHandler().makeEntityBuilder(parent);
    }
    
    public static ItemBlockBuilderAPI makeItemBlockBuilder() {
        return makeItemBlockBuilder(null);
    }
    
    public static ItemBlockBuilderAPI makeItemBlockBuilder(@Nullable ItemBuilderAPI parent) {
        return getHandler().makeItemBlockBuilder(parent);
    }
    
    public static ItemBuilderAPI makeItemBuilder() {
        return makeItemBuilder(null);
    }
    
    public static ItemBuilderAPI makeItemBuilder(@Nullable ItemBuilderAPI parent) {
        return getHandler().makeItemBuilder(parent);
    }
    
    public static SoundBuilderAPI makeSoundBuilder() {
        return makeSoundBuilder(null);
    }
    
    public static SoundBuilderAPI makeSoundBuilder(@Nullable SoundBuilderAPI parent) {
        return getHandler().makeSoundBuilder(parent);
    }
    
    public static ToolBuilderAPI makeToolBuilder(ToolType tool) {
        return makeToolBuilder(null,tool);
    }
    
    public static ToolBuilderAPI makeToolBuilder(@Nullable ItemBuilderAPI builder, ToolType tool) {
        return getHandler().makeToolBuilder(builder,tool);
    }
}
