package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.RegistryHandler1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Set;

public class RegistryHandlerFabric1_16_5 extends RegistryHandler1_16_5 {
    
    @Override protected void collectRegistries(Set<? super Registry1_16_5<?>> registries) {
        this.biome = getRegistry(registries,BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,BLOCKS,"block",Block.class);
        this.blockEntity = getRegistry(registries,TILE_ENTITIES,"block_entity",TileEntityType.class);
        this.effect = getRegistry(registries,POTIONS,"effect",Effect.class);
        this.entity = getRegistry(registries,ENTITIES,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEMS,"item",Item.class);
        this.potion = getRegistry(registries,POTION_TYPES,"potion",Potion.class);
        this.sound = getRegistry(registries,SOUND_EVENTS,"sound",SoundEvent.class);
    }
    
    @SuppressWarnings("unchecked")
    private <V extends IForgeRegistryEntry<V>> RegistryFabric1_16_5<V> getRegistry(
            Set<? super Registry1_16_5<?>> registries, IForgeRegistry<V> forgeRegistry, String name, Class<?> type) {
        ResourceLocation1_16_5 key = new ResourceLocation1_16_5(new ResourceLocation(name));
        RegistryFabric1_16_5<V> registry = new RegistryFabric1_16_5<>(forgeRegistry, key, (Class<V>)type);
        registries.add(registry);
        return registry;
    }
}