package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyStateCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Minecraft1_12_2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Mouse;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static org.lwjgl.input.Keyboard.KEY_BACK;
import static org.lwjgl.input.Keyboard.KEY_ESCAPE;

@ParametersAreNonnullByDefault
public class ScreenWrapper1_12_2 extends GuiScreen implements Wrapped<ScreenAPI> {
    
    private static final Set<ScreenWrapper1_12_2> TICKERS = new HashSet<>();
    private static boolean initializedTicker;
    
    private static void addTicker(ScreenWrapper1_12_2 wrapper) {
        if(Objects.nonNull(wrapper.wrapped)) {
            if(!initializedTicker) initializeTicker();
            TICKERS.add(wrapper);
        }
    }
    
    private static void initializeTicker() {
        EventHelper.addListener(TICK_CLIENT,wrapper -> {
            if(wrapper.isPhase(END))
                for(ScreenWrapper1_12_2 screen : TICKERS)
                    if(screen.isActivelyTicking() && screen.isOpen) screen.wrapped.onTick();
        });
        initializedTicker = true;
    }
    
    private static void removeTicker(ScreenWrapper1_12_2 wrapper) {
        TICKERS.remove(wrapper);
    }
    
    private final ScreenAPI wrapped;
    private boolean isOpen;
    
    public ScreenWrapper1_12_2(@Nullable ScreenAPI wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override public boolean doesGuiPauseGame() {
        return Objects.isNull(this.wrapped) || this.wrapped.shouldPauseGame();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(Objects.nonNull(this.wrapped)) {
            RenderContext ctx = RenderContext.get(Minecraft1_12_2.getInstance());
            ctx.setPartialTicks(partialTicks);
            double x = -1d+((double)mouseX)*ctx.getScale().getScreenScaleX();
            double y = 1d-((double)mouseY)*ctx.getScale().getScreenScaleY();
            this.wrapped.draw(ctx,VectorHelper.zero3D(),x,y);
        }
        updateScreen();
        initGui();
    }
    
    private KeyStateCache getKeyState() {
        return new KeyStateCache(isAltKeyDown(),isCtrlKeyDown(),isShiftKeyDown());
    }
    
    @Override public ScreenAPI getWrapped() {
        return this.wrapped;
    }
    
    @Override public void handleMouseInput() throws IOException {
        if(Objects.nonNull(this.wrapped)) {
            int scroll = Mouse.getEventDWheel();
            if(scroll>0 && this.wrapped.scrollUp(scroll)) return;
            if(scroll<0 && this.wrapped.scrollDown(scroll)) return;
        }
        super.handleMouseInput();
    }
    
    @Override public void initGui() {
        addTicker(this);
        this.isOpen = true;
    }
    
    private boolean isActivelyTicking() {
        return Objects.nonNull(this.wrapped) && this.wrapped.isActivelyTicking();
    }
    
    
    @Override protected void keyTyped(char c, int keyCode) throws IOException {
        if(Objects.nonNull(this.wrapped)) {
            if(keyCode==KEY_ESCAPE) {
                if(this.wrapped.onCloseRequested(true)) this.wrapped.close();
                return;
            }
            if(keyCode==KEY_BACK && this.wrapped.onBackspace()) return;
            if(GuiScreen.isKeyComboCtrlA(keyCode) && this.wrapped.onSelectAll()) return;
            if(GuiScreen.isKeyComboCtrlC(keyCode)) {
                String copied = this.wrapped.onCopy();
                if(Objects.nonNull(copied)) {
                    GuiScreen.setClipboardString(copied);
                    return;
                }
            }
            if(GuiScreen.isKeyComboCtrlV(keyCode) && this.wrapped.onPaste(GuiScreen.getClipboardString())) return;
            if(GuiScreen.isKeyComboCtrlX(keyCode)) {
                String copied = this.wrapped.onCut();
                if(Objects.nonNull(copied)) {
                    GuiScreen.setClipboardString(copied);
                    return;
                }
            }
            if(this.wrapped.onKeyPressed(getKeyState(),keyCode)) return;
            if(ChatAllowedCharacters.isAllowedCharacter(c) && this.wrapped.onCharTyped(c)) return;
        }
        super.keyTyped(c,keyCode);
    }
    
    @Override protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(Objects.nonNull(this.wrapped)) {
            RenderContext ctx = RenderContext.get(Minecraft1_12_2.getInstance());
            double x = -1d+((double)mouseX)*ctx.getScale().getScreenScaleX();
            double y = 1d-((double)mouseY)*ctx.getScale().getScreenScaleY();
            if(mouseButton==0) this.wrapped.onLeftClick(x,y);
            else if(mouseButton==1) this.wrapped.onRightClick(x,y);
        }
    }
    
    @Override public void onGuiClosed() {
        this.isOpen = false;
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenClosed();
        removeTicker(this);
    }
    
    @Override public void onResize(Minecraft mc, int w, int h) {
        super.onResize(mc,w,h);
        if(Objects.nonNull(this.wrapped)) this.wrapped.onResolutionUpdated(Minecraft1_12_2.getInstance().getWindow());
    }
}