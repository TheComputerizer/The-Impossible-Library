package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

public class TILItemBlock1_12_2 extends ItemBlock implements ItemHelpers1_12_2 {
    
    protected final ItemProperties properties;
    
    @IndirectCallers
    public TILItemBlock1_12_2(Block block, ItemProperties properties) {
        super(block);
        this.properties = properties;
    }
    
    @SideOnly(CLIENT)
    @Override public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip,
            ITooltipFlag flag) {
        defaultAppendHoverText(stack,world,tooltip);
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}