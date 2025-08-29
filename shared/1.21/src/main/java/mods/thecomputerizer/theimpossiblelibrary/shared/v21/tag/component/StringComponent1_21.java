package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.core.component.TypedDataComponent;

public class StringComponent1_21 extends StringTagAPI<TypedDataComponent<String>> implements ComponentWrapper {

    public StringComponent1_21(Object component) {
        super(component);
    }
    
    @Override public String getValue() {
        return getIfNotNull(TypedDataComponent::value);
    }
}