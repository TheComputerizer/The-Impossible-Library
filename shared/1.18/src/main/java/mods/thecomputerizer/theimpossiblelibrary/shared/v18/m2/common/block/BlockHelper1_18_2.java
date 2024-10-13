package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

import static net.minecraft.world.level.material.Material.AIR;
import static net.minecraft.world.level.material.Material.WOOD;
import static net.minecraft.world.level.material.MaterialColor.GRASS;

@SuppressWarnings("unused")
public class BlockHelper1_18_2 implements BlockHelperAPI {
    
    @SuppressWarnings("unchecked") @Override public <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal) {
        if(defVal instanceof Boolean) return new BlockProperty1_18_2<>((Property<V>)BooleanProperty.create(name));
        if(defVal instanceof Enum<?>) return new BlockProperty1_18_2<>((Property<V>)createPropertyEnum(name, defVal.getClass()));
        if(defVal instanceof Number) return new BlockProperty1_18_2<>((Property<V>)IntegerProperty.create(name, 0, ((Number)defVal).intValue()));
        TILRef.logError("Unsupported createProperty type for {}!",Objects.nonNull(defVal) ? defVal.getClass() : "null");
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private  <E extends Enum<E> & StringRepresentable> EnumProperty<E> createPropertyEnum(String name, Class<?> clazz) {
        return EnumProperty.create(name,(Class<E>)clazz);
    }
    
    @Override public <P> BlockPropertyAPI<?,?> getAsProperty(P property) {
        if(property instanceof Property<?>) return new BlockProperty1_18_2<>((Property<?>)property);
        TILRef.logError("Object {} is not an instance of {}!",property,Property.class);
        return null;
    }
    
    @Override public Material1_18_2 getMaterialByName(String name) {
        if(StringUtils.isBlank(name)) return new Material1_18_2(WOOD);
        Field field = ReflectionHelper.getField(Material.class, name.toUpperCase());
        if(Objects.nonNull(field)) return new Material1_18_2(ReflectionHelper.getFieldInstance(null, field));
        return new Material1_18_2(AIR);
    }
    
    @Override public MaterialColor1_18_2 getMaterialColorByName(String name) {
        if(StringUtils.isBlank(name)) return new MaterialColor1_18_2(GRASS);
        Field field = ReflectionHelper.getField(MaterialColor.class, name.toUpperCase());
        if(Objects.nonNull(field))
            return new MaterialColor1_18_2((MaterialColor)ReflectionHelper.getFieldInstance(null,field));
        return new MaterialColor1_18_2(GRASS);
    }
}
