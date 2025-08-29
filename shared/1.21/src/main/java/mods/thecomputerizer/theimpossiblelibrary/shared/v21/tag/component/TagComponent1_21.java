package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.TagWrapper;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.item.component.CustomData;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TagComponent1_21 implements TagAPI {
    
    @Override public <T> BaseTagAPI<T> getWrapped(T component) {
        if(component instanceof CustomData) return GenericUtils.cast(new CompoundComponent1_21(component));
        if(component instanceof DataComponentMap)
            return GenericUtils.cast(new ListComponent1_21(component));
        TypedDataComponent<?> typed = (TypedDataComponent<?>)component;
        Object value = typed.value();
        if(value instanceof Number)
            return GenericUtils.cast(new PrimitiveComponent1_21(typed));
        if(value instanceof String)
            return GenericUtils.cast(new StringComponent1_21(typed));
        TILRef.logError("Failed to wrap component! {}",component);
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> makePrimitiveTag(boolean b) {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> makePrimitiveTag(byte b) {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> makePrimitiveTag(double d) {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> makePrimitiveTag(float f) {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> makePrimitiveTag(int i) {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> makePrimitiveTag(long l) {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> makePrimitiveTag(short s) {
        return null;
    }
    
    @Override public StringTagAPI<?> makeStringTag(String value) {
        return null;
    }
    
    @Override public Object newCompoundTag() {
        return CustomData.of(new CompoundTag());
    }
    
    @Override public Object newListTag() {
        return DataComponentMap.builder().build();
    }
    
    @Override public Object newPrimitiveTag(boolean b) {
        return null;
    }
    
    @Override public Object newPrimitiveTag(byte b) {
        return null;
    }
    
    @Override public Object newPrimitiveTag(double d) {
        return null;
    }
    
    @Override public Object newPrimitiveTag(float f) {
        return null;
    }
    
    @Override public Object newPrimitiveTag(int i) {
        return null;
    }
    
    @Override public Object newPrimitiveTag(long l) {
        return null;
    }
    
    @Override public Object newPrimitiveTag(short s) {
        return null;
    }
    
    @Override public Object newStringTag(String value) {
        return null;
    }
    
    @Override public Object readFromFileDirect(File file) throws IOException {
        CompoundTag tag = NbtIo.read(file.toPath());
        return CustomData.of(Objects.nonNull(tag) ? tag : new CompoundTag());
    }
    
    @Override public void writeToFileDirect(CompoundTagAPI<?> tag, File file) throws IOException {
        Object value = tag.getWrapped();
        CompoundTag compound = tag instanceof TagWrapper ? (CompoundTag)value : ((CustomData)value).copyTag();
        NbtIo.write(compound,file.toPath());
    }
}