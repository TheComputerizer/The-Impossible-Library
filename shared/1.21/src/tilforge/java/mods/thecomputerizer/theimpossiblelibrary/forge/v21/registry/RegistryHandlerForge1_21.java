package mods.thecomputerizer.theimpossiblelibrary.forge.v21.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.registry.tab.CreativeTabBuilderForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.Registry1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.RegistryHandler1_21;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Set;

import static net.minecraftforge.registries.ForgeRegistries.*;

public class RegistryHandlerForge1_21 extends RegistryHandler1_21 {
    
    @Override protected void collectRegistries(Set<? super Registry1_21<?>> registries) {
        this.biome = getRegistry(registries,BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,BLOCKS,"block",Block.class);
        this.blockEntity = getRegistry(registries,BLOCK_ENTITY_TYPES,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,MOB_EFFECTS,"effect",MobEffect.class);
        this.entity = getRegistry(registries,ENTITY_TYPES,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEMS,"item",Item.class);
        this.potion = getRegistry(registries,POTIONS,"potion",Potion.class);
        this.sound = getRegistry(registries,SOUND_EVENTS,"sound",SoundEvent.class);
    }
    
    @SuppressWarnings("unchecked")
    private <V> RegistryForge1_21<V> getRegistry(
            Set<? super Registry1_21<?>> registries, IForgeRegistry<V> forgeRegistry, String name, Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(ResourceLocation.parse(name));
        RegistryForge1_21<V> registry = new RegistryForge1_21<>(forgeRegistry,key,(Class<V>)type);
        registries.add(registry);
        return registry;
    }
    
    @Override public CreativeTabBuilderAPI<?> makeCreativeTabBuilder() {
        return new CreativeTabBuilderForge1_21();
    }
}