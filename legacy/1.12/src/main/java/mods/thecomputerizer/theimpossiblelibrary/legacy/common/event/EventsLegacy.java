package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;

public class EventsLegacy implements EventsAPI {

    public static Box getBox(AxisAlignedBB aabb) {
        return new Box(aabb.minX,aabb.minY,aabb.minZ,aabb.maxX,aabb.maxY,aabb.maxZ);
    }

    private boolean defined;

    public EventsLegacy() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEventLegacy());
        BLOCK_BREAK.setConnector(new BlockBreakEventLegacy());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEventLegacy());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEventLegacy());
        BLOCK_INTERACT.setConnector(new BlockInteractEventLegacy());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEventLegacy());
        BLOCK_PLACE.setConnector(new BlockPlaceEventLegacy());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEventLegacy());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEventLegacy());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEventLegacy());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEventLegacy());
        CROP_GROW_POST.setConnector(new CropGrowPostEventLegacy());
        CROP_GROW_PRE.setConnector(new CropGrowPreEventLegacy());
        CUSTOM_TICK.setConnector(new CustomTickEventLegacy());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventLegacy());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEventLegacy());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEventLegacy());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEventLegacy());
        EXPLOSION_START.setConnector(new ExplosionStartEventLegacy());
        LIVING_ATTACKED.setConnector(new LivingAttackedEventLegacy());
        LIVING_DAMAGE.setConnector(new LivingDamageEventLegacy());
        LIVING_DEATH.setConnector(new LivingDeathEventLegacy());
        LIVING_FALL.setConnector(new LivingFallEventLegacy());
        LIVING_HEAL.setConnector(new LivingHealEventLegacy());
        LIVING_HURT.setConnector(new LivingHurtEventLegacy());
        LIVING_JUMP.setConnector(new LivingJumpEventLegacy());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventLegacy());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventLegacy());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEventLegacy());
        LIVING_UPDATE.setConnector(new LivingUpdateEventLegacy());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEventLegacy());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEventLegacy());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEventLegacy());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEventLegacy());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEventLegacy());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEventLegacy());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEventLegacy());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEventLegacy());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEventLegacy());
        PLAYER_CLONE.setConnector(new PlayerCloneEventLegacy());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEventLegacy());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEventLegacy());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEventLegacy());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEventLegacy());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEventLegacy());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEventLegacy());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEventLegacy());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEventLegacy());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEventLegacy());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEventLegacy());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEventLegacy());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEventLegacy());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEventLegacy());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEventLegacy());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEventLegacy());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEventLegacy());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEventLegacy());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEventLegacy());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEventLegacy());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEventLegacy());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEventLegacy());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEventLegacy());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEventLegacy());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEventLegacy());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelUpEventLegacy());
        REGISTER_GENERIC.setConnector(new RegistryEventLegacy());
        TICK_PLAYER.setConnector(new PlayerTickEventLegacy());
        TICK_WORLD.setConnector(new WorldTickEventLegacy());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventLegacy());
        WORLD_LOAD.setConnector(new WorldLoadEventLegacy());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventLegacy());
        WORLD_SAVE.setConnector(new WorldSaveEventLegacy());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventLegacy());
        this.defined = true;
    }

    @Override
    public boolean isDefined() {
        return this.defined;
    }

    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        MinecraftForge.EVENT_BUS.register(wrapper.getClass());
    }
}