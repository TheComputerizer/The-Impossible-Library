package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.IStringSerializable;

import java.util.Objects;

public class BlockHelper1_12_2 implements BlockHelperAPI {
    
    @SuppressWarnings("unchecked") @Override
    public <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal) {
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
    
    @Override public <P> BlockPropertyAPI<?,?> getAsProperty(P property) {
        if(property instanceof IProperty<?>) return new BlockProperty1_12_2<>((IProperty<?>)property);
        TILRef.logError("Object {} is not an instance of {}!",property,IProperty.class);
        return null;
    }
}
