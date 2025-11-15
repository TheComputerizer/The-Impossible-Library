package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.registry.tab.CreativeTabBuilderForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry.RegistryForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.Registry1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.RegistryHandler1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.RegistryVanilla1_20;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Set;

import static net.minecraft.core.registries.BuiltInRegistries.*;
import static net.minecraftforge.registries.ForgeRegistries.*;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;
import static net.minecraftforge.registries.ForgeRegistries.POTIONS;
import static net.minecraftforge.registries.ForgeRegistries.SOUND_EVENTS;

public class RegistryHandlerForge1_20_6 extends RegistryHandler1_20 {
    
    @Override protected void collectRegistries(Set<? super Registry1_20<?>> registries) {
        this.biome = getRegistry(registries,BIOMES, "biome", Biome.class);
        this.block = getRegistry(registries,BLOCKS,"block",Block.class);
        this.blockEntity = getRegistry(registries,BLOCK_ENTITY_TYPES,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,MOB_EFFECTS,"effect",MobEffect.class);
        this.entity = getRegistry(registries,ENTITY_TYPES,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEMS,"item",Item.class);
        this.potion = getRegistry(registries,POTIONS,"potion",Potion.class);
        this.sound = getRegistry(registries,SOUND_EVENTS,"sound",SoundEvent.class);
        this.structure = getVanillaRegistry(registries,STRUCTURE_TYPE,"structure",StructureType.class);
    }
    
    private <V> Registry1_20<V> getRegistry(Set<? super Registry1_20<?>> registries,
            IForgeRegistry<V> forgeRegistry, String name, Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(new ResourceLocation(name));
        Registry1_20<V> registry = new RegistryForge1_20<>(forgeRegistry,key,type);
        registries.add(registry);
        return registry;
    }
    
    @SuppressWarnings("SameParameterValue")
    private <V> Registry1_20<V> getVanillaRegistry(Set<? super Registry1_20<?>> registries,
            Registry<V> vanillaRegistry,String name, Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(new ResourceLocation(name));
        Registry1_20<V> registry = new RegistryVanilla1_20<>(vanillaRegistry, key, type);
        registries.add(registry);
        return registry;
    }
    
    @Override public CreativeTabBuilderAPI<?> makeCreativeTabBuilder() {
        return new CreativeTabBuilderForge1_20_6();
    }
}