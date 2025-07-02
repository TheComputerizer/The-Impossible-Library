package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPunchBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public abstract class PlayerPunchBlockEventForge extends PlayerPunchBlockEventWrapper<LeftClickBlock>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(event -> ((LeftClickBlock)event).getItemStack());
    }
    
    @Override public void setEvent(LeftClickBlock event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LeftClickBlock,Result> wrapBlockResultField() {
        return wrapGenericBoth(event -> EventHelper.getEventResult(event.getUseBlock()),
                (event,result) -> event.setUseBlock(EventHelper.setEventResult((Result)result)),DEFAULT);
    }

    @Override protected EventFieldWrapper<LeftClickBlock,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventHelper.setActionResult((ActionResult)result)),PASS);
    }

    @Override protected EventFieldWrapper<LeftClickBlock,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),Facing.UP);
    }

    @Override protected EventFieldWrapper<LeftClickBlock,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventHelper.getHand(event.getHand()),MAINHAND);
    }

    @Override protected EventFieldWrapper<LeftClickBlock,Vector3> wrapHitVecField() {
        return wrapGenericGetter(event -> VectorHelper.zero3D(),VectorHelper.zero3D());
    }

    @Override protected EventFieldWrapper<LeftClickBlock,Result> wrapItemResultField() {
        return wrapGenericBoth(event -> EventHelper.getEventResult(event.getUseItem()),
                (event,result) -> event.setUseItem(EventHelper.setEventResult((Result)result)),DEFAULT);
    }

    @Override protected EventFieldWrapper<LeftClickBlock,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickBlock::getEntity);
    }

    @Override protected EventFieldWrapper<LeftClickBlock,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickBlock::getPos);
    }
}