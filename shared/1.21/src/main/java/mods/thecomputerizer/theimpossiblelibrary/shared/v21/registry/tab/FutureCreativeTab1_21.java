package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.item.CreativeModeTab;

import javax.annotation.Nullable;

import java.util.function.Supplier;

import static net.minecraft.world.item.ItemStack.EMPTY;

public class FutureCreativeTab1_21 extends CreativeTabAPI<FutureCreativeTab<CreativeModeTab>> {
    
    public FutureCreativeTab1_21(FutureCreativeTab<CreativeModeTab> wrapped) {
        super(wrapped);
    }
    
    @Override public void addStack(Supplier<ItemStackAPI<?>> stack) {
        this.stacks.add(stack);
    }
    
    @Override public ItemStackAPI<?> getIcon() {
        if(this.wrapped.isRegistered()) return WrapperHelper.wrapItemStack(this.wrapped.getWrapped().getIconItem());
        TILRef.logError("Cannot get icon for CreativeModeTab before it has been registered!");
        return WrapperHelper.wrapItemStack(EMPTY);
    }
    
    public void register(@Nullable Object event) {
        if(this.wrapped.isRegistered()) TILRef.logWarn("Tried to register FutureCreativeTab1_21 twice!");
        else this.wrapped.register(event);
    }
    
    public void supply(@Nullable Object event) {
        this.wrapped.supply(event,this.stacks);
    }
    
    @Override public <P> P withItemProperties(P properties) {
        return null; //Not valid here
    }
}