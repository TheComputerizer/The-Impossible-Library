package mods.thecomputerizer.theimpossiblelibrary.client.render;

import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unused")
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
    }

    public static void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
    }

    @SubscribeEvent
    public static void renderAllBackgroundStuff(RenderGameOverlayEvent.Post e) {
        if(e.getType()==RenderGameOverlayEvent.ElementType.ALL) {
            Iterator<Renderable> renderableIterator = renderables.iterator();
            while (renderableIterator.hasNext()) {
                Renderable renderable = renderableIterator.next();
                renderable.render(e.getMatrixStack(),e.getWindow());
                if(!renderable.canRender())
                    renderableIterator.remove();
            }
        }
    }
}