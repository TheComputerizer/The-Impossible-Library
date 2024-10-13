package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.Registry1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.RegistryHandler1_18_2;
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

public class RegistryHandlerForge1_18_2 extends RegistryHandler1_18_2 {
    
    @Override protected void collectRegistries(Set<? super Registry1_18_2<?>> registries) {
        this.biome = getRegistry(registries,BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,BLOCKS,"block",Block.class);
        this.blockEntity = getRegistry(registries,BLOCK_ENTITIES,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,MOB_EFFECTS,"effect", MobEffect.class);
        this.entity = getRegistry(registries,ENTITIES,"entity", EntityType.class);
        this.item = getRegistry(registries,ITEMS,"item", Item.class);
        this.potion = getRegistry(registries,POTIONS,"potion", Potion.class);
        this.sound = getRegistry(registries,SOUND_EVENTS,"sound", SoundEvent.class);
        this.structure = getRegistry(registries,STRUCTURE_FEATURES,"structure",StructureFeature.class);
    }
    
    @SuppressWarnings("unchecked")
    private <V extends IForgeRegistryEntry<V>> RegistryForge1_18_2<V> getRegistry(
            Set<? super Registry1_18_2<?>> registries, IForgeRegistry<V> forgeRegistry, String name, Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(new ResourceLocation(name));
        RegistryForge1_18_2<V> registry = new RegistryForge1_18_2<>(forgeRegistry,key,(Class<V>)type);
        registries.add(registry);
        return registry;
    }
}