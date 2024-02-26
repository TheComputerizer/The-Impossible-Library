package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEventsHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.CommonEventsForge;
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
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Collection;

@EventBusSubscriber(modid = TILRef.MODID, value = Dist.CLIENT)
public class ClientEventsForge extends CommonEventsForge implements ClientEventsAPI {

    @SubscribeEvent
    public static void onClientConnected(LoggedInEvent event) {
        ClientEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onClientDisconnected(LoggedOutEvent event) {
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

    /*@SubscribeEvent
    public static void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event) {
        ClientEventsHelper.invoke(event);
    }*/

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

    public ClientEventsForge() {

    }

    @Override
    public void defineEventClasses(Collection<Class<?>> classes) {
        super.defineEventClasses(classes);
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
    }

    @Override
    public void initDefaultListeners() {
        super.initDefaultListeners();
        ClientEventsHelper.addListener(ClientTickEvent.class,event -> {
            if(event.phase==Phase.END) RenderHelper.tickRenderables();
        });
        ClientEventsHelper.addListener(Post.class,event -> {
            if(event.getType()==ElementType.ALL)
                RenderHelper.renderAllBackgroundStuff(MinecraftForgeTIL.getInstance().getRenderer().init(event.getMatrixStack()));
        });
    }
}