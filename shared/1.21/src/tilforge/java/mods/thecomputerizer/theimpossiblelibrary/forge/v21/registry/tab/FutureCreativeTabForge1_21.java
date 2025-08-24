package mods.thecomputerizer.theimpossiblelibrary.forge.v21.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;
import static net.minecraft.world.item.CreativeModeTab.Row.TOP;

public class FutureCreativeTabForge1_21 extends FutureCreativeTab<CreativeModeTab> {
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    
    public FutureCreativeTabForge1_21(ResourceLocationAPI<?> registryName) {
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
        event.register(CREATIVE_MODE_TAB,name,() -> {
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
    @Override public void supply(@Nullable Object arg, List<Supplier<ItemStackAPI<?>>> stackSuppliers) {
        if(Objects.isNull(arg)) {
            TILRef.logError("Cannot supply future creative tab with null arg!");
            return;
        }
        BuildCreativeModeTabContentsEvent event = (BuildCreativeModeTabContentsEvent)arg;
        if(event.getTab()!=this.wrapped) return;
        for(Supplier<ItemStackAPI<?>> supplier : stackSuppliers) {
            ItemStack stack = supplier.get().unwrap();
            if(!event.getEntries().contains(stack)) event.accept(stack);
            if(!this.suppliedItems.contains(stack)) this.suppliedItems.add(stack);
        }
        stackSuppliers.clear();
    }
}