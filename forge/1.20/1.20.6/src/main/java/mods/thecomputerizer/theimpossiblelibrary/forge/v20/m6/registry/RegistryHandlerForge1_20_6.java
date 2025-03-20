package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.registry.tab.CreativeTabBuilderForge1_20;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Set;

import static net.minecraft.core.registries.BuiltInRegistries.*;

public class RegistryHandlerForge1_20_6 extends RegistryHandler1_20 {
    
    @Override protected void collectRegistries(Set<? super Registry1_20<?>> registries) {
        this.block = getRegistry(registries,BLOCK,"block",Block.class);
        this.blockEntity = getRegistry(registries,BLOCK_ENTITY_TYPE,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,POTION,"effect",Potion.class);
        this.entity = getRegistry(registries,ENTITY_TYPE,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEM,"item",Item.class);
        this.potion = getRegistry(registries,MOB_EFFECT,"potion",MobEffect.class);
        this.sound = getRegistry(registries,SOUND_EVENT,"sound",SoundEvent.class);
        this.structure = getRegistry(registries,STRUCTURE_TYPE,"structure",StructureType.class);
    }
    
    @SuppressWarnings("unchecked")
    private <V> Registry1_20<V> getRegistry(
            Set<? super Registry1_20<?>> registries, Registry<V> vanillaRegistry, String name, Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(new ResourceLocation(name));
        RegistryVanilla1_20<V> registry = new RegistryVanilla1_20<>(vanillaRegistry, key, (Class<V>)type);
        registries.add(registry);
        return registry;
    }
    
    @Override public CreativeTabBuilderAPI<?> makeCreativeTabBuilder() {
        return new CreativeTabBuilderForge1_20();
    }
}