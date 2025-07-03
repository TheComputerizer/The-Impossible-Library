package mods.thecomputerizer.theimpossiblelibrary.forge.client.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;

public interface ClientForgeEvent extends CommonForgeEvent {
    
    String MATRIX_GETTER = V18_OR_EARLIER ? "getMatrixStack" : "getPoseStack";
}