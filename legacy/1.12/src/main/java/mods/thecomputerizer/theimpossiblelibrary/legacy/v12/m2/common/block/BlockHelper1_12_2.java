package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockHelper1_12_2 implements BlockHelperAPI {
    
    static final Map<String,MapColor> COLOR_BY_NAME = buildColorMap();
    static final Map<String,Material> MATERIAL_BY_NAME = buildMaterialMap();
    
    static Map<String,MapColor> buildColorMap() {
        Map<String,MapColor> map = new HashMap<>();
        map.put("AIR",MapColor.AIR);
        map.put("BLACK",MapColor.BLACK);
        map.put("BLACK_TERRACOTTA",MapColor.BLACK_STAINED_HARDENED_CLAY);
        map.put("BLUE",MapColor.BLUE);
        map.put("BLUE_TERRACOTTA",MapColor.BLUE_STAINED_HARDENED_CLAY);
        map.put("BROWN",MapColor.BROWN);
        map.put("BROWN_TERRACOTTA",MapColor.BROWN_STAINED_HARDENED_CLAY);
        map.put("CLAY",MapColor.CLAY);
        map.put("CYAN",MapColor.CYAN);
        map.put("CYAN_TERRACOTTA",MapColor.CYAN_STAINED_HARDENED_CLAY);
        map.put("DIAMOND",MapColor.DIAMOND);
        map.put("DIRT",MapColor.DIRT);
        map.put("EMERALD",MapColor.EMERALD);
        map.put("GOLD",MapColor.GOLD);
        map.put("GRASS",MapColor.GRASS);
        map.put("GRAY",MapColor.GRAY);
        map.put("GRAY_TERRACOTTA",MapColor.GRAY_STAINED_HARDENED_CLAY);
        map.put("GREEN",MapColor.GREEN);
        map.put("GREEN_TERRACOTTA",MapColor.GREEN_STAINED_HARDENED_CLAY);
        map.put("ICE",MapColor.ICE);
        map.put("LAPIS",MapColor.LAPIS);
        map.put("LIGHT_BLUE",MapColor.LIGHT_BLUE);
        map.put("LIGHT_BLUE_TERRACOTTA",MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY);
        map.put("LIGHT_GRAY",MapColor.SILVER);
        map.put("LIGHT_GRAY_TERRACOTTA",MapColor.SILVER_STAINED_HARDENED_CLAY);
        map.put("LIME",MapColor.LIME);
        map.put("LIME_TERRACOTTA",MapColor.LIME_STAINED_HARDENED_CLAY);
        map.put("MAGENTA",MapColor.MAGENTA);
        map.put("MAGENTA_TERRACOTTA",MapColor.MAGENTA_STAINED_HARDENED_CLAY);
        map.put("METAL",MapColor.IRON);
        map.put("NETHER",MapColor.NETHERRACK);
        map.put("ORANGE_TERRACOTTA",MapColor.ORANGE_STAINED_HARDENED_CLAY);
        map.put("PINK",MapColor.PINK);
        map.put("PINK_TERRACOTTA",MapColor.PINK_STAINED_HARDENED_CLAY);
        map.put("PLANT",MapColor.FOLIAGE);
        map.put("PURPLE",MapColor.PURPLE);
        map.put("PURPLE_TERRACOTTA",MapColor.PURPLE_STAINED_HARDENED_CLAY);
        map.put("QUARTZ",MapColor.QUARTZ);
        map.put("RED",MapColor.RED);
        map.put("RED_TERRACOTTA",MapColor.RED_STAINED_HARDENED_CLAY);
        map.put("SAND",MapColor.SAND);
        map.put("SNOW",MapColor.SNOW);
        map.put("STONE",MapColor.STONE);
        map.put("WATER",MapColor.WATER);
        map.put("WHITE_TERRACOTTA",MapColor.WHITE_STAINED_HARDENED_CLAY);
        map.put("WOOL",MapColor.CLOTH);
        map.put("WOOD",MapColor.WOOD);
        map.put("YELLOW",MapColor.YELLOW);
        map.put("YELLOW_TERRACOTTA",MapColor.YELLOW_STAINED_HARDENED_CLAY);
        return Collections.unmodifiableMap(map);
    }
    
    static Map<String,Material> buildMaterialMap() {
        Map<String,Material> map = new HashMap<>();
        map.put("AIR",Material.AIR);
        map.put("ANVIL",Material.ANVIL);
        map.put("BARRIER",Material.BARRIER);
        map.put("CACTUS",Material.CACTUS);
        map.put("CAKE",Material.CAKE);
        map.put("CLAY",Material.CLAY);
        map.put("CORAL",Material.CORAL);
        map.put("DIRT",Material.GROUND);
        map.put("DRAGON_EGG",Material.DRAGON_EGG);
        map.put("EXPLOSIVE",Material.TNT);
        map.put("FIRE",Material.FIRE);
        map.put("GLASS",Material.GLASS);
        map.put("GRASS",Material.GRASS);
        map.put("ICE",Material.ICE);
        map.put("LAVA",Material.LAVA);
        map.put("LEAVES",Material.LEAVES);
        map.put("METAL",Material.IRON);
        map.put("PACKED_ICE",Material.PACKED_ICE);
        map.put("PISTON",Material.PISTON);
        map.put("PORTAL",Material.PORTAL);
        map.put("SAND",Material.SAND);
        map.put("SNOW",Material.SNOW);
        map.put("SNOW_LAYER",Material.CRAFTED_SNOW);
        map.put("SPONGE",Material.SPONGE);
        map.put("STONE",Material.ROCK);
        map.put("STRUCTURE_VOID",Material.STRUCTURE_VOID);
        map.put("VEGETABLE",Material.GOURD);
        map.put("VINE",Material.VINE);
        map.put("WATER",Material.WATER);
        map.put("WEB",Material.WEB);
        map.put("WOOL",Material.CLOTH);
        map.put("WOOD",Material.WOOD);
        return Collections.unmodifiableMap(map);
    }
    
    @SuppressWarnings("unchecked") @Override public <V extends Comparable<V>> BlockProperty1_12_2<V> createProperty(String name, V defVal) {
        if(defVal instanceof Boolean) return new BlockProperty1_12_2<>((IProperty<V>)PropertyBool.create(name));
        if(defVal instanceof Enum<?>) return new BlockProperty1_12_2<>((IProperty<V>)createPropertyEnum(name,defVal.getClass()));
        if(defVal instanceof Number) return new BlockProperty1_12_2<>((IProperty<V>)PropertyInteger.create(name,0,((Number)defVal).intValue()));
        TILRef.logError("Unsupported createProperty type for {}!",Objects.nonNull(defVal) ? defVal.getClass() : "null");
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private  <E extends Enum<E> & IStringSerializable> PropertyEnum<E> createPropertyEnum(String name, Class<?> clazz) {
        return PropertyEnum.create(name,(Class<E>)clazz);
    }
    
    @Override public <P> BlockProperty1_12_2<?> getAsProperty(P property) {
        if(property instanceof IProperty<?>) return new BlockProperty1_12_2<>((IProperty<?>)property);
        TILRef.logError("Object {} is not an instance of {}!",property,IProperty.class);
        return null;
    }
    
    @Override public Material1_12_2 getMaterialByName(String name) {
        Material mat = StringUtils.isBlank(name) ? Material.WOOD : MATERIAL_BY_NAME.getOrDefault(name,Material.WOOD);
        return new Material1_12_2(mat);
    }
    
    @Override public MaterialColor1_12_2 getMaterialColorByName(String name) {
        MapColor color = StringUtils.isBlank(name) ? MapColor.GRASS : COLOR_BY_NAME.getOrDefault(name,MapColor.GRASS);
        return new MaterialColor1_12_2(color);
    }
}
