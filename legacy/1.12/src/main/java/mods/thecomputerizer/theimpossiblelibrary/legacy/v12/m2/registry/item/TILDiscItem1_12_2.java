package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

public class TILDiscItem1_12_2 extends ItemRecord implements ItemHelpers1_12_2 {
    
    protected final ItemProperties properties;
    private final Function<ItemStackAPI<?>,TextAPI<?>> nameSupplier;
    
    @IndirectCallers
    public TILDiscItem1_12_2(Function<ItemStackAPI<?>,TextAPI<?>> nameSupplier, SoundEvent sound, ItemProperties properties) {
        super("name",sound);
        this.nameSupplier = nameSupplier;
        this.properties = properties;
    }
    
    @SideOnly(CLIENT)
    @Override public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip,
            ITooltipFlag flag) {
        defaultAppendHoverText(stack,world,tooltip);
    }
    
    @SideOnly(Side.CLIENT)
    @Override public String getRecordNameLocal() {
        return Objects.nonNull(this.nameSupplier) ? this.nameSupplier.apply(null).getApplied() : "";
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