package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab.CreativeTabBuilder1_19_4;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FutureCreativeTabFabric1_19_4 extends FutureCreativeTab<CreativeModeTab> {
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    
    public FutureCreativeTabFabric1_19_4(ResourceLocationAPI<?> registryName) {
        super(registryName);
    }
    
    /**
     * arg = null
     */
    @Override public void register(@Nullable Object arg) {
        if(Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot register future creative tab with null registryName!");
            return;
        }
        ResourceLocation name = this.registryName.unwrap();
        Builder builder = FabricItemGroup.builder(name)
                .title(TextHelper.getTranslated("itemGroup."+name.getPath()).getAsComponent());
        if(Objects.nonNull(this.iconSupplier)) builder.icon(() -> (ItemStack)this.iconSupplier.get());
        this.wrapped = builder.displayItems((args,output) -> {
            for(ItemStack stack : this.suppliedItems) output.accept(stack);
        }).build();
        ItemGroupEvents.modifyEntriesEvent(this.wrapped).register(CreativeTabBuilder1_19_4::onSupply);
        this.registered = true;
    }
    
    /**
     * arg = FabricItemGroupEntries
     */
    @SuppressWarnings("UnstableApiUsage")
    @Override public void supply(@Nullable Object arg, List<ItemStackAPI<?>> stacks) {
        if(Objects.isNull(arg)) {
            TILRef.logError("Cannot supply future creative tab with null arg!");
            return;
        }
        FabricItemGroupEntries entries = (FabricItemGroupEntries)arg;
        this.suppliedItems.clear();
        for(ItemStackAPI<?> api : stacks) {
            ItemStack stack = api.unwrap();
            entries.accept(stack);
            this.suppliedItems.add(stack);
        }
    }
}