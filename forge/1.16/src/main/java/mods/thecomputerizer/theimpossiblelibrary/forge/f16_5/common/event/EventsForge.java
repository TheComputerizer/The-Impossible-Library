package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.OFFHAND;

public class EventsForge implements EventsAPI {

    public static ActionResult getActionResult(ActionResultType result) {
        switch(result) {
            case CONSUME: return ActionResult.CONSUME;
            case PASS: return ActionResult.PASS;
            case SUCCESS: return ActionResult.SUCCESS;
            default: return ActionResult.FAIL;
        }
    }

    public static Box getBox(AxisAlignedBB aabb) {
        return new Box(aabb.minX,aabb.minY,aabb.minZ,aabb.maxX,aabb.maxY,aabb.maxZ);
    }

    public static @Nullable Facing getFacing(@Nullable Direction facing) {
        if(Objects.isNull(facing)) return null;
        switch(facing) {
            case DOWN: return Facing.DOWN;
            case EAST: return Facing.EAST;
            case NORTH: return Facing.NORTH;
            case SOUTH: return Facing.SOUTH;
            case UP: return Facing.UP;
            default: return Facing.WEST;
        }
    }

    public static Hand getHand(net.minecraft.util.Hand hand) {
        return hand==net.minecraft.util.Hand.MAIN_HAND ? MAINHAND : OFFHAND;
    }

    public static Result getResult(Event.Result result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }

    public static Vector3d getVec3d(net.minecraft.util.math.vector.Vector3d vec) {
        return new Vector3d(vec.x,vec.y,vec.z);
    }

    public static ActionResultType setActionResult(ActionResult result) {
        switch(result) {
            case CONSUME: return ActionResultType.CONSUME;
            case PASS: return ActionResultType.PASS;
            case SUCCESS: return ActionResultType.SUCCESS;
            default: return ActionResultType.FAIL;
        }
    }

    public static AxisAlignedBB setBox(Box box) {
        return new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z);
    }

    public static @Nullable Direction setFacing(@Nullable Facing facing) {
        if(Objects.isNull(facing)) return null;
        switch(facing) {
            case DOWN: return Direction.DOWN;
            case EAST: return Direction.EAST;
            case NORTH: return Direction.NORTH;
            case SOUTH: return Direction.SOUTH;
            case UP: return Direction.UP;
            default: return Direction.WEST;
        }
    }

    public static net.minecraft.util.Hand setHand(Hand hand) {
        return hand==MAINHAND ? net.minecraft.util.Hand.MAIN_HAND : net.minecraft.util.Hand.OFF_HAND;
    }

    public static Event.Result setResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }

    public static net.minecraft.util.math.vector.Vector3d setVec3d(Vector3d vec) {
        return new net.minecraft.util.math.vector.Vector3d(vec.x,vec.y,vec.z);
    }

    private boolean defined;

    public EventsForge() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEventForge());
        BLOCK_BREAK.setConnector(new BlockBreakEventForge());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEventForge());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEventForge());
        BLOCK_INTERACT.setConnector(new BlockInteractEventForge());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEventForge());
        BLOCK_PLACE.setConnector(new BlockPlaceEventForge());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEventForge());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEventForge());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEventForge());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEventForge());
        CROP_GROW_POST.setConnector(new CropGrowPostEventForge());
        CROP_GROW_PRE.setConnector(new CropGrowPreEventForge());
        CUSTOM_TICK.setConnector(new CustomTickEventForge());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventForge());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEventForge());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEventForge());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEventForge());
        EXPLOSION_START.setConnector(new ExplosionStartEventForge());
        LIVING_ATTACKED.setConnector(new LivingAttackedEventForge());
        LIVING_DAMAGE.setConnector(new LivingDamageEventForge());
        LIVING_DEATH.setConnector(new LivingDeathEventForge());
        LIVING_FALL.setConnector(new LivingFallEventForge());
        LIVING_HEAL.setConnector(new LivingHealEventForge());
        LIVING_HURT.setConnector(new LivingHurtEventForge());
        LIVING_JUMP.setConnector(new LivingJumpEventForge());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventForge());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventForge());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEventForge());
        LIVING_UPDATE.setConnector(new LivingUpdateEventForge());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEventForge());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEventForge());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEventForge());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEventForge());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEventForge());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEventForge());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEventForge());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEventForge());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEventForge());
        PLAYER_CLONE.setConnector(new PlayerCloneEventForge());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEventForge());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEventForge());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEventForge());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEventForge());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEventForge());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEventForge());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEventForge());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEventForge());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEventForge());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEventForge());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEventForge());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEventForge());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEventForge());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEventForge());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEventForge());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEventForge());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEventForge());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEventForge());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEventForge());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEventForge());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEventForge());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEventForge());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEventForge());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEventForge());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEventForge());
        REGISTER_GENERIC.setConnector(new RegistryEventForge());
        TICK_PLAYER.setConnector(new PlayerTickEventForge());
        TICK_WORLD.setConnector(new WorldTickEventForge());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventForge());
        WORLD_LOAD.setConnector(new WorldLoadEventForge());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventForge());
        WORLD_SAVE.setConnector(new WorldSaveEventForge());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventForge());
        this.defined = true;
    }

    @Override
    public boolean isDefined() {
        return this.defined;
    }

    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        MinecraftForge.EVENT_BUS.addListener(event -> wrapper.getType().invoke(event));
    }
}