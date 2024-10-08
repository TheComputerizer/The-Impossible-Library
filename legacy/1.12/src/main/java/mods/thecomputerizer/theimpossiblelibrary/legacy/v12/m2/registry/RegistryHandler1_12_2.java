package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ToolType;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.sound.SoundBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block.BlockBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.blockentity.BlockEntityBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.blockentity.BlockEntityRegistry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.entity.EntityBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item.DiscBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item.ItemBlockBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item.ItemBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item.ToolBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.sound.SoundBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.structure.StructureRegistry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.tab.CreativeTabBuilder1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.minecraftforge.fml.common.registry.ForgeRegistries.*;

public class RegistryHandler1_12_2 implements RegistryHandlerAPI {

    private final Set<RegistryAPI<?>> registries;
    private final RegistryAPI<Biome> biome;
    private final RegistryAPI<Block> block;
    private final RegistryAPI<?> blockEntity;
    private final RegistryAPI<Potion> effect;
    private final RegistryAPI<EntityEntry> entity;
    private final RegistryAPI<Item> item;
    private final RegistryAPI<PotionType> potion;
    private final RegistryAPI<SoundEvent> sound;
    private final RegistryAPI<?> structure;

    public RegistryHandler1_12_2() {
        Set<RegistryAPI<?>> registries = new HashSet<>();
        this.biome = getRegistry(registries,BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,BLOCKS,"block",Block.class);
        this.effect = getRegistry(registries,POTIONS,"effect",Potion.class);
        this.entity = getRegistry(registries,ENTITIES,"entity",EntityEntry.class);
        this.item = getRegistry(registries,ITEMS,"item",Item.class);
        this.potion = getRegistry(registries,POTION_TYPES,"potion",PotionType.class);
        this.sound = getRegistry(registries,SOUND_EVENTS,"sound",SoundEvent.class);
        this.registries = Collections.unmodifiableSet(registries);
        this.blockEntity = new BlockEntityRegistry1_12_2();
        this.structure = new StructureRegistry1_12_2();
    }

    private <V extends IForgeRegistryEntry<V>> RegistryAPI<V> getRegistry(
            Set<RegistryAPI<?>> registries, IForgeRegistry<V> forgeRegistry, String name, Class<V> type) {
        ResourceLocation1_12_2 key = new ResourceLocation1_12_2(new ResourceLocation(name));
        Registry1_12_2<V> registry = new Registry1_12_2<>(forgeRegistry,type,key);
        registries.add(registry);
        return registry;
    }

    @SuppressWarnings("unchecked")
    @Override public <V> @Nullable V getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryAPI<V> reg = (RegistryAPI<V>)getRegistry(registryKey);
        return reg.hasKey(entryKey) ? reg.getValue(entryKey) : null;
    }

    @Override public RegistryAPI<Biome> getBiomeRegistry() {
        return this.biome;
    }

    @Override public RegistryAPI<Block> getBlockRegistry() {
        return this.block;
    }

    @Override public RegistryAPI<?> getBlockEntityRegistry() {
        return this.blockEntity;
    }

    @Override public RegistryAPI<Potion> getEffectRegistry() {
        return this.effect;
    }

    @Override public RegistryAPI<EntityEntry> getEntityRegistry() {
        return this.entity;
    }

    @Override public RegistryAPI<Item> getItemRegistry() {
        return this.item;
    }

    @Override public RegistryAPI<PotionType> getPotionRegistry() {
        return this.potion;
    }

    @Override public RegistryAPI<?> getRegistry(ResourceLocationAPI<?> registryKey) {
        switch(registryKey.getPath()) {
            case "biome": return this.biome;
            case "block": return this.block;
            case "block_entity": return this.blockEntity;
            case "entity": return this.entity;
            case "item": return this.item;
            case "potion": return this.potion;
            case "sound": return this.sound;
            case "structure": return this.structure;
            default: return null;
        }
    }

    @Override public RegistryAPI<?> getRegistry(Class<?> type) {
        if(type==TileEntity.class) return getBlockEntityRegistry();
        for(RegistryAPI<?> registry : this.registries)
            if(registry.getType().isAssignableFrom(type)) return registry;
        return null;
    }

    @Override public RegistryAPI<SoundEvent> getSoundRegistry() {
        return this.sound;
    }
    
    @Override public RegistryAPI<?> getStructureRegistry() {
        return this.structure;
    }
    
    @Override public BlockBuilderAPI makeBlockBuilder(@Nullable BlockBuilderAPI parent) {
        return new BlockBuilder1_12_2(parent);
    }
    
    @Override public BlockEntityBuilderAPI makeBlockEntityBuilder(@Nullable BlockEntityBuilderAPI parent) {
        return new BlockEntityBuilder1_12_2(parent);
    }
    
    @Override public CreativeTabBuilderAPI makeCreativeTabBuilder() {
        return new CreativeTabBuilder1_12_2();
    }
    
    @Override public DiscBuilderAPI makeDiscBuilder(@Nullable ItemBuilderAPI parent) {
        return new DiscBuilder1_12_2(parent);
    }
    
    @Override public EntityBuilderAPI makeEntityBuilder(@Nullable EntityBuilderAPI parent) {
        return new EntityBuilder1_12_2(parent);
    }
    
    @Override public ItemBlockBuilderAPI makeItemBlockBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBlockBuilder1_12_2(parent);
    }
    
    @Override public ItemBuilderAPI makeItemBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBuilder1_12_2(parent);
    }
    
    @Override public SoundBuilderAPI makeSoundBuilder(@Nullable SoundBuilderAPI parent) {
        return new SoundBuilder1_12_2(parent);
    }
    
    @Override public ToolBuilderAPI makeToolBuilder(@Nullable ItemBuilderAPI parent, ToolType tool) {
        return new ToolBuilder1_12_2(parent, tool);
    }
}