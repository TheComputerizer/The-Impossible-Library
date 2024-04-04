package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.AttachCapabilitiesEventWrapper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ATTACH_CAPABILITIES;

@Setter
public class AttachCapabilitiesEventLegacy extends AttachCapabilitiesEventWrapper<AttachCapabilitiesEvent<?>> {

    @SubscribeEvent
    public static void onEvent(AttachCapabilitiesEvent<?> event) {
        ATTACH_CAPABILITIES.invoke(event);
    }

    public AttachCapabilitiesEventLegacy() {}

    @Override
    public void setEvent(AttachCapabilitiesEvent<?> event) {

    }
}
