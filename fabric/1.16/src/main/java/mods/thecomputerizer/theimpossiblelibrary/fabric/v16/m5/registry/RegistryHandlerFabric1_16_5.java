package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.RegistryHandler1_16_5;
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

import java.util.Set;

import static net.minecraft.core.Registry.*;
import static net.minecraft.data.BuiltinRegistries.BIOME;

public class RegistryHandlerFabric1_16_5 extends RegistryHandler1_16_5 {
    
    @Override protected void collectRegistries(Set<? super Registry1_16_5<?>> registries) {
        this.biome = getRegistry(registries,BIOME,"biome",Biome.class);
        this.block = getRegistry(registries,BLOCK,"block", Block.class);
        this.blockEntity = getRegistry(registries,BLOCK_ENTITY_TYPE,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,POTION,"effect",Potion.class);
        this.entity = getRegistry(registries,ENTITY_TYPE,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEM,"item",Item.class);
        this.potion = getRegistry(registries,MOB_EFFECT,"potion",MobEffect.class);
        this.sound = getRegistry(registries,SOUND_EVENT,"sound",SoundEvent.class);
        this.structure = getRegistry(registries,STRUCTURE_FEATURE,"structure",SoundEvent.class);
    }
    
    @SuppressWarnings("unchecked")
    private <V> Registry1_16_5<V> getRegistry(
            Set<? super Registry1_16_5<?>> registries, Registry<V> forgeRegistry, String name, Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(new ResourceLocation(name));
        RegistryFabric1_16_5<V> registry = new RegistryFabric1_16_5<>(forgeRegistry,key,(Class<V>)type);
        registries.add(registry);
        return registry;
    }
}