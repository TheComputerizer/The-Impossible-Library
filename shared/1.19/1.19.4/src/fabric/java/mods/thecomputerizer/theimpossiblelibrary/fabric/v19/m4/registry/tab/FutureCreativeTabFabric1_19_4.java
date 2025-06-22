package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab.CreativeTabBuilder1_19_4;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class FutureCreativeTabFabric1_19_4 extends FutureCreativeTab<CreativeModeTab> {
    
    private static final String VISIBILITY = "net.minecraft.world.item.CreativeModeTab.TabVisibility";
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    private final Object both;
    private final Object parent;
    private final Object search;
    
    public FutureCreativeTabFabric1_19_4(ResourceLocationAPI<?> registryName) {
        super(registryName);
        this.both = Hacks.getFieldStaticDirect(VISIBILITY,"PARENT_AND_SEARCH_TABS","field_40191");
        this.parent = Hacks.getFieldStaticDirect(VISIBILITY,"PARENT_TAB_ONLY","field_40192");
        this.search = Hacks.getFieldStaticDirect(VISIBILITY,"SEARCH_TAB_ONLY","field_40193");
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
        Builder builder = Hacks.invokeStatic(FabricItemGroup.class,"builder",name);
        builder = builder.title(TextHelper.getTranslated("itemGroup."+name.getPath()).getAsComponent());
        if(Objects.nonNull(this.iconSupplier)) builder.icon(() -> (ItemStack)this.iconSupplier.get());
        DisplayItemsGenerator generator = ClassHelper.newGenericProxy(DisplayItemsGenerator.class,"accept",
                args -> {
                    for(ItemStack stack : this.suppliedItems)
                        Hacks.invokeDirect(args[1],"accept","method_45417",stack);
                    return null;
                });
        this.wrapped = builder.displayItems(generator).build();
        Event<ModifyEntries> event = Hacks.invokeStatic(ItemGroupEvents.class,"modifyEntriesEvent",this.wrapped);
        event.register(CreativeTabBuilder1_19_4::onSupply);
        this.registered = true;
    }
    
    /**
     * arg = FabricItemGroupEntries
     */
    @SuppressWarnings("UnstableApiUsage")
    @Override public void supply(@Nullable Object arg, List<Supplier<ItemStackAPI<?>>> stackSuppliers) {
        if(Objects.isNull(arg)) {
            TILRef.logError("Cannot supply future creative tab with null arg!");
            return;
        }
        
        FabricItemGroupEntries entries = (FabricItemGroupEntries)arg;
        for(Supplier<ItemStackAPI<?>> supplier : stackSuppliers) {
            ItemStack stack = supplier.get().unwrap();
            Object visibility = visibility(Hacks.invoke(entries,"getDisplayStacks"),
                                           Hacks.invoke(entries,"getSearchTabStacks"),stack);
            if(Objects.nonNull(visibility))
                Hacks.invokeDirect(entries,"accept","method_45417",stack,visibility);
            if(!this.suppliedItems.contains(stack)) this.suppliedItems.add(stack);
        }
        stackSuppliers.clear();
    }
    
    /**
     * Check if the stack exists before adding to the tab
     */
    private @Nullable Object visibility(List<?> display, List<?> search, ItemStack stack) {
        if(display.contains(stack)) return search.contains(stack) ? this.search : null; //field_40193
        if(search.contains(stack)) return display.contains(stack) ? this.parent : null; //field_40192
        return this.both; //field_40191
    }
}