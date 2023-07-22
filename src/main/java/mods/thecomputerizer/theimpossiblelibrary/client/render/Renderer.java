package mods.thecomputerizer.theimpossiblelibrary.client.render;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT)
public class Renderer {

    private static final ArrayList<Renderable> RENDERABLES = new ArrayList<>();

    public static PNG initializePng(ResourceLocation location, Map<String, Object> parameters) {
        try {
            boolean isAnimated = parameters.containsKey("animated") && Boolean.parseBoolean(parameters.get("animated").toString());
            return isAnimated ? new SpriteSheet(location, parameters) : new PNG(location, parameters);
        } catch (IOException ex) {
            LogUtil.logInternal(Level.ERROR,"Failed to initialize png at resource location {}",location,ex);
        }
        return null;
    }

    public static void addRenderable(Renderable renderable) {
        RENDERABLES.add(renderable);
        renderable.initializeTimers();
    }

    public static void removeRenderable(Renderable renderable) {
        RENDERABLES.remove(renderable);
    }

    @SubscribeEvent
    public static void tickRenderables(TickEvent.ClientTickEvent ev) {
        if(ev.phase == TickEvent.Phase.END)
            RENDERABLES.removeIf(renderable -> !renderable.tick());
    }

    @SubscribeEvent
    public static void renderAllBackgroundStuff(RenderGameOverlayEvent.Post e) {
        if(e.getType()==RenderGameOverlayEvent.ElementType.ALL)
            for(Renderable type : RENDERABLES)
                type.render(e.getMatrixStack(),e.getWindow(),e.getPartialTicks());
    }
}