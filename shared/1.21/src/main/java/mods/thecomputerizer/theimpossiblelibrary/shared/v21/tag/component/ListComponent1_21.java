package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.CompoundTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.PrimitiveTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.StringTag1_21;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.component.TypedDataComponent;

import java.util.ArrayList;
import java.util.List;

public class ListComponent1_21 extends ListTagAPI<DataComponentMap> {

    private DataComponentMap mutableWrapped;
    
    public ListComponent1_21(DataComponentMap map) {
        super(map);
        this.mutableWrapped = map;
    }

    @Override public void addTag(BaseTagAPI<?> tag) {
        Builder builder = DataComponentMap.builder();
        for(TypedDataComponent<?> component : this.wrapped) addComponent(builder,component);
        this.mutableWrapped = builder.build();
    }
    
    private <T> void addComponent(Builder builder, TypedDataComponent<T> component) {
        builder.set(component.type(),component.value());
    }
    
    @Override public CompoundTag1_21 asCompoundTag() {
        return null;
    }
    
    @Override public ListComponent1_21 asListTag() {
        return this;
    }
    
    @Override public PrimitiveTag1_21 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTag1_21 asStringTag() {
        return null;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return true;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return false;
    }
    
    @Override public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        this.mutableWrapped.forEach(based -> tags.add(TagHelper.getWrapped(based)));
        return tags;
    }
}