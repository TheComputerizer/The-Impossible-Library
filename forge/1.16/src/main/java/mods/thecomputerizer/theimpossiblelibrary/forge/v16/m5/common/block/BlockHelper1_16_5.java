package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

import static net.minecraft.block.material.Material.AIR;
import static net.minecraft.block.material.Material.WOOD;
import static net.minecraft.block.material.MaterialColor.GRASS;

@SuppressWarnings("unused")
public class BlockHelper1_16_5 implements BlockHelperAPI {
    
    @SuppressWarnings("unchecked") @Override
    public <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal) {
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
        if(StringUtils.isBlank(name)) return new Material1_16_5(WOOD);
        Field field = ReflectionHelper.getField(Material.class, name.toUpperCase());
        if(Objects.nonNull(field)) return new Material1_16_5((Material)ReflectionHelper.getFieldInstance(null,field));
        return new Material1_16_5(AIR);
    }
    
    @Override public MaterialColor1_16_5 getMaterialColorByName(String name) {
        if(StringUtils.isBlank(name)) return new MaterialColor1_16_5(GRASS);
        Field field = ReflectionHelper.getField(MaterialColor.class,name.toUpperCase());
        if(Objects.nonNull(field))
            return new MaterialColor1_16_5((MaterialColor)ReflectionHelper.getFieldInstance(null,field));
        return new MaterialColor1_16_5(GRASS);
    }
}
