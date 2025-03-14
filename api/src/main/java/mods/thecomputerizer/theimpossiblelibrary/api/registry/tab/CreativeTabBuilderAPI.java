package mods.thecomputerizer.theimpossiblelibrary.api.registry.tab;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import java.util.function.Supplier;

/**
 * Parameterized with ItemStack
 */
public abstract class CreativeTabBuilderAPI<S> {
    
    protected Supplier<S> icon;
    @Setter protected ResourceLocationAPI<?> registryName;
    
    public abstract CreativeTabAPI<?> build();
    
    public void setIcon(Supplier<ItemStackAPI<?>> stack) {
        this.icon = () -> stack.get().unwrap();
    }
    
    public void setIconDirect(Supplier<S> stack) {
        this.icon = stack;
    }
    
    public void setIconItem(Supplier<ItemAPI<?>> item) {
        this.icon = () -> item.get().defaultStack().unwrap();
    }
}