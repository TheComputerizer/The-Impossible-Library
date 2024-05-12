package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPunchBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerPunchBlockEvent1_12_2 extends PlayerPunchBlockEventWrapper<LeftClickBlock> {

    @SubscribeEvent
    public static void onEvent(LeftClickBlock event) {
        PLAYER_PUNCH_BLOCK.invoke(event);
    }

    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(LeftClickBlock::getItemStack);
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return wrapWorld(LeftClickBlock::getWorld);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,Result> wrapBlockResultField() {
        return wrapGenericBoth(event -> Events1_12_2.getResult(event.getUseBlock()),
                (event,result) -> event.setUseBlock(Events1_12_2.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> Events1_12_2.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_12_2.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_12_2.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,Hand> wrapHandField() {
        return wrapGenericGetter(event -> Events1_12_2.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,Vector3d> wrapHitVecField() {
        return wrapGenericGetter(event -> Events1_12_2.getVec3d(event.getHitVec()), VectorHelper.ZERO_3D);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,Result> wrapItemResultField() {
        return wrapGenericBoth(event -> Events1_12_2.getResult(event.getUseItem()),
                (event,result) -> event.setUseItem(Events1_12_2.setResult(result)),DEFAULT);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickBlock::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<LeftClickBlock,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickBlock::getPos);
    }
}