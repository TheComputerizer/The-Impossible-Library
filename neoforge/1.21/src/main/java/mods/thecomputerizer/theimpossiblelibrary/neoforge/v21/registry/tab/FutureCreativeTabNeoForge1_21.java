package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static net.minecraft.world.item.CreativeModeTab.Row.TOP;
import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_TAB_ONLY;
import static net.minecraft.world.item.CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY;

public class FutureCreativeTabNeoForge1_21 extends FutureCreativeTab<CreativeModeTab> {
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    
    public FutureCreativeTabNeoForge1_21(ResourceLocationAPI<?> registryName) {
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
    @Override public void supply(@Nullable Object arg, List<Supplier<ItemStackAPI<?>>> stackSuppliers) {
        if(Objects.isNull(arg)) {
            TILRef.logError("Cannot supply future creative tab with null arg!");
            return;
        }
        BuildCreativeModeTabContentsEvent event = (BuildCreativeModeTabContentsEvent)arg;
        this.suppliedItems.clear();
        for(Supplier<ItemStackAPI<?>> supplier : stackSuppliers) {
            ItemStack stack = supplier.get().unwrap();
            TabVisibility visibility = visibility(event.getParentEntries(),event.getSearchEntries(),stack);
            if(Objects.nonNull(visibility)) event.accept(stack,visibility);
            this.suppliedItems.add(stack);
        }
    }
    
    /**
     * Check if the stack exists before adding to the tab
     */
    private @Nullable TabVisibility visibility(Set<ItemStack> parent, Set<ItemStack> search, ItemStack stack) {
        if(parent.contains(stack)) return search.contains(stack) ? SEARCH_TAB_ONLY : null;
        if(search.contains(stack)) return parent.contains(stack) ? PARENT_TAB_ONLY : null;
        return PARENT_AND_SEARCH_TABS;
    }
}