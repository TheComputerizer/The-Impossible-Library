package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface RegistryHandlerAPI {

    <V> V getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey);
    RegistryAPI<?> getBiomeRegistry();
    RegistryAPI<?> getBlockRegistry();
    RegistryAPI<?> getBlockEntityRegistry();
    RegistryAPI<?> getEffectRegistry();
    RegistryAPI<?> getEntityRegistry();
    RegistryAPI<?> getItemRegistry();
    RegistryAPI<?> getPotionRegistry();
    RegistryAPI<?> getRegistry(ResourceLocationAPI<?> registryKey);
    RegistryAPI<?> getRegistry(Class<?> type);
    RegistryAPI<?> getSoundRegistry();
    BlockBuilderAPI makeBlockBuilder();
    CreativeTabBuilderAPI makeCreativeTabBuilder();
    EntityBuilderAPI makeEntityBuilder();
    ItemBuilderAPI makeItemBuilder();
    SoundBuilderAPI makeSoundBuilder();
}
