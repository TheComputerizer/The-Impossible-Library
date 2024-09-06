package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
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
import mods.thecomputerizer.theimpossiblelibrary.api.util.BasicWrapped;

import javax.annotation.Nullable;
import java.util.function.Function;

public class RegistryHelper {
    
    static <R extends RegistryEntryAPI<?>> RegistryAPI<R> get(Function<RegistryHandlerAPI,RegistryAPI<?>> getter) {
        return BasicWrapped.cast(getter.apply(getHandler()));
    }

    @IndirectCallers
    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getBiomeRegistry() {
        return get(RegistryHandlerAPI::getBiomeRegistry);
    }
    
    @IndirectCallers
    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getBlockRegistry() {
        return get(RegistryHandlerAPI::getBlockRegistry);
    }

    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getBlockEntityRegistry() {
        return get(RegistryHandlerAPI::getBlockEntityRegistry);
    }
    
    @IndirectCallers
    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getEffectRegistry() {
        return get(RegistryHandlerAPI::getEffectRegistry);
    }
    
    @IndirectCallers
    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getEntityRegistry() {
        return get(RegistryHandlerAPI::getEntityRegistry);
    }
    
    @IndirectCallers
    public static <E extends RegistryEntryAPI<?>> E getEntryIfPresent(
            ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        return getHandler().getEntryIfPresent(registryKey,entryKey);
    }

    public static RegistryHandlerAPI getHandler() {
        return TILRef.getCommonSubAPI(CommonAPI::getRegistryHandler);
    }
    
    @IndirectCallers
    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getItemRegistry() {
        return get(RegistryHandlerAPI::getItemRegistry);
    }
    
    @IndirectCallers
    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getPotionRegistry() {
        return get(RegistryHandlerAPI::getPotionRegistry);
    }

    public static RegistryAPI<?> getRegistry(ResourceLocationAPI<?> key) {
        return getHandler().getRegistry(key);
    }

    public static RegistryAPI<?> getRegistry(Class<?> type) {
        return getHandler().getRegistry(type);
    }
    
    @IndirectCallers
    public static <R extends RegistryEntryAPI<?>> RegistryAPI<R> getSoundRegistry() {
        return get(RegistryHandlerAPI::getSoundRegistry);
    }
    
    @IndirectCallers
    public static BlockBuilderAPI makeBlockBuilder() {
        return makeBlockBuilder(null);
    }
    
    public static BlockBuilderAPI makeBlockBuilder(@Nullable BlockBuilderAPI parent) {
        return getHandler().makeBlockBuilder(parent);
    }
    
    @IndirectCallers
    public static BlockEntityBuilderAPI makeBlockEntityBuilder() {
        return makeBlockEntityBuilder(null);
    }
    
    public static BlockEntityBuilderAPI makeBlockEntityBuilder(@Nullable BlockEntityBuilderAPI parent) {
        return getHandler().makeBlockEntityBuilder(parent);
    }
    
    @IndirectCallers
    public static CreativeTabBuilderAPI makeCreativeTabBuilder() {
        return getHandler().makeCreativeTabBuilder();
    }
    
    @IndirectCallers
    public static DiscBuilderAPI makeDiscBuilder() {
        return makeDiscBuilder(null);
    }
    
    public static DiscBuilderAPI makeDiscBuilder(@Nullable ItemBuilderAPI parent) {
        return getHandler().makeDiscBuilder(parent);
    }
    
    @IndirectCallers
    public static EntityBuilderAPI makeEntityBuilder() {
        return makeEntityBuilder(null);
    }
    
    public static EntityBuilderAPI makeEntityBuilder(@Nullable EntityBuilderAPI parent) {
        return getHandler().makeEntityBuilder(parent);
    }
    
    @IndirectCallers
    public static ItemBlockBuilderAPI makeItemBlockBuilder() {
        return makeItemBlockBuilder(null);
    }
    
    public static ItemBlockBuilderAPI makeItemBlockBuilder(@Nullable ItemBuilderAPI parent) {
        return getHandler().makeItemBlockBuilder(parent);
    }
    
    @IndirectCallers
    public static ItemBuilderAPI makeItemBuilder() {
        return makeItemBuilder(null);
    }
    
    public static ItemBuilderAPI makeItemBuilder(@Nullable ItemBuilderAPI parent) {
        return getHandler().makeItemBuilder(parent);
    }
    
    @IndirectCallers
    public static SoundBuilderAPI makeSoundBuilder() {
        return makeSoundBuilder(null);
    }
    
    public static SoundBuilderAPI makeSoundBuilder(@Nullable SoundBuilderAPI parent) {
        return getHandler().makeSoundBuilder(parent);
    }
    
    @IndirectCallers
    public static ToolBuilderAPI makeToolBuilder(ToolType tool) {
        return makeToolBuilder(null,tool);
    }
    
    public static ToolBuilderAPI makeToolBuilder(@Nullable ItemBuilderAPI builder, ToolType tool) {
        return getHandler().makeToolBuilder(builder,tool);
    }
}
