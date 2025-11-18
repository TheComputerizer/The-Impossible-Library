package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client;

import com.google.common.base.Strings;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.ForgeHandlesClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent.DebugText;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static net.minecraftforge.client.event.CustomizeGuiOverlayEvent.DebugText.Side.Left;
import static net.minecraftforge.client.event.CustomizeGuiOverlayEvent.DebugText.Side.Right;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ForgeHandlesClient1_20_6 extends ForgeHandlesClient {
    
    private final Set<KeyMapping> keys = new HashSet<>();
    private boolean registeredKeys;
    
    @Override public void endRenderTypeBatch(Object source, @Nullable Object type) {
        if(Objects.nonNull(type)) ((BufferSource)source).endBatch((RenderType)type);
        else ((BufferSource)source).endBatch();
    }
    
    @SuppressWarnings("UnstableApiUsage")
    protected Event getDebugTextEvent(Minecraft mc, GuiGraphics graphics, List<String> lines, boolean left) {
        return new DebugText(mc.getWindow(),graphics,mc.getFrameTime(),lines,left ? Left : Right);
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {
        if(this.registeredKeys) ArrayHelper.append(Minecraft.getInstance().options.keyMappings,key.unwrap(),false);
        else this.keys.add(key.unwrap());
    }
    
    @Override public void registerKeyBindingsEvent(Object eventObj) {
        RegisterKeyMappingsEvent event = (RegisterKeyMappingsEvent)eventObj;
        TILRef.logInfo("Registering {} keybinds for 1.20",this.keys.size());
        for(KeyMapping key : this.keys) event.register(key);
        this.keys.clear();
        this.registeredKeys = true;
    }
    
    @Override public void renderDebugText(Object graphicsObj, List<String> left, List<String> right) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics graphics = (GuiGraphics)graphicsObj;
        EVENT_BUS.post(getDebugTextEvent(mc,graphics,left,true));
        EVENT_BUS.post(getDebugTextEvent(mc,graphics,right,false));
        renderDebugText(graphicsObj,left,true);
        renderDebugText(graphicsObj,right,false);
    }
    
    @Override public void renderDebugText(Object graphicsObj, List<String> text, boolean left) {
        if(text.isEmpty()) return;
        GuiGraphics graphics = (GuiGraphics)graphicsObj;
        Minecraft mc = Minecraft.getInstance();
        for(int i=0;i<text.size();i++) {
            String string = text.get(i);
            if(!Strings.isNullOrEmpty(string)) {
                int lineSpacing = 9;
                int textWidth = mc.font.width(string);
                int x = left ? 2 : mc.getWindow().getGuiScaledWidth()-textWidth-2;
                int y = 2+lineSpacing*i;
                graphics.fill(x-1,y-1,x+textWidth+1,y+lineSpacing-1,-1873784752);
                graphics.drawString(mc.font,string,x,y,14737632);
            }
        }
    }
}