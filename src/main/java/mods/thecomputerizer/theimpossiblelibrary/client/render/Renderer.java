package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.platform.Window;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class Renderer {

    private static final List<Renderable> RENDERABLES = Collections.synchronizedList(new ArrayList<>());

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
        synchronized (RENDERABLES) {
            RENDERABLES.add(renderable);
            renderable.initializeTimers();
        }
    }

    public static void removeRenderable(Renderable renderable) {
        synchronized (RENDERABLES) {
            RENDERABLES.remove(renderable);
        }
    }

    public static void tickRenderables() {
        synchronized (RENDERABLES) {
            RENDERABLES.removeIf(renderable -> !renderable.tick());
        }
    }


    public static void renderAllBackgroundStuff(GuiGraphics graphics, Window window) {
        synchronized (RENDERABLES) {
            for(Renderable type : RENDERABLES)
                type.render(graphics,window);
        }
    }
}