package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractBlockEvent1_12_2 extends PlayerInteractBlockEventWrapper<RightClickBlock> {

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
        return wrapGenericBoth(event -> Events1_12_2.getResult(event.getUseBlock()),
                (event,result) -> event.setUseBlock(Events1_12_2.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> Events1_12_2.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_12_2.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_12_2.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Hand> wrapHandField() {
        return wrapGenericGetter(event -> Events1_12_2.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Vector3d> wrapHitVecField() {
        return wrapGenericGetter(event -> Events1_12_2.getVec3d(event.getHitVec()),VectorHelper.zero3D());
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,Result> wrapItemResultField() {
        return wrapGenericBoth(event -> Events1_12_2.getResult(event.getUseItem()),
                (event,result) -> event.setUseItem(Events1_12_2.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickBlock::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<RightClickBlock,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickBlock::getPos);
    }
}