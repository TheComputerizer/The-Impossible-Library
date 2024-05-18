package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVModifierEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_MODIFIER;

public class FOVModifierEvent1_12_2 extends FOVModifierEventWrapper<FOVModifier> {

    @SubscribeEvent
    public static void onEvent(FOVModifier event) {
        FOV_MODIFIER.invoke(event);
    }

    @Override
    protected EventFieldWrapper<FOVModifier,Float> wrapFOVField() {
        return wrapGenericBoth(FOVModifier::getFOV,FOVModifier::setFOV,0f);
    }

    @Override
    protected EventFieldWrapper<FOVModifier,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(FOVModifier::getEntity);
    }

    @Override
    protected EventFieldWrapper<FOVModifier,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FOVModifier::getState);
    }

    @Override
    protected RenderContext initRenderer(@Nonnull FOVModifier event) {
        return ClientEvents1_12_2.initRenderer(() -> (float)event.getRenderPartialTicks());
    }
}