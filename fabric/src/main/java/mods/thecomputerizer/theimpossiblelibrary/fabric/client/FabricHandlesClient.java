package mods.thecomputerizer.theimpossiblelibrary.fabric.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class FabricHandlesClient implements SharedHandlesClient {
    
    @Override public boolean isLoading(@Nullable Object minecraft) {
        Minecraft mc = (Minecraft)minecraft;
        return Objects.isNull(mc) || (Objects.isNull(mc.level) && Objects.isNull(mc.screen));
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        KeyBindingHelper.registerKeyBinding(key.unwrap());
    }
    
    @SuppressWarnings("unchecked")
    @Override public void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height,
            int maxWidth) {
        FabricHelper.renderTooltip(renderer.unwrapMatrix(),(List<MutableComponent>)lines,x,y,width,height,maxWidth,
                                   renderer.unwrapFont());
    }
}