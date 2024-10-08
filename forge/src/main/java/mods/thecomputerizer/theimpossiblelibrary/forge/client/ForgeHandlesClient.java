package mods.thecomputerizer.theimpossiblelibrary.forge.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.ClientModLoader;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ForgeHandlesClient implements SharedHandlesClient {
    
    @Override public boolean isLoading(@Nullable Object minecraft) {
        return Objects.isNull(minecraft) || ClientModLoader.isLoading();
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        ClientRegistry.registerKeyBinding(key.unwrap());
    }
    
    @SuppressWarnings("unchecked")
    @Override public void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height,
            int maxWidth) {
        GuiUtils.drawHoveringText(renderer.unwrapMatrix(),(List<ITextComponent>)lines,x,y,width,height,maxWidth,
                                  renderer.unwrapFont());
    }
}