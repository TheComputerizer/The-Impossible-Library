package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.util.IStringSerializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockHelper1_16_5 implements BlockHelperAPI {
    
    static final Map<String,MaterialColor> COLOR_BY_NAME = buildColorMap();
    static final Map<String,Material> MATERIAL_BY_NAME = buildMaterialMap();
    
    static Map<String,MaterialColor> buildColorMap() {
        Map<String,MaterialColor> map = new HashMap<>();
        map.put("AIR",MaterialColor.NONE);
        map.put("BLACK",MaterialColor.COLOR_BLACK);
        map.put("BLACK_TERRACOTTA",MaterialColor.TERRACOTTA_BLACK);
        map.put("BLUE",MaterialColor.COLOR_BLUE);
        map.put("BLUE_TERRACOTTA",MaterialColor.TERRACOTTA_BLUE);
        map.put("BROWN",MaterialColor.COLOR_BROWN);
        map.put("BROWN_TERRACOTTA",MaterialColor.TERRACOTTA_BROWN);
        map.put("CLAY",MaterialColor.CLAY);
        map.put("CYAN",MaterialColor.COLOR_CYAN);
        map.put("CYAN_TERRACOTTA",MaterialColor.TERRACOTTA_CYAN);
        map.put("DIAMOND",MaterialColor.DIAMOND);
        map.put("DIRT",MaterialColor.DIRT);
        map.put("EMERALD",MaterialColor.EMERALD);
        map.put("FOLIAGE",MaterialColor.PLANT);
        map.put("GOLD",MaterialColor.GOLD);
        map.put("GRASS",MaterialColor.GRASS);
        map.put("GRAY",MaterialColor.COLOR_GRAY);
        map.put("GRAY_TERRACOTTA",MaterialColor.TERRACOTTA_GRAY);
        map.put("GREEN",MaterialColor.COLOR_GREEN);
        map.put("GREEN_TERRACOTTA",MaterialColor.TERRACOTTA_GREEN);
        map.put("ICE",MaterialColor.ICE);
        map.put("LAPIS",MaterialColor.LAPIS);
        map.put("LIGHT_BLUE",MaterialColor.COLOR_LIGHT_BLUE);
        map.put("LIGHT_BLUE_TERRACOTTA",MaterialColor.TERRACOTTA_LIGHT_BLUE);
        map.put("LIGHT_GRAY",MaterialColor.COLOR_LIGHT_GRAY);
        map.put("LIGHT_GRAY_TERRACOTTA",MaterialColor.TERRACOTTA_LIGHT_GRAY);
        map.put("LIME",MaterialColor.COLOR_LIGHT_GREEN);
        map.put("LIME_TERRACOTTA",MaterialColor.TERRACOTTA_LIGHT_GREEN);
        map.put("MAGENTA",MaterialColor.COLOR_MAGENTA);
        map.put("MAGENTA_TERRACOTTA",MaterialColor.TERRACOTTA_MAGENTA);
        map.put("METAL",MaterialColor.METAL);
        map.put("NETHER",MaterialColor.NETHER);
        map.put("ORANGE",MaterialColor.COLOR_ORANGE);
        map.put("ORANGE_TERRACOTTA",MaterialColor.TERRACOTTA_ORANGE);
        map.put("PINK",MaterialColor.COLOR_PINK);
        map.put("PINK_TERRACOTTA",MaterialColor.TERRACOTTA_PINK);
        map.put("PURPLE",MaterialColor.COLOR_PURPLE);
        map.put("PURPLE_TERRACOTTA",MaterialColor.TERRACOTTA_PURPLE);
        map.put("QUARTZ",MaterialColor.QUARTZ);
        map.put("RED",MaterialColor.COLOR_RED);
        map.put("RED_TERRACOTTA",MaterialColor.TERRACOTTA_RED);
        map.put("SAND",MaterialColor.SAND);
        map.put("SNOW",MaterialColor.SNOW);
        map.put("STONE",MaterialColor.STONE);
        map.put("WATER",MaterialColor.WATER);
        map.put("WHITE_TERRACOTTA",MaterialColor.TERRACOTTA_WHITE);
        map.put("WOOL",MaterialColor.WOOL);
        map.put("WOOD",MaterialColor.WOOD);
        map.put("YELLOW",MaterialColor.COLOR_YELLOW);
        map.put("YELLOW_TERRACOTTA",MaterialColor.TERRACOTTA_YELLOW);
        return Collections.unmodifiableMap(map);
    }
    
    static Map<String,Material> buildMaterialMap() {
        Map<String,Material> map = new HashMap<>();
        map.put("AIR",Material.AIR);
        map.put("ANVIL",Material.HEAVY_METAL);
        map.put("BARRIER",Material.BARRIER);
        map.put("CACTUS",Material.CACTUS);
        map.put("CAKE",Material.CAKE);
        map.put("CLAY",Material.CLAY);
        map.put("CORAL",Material.CORAL);
        map.put("DIRT",Material.DIRT);
        map.put("DRAGON_EGG",Material.EGG);
        map.put("EXPLOSIVE",Material.EXPLOSIVE);
        map.put("FIRE",Material.FIRE);
        map.put("GLASS",Material.GLASS);
        map.put("VEGETABLE",Material.VEGETABLE);
        map.put("GRASS",Material.GRASS);
        map.put("ICE",Material.ICE);
        map.put("LAVA",Material.LAVA);
        map.put("LEAVES",Material.LEAVES);
        map.put("METAL",Material.METAL);
        map.put("PACKED_ICE",Material.ICE_SOLID);
        map.put("PISTON",Material.PISTON);
        map.put("PORTAL",Material.PORTAL);
        map.put("SAND",Material.SAND);
        map.put("SNOW",Material.SNOW);
        map.put("SNOW_LAYER",Material.TOP_SNOW);
        map.put("SPONGE",Material.SPONGE);
        map.put("STONE",Material.STONE);
        map.put("STRUCTURE_VOID",Material.STRUCTURAL_AIR);
        map.put("WATER",Material.WATER);
        map.put("WEB",Material.WEB);
        map.put("WOOL",Material.WOOL);
        map.put("WOOD",Material.WOOD);
        return Collections.unmodifiableMap(map);
    }
    
    @SuppressWarnings("unchecked") @Override public <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal) {
        if(defVal instanceof Boolean) return new BlockProperty1_16_5<>((Property<V>)BooleanProperty.create(name));
        if(defVal instanceof Enum<?>) return new BlockProperty1_16_5<>((Property<V>)createPropertyEnum(name, defVal.getClass()));
        if(defVal instanceof Number) return new BlockProperty1_16_5<>((Property<V>)IntegerProperty.create(name, 0, ((Number)defVal).intValue()));
        TILRef.logError("Unsupported createProperty type for {}!",Objects.nonNull(defVal) ? defVal.getClass() : "null");
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private  <E extends Enum<E> & IStringSerializable> EnumProperty<E> createPropertyEnum(String name, Class<?> clazz) {
        return EnumProperty.create(name,(Class<E>)clazz);
    }
    
    @Override public <P> BlockPropertyAPI<?,?> getAsProperty(P property) {
        if(property instanceof Property<?>) return new BlockProperty1_16_5<>((Property<?>)property);
        TILRef.logError("Object {} is not an instance of {}!",property,Property.class);
        return null;
    }
    
    @Override public Material1_16_5 getMaterialByName(String name) {
        Material mat = TextHelper.isBlank(name) ? Material.WOOD : MATERIAL_BY_NAME.getOrDefault(name, Material.WOOD);
        return new Material1_16_5(mat);
    }
    
    @Override public MaterialColor1_16_5 getMaterialColorByName(String name) {
        MaterialColor color = TextHelper.isBlank(name) ? MaterialColor.GRASS :
                COLOR_BY_NAME.getOrDefault(name,MaterialColor.GRASS);
        return new MaterialColor1_16_5(color);
    }
}