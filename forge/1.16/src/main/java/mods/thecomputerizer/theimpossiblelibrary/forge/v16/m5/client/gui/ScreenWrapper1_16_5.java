package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Minecraft1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;

public class ScreenWrapper1_16_5 extends Screen implements Wrapped<ScreenAPI> {
    
    private static final Set<ScreenWrapper1_16_5> TICKERS = new HashSet<>();
    private static boolean initializedTicker;
    
    private static void addTicker(ScreenWrapper1_16_5 wrapper) {
        if(Objects.nonNull(wrapper.wrapped)) {
            if(!initializedTicker) initializeTicker();
            TICKERS.add(wrapper);
        }
    }
    
    private static void initializeTicker() {
        EventHelper.addListener(TICK_CLIENT, wrapper -> {
            if(wrapper.isPhase(END))
                for(ScreenWrapper1_16_5 screen : TICKERS)
                    if(screen.isActivelyTicking() && screen.isOpen) screen.wrapped.onTick();
        });
        initializedTicker = true;
    }
    
    private static void removeTicker(ScreenWrapper1_16_5 wrapper) {
        TICKERS.remove(wrapper);
    }
    
    private final ScreenAPI wrapped;
    private boolean isOpen;

    public ScreenWrapper1_16_5(ScreenAPI wrapped) {
        super(((Text1_16_5)wrapped.getTitle()).getComponent());
        this.wrapped = wrapped;
    }
    
    @Override public ScreenAPI getWrapped() {
        return this.wrapped;
    }
    
    @Override public void init() {
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenOpened();
        addTicker(this);
        this.isOpen = true;
    }
    
    private boolean isActivelyTicking() {
        return Objects.nonNull(this.wrapped) && this.wrapped.isActivelyTicking();
    }
    
    @Override public boolean isPauseScreen() {
        return Objects.isNull(this.wrapped) || this.wrapped.shouldPauseGame();
    }
    
    @Override public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if(Objects.nonNull(this.wrapped)) {
            RenderContext ctx = RenderContext.get(Minecraft1_16_5.getInstance());
            double x = -1d+mouseX*ctx.getScale().getScreenScaleX();
            double y = 1d-mouseY*ctx.getScale().getScreenScaleY();
            if(mouseButton==0) {
                if(this.wrapped.onLeftClick(x,y)) return true;
            } else if(mouseButton==1) {
                if(this.wrapped.onRightClick(x,y)) return true;
            }
        }
        return super.mouseClicked(mouseX,mouseY,mouseButton);
    }
    
    @Override public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if(Objects.nonNull(this.wrapped)) {
            if(delta>0d && this.wrapped.scrollUp(delta)) return true;
            if(delta<0d && this.wrapped.scrollDown(delta)) return true;
        }
        return super.mouseScrolled(mouseX,mouseY,delta);
    }
    
    @Override public void onClose() {
        this.isOpen = false;
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenClosed();
        super.onClose();
    }
    
    @Override public void render(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        if(Objects.nonNull(this.wrapped)) {
            RenderContext ctx = RenderContext.get(Minecraft1_16_5.getInstance());
            ctx.setPartialTicks(partialTicks);
            ctx.getRenderer().setMatrix(matrix);
            double x = -1d+((double)mouseX)*ctx.getScale().getScreenScaleX();
            double y = 1d-((double)mouseY)*ctx.getScale().getScreenScaleY();
            this.wrapped.draw(ctx, VectorHelper.zero3D(), x, y);
        }
    }
    
    @Override public void resize(@Nonnull Minecraft mc, int width, int height) {
        super.resize(mc,width,height);
        if(Objects.nonNull(this.wrapped)) this.wrapped.onResolutionUpdated(Minecraft1_16_5.getInstance().getWindow());
    }
    
    @Override public void tick() {
        if(isActivelyTicking() && this.isOpen) this.wrapped.onTick();
    }
}