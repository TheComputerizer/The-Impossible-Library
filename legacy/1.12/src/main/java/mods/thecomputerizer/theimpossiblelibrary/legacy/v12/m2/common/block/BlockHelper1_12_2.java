package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

import static net.minecraft.block.material.MapColor.GRASS;
import static net.minecraft.block.material.Material.WOOD;

public class BlockHelper1_12_2 implements BlockHelperAPI {
    
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
        if(StringUtils.isBlank(name)) return new Material1_12_2(WOOD);
        Field field = ReflectionHelper.getField(Material.class,name.toUpperCase());
        if(Objects.nonNull(field)) new Material1_12_2((Material)ReflectionHelper.getFieldInstance(null,field));
        return new Material1_12_2(WOOD);
    }
    
    @Override public MaterialColor1_12_2 getMaterialColorByName(String name) {
        if(StringUtils.isBlank(name)) return new MaterialColor1_12_2(GRASS);
        Field field = ReflectionHelper.getField(MapColor.class, name.toUpperCase());
        if(Objects.nonNull(field)) return new MaterialColor1_12_2((MapColor)ReflectionHelper.getFieldInstance(null,field));
        return new MaterialColor1_12_2(GRASS);
    }
}
