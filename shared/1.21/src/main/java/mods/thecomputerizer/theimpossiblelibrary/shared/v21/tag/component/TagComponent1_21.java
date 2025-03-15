package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.CompoundTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.ListTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.PrimitiveTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.StringTag1_21;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.nbt.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 *
 */
public class TagComponent1_21 implements TagAPI {
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T component) {
        if(component instanceof DataComponentMap)
            return (BaseTagAPI<T>)new ListComponent1_21((DataComponentMap)component);
        TypedDataComponent<?> typed = (TypedDataComponent<?>)component;
        Object value = typed.value();
        if(value instanceof Number)
            return (BaseTagAPI<T>)new PrimitiveComponent1_21((TypedDataComponent<Number>)typed);
        if(value instanceof String)
            return (BaseTagAPI<T>)new StringComponent1_21((TypedDataComponent<String>)typed);
        TILRef.logError("Failed to wrap component! {}",component);
        return null;
    }
    
    @Override public CompoundTag1_21 makeCompoundTag() {
        return null; //No compound components?
    }

    @Override public ListComponent1_21 makeListTag() {
        return null;
    }
    
    @Override public PrimitiveComponent1_21 makePrimitiveTag(boolean b) {
        return null;
    }
    
    @Override public PrimitiveComponent1_21 makePrimitiveTag(byte b) {
        return null;
    }
    
    @Override public PrimitiveComponent1_21 makePrimitiveTag(double d) {
        return null;
    }
    
    @Override public PrimitiveComponent1_21 makePrimitiveTag(float f) {
        return null;
    }
    
    @Override public PrimitiveComponent1_21 makePrimitiveTag(int i) {
        return null;
    }
    
    @Override public PrimitiveComponent1_21 makePrimitiveTag(long l) {
        return null;
    }
    
    @Override public PrimitiveComponent1_21 makePrimitiveTag(short s) {
        return null;
    }
    
    @Override public StringComponent1_21 makeStringTag(String value) {
        return null;
    }
    
    //TODO Figure out if these need to be handled
    @Override public CompoundTag1_21 readFromFile(File file) {
        return null;
    }

    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) {}
}