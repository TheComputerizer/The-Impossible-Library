package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.minecraft.core.registries.BuiltInRegistries.CREATIVE_MODE_TAB;

public class FutureCreativeTabFabric1_20 extends FutureCreativeTab<CreativeModeTab> {
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    
    public FutureCreativeTabFabric1_20(ResourceLocationAPI<?> registryName) {
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
        Builder builder = FabricItemGroup.builder()
                .title(TextHelper.getTranslated("itemGroup."+name.getPath()).getAsComponent());
        if(Objects.nonNull(this.iconSupplier)) builder.icon(() -> (ItemStack)this.iconSupplier.get());
        this.wrapped = Registry.register(CREATIVE_MODE_TAB,name,
                                         builder.displayItems((args,output) -> {
                                             for(ItemStack stack : this.suppliedItems) output.accept(stack);
                                         }).build());
        ResourceKey<CreativeModeTab> key = CREATIVE_MODE_TAB.getResourceKey(this.wrapped).orElse(null);
        if(Objects.nonNull(key)) ItemGroupEvents.modifyEntriesEvent(key).register(CreativeTabBuilder1_20::onSupply);
        else TILRef.logError("ResourceKey for CreativeModeTab was somehow null after getting registered?");
        this.registered = true;
    }
    
    /**
     * arg = FabricItemGroupEntries
     */
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