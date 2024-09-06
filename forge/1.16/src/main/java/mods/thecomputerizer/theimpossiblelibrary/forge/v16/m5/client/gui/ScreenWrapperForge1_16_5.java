package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyStateCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.SharedConstants;

import javax.annotation.Nonnull;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class ScreenWrapperForge1_16_5 extends Screen implements Wrapped<ScreenAPI> {
    
    private final ScreenAPI wrapped;
    private boolean isOpen;

    public ScreenWrapperForge1_16_5(ScreenAPI wrapped) {
        super(wrapped.getTitle().getAsComponent());
        this.wrapped = wrapped;
    }
    
    @Override public boolean charTyped(char c, int mods) {
        if(SharedConstants.isAllowedChatCharacter(c) && this.wrapped.onCharTyped(c)) return true;
        return super.charTyped(c,mods);
    }
    
    private KeyStateCache getKeyState() {
        return new KeyStateCache(hasAltDown(),hasControlDown(),hasShiftDown());
    }
    
    @Override public ScreenAPI getWrapped() {
        return this.wrapped;
    }
    
    @Override public void init() {
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenOpened();
        this.isOpen = true;
    }
    
    private boolean isActivelyTicking() {
        return Objects.nonNull(this.wrapped) && this.wrapped.isActivelyTicking();
    }
    
    @Override public boolean isPauseScreen() {
        return Objects.isNull(this.wrapped) || this.wrapped.shouldPauseGame();
    }
    
    @Override public boolean keyPressed(int keyCode, int scanCode, int mod) {
        if(Objects.nonNull(this.wrapped)) {
            if(keyCode==GLFW_KEY_ESCAPE) {
                if(this.wrapped.onCloseRequested(true)) onClose();
                return true;
            }
            if(keyCode==GLFW_KEY_BACKSPACE && this.wrapped.onBackspace()) return true;
            if(Screen.isSelectAll(keyCode) && this.wrapped.onSelectAll()) return true;
            if(Screen.isCopy(keyCode)) {
                String copied = this.wrapped.onCopy();
                if(Objects.nonNull(copied)) {
                    TextInputUtil.setClipboardContents(getMinecraft(),copied);
                    return true;
                }
            }
            if(Screen.isPaste(keyCode)) {
                String pasted = TextInputUtil.getClipboardContents(getMinecraft());
                if(this.wrapped.onPaste(pasted)) return true;
            }
            if(Screen.isCut(keyCode)) {
                String copied = this.wrapped.onCut();
                if(Objects.nonNull(copied)) {
                    TextInputUtil.setClipboardContents(getMinecraft(),copied);
                    return true;
                }
            }
            if(this.wrapped.onKeyPressed(getKeyState(),keyCode)) return true;
        }
        return super.keyPressed(keyCode,scanCode,mod);
    }
    
    @Override public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if(Objects.nonNull(this.wrapped)) {
            RenderContext ctx = RenderContext.get(ClientHelper.getMinecraft());
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
        if(Objects.nonNull(this.wrapped) && delta!=0d) {
            if(delta>=1d) {
                if(this.wrapped.scrollUp(MathHelper.clamp(delta,-1d,1d))) return true;
            } else if(this.wrapped.scrollDown(MathHelper.clamp(delta,-1d,1d))) return true;
        }
        return super.mouseScrolled(mouseX,mouseY,delta);
    }
    
    @Override public void onClose() {
        this.isOpen = false;
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenClosed();
        super.onClose();
    }
    
    @Override public void removed() {
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }
    
    @Override public void render(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        if(Objects.nonNull(this.wrapped)) {
            RenderContext ctx = RenderContext.get(ClientHelper.getMinecraft());
            ctx.setPartialTicks(partialTicks);
            ctx.getRenderer().setMatrix(matrix);
            double x = -1d+((double)mouseX)*ctx.getScale().getScreenScaleX();
            double y = 1d-((double)mouseY)*ctx.getScale().getScreenScaleY();
            this.wrapped.draw(ctx, VectorHelper.zero3D(), x, y);
        }
    }
    
    @Override public void resize(@Nonnull Minecraft mc, int width, int height) {
        super.resize(mc,width,height);
        MinecraftAPI minecraft = ClientHelper.getMinecraft();
        if(Objects.nonNull(this.wrapped) && Objects.nonNull(minecraft))
            this.wrapped.onResolutionUpdated(minecraft.getWindow());
    }
    
    @Override public void tick() {
        if(isActivelyTicking() && this.isOpen) this.wrapped.onTick();
    }
}