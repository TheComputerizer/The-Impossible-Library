package mods.thecomputerizer.theimpossiblelibrary.client.render;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Constants.MODID, value = Side.CLIENT)
public class Renderer {

    private static final ArrayList<Renderable> renderables = new ArrayList<>();

    public static PNG initializePng(ResourceLocation location, Map<String, Object> parameters) {
        try {
            return new PNG(location, parameters);
        } catch (IOException ex) {
            LogUtil.logInternal(Level.ERROR,"Failed to initialize png at resource location {}",location,ex);
        }
        return null;
    }

    public static void addRenderable(Renderable renderable) {
        renderables.add(renderable);
        renderable.initializeTimers();
    }

    public static void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
    }

    @SubscribeEvent
    public static void tickRenderables(TickEvent.ClientTickEvent ev) {
        if(ev.phase == TickEvent.Phase.END)
            renderables.removeIf(renderable -> !renderable.tick());
    }

    @SubscribeEvent
    public static void renderAllBackgroundStuff(RenderGameOverlayEvent.Post e) {
        if(e.getType()==RenderGameOverlayEvent.ElementType.ALL)
            for(Renderable type : renderables)
                type.render(e.getResolution());
    }
}
