package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

import static net.minecraft.world.item.ItemStack.EMPTY;

public class CreativeTabBuilderFabric1_19_2 extends CreativeTabBuilderAPI<ItemStack> {
    
    @Override public CreativeTabAPI<?> build() {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot build creative tab with null registry name!");
            return null;
        }
        return WrapperHelper.wrapTab(FabricItemGroupBuilder.build(this.registryName.unwrap(),this::getBuilderIcon));
    }
    
    /**
     * Fabric API isn't getting remapped properly atm and I don't feel like fixing it
     */
    @SuppressWarnings("unchecked")
    <T> T castStupidly(Object o) {
        return (T)o;
    }
    
    <T> T getBuilderIcon() {
        ItemStack icon = Objects.nonNull(this.icon) ? this.icon.get() : EMPTY;
        return castStupidly(Objects.nonNull(icon) ? icon : EMPTY);
    }
}