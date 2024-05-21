package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class RenderHelper {

    private static final List<Renderable> RENDERABLES = Collections.synchronizedList(new ArrayList<>());
    private static Random RANDOM;

    public static void addRenderable(Renderable renderable) {
        synchronized(RENDERABLES) {
            RENDERABLES.add(renderable);
            renderable.initializeTimers();
        }
    }
    
    public static RenderContext getContext() {
        MinecraftAPI mc = TILRef.getClientSubAPI(ClientAPI::getMinecraft);
        return Objects.nonNull(mc) ? new RenderContext(mc) : null;
    }
    
    public static double getCurrentHeightRatio() {
        RenderContext ctx = RenderHelper.getContext();
        return Objects.nonNull(ctx) ? ctx.getHeightRatio() : 1d;
    }

    public static @Nullable RenderAPI getRenderer() {
        MinecraftAPI mc = TILRef.getClientSubAPI(ClientAPI::getMinecraft);
        return Objects.nonNull(mc) ? mc.getRenderer() : null;
    }

    public static RenderablePNG initPNG(ResourceLocationAPI<?> source, Map<String,Object> parameters) {
        try {
            boolean isAnimated = parameters.containsKey("animated") && Boolean.parseBoolean(parameters.get("animated").toString());
            return isAnimated ? new RenderableAnimated(source,parameters) : new RenderablePNG(source,parameters);
        } catch(IOException ex) {
            TILRef.logError("Failed to initialize png at resource location {}",source,ex);
        }
        return null;
    }

    public static void removeRenderable(Renderable renderable) {
        synchronized(RENDERABLES) {
            RENDERABLES.remove(renderable);
        }
    }


    public static void renderAllBackgroundStuff(RenderContext ctx) {
        synchronized(RENDERABLES) {
            for(Renderable type : RENDERABLES) type.render(ctx);
        }
    }

    public static void tickRenderables() {
        synchronized(RENDERABLES) {
            RENDERABLES.removeIf(renderable -> !renderable.tick());
        }
    }
}
