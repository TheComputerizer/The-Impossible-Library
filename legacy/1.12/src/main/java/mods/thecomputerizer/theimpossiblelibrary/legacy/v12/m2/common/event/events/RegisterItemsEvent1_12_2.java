package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterItemsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ITEMS;

public class RegisterItemsEvent1_12_2 extends RegisterItemsEventWrapper<Register<Item>> {
    
    @SubscribeEvent
    public static void onEvent(Register<Item> event) {
        REGISTER_ITEMS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<Item> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override public void register(ItemAPI<?> entry) {
        Item item = entry.unwrap();
        this.event.getRegistry().register(item);
        if(CoreAPI.getInstance().getSide().isClient()) {
            ResourceLocation registryName = item.getRegistryName();
            if(Objects.nonNull(registryName)) {
                ModelResourceLocation model = new ModelResourceLocation(registryName,"inventory");
                ModelLoader.setCustomModelResourceLocation(item,0,model);
            }
        }
    }
}