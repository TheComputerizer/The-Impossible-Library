package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper.EventEntry;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.EventsForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedOutEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Set;

@EventBusSubscriber(modid = TILRef.MODID, value = Dist.CLIENT)
public class ClientEventsForge extends EventsForge implements ClientEventsAPI {

    @SubscribeEvent
    public static void onClientConnected(LoggedInEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onClientDisconnected(LoggedOutEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onFogColors(FogColors event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onFogDensity(FogDensity event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        ClientEventHelper.invoke(event);
    }

    /*@SubscribeEvent
    public static void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event) {
        ClientEventsHelper.invoke(event);
    }*/

    @SubscribeEvent
    public static void onRenderBossOverlay(BossInfo event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderChatOverlay(Chat event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderGameOverlayPost(Post event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderGameOverlayPre(Pre event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderTextOverlay(Text event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderTick(RenderTickEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        ClientEventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        ClientEventHelper.invoke(event);
    }

    public ClientEventsForge() {

    }

    @Override
    public void defineEventClasses(Set<EventEntry<?,?,?>> entries) {
        super.defineEventClasses(entries);
        /*
        classes.add(LoggedInEvent.class);
        classes.add(LoggedOutEvent.class);
        classes.add(ClientTickEvent.class);
        classes.add(FogColors.class);
        classes.add(FogDensity.class);
        classes.add(FOVUpdateEvent.class);
        classes.add(ItemTooltipEvent.class);
        classes.add(KeyInputEvent.class);
        classes.add(ModelRegistryEvent.class);
        //classes.add(PlayerSPPushOutOfBlocksEvent.class);
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
        MinecraftForge.EVENT_BUS.addListener();
        ClientEventHelper.addListener(ClientTickEvent.class, event -> {
            if(event.phase==Phase.END) RenderHelper.tickRenderables();
        });
        ClientEventHelper.addListener(Post.class, event -> {
            if(event.getType()==ElementType.ALL)
                RenderHelper.renderAllBackgroundStuff(MinecraftForgeTIL.getInstance().getRenderer().init(event.getMatrixStack()));
        });
    }
}