package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.minecraft.world.item.CreativeModeTab.Row.TOP;

public class FutureCreativeTabNeoForge1_20 extends FutureCreativeTab<CreativeModeTab> {
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    
    public FutureCreativeTabNeoForge1_20(ResourceLocationAPI<?> registryName) {
        super(registryName);
    }
    
    /**
     * arg = RegisterEvent
     */
    @Override public void register(@Nullable Object arg) {
        if(Objects.isNull(arg) || Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot register future creative tab with null arg or registryName!");
            return;
        }
        RegisterEvent event = (RegisterEvent)arg;
        ResourceLocation name = this.registryName.unwrap();
        event.register(Registries.CREATIVE_MODE_TAB, name, () -> {
            Builder builder = new Builder(TOP,0)
                    .title(TextHelper.getTranslated("itemGroup."+name.getPath()).getAsComponent());
            if(Objects.nonNull(this.iconSupplier)) builder.icon(() -> (ItemStack)this.iconSupplier.get());
            this.wrapped = builder.displayItems((args,output) -> {
                for(ItemStack stack : this.suppliedItems) output.accept(stack);
            }).build();
            this.registered = true;
            return this.wrapped;
        });
    }
    
    /**
     * arg = BuildCreativeModeTabContentsEvent
     */
    @Override public void supply(@Nullable Object arg, List<ItemStackAPI<?>> stacks) {
        if(Objects.isNull(arg)) {
            TILRef.logError("Cannot supply future creative tab with null arg!");
            return;
        }
        BuildCreativeModeTabContentsEvent event = (BuildCreativeModeTabContentsEvent)arg;
        this.suppliedItems.clear();
        for(ItemStackAPI<?> api : stacks) {
            ItemStack stack = api.unwrap();
            event.accept(stack);
            this.suppliedItems.add(stack);
        }
    }
}