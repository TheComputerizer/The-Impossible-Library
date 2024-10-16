package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntitySpecificEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY_AT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractEntitySpecificEvent1_12_2 extends PlayerInteractEntitySpecificEventWrapper<EntityInteractSpecific> {

    @SubscribeEvent
    public static void onEvent(EntityInteractSpecific event) {
        PLAYER_INTERACT_ENTITY_AT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(EntityInteractSpecific::getItemStack);
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(EntityInteractSpecific::getWorld);
    }
    
    @Override public void setEvent(EntityInteractSpecific event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<EntityInteractSpecific,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                               (event,result) -> event.setCancellationResult(EventHelper.setActionResult(result)),PASS);
    }

    @Override protected EventFieldWrapper<EntityInteractSpecific,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),UP);
    }

    @Override protected EventFieldWrapper<EntityInteractSpecific,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventHelper.getHand(event.getHand()), MAINHAND);
    }

    @Override protected EventFieldWrapper<EntityInteractSpecific,Vector3d> wrapLocalPosField() {
        return wrapGenericGetter(event -> EventHelper.getVec3d(event.getLocalPos()), VectorHelper.zero3D());
    }

    @Override protected EventFieldWrapper<EntityInteractSpecific,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(EntityInteractSpecific::getEntityPlayer);
    }

    @Override protected EventFieldWrapper<EntityInteractSpecific,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityInteractSpecific::getPos);
    }

    @Override protected EventFieldWrapper<EntityInteractSpecific,EntityAPI<?,?>> wrapTargetField() {
        return wrapEntityGetter(EntityInteractSpecific::getTarget);
    }
}