package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.util.CustomTick1_12_2;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class CommonEvents1_12_2 implements CommonEventsAPI {

    private boolean defined;

    public CommonEvents1_12_2() {
        this.defined = false;
    }

    @Override public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEvent1_12_2());
        BLOCK_BREAK.setConnector(new BlockBreakEvent1_12_2());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEvent1_12_2());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEvent1_12_2());
        BLOCK_INTERACT.setConnector(new BlockInteractEvent1_12_2());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEvent1_12_2());
        BLOCK_PLACE.setConnector(new BlockPlaceEvent1_12_2());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEvent1_12_2());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEvent1_12_2());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEvent1_12_2());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEvent1_12_2());
        CROP_GROW_POST.setConnector(new CropGrowPostEvent1_12_2());
        CROP_GROW_PRE.setConnector(new CropGrowPreEvent1_12_2());
        CUSTOM_TICK.setConnector(new CustomTickEvent1_12_2());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEvent1_12_2());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEvent1_12_2());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEvent1_12_2());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEvent1_12_2());
        EXPLOSION_START.setConnector(new ExplosionStartEvent1_12_2());
        LIVING_ATTACKED.setConnector(new LivingAttackedEvent1_12_2());
        LIVING_DAMAGE.setConnector(new LivingDamageEvent1_12_2());
        LIVING_DEATH.setConnector(new LivingDeathEvent1_12_2());
        LIVING_FALL.setConnector(new LivingFallEvent1_12_2());
        LIVING_HEAL.setConnector(new LivingHealEvent1_12_2());
        LIVING_HURT.setConnector(new LivingHurtEvent1_12_2());
        LIVING_JUMP.setConnector(new LivingJumpEvent1_12_2());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEvent1_12_2());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEvent1_12_2());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEvent1_12_2());
        LIVING_UPDATE.setConnector(new LivingUpdateEvent1_12_2());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEvent1_12_2());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEvent1_12_2());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEvent1_12_2());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEvent1_12_2());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEvent1_12_2());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEvent1_12_2());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEvent1_12_2());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEvent1_12_2());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEvent1_12_2());
        PLAYER_CLONE.setConnector(new PlayerCloneEvent1_12_2());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEvent1_12_2());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEvent1_12_2());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEvent1_12_2());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEvent1_12_2());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEvent1_12_2());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEvent1_12_2());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEvent1_12_2());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEvent1_12_2());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEvent1_12_2());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEvent1_12_2());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEvent1_12_2());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEvent1_12_2());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEvent1_12_2());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEvent1_12_2());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEvent1_12_2());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEvent1_12_2());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEvent1_12_2());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEvent1_12_2());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEvent1_12_2());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEvent1_12_2());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEvent1_12_2());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEvent1_12_2());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEvent1_12_2());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEvent1_12_2());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEvent1_12_2());
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEvent1_12_2());
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEvent1_12_2());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEvent1_12_2());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEvent1_12_2());
        REGISTER_ITEMS.setConnector(new RegisterItemsEvent1_12_2());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEvent1_12_2());
        TICK_PLAYER.setConnector(new PlayerTickEvent1_12_2());
        TICK_WORLD.setConnector(new WorldTickEvent1_12_2());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEvent1_12_2());
        WORLD_LOAD.setConnector(new WorldLoadEvent1_12_2());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEvent1_12_2());
        WORLD_SAVE.setConnector(new WorldSaveEvent1_12_2());
        WORLD_UNLOAD.setConnector(new WorldUnloadEvent1_12_2());
        this.defined = true;
    }
    
    @Override public <A> Box getAABB(A aabb) {
        AxisAlignedBB box = (AxisAlignedBB)aabb;
        return ShapeHelper.box(box.minX,box.minY,box.minZ,box.maxX,box.maxY,box.maxZ);
    }
    
    @Override public <A> ActionResult getActionResult(A result) {
        return result==EnumActionResult.PASS ? PASS : (result==EnumActionResult.FAIL ? FAIL : SUCCESS);
    }
    
    @Override public <E> Result getEventResult(E result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override public <D> Facing getFacing(D direction) {
        if(Objects.isNull(direction)) return null;
        switch((EnumFacing)direction) {
            case DOWN: return Facing.DOWN;
            case EAST: return Facing.EAST;
            case NORTH: return Facing.NORTH;
            case SOUTH: return Facing.SOUTH;
            case UP: return Facing.UP;
            default: return Facing.WEST;
        }
    }
    
    @Override public <H> Hand getHand(H hand) {
        return hand==EnumHand.MAIN_HAND ? MAINHAND : OFFHAND;
    }
    
    @Override public <V> Vector3d getVec3d(V vector) {
        Vec3d vec = (Vec3d)vector;
        return new Vector3d(vec.x,vec.y,vec.z);
    }
    
    @Override public boolean isDefined() {
        return this.defined;
    }

    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTick1_12_2(ticker));
    }

    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public AxisAlignedBB setAABB(Box box) {
        return new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z);
    }
    
    @SuppressWarnings("unchecked")
    @Override public EnumActionResult setActionResult(ActionResult result) {
        return result==PASS ? EnumActionResult.PASS : (result==FAIL ? EnumActionResult.FAIL : EnumActionResult.SUCCESS);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
    
    @SuppressWarnings("unchecked")
    @Override public @Nullable EnumFacing setFacing(@Nullable Facing facing) {
        if(Objects.isNull(facing)) return null;
        switch(facing) {
            case DOWN: return EnumFacing.DOWN;
            case EAST: return EnumFacing.EAST;
            case NORTH: return EnumFacing.NORTH;
            case SOUTH: return EnumFacing.SOUTH;
            case UP: return EnumFacing.UP;
            default: return EnumFacing.WEST;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override public EnumHand setHand(Hand hand) {
        return hand==MAINHAND ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
    }
    
    @SuppressWarnings("unchecked")
    @Override public Vec3d setVec3d(Vector3d vector) {
        return new Vec3d(vector.x,vector.y,vector.z);
    }
}