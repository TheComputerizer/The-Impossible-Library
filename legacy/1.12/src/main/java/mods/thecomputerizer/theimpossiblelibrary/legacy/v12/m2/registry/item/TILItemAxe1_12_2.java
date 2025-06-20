package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

public class TILItemAxe1_12_2 extends ItemAxe implements ItemHelpers1_12_2 {
    
    protected final ItemProperties properties;
    
    @IndirectCallers
    public TILItemAxe1_12_2(ToolMaterial material, float damage, float speed, ItemProperties properties) {
        super(material,damage,speed);
        this.properties = properties;
    }
    
    @SideOnly(CLIENT)
    @Override public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip,
            ITooltipFlag flag) {
        defaultAppendHoverText(stack,world,tooltip);
    }
    
    @Override public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        Supplier<EnumActionResult> superUseOn = () -> super.onItemUse(player,world,pos,hand,facing,hitX,hitY,hitZ);
        return defaultUseOn(player,world,pos,hand,facing,superUseOn);
    }
    
    @Override public ItemProperties getProperties() {
        return this.properties;
    }
}