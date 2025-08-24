package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.event.CommonEvents1_16_5;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import org.jetbrains.annotations.Nullable;


import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.*;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class CommonEventsForge1_16_5 extends CommonEvents1_16_5 implements CommonForgeEventHelper {

    @Override public void defineEvents() {
        defaultEventDefinitions();
        BLOCK_BREAK.setConnector(new BlockBreakEventForge1_16_5());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEventForge1_16_5());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEventForge());
        BLOCK_INTERACT.setConnector(new BlockInteractEventForge());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEventForge1_16_5());
        BLOCK_PLACE.setConnector(new BlockPlaceEventForge1_16_5());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEventForge1_16_5());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEventForge1_16_5());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEventForge1_16_5());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEventForge1_16_5());
        CROP_GROW_POST.setConnector(new CropGrowPostEventForge1_16_5());
        CROP_GROW_PRE.setConnector(new CropGrowPreEventForge1_16_5());
        CUSTOM_TICK.setConnector(new CustomTickEventForge());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventForge1_16_5());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEventForge1_16_5());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEventForge());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEventForge1_16_5());
        EXPLOSION_START.setConnector(new ExplosionStartEventForge1_16_5());
        LIVING_ATTACKED.setConnector(new LivingAttackedEventForge1_16_5());
        LIVING_DAMAGE.setConnector(new LivingDamageEventForge1_16_5());
        LIVING_DEATH.setConnector(new LivingDeathEventForge1_16_5());
        LIVING_FALL.setConnector(new LivingFallEventForge());
        LIVING_HEAL.setConnector(new LivingHealEventForge());
        LIVING_HURT.setConnector(new LivingHurtEventForge1_16_5());
        LIVING_JUMP.setConnector(new LivingJumpEventForge());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventForge());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventForge1_16_5());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEventForge1_16_5());
        LIVING_UPDATE.setConnector(new LivingUpdateEventForge1_16_5());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEventForge());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEventForge());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEventForge());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEventForge());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEventForge1_16_5());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEventForge1_16_5());
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
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEventForge1_16_5());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEventForge());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEventForge());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEventForge());
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventForge1_16_5()); //TODO Deferred registers?
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventForge1_16_5());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventForge1_16_5());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventForge1_16_5());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventForge1_16_5());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventForge1_16_5());
        TICK_PLAYER.setConnector(new PlayerTickEventForge());
        TICK_WORLD.setConnector(new WorldTickEventForge1_16_5());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventForge1_16_5());
        WORLD_LOAD.setConnector(new WorldLoadEventForge1_16_5());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventForge1_16_5());
        WORLD_SAVE.setConnector(new WorldSaveEventForge1_16_5());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventForge1_16_5());
        super.defineEvents();
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
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}