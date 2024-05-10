package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Collection;
import java.util.function.BiFunction;

import static mods.thecomputerizer.theimpossiblelibrary.api.registry.ItemBuilderAPI.ItemType.BASIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.registry.ItemBuilderAPI.ToolType.NONE;

@Setter
public abstract class ItemBuilderAPI extends RegistryEntryBuilder<ItemAPI<?>> {
    
    protected CreativeTabAPI creativeTab;
    protected BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc;
    protected ItemType itemType = BASIC;
    protected int stackSize = 1;
    protected ToolType toolType = NONE;
    
    public enum ItemType{ BASIC, BOW, DISC, FISHING_ROD, TOOL }
    public enum ToolType { NONE, AXE, HOE, PICKAXE, SHOVEL, SWORD }
}