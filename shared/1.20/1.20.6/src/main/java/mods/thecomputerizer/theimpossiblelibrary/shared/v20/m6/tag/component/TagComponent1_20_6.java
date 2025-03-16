package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.TagWrapper;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.item.component.CustomData;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TagComponent1_20_6 implements TagAPI {
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T component) {
        if(component instanceof CustomData)
            return (BaseTagAPI<T>)new CompoundComponent1_20_6((CustomData)component);
        if(component instanceof DataComponentMap)
            return (BaseTagAPI<T>)new ListComponent1_20_6((DataComponentMap)component);
        TypedDataComponent<?> typed = (TypedDataComponent<?>)component;
        Object value = typed.value();
        if(value instanceof Number)
            return (BaseTagAPI<T>)new PrimitiveComponent1_20_6((TypedDataComponent<Number>)typed);
        if(value instanceof String)
            return (BaseTagAPI<T>)new StringComponent1_20_6((TypedDataComponent<String>)typed);
        TILRef.logError("Failed to wrap component! {}",component);
        return null;
    }
    
    @Override public CompoundComponent1_20_6 makeCompoundTag() {
        return new CompoundComponent1_20_6(CustomData.of(new CompoundTag()));
    }

    @Override public ListTagAPI<?> makeListTag() {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 makePrimitiveTag(boolean b) {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 makePrimitiveTag(byte b) {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 makePrimitiveTag(double d) {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 makePrimitiveTag(float f) {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 makePrimitiveTag(int i) {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 makePrimitiveTag(long l) {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 makePrimitiveTag(short s) {
        return null;
    }
    
    @Override public StringComponent1_20_6 makeStringTag(String value) {
        return null;
    }
    
    @Override public CompoundComponent1_20_6 readFromFile(File file) throws IOException {
        CompoundTag tag = null;
        try {
            tag = NbtIo.read(file.toPath());
        } catch(EOFException ex) {
            TILRef.logWarn("Empty data file {}",file.toPath(),ex.getMessage());
        }
        if(Objects.isNull(tag)) tag = new CompoundTag();
        return new CompoundComponent1_20_6(CustomData.of(tag));
    }
    
    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        if(!tag.isEmpty()) {
            Object value = tag.getWrapped();
            CompoundTag compound = tag instanceof TagWrapper ? (CompoundTag)value : ((CustomData)value).copyTag();
            NbtIo.write(compound,file.toPath());
        }
    }
}