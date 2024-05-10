package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.EntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.SoundBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
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
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RegistryHandler1_12_2 implements RegistryHandlerAPI {

    private final Set<Registry1_12_2<?>> registries;
    private final Registry1_12_2<Biome> biome;
    private final Registry1_12_2<Block> block;
    private final TileEntityRegistry1_12_2 blockEntity;
    private final Registry1_12_2<Potion> effect;
    private final Registry1_12_2<EntityEntry> entity;
    private final Registry1_12_2<Item> item;
    private final Registry1_12_2<PotionType> potion;
    private final Registry1_12_2<SoundEvent> sound;

    public RegistryHandler1_12_2() {
        Set<Registry1_12_2<?>> registries = new HashSet<>();
        this.biome = getRegistry(registries,ForgeRegistries.BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,ForgeRegistries.BLOCKS,"block",Block.class);
        this.effect = getRegistry(registries,ForgeRegistries.POTIONS,"effect",Potion.class);
        this.entity = getRegistry(registries,ForgeRegistries.ENTITIES,"entity",EntityEntry.class);
        this.item = getRegistry(registries,ForgeRegistries.ITEMS,"item",Item.class);
        this.potion = getRegistry(registries,ForgeRegistries.POTION_TYPES,"potion",PotionType.class);
        this.sound = getRegistry(registries,ForgeRegistries.SOUND_EVENTS,"sound",SoundEvent.class);
        this.registries = Collections.unmodifiableSet(registries);
        this.blockEntity = new TileEntityRegistry1_12_2();
    }

    private <V extends IForgeRegistryEntry<V>> Registry1_12_2<V> getRegistry(
            Set<Registry1_12_2<?>> registries, IForgeRegistry<V> forgeRegistry, String name, Class<V> type) {
        ResourceLocation1_12_2 key = new ResourceLocation1_12_2(new ResourceLocation(name));
        Registry1_12_2<V> registry = new Registry1_12_2<>(forgeRegistry,type,key);
        registries.add(registry);
        return registry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> @Nullable V getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryAPI<V> reg = (RegistryAPI<V>)getRegistry(registryKey);
        return reg.hasKey(entryKey) ? reg.getValue(entryKey) : null;
    }

    @Override public Registry1_12_2<Biome> getBiomeRegistry() {
        return this.biome;
    }

    @Override public Registry1_12_2<Block> getBlockRegistry() {
        return this.block;
    }

    @Override public TileEntityRegistry1_12_2 getBlockEntityRegistry() {
        return this.blockEntity;
    }

    @Override public Registry1_12_2<Potion> getEffectRegistry() {
        return this.effect;
    }

    @Override public Registry1_12_2<EntityEntry> getEntityRegistry() {
        return this.entity;
    }

    @Override public Registry1_12_2<Item> getItemRegistry() {
        return this.item;
    }

    @Override public Registry1_12_2<PotionType> getPotionRegistry() {
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
            default: return null;
        }
    }

    @Override public RegistryAPI<?> getRegistry(Class<?> type) {
        if(type==TileEntity.class) return getBlockEntityRegistry();
        for(Registry1_12_2<?> registry : this.registries)
            if(registry.getType().isAssignableFrom(type)) return registry;
        return null;
    }

    @Override public Registry1_12_2<SoundEvent> getSoundRegistry() {
        return this.sound;
    }
    
    @Override public BlockBuilderAPI makeBlockBuilder() {
        return new BlockBuilder1_12_2();
    }
    
    @Override public CreativeTabBuilderAPI makeCreativeTabBuilder() {
        return new CreativeTabBuilder1_12_2();
    }
    
    @Override public EntityBuilderAPI makeEntityBuilder() {
        return new EntityBuilder1_12_2();
    }
    
    @Override public ItemBuilderAPI makeItemBuilder() {
        return new ItemBuilder1_12_2();
    }
    
    @Override public SoundBuilderAPI makeSoundBuilder() {
        return new SoundBuilder1_12_2();
    }
}
