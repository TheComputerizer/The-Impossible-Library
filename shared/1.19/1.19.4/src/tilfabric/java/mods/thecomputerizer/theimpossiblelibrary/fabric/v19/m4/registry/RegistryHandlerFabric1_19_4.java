package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.registry.tab.CreativeTabBuilderFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.Registry1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.RegistryHandler1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.RegistryVanilla1_19_4;
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

public class RegistryHandlerFabric1_19_4 extends RegistryHandler1_19_4 {
    
    @Override protected void collectRegistries(Set<? super Registry1_19_4<?>> registries) {
        this.block = getRegistry(registries,BLOCK,"block",Block.class);
        this.blockEntity = getRegistry(registries,BLOCK_ENTITY_TYPE,"block_entity",BlockEntityType.class);
        this.effect = getRegistry(registries,POTION,"effect",Potion.class);
        this.entity = getRegistry(registries,ENTITY_TYPE,"entity",EntityType.class);
        this.item = getRegistry(registries,ITEM,"item",Item.class);
        this.potion = getRegistry(registries,MOB_EFFECT,"potion",MobEffect.class);
        this.sound = getRegistry(registries,SOUND_EVENT,"sound",SoundEvent.class);
        this.structure = getRegistry(registries,STRUCTURE_TYPE,"structure",StructureType.class);
    }
    
    private <V> Registry1_19_4<V> getRegistry(Set<? super Registry1_19_4<?>> registries,
            Registry<V> vanillaRegistry, String name, Class<?> type) {
        ResourceLocationAPI<?> key = WrapperHelper.wrapResourceLocation(new ResourceLocation(name));
        Registry1_19_4<V> registry = new RegistryVanilla1_19_4<>(vanillaRegistry, key, type);
        registries.add(registry);
        return registry;
    }
    
    @Override public CreativeTabBuilderAPI<?> makeCreativeTabBuilder() {
        return new CreativeTabBuilderFabric1_19_4();
    }
}