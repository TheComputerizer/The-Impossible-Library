package mods.thecomputerizer.theimpossiblelibrary.api.registry;

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
    BlockBuilderAPI makeBlockBuilder(@Nullable BlockBuilderAPI parent);
    BlockEntityBuilderAPI makeBlockEntityBuilder(@Nullable BlockEntityBuilderAPI parent);
    CreativeTabBuilderAPI makeCreativeTabBuilder();
    DiscBuilderAPI makeDiscBuilder(@Nullable ItemBuilderAPI parent);
    EntityBuilderAPI makeEntityBuilder(@Nullable EntityBuilderAPI parent);
    ItemBlockBuilderAPI makeItemBlockBuilder(@Nullable ItemBuilderAPI parent);
    ItemBuilderAPI makeItemBuilder(@Nullable ItemBuilderAPI parent);
    SoundBuilderAPI makeSoundBuilder(@Nullable SoundBuilderAPI parent);
    ToolBuilderAPI makeToolBuilder(@Nullable ItemBuilderAPI parent, ToolType tool);
}
