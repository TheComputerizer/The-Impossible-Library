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
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerPunchBlockEventForge extends PlayerPunchBlockEventWrapper<LeftClickBlock>
        implements CommonForgeEvent {
    
    @SubscribeEvent
    public static void onEvent(LeftClickBlock event) {
        PLAYER_PUNCH_BLOCK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(stackGetter());
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(worldGetter());
    }
    
    @Override public void setEvent(LeftClickBlock event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LeftClickBlock,Result> wrapBlockResultField() {
        return wrapEventResultBoth("getUseBlock","setUseBlock");
    }
    
    @Override protected EventFieldWrapper<LeftClickBlock,ActionResult> wrapCancelResultField() {
        return wrapActionResultBoth("getCancellationResult","setCancellationResult");
    }
    
    @Override protected EventFieldWrapper<LeftClickBlock,Facing> wrapFacingField() {
        return wrapGenericGetter(getter("getFace",EventHelper::getFacing),UP);
    }
    
    @Override protected EventFieldWrapper<LeftClickBlock,Hand> wrapHandField() {
        return wrapGenericGetter(getter("getHand",EventHelper::getHand),MAINHAND);
    }

    @Override protected EventFieldWrapper<LeftClickBlock,Vector3> wrapHitVecField() {
        return wrapGenericGetter(event -> VectorHelper.zero3D(),VectorHelper.zero3D());
    }
    
    @Override protected EventFieldWrapper<LeftClickBlock,Result> wrapItemResultField() {
        return wrapEventResultBoth("getUseItem","setUseItem");
    }
    
    @Override protected EventFieldWrapper<LeftClickBlock,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(entityGetter());
    }
    
    @Override protected EventFieldWrapper<LeftClickBlock,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(getter("getPos"));
    }
}