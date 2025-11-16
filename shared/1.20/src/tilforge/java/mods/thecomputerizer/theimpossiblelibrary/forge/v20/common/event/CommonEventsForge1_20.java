package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.event.CommonEvents1_20;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import org.jetbrains.annotations.Nullable;


import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.*;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public abstract class CommonEventsForge1_20 extends CommonEvents1_20 implements CommonForgeEventHelper {

    @Override public void defineEvents() {
        defaultEventDefinitions();
        BLOCK_BREAK.setConnector(new BlockBreakEventForge1_20());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEventForge1_20());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEventForge());
        BLOCK_INTERACT.setConnector(new BlockInteractEventForge());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEventForge1_20());
        BLOCK_PLACE.setConnector(new BlockPlaceEventForge1_20());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEventForge1_20());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEventForge1_20());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEventForge1_20());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEventForge1_20());
        CROP_GROW_POST.setConnector(new CropGrowPostEventForge1_20());
        CROP_GROW_PRE.setConnector(new CropGrowPreEventForge1_20());
        CUSTOM_TICK.setConnector(new CustomTickEventForge());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventForge1_20());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEventForge1_20());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEventForge());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEventForge1_20());
        EXPLOSION_START.setConnector(new ExplosionStartEventForge1_20());
        LIVING_ATTACKED.setConnector(new LivingAttackedEventForge1_20());
        LIVING_DAMAGE.setConnector(new LivingDamageEventForge1_20());
        LIVING_DEATH.setConnector(new LivingDeathEventForge1_20());
        LIVING_FALL.setConnector(new LivingFallEventForge());
        LIVING_HEAL.setConnector(new LivingHealEventForge());
        LIVING_HURT.setConnector(new LivingHurtEventForge1_20());
        LIVING_JUMP.setConnector(new LivingJumpEventForge());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventForge());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventForge1_20());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEventForge1_20());
        LIVING_UPDATE.setConnector(new LivingUpdateEventForge1_20());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEventForge());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEventForge());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEventForge());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEventForge());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEventForge1_20());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEventForge());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEventForge());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEventForge());
        PLAYER_CLONE.setConnector(new PlayerCloneEventForge());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEventForge());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEventForge());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEventForge());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEventForge());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEventForge());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEventForge());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEventForge());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEventForge());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEventForge1_20());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEventForge());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEventForge());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEventForge());
        TICK_PLAYER.setConnector(new PlayerTickEventForge());
        TICK_WORLD.setConnector(new WorldTickEventForge1_20());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventForge1_20());
        WORLD_LOAD.setConnector(new WorldLoadEventForge1_20());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventForge1_20());
        WORLD_SAVE.setConnector(new WorldSaveEventForge1_20());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventForge1_20());
        defineVersionedEvents();
        super.defineEvents();
    }
    
    protected void defineVersionedEvents() {
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventForge1_20());
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventForge1_20());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventForge1_20());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventForge1_20());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventForge1_20());
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override @Nullable public IEventBus getModBus(ModContainer container) {
        return container instanceof FMLModContainer ? ((FMLModContainer)container).getEventBus() : null;
    }

    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickForge(ticker));
    }

    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        registerForgeOrModBus(wrapper);
    }
    
    @Override public <E> E setEventResult(Result result) {
        return GenericUtils.cast(result==DEFAULT ?
                        Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW));
    }
}