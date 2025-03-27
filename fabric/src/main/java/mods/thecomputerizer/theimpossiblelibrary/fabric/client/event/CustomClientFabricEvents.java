package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.List;

public interface CustomClientFabricEvents {
    
    Event<KeyPressed> KEY_PRESSED = EventFactory.createArrayBacked(
            KeyPressed.class,listeners -> (key,scanCode,action,modifiers) -> {
                for(KeyPressed listener : listeners) listener.onKeyPressed(key,scanCode,action,modifiers);
            }
    );
    
    Event<RenderDebugInfo> RENDER_DEBUG_INFO = EventFactory.createArrayBacked(
            RenderDebugInfo.class,listeners -> (matrix,left,right) -> {
                for(RenderDebugInfo listener : listeners) listener.onRenderDebug(matrix,left,right);
            }
    );
    
    interface KeyPressed { void onKeyPressed(int key, int scanCode, int action, int modifiers); }
    
    interface RenderDebugInfo { void onRenderDebug(PoseStack matrix, List<String> left, List<String> right); }
}