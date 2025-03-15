package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

public class BlockHelper1_21 implements BlockHelperAPI {
    
    static final Map<String,MapColor> COLOR_BY_NAME = buildColorMap();
    
    static Map<String,MapColor> buildColorMap() {
        return Map.ofEntries(Map.entry("AIR",MapColor.NONE),
                Map.entry("BLACK",MapColor.COLOR_BLACK),
                Map.entry("BLACK_TERRACOTTA",MapColor.TERRACOTTA_BLACK),
                Map.entry("BLUE",MapColor.COLOR_BLUE),
                Map.entry("BLUE_TERRACOTTA",MapColor.TERRACOTTA_BLUE),
                Map.entry("BROWN",MapColor.COLOR_BROWN),
                Map.entry("BROWN_TERRACOTTA",MapColor.TERRACOTTA_BROWN),
                Map.entry("CLAY",MapColor.CLAY),
                Map.entry("CYAN", MapColor.COLOR_CYAN),
                Map.entry("CYAN_TERRACOTTA",MapColor.TERRACOTTA_CYAN),
                Map.entry("DIAMOND",MapColor.DIAMOND),
                Map.entry("DIRT", MapColor.DIRT),
                Map.entry("EMERALD",MapColor.EMERALD),
                Map.entry("FOLIAGE", MapColor.PLANT),
                Map.entry("GOLD",MapColor.GOLD),
                Map.entry("GRASS",MapColor.GRASS),
                Map.entry("GRAY",MapColor.COLOR_GRAY),
                Map.entry("GRAY_TERRACOTTA",MapColor.TERRACOTTA_GRAY),
                Map.entry("GREEN",MapColor.COLOR_GREEN),
                Map.entry("GREEN_TERRACOTTA",MapColor.TERRACOTTA_GREEN),
                Map.entry("ICE",MapColor.ICE),
                Map.entry("LAPIS", MapColor.LAPIS),
                Map.entry("LIGHT_BLUE",MapColor.COLOR_LIGHT_BLUE),
                Map.entry("LIGHT_BLUE_TERRACOTTA",MapColor.TERRACOTTA_LIGHT_BLUE),
                Map.entry("LIGHT_GRAY",MapColor.COLOR_LIGHT_GRAY),
                Map.entry("LIGHT_GRAY_TERRACOTTA",MapColor.TERRACOTTA_LIGHT_GRAY),
                Map.entry("LIME",MapColor.COLOR_LIGHT_GREEN),
                Map.entry("LIME_TERRACOTTA",MapColor.TERRACOTTA_LIGHT_GREEN),
                Map.entry("MAGENTA",MapColor.COLOR_MAGENTA),
                Map.entry("MAGENTA_TERRACOTTA",MapColor.TERRACOTTA_MAGENTA),
                Map.entry("METAL",MapColor.METAL),
                Map.entry("NETHER", MapColor.NETHER),
                Map.entry("ORANGE",MapColor.COLOR_ORANGE),
                Map.entry("ORANGE_TERRACOTTA",MapColor.TERRACOTTA_ORANGE),
                Map.entry("PINK",MapColor.COLOR_PINK),
                Map.entry("PINK_TERRACOTTA",MapColor.TERRACOTTA_PINK),
                Map.entry("PURPLE",MapColor.COLOR_PURPLE),
                Map.entry("PURPLE_TERRACOTTA",MapColor.TERRACOTTA_PURPLE),
                Map.entry("QUARTZ",MapColor.QUARTZ),
                Map.entry("RED",MapColor.COLOR_RED),
                Map.entry("RED_TERRACOTTA",MapColor.TERRACOTTA_RED),
                Map.entry("SAND",MapColor.SAND),
                Map.entry("SNOW",MapColor.SNOW),
                Map.entry("STONE",MapColor.STONE),
                Map.entry("WATER",MapColor.WATER),
                Map.entry("WHITE_TERRACOTTA",MapColor.TERRACOTTA_WHITE),
                Map.entry("WOOL",MapColor.WOOL),
                Map.entry("WOOD",MapColor.WOOD),
                Map.entry("YELLOW",MapColor.COLOR_YELLOW),
                Map.entry("YELLOW_TERRACOTTA",MapColor.TERRACOTTA_YELLOW));
    }
    
    @SuppressWarnings("unchecked") @Override public <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal) {
        if(defVal instanceof Boolean) return new BlockProperty1_21<>((Property<V>)BooleanProperty.create(name));
        if(defVal instanceof Enum<?>) return new BlockProperty1_21<>((Property<V>)createPropertyEnum(name, defVal.getClass()));
        if(defVal instanceof Number) return new BlockProperty1_21<>((Property<V>)IntegerProperty.create(name,0, ((Number)defVal).intValue()));
        TILRef.logError("Unsupported createProperty type for {}!",Objects.nonNull(defVal) ? defVal.getClass() : "null");
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private  <E extends Enum<E> & StringRepresentable> EnumProperty<E> createPropertyEnum(String name, Class<?> clazz) {
        return EnumProperty.create(name,(Class<E>)clazz);
    }
    
    @Override public <P> BlockPropertyAPI<?,?> getAsProperty(P property) {
        if(property instanceof Property<?>) return new BlockProperty1_21<>((Property<?>)property);
        TILRef.logError("Object {} is not an instance of {}!",property,Property.class);
        return null;
    }
    
    @Override public Material1_21 getMaterialByName(String name) { //TODO figure out how to handle materials in 1.20+
        //if(StringUtils.isBlank(name)) return new Material1_21(WOOD);
        //Field field = ReflectionHelper.getField(Material.class, name.toUpperCase());
        //if(Objects.nonNull(field)) return new Material1_21(ReflectionHelper.getFieldInstance(null, field));
        //return new Material1_21(AIR);
        return null;
    }
    
    @Override public MaterialColor1_21 getMaterialColorByName(String name) {
        MapColor color = StringUtils.isBlank(name) ? MapColor.GRASS :
                COLOR_BY_NAME.getOrDefault(name,MapColor.GRASS);
        return new MaterialColor1_21(color);
    }
}
