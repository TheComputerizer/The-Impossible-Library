package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks.CallStrategy.STATIC_DIRECT;
import static net.minecraft.core.component.DataComponents.CUSTOM_DATA;

public class ListComponent1_20_6 extends ListTagAPI<DataComponentMap> implements ComponentWrapper {

    private DataComponentMap mutableWrapped;
    
    public ListComponent1_20_6(Object map) {
        super(map);
        this.mutableWrapped = GenericUtils.cast(map);
    }

    @Override public void addTag(BaseTagAPI<?> api) {
        boolean hasCustomData = this.mutableWrapped.has(CUSTOM_DATA);
        Builder builder = DataComponentMap.builder();
        for(TypedDataComponent<?> component : this.wrapped) addComponent(builder,component);
        CompoundTag customTag = addComponent(builder,api.getWrapped(),hasCustomData);
        this.mutableWrapped = builder.build();
        if(Objects.nonNull(customTag)) {
            TypedDataComponent<CustomData> customComponent = this.mutableWrapped.getTyped(CUSTOM_DATA);
            if(Objects.nonNull(customComponent))
                customComponent.value().update(tag -> tag.merge(customTag));
        }
    }
    
    private CompoundTag addComponent(Builder builder, @Nullable Object wrapped, boolean hasCustomData) {
        if(Objects.isNull(wrapped)) return null;
        return switch(wrapped) {
            case TypedDataComponent<?> component -> {
                addComponent(builder, component);
                yield null;
            }
            case DataComponentMap map -> {
                builder.addAll(map);
                yield null;
            }
            case Tag tag -> {
                if(tag instanceof CompoundTag compound) {
                    if(hasCustomData) yield compound;
                    Hacks.checkBurningWaveInit();
                    //Why did TypedDataComponent#createUnchecked start out as package-private??
                    addComponent(builder,STATIC_DIRECT.invoke(TypedDataComponent.class,"createUnchecked",
                            CUSTOM_DATA,CustomData.of(compound)));
                } else TILRef.logWarn("Tag must be CompoundTag instance to add to ListComponent! {}", tag);
                yield null;
            }
            default -> {
                TILRef.logWarn("Not adding unknown tag type to ListComponent {}", wrapped);
                yield null;
            }
        };
    }
    
    private <T> void addComponent(Builder builder, TypedDataComponent<T> component) {
        builder.set(component.type(),component.value());
    }
    
    @Override public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        this.mutableWrapped.forEach(based -> tags.add(TagHelper.getWrapped(based)));
        return tags;
    }
}