package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;
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
        renderable.initializeTimers();
    }

    public static void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
    }

    public static void tickRenderables() {
        renderables.removeIf(renderable -> !renderable.tick());
    }


    public static void renderAllBackgroundStuff(PoseStack matrix, Window window) {
        for(Renderable type : renderables) type.render(matrix,window);
    }
}