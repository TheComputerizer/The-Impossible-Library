package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TILBasicItem1_12_2 extends Item {
    
    protected final BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc;
    
    public TILBasicItem1_12_2(
            @Nullable BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
        this.descFunc = descFunc;
    }
    
    @Override
    @SideOnly(CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if(Objects.nonNull(this.descFunc)) {
            ItemStackAPI<?> stackAPI = new ItemStack1_12_2(stack);
            WorldAPI<?> worldAPI = Objects.nonNull(world) ? new World1_12_2(world) : null;
            this.descFunc.apply(stackAPI,worldAPI).forEach(text -> tooltip.add(text.getApplied()));
        }
    }
}