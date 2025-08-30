package mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry.tab.CreativeTabBuilderForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.Registry1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.RegistryHandler1_20;
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

public class RegistryHandlerForge1_20 extends RegistryHandler1_20 {
    
    @Override protected void collectRegistries(Set<? super Registry1_20<?>> registries) {
        this.biome = getRegistry(registries,BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,BLOCKS,"block",Block.class);
        this.blockEntity = getRegistry(registries,BLOCK_ENTITY_TYPES,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,MOB_EFFECTS,"effect",MobEffect.class);
        this.entity = getRegistry(registries,ENTITY_TYPES,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEMS,"item",Item.class);
        this.potion = getRegistry(registries,POTIONS,"potion",Potion.class);
        this.sound = getRegistry(registries,SOUND_EVENTS,"sound",SoundEvent.class);
    }
    
    private <V> RegistryForge1_20<V> getRegistry(
            Set<? super Registry1_20<?>> registries, IForgeRegistry<V> forgeRegistry, String name,
            Class<?> type) {
        //Avoid deprecation warning I guess
        ResourceLocation location = Hacks.construct(ResourceLocation.class,name);
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(location);
        RegistryForge1_20<V> registry = new RegistryForge1_20<>(forgeRegistry,key,GenericUtils.cast(type));
        registries.add(registry);
        return registry;
    }
    
    @Override public CreativeTabBuilderAPI<?> makeCreativeTabBuilder() {
        return new CreativeTabBuilderForge1_20();
    }
}