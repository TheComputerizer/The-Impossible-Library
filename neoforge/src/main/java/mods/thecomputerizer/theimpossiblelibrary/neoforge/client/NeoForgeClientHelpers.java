package mods.thecomputerizer.theimpossiblelibrary.neoforge.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.DebugText;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Post;

import java.util.ArrayList;
import java.util.List;

import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class NeoForgeClientHelpers {
    
    /**
     * I could have also used ASM like I did for fabric but why bother
     */
    @SuppressWarnings("UnstableApiUsage")
    public static void emulateForgeDebugTextEvent(RenderOverlayPostEventWrapper<?> wrapper) {
        if(Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen()) return;
        Post event = (Post)wrapper.getEvent();
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();
        GuiGraphics graphics = event.getGuiGraphics();
        //Not cancellable & does not have a result
        EVENT_BUS.post(new DebugText(event.getWindow(),graphics,event.getPartialTick(),left,right));
        TILRef.getClientHandles().renderDebugText(graphics,left,right);
    }
}