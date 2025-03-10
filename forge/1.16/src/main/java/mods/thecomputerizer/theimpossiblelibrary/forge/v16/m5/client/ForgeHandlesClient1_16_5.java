package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.ForgeHandlesClient;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ForgeHandlesClient1_16_5 extends ForgeHandlesClient {
    
    @Override public void endRenderTypeBatch(Object source, @Nullable Object type) {
        if(Objects.nonNull(type)) ((Impl)source).endBatch((RenderType)type);
        else ((Impl)source).endBatch();
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        ClientRegistry.registerKeyBinding(key.unwrap());
    }
    
    @SuppressWarnings("unchecked")
    @Override public void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height,
            int maxWidth) {
        MatrixStack stack = renderer.unwrapMatrix();
        List<ITextComponent> unwrappedLines = (List<ITextComponent>)lines;
        GuiUtils.drawHoveringText(stack,unwrappedLines,x,y,width,height,maxWidth, renderer.unwrapFont());
    }
}