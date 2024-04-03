package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsHelper.EventEntry;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.CommonEventsLegacy;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;
import java.util.Set;

@EventBusSubscriber(modid = TILRef.MODID, value = Side.CLIENT)
public class ClientEventsLegacy extends CommonEventsLegacy implements ClientEventsAPI {

    @SubscribeEvent
    public static void onClientConnected(ClientConnectedToServerEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onClientDisconnected(ClientDisconnectionFromServerEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onFogColors(FogColors event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onFogDensity(FogDensity event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderBossOverlay(BossInfo event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderChatOverlay(Chat event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderGameOverlayPost(Post event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderGameOverlayPre(Pre event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderTextOverlay(Text event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderTick(RenderTickEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        ClientEventsHelper.invoke(event);
    }

    public ClientEventsLegacy() {

    }

    @Override
    public void defineEventClasses(Set<EventEntry<?,?,?>> entries) {
        super.defineEventClasses(entries);
        /*
        classes.add(ClientConnectedToServerEvent.class);
        classes.add(ClientDisconnectionFromServerEvent.class);
        classes.add(ClientTickEvent.class);
        classes.add(FogColors.class);
        classes.add(FogDensity.class);
        classes.add(FOVUpdateEvent.class);
        classes.add(ItemTooltipEvent.class);
        classes.add(KeyInputEvent.class);
        classes.add(ModelRegistryEvent.class);
        classes.add(PlayerSPPushOutOfBlocksEvent.class);
        classes.add(BossInfo.class);
        classes.add(Chat.class);
        classes.add(Post.class);
        classes.add(Pre.class);
        classes.add(Text.class);
        classes.add(RenderTickEvent.class);
        classes.add(RenderWorldLastEvent.class);
        classes.add(PlaySoundEvent.class);
         */
    }

    @Override
    public void initDefaultListeners() {
        super.initDefaultListeners();
        ClientEventsHelper.addListener(ClientTickEvent.class,event -> {
            if(event.phase==Phase.END) RenderHelper.tickRenderables();
        });
        ClientEventsHelper.addListener(Post.class,event -> {
            if(event.getType()==ElementType.ALL)
                RenderHelper.renderAllBackgroundStuff(MinecraftLegacy.getInstance().getRenderer());
        });
    }
}