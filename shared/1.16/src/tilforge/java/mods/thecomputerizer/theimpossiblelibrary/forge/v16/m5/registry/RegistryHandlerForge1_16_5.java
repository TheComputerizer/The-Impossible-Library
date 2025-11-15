package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.tab.CreativeTabBuilderForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.RegistryHandler1_16_5;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Set;

import static net.minecraftforge.registries.ForgeRegistries.*;

public class RegistryHandlerForge1_16_5 extends RegistryHandler1_16_5 {
    
    @Override protected void collectRegistries(Set<? super Registry1_16_5<?>> registries) {
        this.biome = getRegistry(registries,BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,BLOCKS,"block",Block.class);
        this.blockEntity = getRegistry(registries,TILE_ENTITIES,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,POTIONS,"effect",MobEffect.class);
        this.entity = getRegistry(registries,ENTITIES,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEMS,"item",Item.class);
        this.potion = getRegistry(registries,POTION_TYPES,"potion",Potion.class);
        this.sound = getRegistry(registries,SOUND_EVENTS,"sound",SoundEvent.class);
        this.structure = getRegistry(registries,STRUCTURE_FEATURES,"structure",StructureFeature.class);
    }
    
    private <V extends IForgeRegistryEntry<V>> RegistryForge1_16_5<V> getRegistry(
            Set<? super Registry1_16_5<?>> registries, IForgeRegistry<V> forgeRegistry, String name,
            Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(new ResourceLocation(name));
        RegistryForge1_16_5<V> registry = new RegistryForge1_16_5<>(forgeRegistry,key,type);
        registries.add(registry);
        return registry;
    }
    
    @Override public CreativeTabBuilderAPI<?> makeCreativeTabBuilder() {
        return new CreativeTabBuilderForge1_16_5();
    }
}