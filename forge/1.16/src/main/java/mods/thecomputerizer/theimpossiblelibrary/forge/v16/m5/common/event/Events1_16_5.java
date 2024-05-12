package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.util.CustomTick1_16_5;
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
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;

@SuppressWarnings("unused") public class Events1_16_5 implements EventsAPI {

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

    public Events1_16_5() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEvent1_16_5());
        BLOCK_BREAK.setConnector(new BlockBreakEvent1_16_5());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEvent1_16_5());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEvent1_16_5());
        BLOCK_INTERACT.setConnector(new BlockInteractEvent1_16_5());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEvent1_16_5());
        BLOCK_PLACE.setConnector(new BlockPlaceEvent1_16_5());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEvent1_16_5());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEvent1_16_5());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEvent1_16_5());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEvent1_16_5());
        CROP_GROW_POST.setConnector(new CropGrowPostEvent1_16_5());
        CROP_GROW_PRE.setConnector(new CropGrowPreEvent1_16_5());
        CUSTOM_TICK.setConnector(new CustomTickEvent1_16_5());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEvent1_16_5());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEvent1_16_5());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEvent1_16_5());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEvent1_16_5());
        EXPLOSION_START.setConnector(new ExplosionStartEvent1_16_5());
        LIVING_ATTACKED.setConnector(new LivingAttackedEvent1_16_5());
        LIVING_DAMAGE.setConnector(new LivingDamageEvent1_16_5());
        LIVING_DEATH.setConnector(new LivingDeathEvent1_16_5());
        LIVING_FALL.setConnector(new LivingFallEvent1_16_5());
        LIVING_HEAL.setConnector(new LivingHealEvent1_16_5());
        LIVING_HURT.setConnector(new LivingHurtEvent1_16_5());
        LIVING_JUMP.setConnector(new LivingJumpEvent1_16_5());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEvent1_16_5());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEvent1_16_5());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEvent1_16_5());
        LIVING_UPDATE.setConnector(new LivingUpdateEvent1_16_5());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEvent1_16_5());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEvent1_16_5());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEvent1_16_5());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEvent1_16_5());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEvent1_16_5());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEvent1_16_5());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEvent1_16_5());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEvent1_16_5());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEvent1_16_5());
        PLAYER_CLONE.setConnector(new PlayerCloneEvent1_16_5());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEvent1_16_5());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEvent1_16_5());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEvent1_16_5());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEvent1_16_5());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEvent1_16_5());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEvent1_16_5());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEvent1_16_5());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEvent1_16_5());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEvent1_16_5());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEvent1_16_5());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEvent1_16_5());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEvent1_16_5());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEvent1_16_5());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEvent1_16_5());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEvent1_16_5());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEvent1_16_5());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEvent1_16_5());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEvent1_16_5());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEvent1_16_5());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEvent1_16_5());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEvent1_16_5());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEvent1_16_5());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEvent1_16_5());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEvent1_16_5());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEvent1_16_5());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEvent1_16_5());
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEvent1_16_5()); //TODO Deferred registers?
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEvent1_16_5());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEvent1_16_5());
        REGISTER_ITEMS.setConnector(new RegisterItemsEvent1_16_5());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEvent1_16_5());
        TICK_PLAYER.setConnector(new PlayerTickEvent1_16_5());
        TICK_WORLD.setConnector(new WorldTickEvent1_16_5());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEvent1_16_5());
        WORLD_LOAD.setConnector(new WorldLoadEvent1_16_5());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEvent1_16_5());
        WORLD_SAVE.setConnector(new WorldSaveEvent1_16_5());
        WORLD_UNLOAD.setConnector(new WorldUnloadEvent1_16_5());
        this.defined = true;
    }

    @Override
    public boolean isDefined() {
        return this.defined;
    }

    @Override
    public void postCustomTick(CustomTick ticker) {
        MinecraftForge.EVENT_BUS.post(new CustomTick1_16_5(ticker));
    }

    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        MinecraftForge.EVENT_BUS.addListener(event -> wrapper.getType().invoke(event));
    }
}