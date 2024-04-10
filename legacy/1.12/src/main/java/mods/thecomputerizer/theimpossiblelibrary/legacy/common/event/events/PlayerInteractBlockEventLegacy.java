package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.EventsLegacy;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;

public class PlayerInteractBlockEventLegacy extends PlayerInteractBlockEventWrapper<RightClickBlock> {

    @SubscribeEvent
    public static void onEvent(RightClickBlock event) {
        PLAYER_INTERACT_BLOCK.invoke(event);
    }

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
        return wrapGenericBoth(event -> EventsLegacy.getResult(event.getUseBlock()),
                (event,result) -> event.setUseBlock(EventsLegacy.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventsLegacy.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventsLegacy.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventsLegacy.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventsLegacy.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Vector3d> wrapHitVecField() {
        return wrapGenericGetter(event -> EventsLegacy.getVec3d(event.getHitVec()),VectorHelper.ZERO_3D);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Result> wrapItemResultField() {
        return wrapGenericBoth(event -> EventsLegacy.getResult(event.getUseItem()),
                (event,result) -> event.setUseItem(EventsLegacy.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickBlock::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickBlock::getPos);
    }
}