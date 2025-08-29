package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Map;
import java.util.Objects;

public class BlockHelper1_18_2 implements BlockHelperAPI {
    
    static final Map<String,MaterialColor> COLOR_BY_NAME = buildColorMap();
    static final Map<String,Material> MATERIAL_BY_NAME = buildMaterialMap();
    
    static Map<String,MaterialColor> buildColorMap() {
        return Map.ofEntries(Map.entry("AIR",MaterialColor.NONE),
                Map.entry("BLACK",MaterialColor.COLOR_BLACK),
                Map.entry("BLACK_TERRACOTTA",MaterialColor.TERRACOTTA_BLACK),
                Map.entry("BLUE",MaterialColor.COLOR_BLUE),
                Map.entry("BLUE_TERRACOTTA",MaterialColor.TERRACOTTA_BLUE),
                Map.entry("BROWN",MaterialColor.COLOR_BROWN),
                Map.entry("BROWN_TERRACOTTA",MaterialColor.TERRACOTTA_BROWN),
                Map.entry("CLAY",MaterialColor.CLAY),
                Map.entry("CYAN", MaterialColor.COLOR_CYAN),
                Map.entry("CYAN_TERRACOTTA",MaterialColor.TERRACOTTA_CYAN),
                Map.entry("DIAMOND",MaterialColor.DIAMOND),
                Map.entry("DIRT", MaterialColor.DIRT),
                Map.entry("EMERALD",MaterialColor.EMERALD),
                Map.entry("FOLIAGE", MaterialColor.PLANT),
                Map.entry("GOLD",MaterialColor.GOLD),
                Map.entry("GRASS", MaterialColor.GRASS),
                Map.entry("GRAY",MaterialColor.COLOR_GRAY),
                Map.entry("GRAY_TERRACOTTA",MaterialColor.TERRACOTTA_GRAY),
                Map.entry("GREEN",MaterialColor.COLOR_GREEN),
                Map.entry("GREEN_TERRACOTTA",MaterialColor.TERRACOTTA_GREEN),
                Map.entry("ICE",MaterialColor.ICE),
                Map.entry("LAPIS", MaterialColor.LAPIS),
                Map.entry("LIGHT_BLUE",MaterialColor.COLOR_LIGHT_BLUE),
                Map.entry("LIGHT_BLUE_TERRACOTTA",MaterialColor.TERRACOTTA_LIGHT_BLUE),
                Map.entry("LIGHT_GRAY",MaterialColor.COLOR_LIGHT_GRAY),
                Map.entry("LIGHT_GRAY_TERRACOTTA",MaterialColor.TERRACOTTA_LIGHT_GRAY),
                Map.entry("LIME",MaterialColor.COLOR_LIGHT_GREEN),
                Map.entry("LIME_TERRACOTTA",MaterialColor.TERRACOTTA_LIGHT_GREEN),
                Map.entry("MAGENTA",MaterialColor.COLOR_MAGENTA),
                Map.entry("MAGENTA_TERRACOTTA",MaterialColor.TERRACOTTA_MAGENTA),
                Map.entry("METAL",MaterialColor.METAL),
                Map.entry("NETHER", MaterialColor.NETHER),
                Map.entry("ORANGE",MaterialColor.COLOR_ORANGE),
                Map.entry("ORANGE_TERRACOTTA",MaterialColor.TERRACOTTA_ORANGE),
                Map.entry("PINK",MaterialColor.COLOR_PINK),
                Map.entry("PINK_TERRACOTTA",MaterialColor.TERRACOTTA_PINK),
                Map.entry("PURPLE",MaterialColor.COLOR_PURPLE),
                Map.entry("PURPLE_TERRACOTTA",MaterialColor.TERRACOTTA_PURPLE),
                Map.entry("QUARTZ",MaterialColor.QUARTZ),
                Map.entry("RED",MaterialColor.COLOR_RED),
                Map.entry("RED_TERRACOTTA",MaterialColor.TERRACOTTA_RED),
                Map.entry("SAND",MaterialColor.SAND),
                Map.entry("SNOW",MaterialColor.SNOW),
                Map.entry("STONE",MaterialColor.STONE),
                Map.entry("WATER",MaterialColor.WATER),
                Map.entry("WHITE_TERRACOTTA",MaterialColor.TERRACOTTA_WHITE),
                Map.entry("WOOL",MaterialColor.WOOL),
                Map.entry("WOOD",MaterialColor.WOOD),
                Map.entry("YELLOW",MaterialColor.COLOR_YELLOW),
                Map.entry("YELLOW_TERRACOTTA",MaterialColor.TERRACOTTA_YELLOW));
    }
    
    static Map<String,Material> buildMaterialMap() {
        return Map.ofEntries(Map.entry("AIR",Material.AIR),
                Map.entry("ANVIL",Material.HEAVY_METAL),
                Map.entry("BARRIER",Material.BARRIER),
                Map.entry("CACTUS",Material.CACTUS),
                Map.entry("CAKE",Material.CAKE),
                Map.entry("CLAY",Material.CLAY),
                Map.entry("CORAL",Material.WATER_PLANT),
                Map.entry("DIRT",Material.DIRT),
                Map.entry("DRAGON_EGG",Material.EGG),
                Map.entry("EXPLOSIVE",Material.EXPLOSIVE),
                Map.entry("FIRE",Material.FIRE),
                Map.entry("GLASS",Material.GLASS),
                Map.entry("VEGETABLE",Material.VEGETABLE),
                Map.entry("GRASS",Material.GRASS),
                Map.entry("ICE",Material.ICE),
                Map.entry("LAVA",Material.LAVA),
                Map.entry("LEAVES",Material.LEAVES),
                Map.entry("METAL",Material.METAL),
                Map.entry("PACKED_ICE",Material.ICE_SOLID),
                Map.entry("PISTON",Material.PISTON),
                Map.entry("PORTAL",Material.PORTAL),
                Map.entry("SAND",Material.SAND),
                Map.entry("SNOW",Material.SNOW),
                Map.entry("SNOW_LAYER",Material.TOP_SNOW),
                Map.entry("SPONGE",Material.SPONGE),
                Map.entry("STONE",Material.STONE),
                Map.entry("STRUCTURE_VOID",Material.STRUCTURAL_AIR),
                Map.entry("WATER",Material.WATER),
                Map.entry("WEB",Material.WEB),
                Map.entry("WOOL",Material.WOOL),
                Map.entry("WOOD",Material.WOOD));
    }
    
    @Override public <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal) {
        if(defVal instanceof Boolean) return new BlockProperty1_18_2<>(BooleanProperty.create(name));
        if(defVal instanceof Enum<?>) return new BlockProperty1_18_2<>(createPropertyEnum(name, defVal.getClass()));
        if(defVal instanceof Number)
            return new BlockProperty1_18_2<>(IntegerProperty.create(name,0,((Number)defVal).intValue()));
        TILRef.logError("Unsupported createProperty type for {}!",
                        Objects.nonNull(defVal) ? defVal.getClass() : "null");
        return null;
    }
    
    private <E extends Enum<E> & StringRepresentable> EnumProperty<E> createPropertyEnum(String name,
            Class<?> clazz) {
        Class<E> enumClass = GenericUtils.cast(clazz);
        return Objects.nonNull(enumClass) ? EnumProperty.create(name,enumClass) : null;
    }
    
    @Override public <P> BlockPropertyAPI<?,?> getAsProperty(P property) {
        if(property instanceof Property<?>) return new BlockProperty1_18_2<>(property);
        TILRef.logError("Object {} is not an instance of {}!",property,Property.class);
        return null;
    }
    
    @Override public Object getMaterialByNameDirect(String name) {
        return TextHelper.isBlank(name) ? Material.WOOD : MATERIAL_BY_NAME.getOrDefault(name,Material.WOOD);
    }
    
    @Override public MaterialColorAPI<?> getMaterialColorByName(String name) {
        MaterialColor color = TextHelper.isBlank(name) ? MaterialColor.GRASS :
                COLOR_BY_NAME.getOrDefault(name,MaterialColor.GRASS);
        return new MaterialColor1_18_2(color);
    }
}