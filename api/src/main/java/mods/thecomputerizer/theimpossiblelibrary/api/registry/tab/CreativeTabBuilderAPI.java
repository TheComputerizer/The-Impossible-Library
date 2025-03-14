package mods.thecomputerizer.theimpossiblelibrary.api.registry.tab;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import java.util.function.Supplier;

/**
 * Parameterized with ItemStack
 */
public abstract class CreativeTabBuilderAPI<S> {
    
    protected static final GameVersion VERSION = CoreAPI.getInstance().getVersion();
    
    protected Supplier<S> icon;
    @Setter protected ResourceLocationAPI<?> registryName;
    
    public abstract CreativeTabAPI<?> build();
    
    public void setIcon(Supplier<ItemStackAPI<?>> stack) {
        this.icon = () -> stack.get().unwrap();
    }
    
    @IndirectCallers
    public void setIconDirect(Supplier<S> stack) {
        this.icon = stack;
    }
    
    @IndirectCallers
    public void setIconItem(Supplier<ItemAPI<?>> item) {
        this.icon = () -> item.get().defaultStack().unwrap();
    }
}