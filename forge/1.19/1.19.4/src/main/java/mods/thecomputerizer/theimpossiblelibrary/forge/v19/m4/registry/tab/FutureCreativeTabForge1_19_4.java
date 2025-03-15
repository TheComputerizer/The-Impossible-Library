package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.FutureCreativeTab;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent.BuildContents;
import net.minecraftforge.event.CreativeModeTabEvent.Register;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class FutureCreativeTabForge1_19_4 extends FutureCreativeTab<CreativeModeTab> {
    
    private final List<ItemStack> suppliedItems = new ArrayList<>();
    
    public FutureCreativeTabForge1_19_4(ResourceLocationAPI<?> registryName) {
        super(registryName);
    }
    
    /**
     * arg = CreativeModeTabEvent.Register
     */
    @Override public void register(@Nullable Object arg) {
        if(Objects.isNull(arg) || Objects.isNull(this.registryName)) {
            TILRef.logError("Cannot register future creative tab with null arg or registryName!");
            return;
        }
        Register event = (Register)arg;
        ResourceLocation name = this.registryName.unwrap();
        this.wrapped = event.registerCreativeModeTab(name,builder -> {
            if(Objects.nonNull(this.iconSupplier)) builder.icon(() -> (ItemStack)this.iconSupplier.get());
            builder.title(TextHelper.getTranslated("itemGroup."+name.getPath()).getAsComponent())
                    .displayItems((args,output) -> {
                        for(ItemStack stack : this.suppliedItems) output.accept(stack);
                    }).build();
        });
        this.registered = true;
    }
    
    /**
     * arg = CreativeModeTabEvent.BuildContents
     */
    @Override public void supply(@Nullable Object arg, List<Supplier<ItemStackAPI<?>>> stackSuppliers) {
        if(Objects.isNull(arg)) {
            TILRef.logError("Cannot supply future creative tab with null arg!");
            return;
        }
        BuildContents event = (BuildContents)arg;
        if(event.getTab()!=this.wrapped) return;
        this.suppliedItems.clear();
        for(Supplier<ItemStackAPI<?>> supplier : stackSuppliers) {
            ItemStack stack = supplier.get().unwrap();
            event.accept(stack);
            this.suppliedItems.add(stack);
        }
    }
}