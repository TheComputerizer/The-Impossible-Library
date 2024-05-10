package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;


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
        return getHandler().makeBlockBuilder();
    }
    
    public static CreativeTabBuilderAPI makeCreativeTabBuilder() {
        return getHandler().makeCreativeTabBuilder();
    }
    
    public static EntityBuilderAPI makeEntityBuilder() {
        return getHandler().makeEntityBuilder();
    }
    
    public static ItemBuilderAPI makeItemBuilder() {
        return getHandler().makeItemBuilder();
    }
    
    public static SoundBuilderAPI makeSoundBuilder() {
        return getHandler().makeSoundBuilder();
    }
}
