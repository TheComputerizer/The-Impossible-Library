package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.function.Supplier;

import static net.minecraft.world.item.ItemStack.EMPTY;

public class CreativeTabBuilderFabric1_16_5 extends CreativeTabBuilderAPI<ItemStack> {
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        Object name = this.registryName.unwrap();
        FabricItemGroupBuilder builder = Hacks.invokeStatic(FabricItemGroupBuilder.class, "create", name);
        builder = Hacks.invoke(builder,"icon",(Supplier<?>)this::getBuilderIcon);
        return WrapperHelper.wrapTab(Hacks.invoke(builder,"build"));
    }
    
    @SuppressWarnings("unchecked")
    <T> T castStupidly(Object o) {
        return (T)o;
    }
    
    <T> T getBuilderIcon() {
        ItemStack icon = Objects.nonNull(this.icon) ? this.icon.get() : EMPTY;
        return castStupidly(Objects.nonNull(icon) ? icon : EMPTY);
    }
}