package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.EventsForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;

public class PlayerInteractBlockEventForge extends PlayerInteractBlockEventWrapper<RightClickBlock> {

    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(RightClickBlock::getItemStack);
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return wrapWorld(RightClickBlock::getWorld);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Result> wrapBlockResultField() {
        return wrapGenericBoth(event -> EventsForge.getResult(event.getUseBlock()),
                (event,result) -> event.setUseBlock(EventsForge.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventsForge.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventsForge.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventsForge.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventsForge.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Vector3d> wrapHitVecField() {
        return wrapGenericGetter(event -> EventsForge.getVec3d(event.getHitVec().getLocation()),VectorHelper.ZERO_3D);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Result> wrapItemResultField() {
        return wrapGenericBoth(event -> EventsForge.getResult(event.getUseItem()),
                (event,result) -> event.setUseItem(EventsForge.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickBlock::getPlayer);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickBlock::getPos);
    }
}