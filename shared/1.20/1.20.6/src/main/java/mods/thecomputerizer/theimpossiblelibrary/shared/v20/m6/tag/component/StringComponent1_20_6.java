package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.core.component.TypedDataComponent;

public class StringComponent1_20_6 extends StringTagAPI<TypedDataComponent<String>> implements ComponentWrapper {

    public StringComponent1_20_6(Object component) {
        super(component);
    }
    
    @Override public String getValue() {
        return getIfNotNull(TypedDataComponent::value);
    }
}