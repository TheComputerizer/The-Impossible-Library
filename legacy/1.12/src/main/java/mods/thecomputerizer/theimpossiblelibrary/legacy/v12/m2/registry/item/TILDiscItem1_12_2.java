package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.WithItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILDiscItem1_12_2 extends ItemRecord implements WithItemProperties {
    
    protected final ItemProperties properties;
    private final Function<ItemStackAPI<?>,TextAPI<?>> nameSupplier;
    
    public TILDiscItem1_12_2(Function<ItemStackAPI<?>,TextAPI<?>> nameSupplier, SoundEvent sound, ItemProperties properties) {
        super("name",sound);
        this.nameSupplier = nameSupplier;
        this.properties = properties;
        this.setMaxStackSize(properties.getStackSize());
        ResourceLocation name = properties.getRegistryName().unwrap();
        setRegistryName(name);
        setTranslationKey(name.getNamespace()+"."+name.getPath());
    }
    
    @SideOnly(CLIENT)
    @Override public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        getTooltipLines(() -> WrapperHelper.wrapItemStack(stack),() -> Objects.nonNull(world) ?
                WrapperHelper.wrapWorld(world) : null).forEach(text -> tooltip.add(text.getApplied()));
    }
    
    @SideOnly(Side.CLIENT)
    @Override public String getRecordNameLocal() {
        return Objects.nonNull(this.nameSupplier) ? this.nameSupplier.apply(null).getApplied() : "";
    }
    
    @Override public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        return EventHelper.setActionResult(getUseResult(() -> {
            TILItemUseContext ctx = TILItemUseContext.wrap(player,world,pos,null,hand,facing);
            ctx.setSuperResult(EventHelper.getActionResult(super.onItemUse(player,world,pos,hand,facing,hitX,hitY,hitZ)));
            return ctx;
        }));
    }
    
    @Override public @Nonnull ItemProperties getProperties() {
        return this.properties;
    }
}